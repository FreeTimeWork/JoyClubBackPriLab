package com.joycity.joyclub.subject.modal;

import com.joycity.joyclub.subject.modal.generated.SubjectWithBLOBs;

/**
 * @auther fangchen.chai ON 2017/10/25
 */
public class SubjectWithType extends SubjectWithBLOBs {

    private String subjectTypeName;

    public String getSubjectTypeName() {
        return subjectTypeName;
    }

    public void setSubjectTypeName(String subjectTypeName) {
        this.subjectTypeName = subjectTypeName;
    }
}
