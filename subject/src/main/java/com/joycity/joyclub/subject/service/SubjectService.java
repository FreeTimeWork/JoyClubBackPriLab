package com.joycity.joyclub.subject.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.subject.modal.generated.SubjectType;
import com.joycity.joyclub.subject.modal.generated.SubjectWithBLOBs;

public interface SubjectService {

    ResultData createSubject(SubjectWithBLOBs subjectWithBLOBs);

    ResultData getSubjects(PageUtil pageUtil);

    ResultData getSubjectDetail(Long id);

    ResultData getSubjectTypes();

    ResultData updateSubjectType(SubjectType subjectType);

}
