package com.joycity.joyclub.act.mapper;

import com.joycity.joyclub.act.modal.generated.FrontApplyAct;
import com.joycity.joyclub.act.modal.generated.FrontApplyActExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/9/14
 */
public interface FrontApplyActMapper extends BaseMapper<FrontApplyAct,Long,FrontApplyActExample> {

    Long countList(@Param("reviewStatus") Byte reviewStatus);
    List<FrontApplyAct> selectList(@Param("reviewStatus") Byte reviewStatus, @Param("pageUtil") PageUtil pageUtil);

    int updateReviewApplyAct(@Param("id") Long id, @Param("reviewStatus") Byte reviewStatus, @Param("reviewInfo") String reviewInfo);
}
