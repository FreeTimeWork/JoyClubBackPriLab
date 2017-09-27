package com.joycity.joyclub.act.service.impl;

import com.joycity.joyclub.act.mapper.FrontApplyActMapper;
import com.joycity.joyclub.act.mapper.FrontApplyActTypeMapper;
import com.joycity.joyclub.act.modal.generated.FrontApplyAct;
import com.joycity.joyclub.act.modal.generated.FrontApplyActType;
import com.joycity.joyclub.act.service.FrontApplyActService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.commons.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private FrontApplyActTypeMapper applyActTypeMapper;

    @Override
    public ResultData createApplyAct(FrontApplyAct applyAct) {

        applyActMapper.insertSelective(applyAct);
        return new ResultData(new CreateResult(applyAct.getId()));
    }

    @Override
    public ResultData getApplyActById(Long id) {
        return new ResultData(applyActMapper.selectApplyActById(id));
    }

    @Override
    public ResultData getListApplyAct(Byte reviewStatus,String name, PageUtil pageUtil) {
        String finalName = SqlUtil.getLikeStr(name);
        return new AbstractGetListData<FrontApplyAct>() {
            @Override
            public Long countByFilter() {
                return applyActMapper.countList(reviewStatus, finalName);
            }

            @Override
            public List<FrontApplyAct> selectByFilter() {
                return applyActMapper.selectList(reviewStatus, finalName,pageUtil);
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

    @Override
    public ResultData getEffListApplyAct(String name,PageUtil pageUtil) {
        String finalName = SqlUtil.getLikeStr(name);
        return new AbstractGetListData<FrontApplyAct>() {
            @Override
            public Long countByFilter() {
                return applyActMapper.countEffList(finalName);
            }

            @Override
            public List<FrontApplyAct> selectByFilter() {
                return applyActMapper.selectEffList(finalName,pageUtil);
            }
        }.getList(pageUtil);
    }

    @Override
    public ResultData createApplyActType(FrontApplyActType applyActType) {
        applyActTypeMapper.insertSelective(applyActType);
        return new ResultData(new CreateResult(applyActType.getId()));
    }

    @Override
    public ResultData getListApplyActType() {
        return new ResultData(new ListResult(applyActTypeMapper.selectList()));
    }

    @Override
    public ResultData deleteApplyActType(Long id) {
        int num = applyActTypeMapper.deleteByPrimaryKey(id);
        return new ResultData(new UpdateResult(num));
    }

    @Override
    public ResultData updateApplyActType(FrontApplyActType applyActType) {
        int num = applyActTypeMapper.updateByPrimaryKeySelective(applyActType);
        return new ResultData(new UpdateResult(num));
    }

    @Override
    public ResultData getApplyActTypeById(Long id) {
        return new ResultData(applyActTypeMapper.selectByPrimaryKey(id));
    }


}
