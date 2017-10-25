package com.joycity.joyclub.subject.mapper;

import com.joycity.joyclub.commons.mapper.BaseMapperWithBLOBS;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.subject.modal.SubjectDetail;
import com.joycity.joyclub.subject.modal.SubjectWithType;
import com.joycity.joyclub.subject.modal.generated.Subject;
import com.joycity.joyclub.subject.modal.generated.SubjectExample;
import com.joycity.joyclub.subject.modal.generated.SubjectWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SubjectMapper extends BaseMapperWithBLOBS<Subject, SubjectWithBLOBs, Long, SubjectExample> {

    List<SubjectWithType> getSubjectList(@Param("pageUtil") PageUtil pageUtil);

    Long countSubjectList();

    @Select("select * from subject where id = #{id}")
    SubjectDetail selectSubjectById(@Param("id") Long id);
}
