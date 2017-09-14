package com.joycity.joyclub.act.service.impl;

import com.joycity.joyclub.act.mapper.FrontApplyActMapper;
import com.joycity.joyclub.act.modal.generated.FrontApplyAct;
import com.joycity.joyclub.act.service.FrontApplyActService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/9/14
 */
@Service
public class FrontApplyActServiceImpl implements FrontApplyActService {

    @Autowired
    private FrontApplyActMapper applyActMapper;

    @Override
    public ResultData createApplyAct(FrontApplyAct applyAct) {

        applyActMapper.insertSelective(applyAct);
        return new ResultData(new CreateResult(applyAct.getId()));
    }

    @Override
    public ResultData getListApplyAct(Byte reviewStatus, PageUtil pageUtil) {
        return new AbstractGetListData<FrontApplyAct>() {
            @Override
            public Long countByFilter() {
                return applyActMapper.countList(reviewStatus);
            }

            @Override
            public List<FrontApplyAct> selectByFilter() {
                return applyActMapper.selectList(reviewStatus,pageUtil);
            }
        }.getList(pageUtil);
    }

    @Override
    public ResultData permitApplyAct(Long id) {
        Integer num = applyActMapper.updateReviewApplyAct(id, new Byte("1"),null);
        return new ResultData(new UpdateResult(num));
    }

    @Override
    public ResultData rejectApplyAct(Long id,String reviewInfo) {
        Integer num = applyActMapper.updateReviewApplyAct(id, new Byte("2"), reviewInfo);
        return new ResultData(new UpdateResult(num));
    }


}
