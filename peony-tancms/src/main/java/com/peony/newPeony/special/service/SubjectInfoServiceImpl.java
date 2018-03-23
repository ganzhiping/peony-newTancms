package com.peony.newPeony.special.service;

import com.peony.newPeony.special.dao.SubjectInfoMapper;
import com.peony.newPeony.special.model.SubjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */
@Service
public class SubjectInfoServiceImpl implements SubjectInfoService {
    @Autowired
    private SubjectInfoMapper subjectInfoMapper;

    @Override
    public SubjectInfo selectBySubjectId(Integer subjectId) {
        return subjectInfoMapper.selectByPrimaryKey(subjectId);
    }

    @Override
    public void insertSubjectSpecial(SubjectInfo subjectInfo) {
        subjectInfoMapper.insertSubjectSpecial(subjectInfo);
    }

    @Override
    public void changeSpecialName(SubjectInfo subject) {
        subjectInfoMapper.reNameSubject(subject);
    }

    @Override
    public List<SubjectInfo> selectSubjectsByParentSubject(SubjectInfo subject) {
        List<SubjectInfo> list = subjectInfoMapper.selectSubjectidsBysubjectInfo(subject);
        return list;
    }

    @Override
    public void deleteSubject(SubjectInfo tmp) {
        subjectInfoMapper.delbySubjectid(tmp);
    }

    @Override
    public void saveSubjectParent(SubjectInfo subjectInfo) {
        subjectInfoMapper.insertToSubjectParent(subjectInfo);
    }

    @Override
    public String selectIsChildeNode(Integer pid) {
        String ret = subjectInfoMapper.selectIsChildeNode((int)pid);
        return ret;
    }

    @Override
    public void updateChildsNode(int subjectId) {
        subjectInfoMapper.updateChildsNode(subjectId);
    }
}
