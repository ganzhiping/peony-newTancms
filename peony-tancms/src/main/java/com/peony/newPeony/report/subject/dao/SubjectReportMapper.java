package com.peony.newPeony.report.subject.dao;

import com.peony.newPeony.report.subject.model.SubjectUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */
public interface SubjectReportMapper {
    List<SubjectUtil> selectSubjectDateType(SubjectUtil util);
    List<SubjectUtil> selectSubjectHourType(SubjectUtil util);
}
