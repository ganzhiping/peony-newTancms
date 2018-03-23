package com.peony.newPeony.opinionMon.service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.JSONObject;
import com.peony.newPeony.opinionMon.dao.ConcernMapper;
import com.peony.newPeony.opinionMon.dao.OpinionMonMapper;
import com.peony.newPeony.opinionMon.dao.QueryRequireMapper;
import com.peony.newPeony.opinionMon.model.Concern;
import com.peony.newPeony.opinionMon.model.OpinionCondition;
import com.peony.newPeony.opinionMon.model.QueryRequire;
import com.peony.newPeony.util.NewPeonyUtil;
import com.peony.peonyfront.subject.model.Subject;
import com.peony.peonyfront.subject.service.SubjectService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gzp on 2017/8/16.
 */
@Service
public class OpinionMonServiceImpl implements OpinionMonService {
    @Autowired
    private OpinionMonMapper opinionMonMapper;
    @Autowired
    private QueryRequireMapper queryRequireMapper;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ConcernMapper concernMapper;
    /** 境内 */
    private final static String    NEW_LEVEL_JN = "'0','3','4','5'";
    /**
     * 境外
     */
    private final static String NEW_LEVEL_JW = "1,2";
    private final Log logger       = LogFactory.getLog(OpinionMonServiceImpl.class);
    @Override
    public List<OpinionCondition> findByPage(OpinionCondition opinionCondition) {
        List<OpinionCondition> pagination = opinionMonMapper.selectByPage(opinionCondition);
        return pagination;
    }

    /**
     * 总览页   信息查询
     * @param userId
     * @return
     */
    @Override
    public List<OpinionCondition> findOverViewTop(int userId) {
        OpinionCondition op = new OpinionCondition();
        op.setUserid(userId);
        op.setStartTime(startTime());
        op.setPolarity(-1);
        op.setNewslevelConditions("'0','3','4','5'");
        List<OpinionCondition> list = opinionMonMapper.selectByOverViewTop(op);
        return changeBackTime(NewPeonyUtil.removeDuplicateByTitle(list));
    }
    @Override
    public List<OpinionCondition> selectByExportExecle(OpinionCondition opinionCondition) {
        return opinionMonMapper.selectByExportExecle(opinionCondition);
    }

    @Override
    public QueryRequire selectQueryRequireByUesrId(int userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        QueryRequire qu = queryRequireMapper.selectByUserId(userId);
        try{
            if(qu!=null){
                qu.setStartTime(sdf.format(sdf.parse(qu.getStartTime())));
                qu.setEndTime(sdf.format(sdf.parse(qu.getEndTime())));
            }
        }catch (Exception e ){
            logger.info("error》》OpinionMonServiceImpl》》》selectQueryRequireByUesrId》》SimpleDateFormat");
            e.printStackTrace();
        }
        return qu;
    }

    @Override
    public int updateOrInsertQuery(QueryRequire require) {
       /* String[] str = choseTime(String.valueOf(require.getDataType()));
        require.setEndTime(str[0]);
        require.setStartTime(str[1]);*/
        return queryRequireMapper.insertOrUpdate(require);
    }

    @Override
    public OpinionCondition selectByPageId(String id, int userId,int subjectid) {
        OpinionCondition op  = new OpinionCondition();
        op.setPageid(id);
        op.setUserid(userId);
        op.setSubjectid(subjectid);
        return opinionMonMapper.selectByPageIdOnPo(op);
    }

    @Override
    public String changeConcernValue(Concern concern) {
        Concern con = concernMapper.selectByConcern(concern);
        if(con!=null&&!"".equals(con)&&con.getPageid()!=null&&!"".equals(con.getPageid())){
            concernMapper.deleteConcern(concern);
            return "delete";
        }else{
            concernMapper.insertIntoConcern(concern);
            return "insert";
        }
    }

    @Override
    public OpinionCondition selectPageById(String id) {
        return opinionMonMapper.selectByPageId(id);
    }

    @Override
    public int batchUpdate(OpinionCondition subjectPage) {
        return this.opinionMonMapper.updateState(subjectPage);
    }

    @Override
    public OpinionCondition selectPageNo(OpinionCondition opinionCondition) {
        return this.opinionMonMapper.selectPageCount(opinionCondition);
    }

    public String startTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,-24);
        String ret = sdf.format(calendar.getTime());
        return ret;
    }

    public  List<OpinionCondition> changeBackTime(List<OpinionCondition> list){
        List<OpinionCondition> listNew = new ArrayList<OpinionCondition>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long nowTime = System.currentTimeMillis();
        for (OpinionCondition op : list) {
            long writeTime = op.getPublishdate().getTime();
            int topTime = (int)((nowTime - writeTime)/(1000*60*60));
            if(topTime>0){
                op.setTopTime(topTime+"小时前");
            }else{
                op.setTopTime("1小时内");
            }
            listNew.add(op);
        }
        return listNew;
    }


    /**
     * 查询条件
     * @param request
     * @param queryRequire
     * @return
     */
    @Override
    public OpinionCondition queryCriteria(HttpServletRequest request, int userId, OpinionCondition op, QueryRequire queryRequire) {
        String timeType=null;
        if(queryRequire!=null &&!"".equals(queryRequire)){
           timeType = String.valueOf(queryRequire.getDataType());
           if("6".equals(timeType)||(timeType==null && !"0".equals(timeType)&& !"1".equals(timeType)&& !"2".equals(timeType)&& !"3".equals(timeType)&& !"4".equals(timeType)&& !"5".equals(timeType))){
               if(queryRequire.getEndTime() != null&&!"".equals(queryRequire.getEndTime())){
                   op.setEndTime(queryRequire.getEndTime());
                   op.setStartTime(queryRequire.getStartTime());
               }else{
                   String[] times = choseTime(timeType);
                   op.setEndTime(times[0]);
                   op.setStartTime(times[1]);
               }
           }else{
               String[] times = choseTime(timeType);
               op.setEndTime(times[0]);
               op.setStartTime(times[1]);
           }
           /* op.setIsRead(queryRequire.getIsRead());*/
           if(queryRequire.getPolarity()==2){
                op.setPolarity(1);
           }else if(queryRequire.getPolarity()==1){
               op.setPolarity(-1);
           }else if(queryRequire.getPolarity()==0){
               op.setPolarity(0);
           }
            queryRequire.getUserid();
            op.setSiteLevel(queryRequire.getWeblevel());
            op.setType(queryRequire.getSource());
        }else{
            String[] times = choseTime(timeType);
            op.setEndTime(times[0]);
            op.setStartTime(times[1]);
        }
       /* String[] times = choseTime(null);
        op.setEndTime(times[0]);
        op.setStartTime(times[1]);*/
       op.setNewslevelConditions(NEW_LEVEL_JN);
        Subject subjectInfo = new Subject();
        String sbId = request.getParameter("subjectid");
        if(sbId==null||"".equals(sbId)){
            sbId="1";
        }
        if ("1".equals(sbId)&&sbId!=null) {
            subjectInfo.setUserid(userId);
            List<Subject> list = this.subjectService.selectSubjectByUserIdExclusive(subjectInfo);
            String subjectidArray = "";
            for (int i = 0; i < list.size(); i++) {
                if (StringUtils.isBlank(subjectidArray)) {
                    subjectidArray = list.get(i).getId().toString();
                } else {
                    subjectidArray = subjectidArray + "," + list.get(i).getId().toString();
                }
            }
            op.setSubjectidArray(subjectidArray);
            op.setSubjectid(1);
        } else {
            subjectInfo.setUserid(userId);
            subjectInfo.setPid(Integer.parseInt(request.getParameter("subjectid")));
            List<Subject> list = this.subjectService.selectSubjectByUserIdAndPid(subjectInfo);
            String subjectidArray = request.getParameter("subjectid");
            Queue<Integer> fifoQueue = new LinkedList<Integer>();
            for (Subject subj : list) {
                // 记录当前Id
                subjectidArray = subjectidArray + "," + subj.getId();
                // 记录父类节点id
                if ("0".equals(subj.getIschildnodes())) {
                    fifoQueue.offer(subj.getId());
                }
            }
            while (!fifoQueue.isEmpty()) {
                Integer pid = fifoQueue.poll();
                Subject tempSubject = new Subject();
                tempSubject.setIschildnodes(null);
                tempSubject.setUserid(userId);
                tempSubject.setPid(pid);
                List<Subject> tempSubjectList = this.subjectService.selectSubjectByUserIdAndPid(tempSubject);
                for (Subject tempSubj : tempSubjectList) {
                    subjectidArray = subjectidArray + "," + tempSubj.getId();
                    if ("0".equals(tempSubj.getIschildnodes())) {
                        fifoQueue.offer(tempSubj.getId());
                    }
                }
            }
            op.setSubjectidArray(subjectidArray);
            op.setSubjectid(Integer.parseInt(request.getParameter("subjectid")));
        }
        op.setNewslevelConditions(NEW_LEVEL_JN);
        return op;
    }

    /**
     * 设置时间
     * @param str
     * @return
     */
    private String[] choseTime(String str) {
        String[] retStr = new String[2];
        Calendar cal = Calendar.getInstance();
        Date startTime = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //结束时间
        retStr[0] = sdf.format(startTime);
        Date endDate = new Date();
        //开始时间计算
        //今天 12小时内
        if("1".equals(str)){
            cal.add(Calendar.HOUR_OF_DAY,-12);
            endDate = cal.getTime();
        }
        //24小时内 即 一天内
        if("2".equals(str)){
            cal.add(Calendar.DATE,-1);
            endDate = cal.getTime();
        }
        //2天内
        if("3".equals(str)||str==null || "".equals(str)){
            cal.add(Calendar.DATE,-2);
            endDate = cal.getTime();
        }
        //一周内
        if("4".equals(str)){
            cal.add(Calendar.DATE,-7);
            endDate = cal.getTime();
        }
        //一个月内
        if("5".equals(str)){
            cal.add(Calendar.MONTH,-1);
            endDate = cal.getTime();
        }
        if("0".equals(str)){
            cal.add(Calendar.MONTH,-1);
            endDate = cal.getTime();
        }
        retStr[1] = sdf.format(endDate);
        return retStr;
    }

    /**
     * 文章详情页  关键字和作者
     * @param str
     * @return
     */
    @Override
    public String keywordsAndAuthor(String str){
        String ret = "";
        String res = "";
        try {
            JSONObject ob = (JSONObject) JSON.parse(str);
            JSONArray val1 = (JSONArray) ob.get("docs");
            JSONObject url = (JSONObject) val1.get(0);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> m = mapper.readValue(JSON.json(url), Map.class);
           /* JSONObject key = (JSONObject) url.get("keywords");
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> m = mapper.readValue(JSON.json(key), Map.class);
            Map<String, Object> stringObjectMap = sortMap(m);
            Set<Map.Entry<String, Object>> entries = stringObjectMap.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            int num = 0;
            if (entries.size() > 5) {
                num = 5;
            }
            int i = 0;
            while (iterator.hasNext()) {
                if (i >= num) {
                    break;
                }
                Map.Entry<String, Object> next = iterator.next();
                String key1 = next.getKey();
                res += key1 + " ";
                i++;
            }
            ret[0] = res.substring(0, res.length() - 1);*/
            Object author = m.get("author");
            ret = (author==null?"":author.toString()==""?"":"".equals(author.toString())?"":author.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public Map<String,List<OpinionCondition>> selectRouteReport(int userId, int subjectid) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Map<String,List<OpinionCondition>> map = new HashMap<String,List<OpinionCondition>>();
        for(int i =4;i>=0;i--){
            OpinionCondition op = new OpinionCondition();
            op.setUserid(userId);
            op.setSubjectid(subjectid);

            String format = sdf.format(calendar.getTime());
            op.setTimeStr(format);
            op.setState(0);
            List<OpinionCondition> list = opinionMonMapper.selectRouteReport(op);
            map.put(format,list);
            calendar.add(Calendar.DATE,-1);
        }
        Map<String,List<OpinionCondition>> map1 = sortMapKey(map);
        return map1;
    }

    @Override
    public List<OpinionCondition> selectBySearchValue(OpinionCondition op) {
        List<OpinionCondition>  list = opinionMonMapper.selectByPage(op);

        return list;
    }

    @Override
    public String selectSearchTotal(OpinionCondition op) {
        String total = opinionMonMapper.selectSearchTotal(op);
        return total;
    }

    @Override
    public String insertIntoProlarity(OpinionCondition op) {
        String id = opinionMonMapper.selectProparity(op);
        if(id==null||id=="0"){
            opinionMonMapper.insertProparity(op);
        }else{
            op.setP_id(Integer.valueOf(id));
            opinionMonMapper.updateProparity(op);
        }
        opinionMonMapper.updateSubject_pagePolarity(op);
        return null;
    }


    /**
     * 对map进行value排序(从大到小排序)
     *
     * @param oldMap
     * @return
     */
    public Map<String, List<OpinionCondition>> sortMapKey(Map<String, List<OpinionCondition>> oldMap) {

        ArrayList<Map.Entry<String, List<OpinionCondition>>> list = new ArrayList<Map.Entry<String, List<OpinionCondition>>>(oldMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, List<OpinionCondition>>>() {
            @Override
            public int compare(Map.Entry<String, List<OpinionCondition>> arg0, Map.Entry<String, List<OpinionCondition>> arg1) {
                String k1 = arg0.getKey().toString();
                String k2 = arg1.getKey().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                int a = 0;
                try{
                    long a0 = sdf.parse(k1).getTime();
                    long a1 = sdf.parse(k2).getTime();
                    a = Integer.valueOf(String.valueOf(a0-a1));
                }catch (Exception e){
                    e.printStackTrace();
                }

                return a;
            }
        });
        Map<String, List<OpinionCondition>> newMap = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            newMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return newMap;
    }

    /**
     * 对map进行value排序(从大到小排序)
     * @param oldMap
     * @return
     */
    public  Map<String, Object> sortMap(Map<String, Object> oldMap) {
        ArrayList<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(oldMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> arg0, Map.Entry<String, Object> arg1) {
                int a0 = Integer.valueOf(arg0.getValue().toString());
                int a1 = Integer.valueOf(arg1.getValue().toString());
                return a1- a0;
            }
        });
        Map<String, Object> newMap = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            newMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return newMap;
    }
}
