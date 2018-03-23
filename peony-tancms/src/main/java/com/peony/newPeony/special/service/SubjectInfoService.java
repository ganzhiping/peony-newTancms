package com.peony.newPeony.special.service;

import com.peony.newPeony.special.model.SubjectInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */
public interface SubjectInfoService {
    SubjectInfo selectBySubjectId(Integer integer);

    void insertSubjectSpecial(SubjectInfo subjectInfo);

    void changeSpecialName(SubjectInfo subject);

    List<SubjectInfo> selectSubjectsByParentSubject(SubjectInfo subject);

    void deleteSubject(SubjectInfo tmp);

    void saveSubjectParent(SubjectInfo subjectInfo);

    String selectIsChildeNode(Integer pid);

    void updateChildsNode(int subjectId);
}
