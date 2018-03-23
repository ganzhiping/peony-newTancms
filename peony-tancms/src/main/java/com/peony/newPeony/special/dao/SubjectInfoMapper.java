package com.peony.newPeony.special.dao;


import com.peony.newPeony.special.model.SubjectInfo;

import java.util.List;

public interface SubjectInfoMapper {

    SubjectInfo selectByPrimaryKey(Integer subjectId);
    List<SubjectInfo> selectSubjectByUserId(int userid);
    void insertSubjectSpecial(SubjectInfo subjectInfo);

    void reNameSubject(SubjectInfo subject);

    List<SubjectInfo> selectSubjectidsBysubjectInfo(SubjectInfo subject);

    void delbySubjectid(SubjectInfo tmp);

    void insertToSubjectParent(SubjectInfo subjectInfo);

    String selectIsChildeNode(int pid);

    void updateChildsNode(int subjectId);
}