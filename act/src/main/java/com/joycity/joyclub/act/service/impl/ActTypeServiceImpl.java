package com.joycity.joyclub.act.service.impl;

import com.joycity.joyclub.act.mapper.ActMapper;
import com.joycity.joyclub.act.mapper.SaleActTypeMapper;
import com.joycity.joyclub.act.modal.ActSimple;
import com.joycity.joyclub.act.modal.ActTypeWithAct;
import com.joycity.joyclub.act.modal.generated.SaleActType;
import com.joycity.joyclub.act.service.ActTypeService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.modal.base.*;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/9/14
 * 活动的大类
 */
@Service
public class ActTypeServiceImpl implements ActTypeService {

    @Autowired
    private SaleActTypeMapper actTypeMapper;
    @Autowired
    private ActMapper actMapper;

    @Override
    public ResultData getList(PageUtil pageUtil) {

        return new AbstractGetListData<ActTypeWithAct>() {
            @Override
            public Long countByFilter() {
                return actTypeMapper.countList();
            }

            @Override
            public List<ActTypeWithAct> selectByFilter() {
                List<ActTypeWithAct> list = actTypeMapper.selectList(true, pageUtil);
                for (ActTypeWithAct item : list) {
                    List<ActSimple> actSimples = actMapper.selectSimpleList(null, null, item.getId(),false, new PageUtil());
                    item.setActs(actSimples);
                }
                return list;
            }
        }.getList(pageUtil);
    }

    @Override
    public ResultData createActType(SaleActType actType) {
        actTypeMapper.insertSelective(actType);
        return new ResultData(new CreateResult(actType.getId()));
    }

    @Override
    public ResultData deleteActType(Long id) {
        int num = actTypeMapper.deleteByPrimaryKey(id);
        return new ResultData(new UpdateResult(num));
    }

    @Override
    public List<IdName> getSaleActTypes(Long projectId) {
        return actTypeMapper.selectSaleActTypes(projectId);
    }

    @Override
    public ResultData getAllSaleActTypes(Long projectId) {
        return new ResultData(new ListResult(actTypeMapper.selectAllSaleActTypes(projectId)));
    }

    @Override
    public ResultData updateActType(SaleActType actType) {
        int num = actTypeMapper.updateByPrimaryKeySelective(actType);
        return new ResultData(new UpdateResult(num));
    }

    @Override
    public ResultData getActType(Long id) {
        return new ResultData(actTypeMapper.selectByPrimaryKey(id));
    }

}
