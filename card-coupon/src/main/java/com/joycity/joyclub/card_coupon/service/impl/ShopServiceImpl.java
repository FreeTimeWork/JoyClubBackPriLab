package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.card_coupon.mapper.ShopMapper;
import com.joycity.joyclub.card_coupon.modal.generated.SysShop;
import com.joycity.joyclub.card_coupon.modal.generated.SysShopExample;
import com.joycity.joyclub.card_coupon.service.ShopService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.AbstractBatchInsertlUtils;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.mallcoo.modal.result.data.OffLineShopInfo;
import com.joycity.joyclub.mallcoo.service.MallCooService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private MallCooService mallcooService;

    @Override
    public ResultData batchInsertOrUpdate(List<OffLineShopInfo> shops, Long projectId) {
        return new ResultData(new UpdateResult(new AbstractBatchInsertlUtils() {

            @Override
            public int executeInsert(String longInsertSql) {
                return shopMapper.batchInsertShop(longInsertSql);
            }

            @Override
            public String getTableName() {
                return "sys_shop";
            }

            @Override
            public String getValuesNames() {
                return "(project_id, code, name, logo," +
                        "shop_type, commercial_type_id, sub_commercial_type_name," +
                        "floor_id, floor_name, door_no)";
            }

            @Override
            public Boolean ifIgnoreDuplicate() {
                return false;
            }

            @Override
            public String getUpdateSqlWhenDuplicate() {
                return "code = VALUES (code),name = VALUES (name), logo = VALUES (logo), shop_type = VALUES (shop_type), " +
                        "commercial_type_id = VALUES (commercial_type_id) ," +
                        "sub_commercial_type_name = VALUES (sub_commercial_type_name)," +
                        "floor_id = VALUES (floor_id), floor_name = VALUES (floor_name), door_no = VALUES (door_no)";
            }

            @Override
            public String getValues(int index) {
                OffLineShopInfo shop = shops.get(index);
                shop.setName(shop.getName().replaceAll("'", "\\\\'"));

                StringBuilder builder = new StringBuilder();
                builder.append("(")
                        .append("'"+projectId+"', ")
                        .append("'"+shop.getCrmShopID()+"', ")
                        .append("'"+shop.getName()+"', ")
                        .append("'"+shop.getLogo()+"', ")
                        .append("'"+shop.getShopType()+"', ")
                        .append("'"+shop.getCommercialTypeID()+"', ")
                        .append("'"+shop.getSubCommercialTypeName()+"', ")
                        .append("'"+shop.getFloorID()+"', ")
                        .append("'"+shop.getFloorName()+"', ")
                        .append("'"+shop.getDoorNo()+"'")
                        .append(") ")
                ;

                return builder.toString();
            }
        }.batchInsert(shops.size())));
    }


    @Override
    public ResultData syncMallCooShop(Long projectId) {
        ResultData resultData = new ResultData();
        List<OffLineShopInfo> offLineShopInfos = mallcooService.getShops(projectId);
        ResultData result = batchInsertOrUpdate(offLineShopInfos, projectId);
        resultData.setData(result.getData());
        return resultData;
    }

    @Override
    public ResultData getListByCodeAndSubCommercial(Long projectId, String code, String name, PageUtil pageUtil) {

        if (name != null) {
            name = "%" + name + "%";
        }
        final String  finalName = name;

        return new AbstractGetListData<SysShop>() {
            @Override
            public Long countByFilter() {
                return shopMapper.countShopByCodeAndSubCommercial(projectId, code, finalName, pageUtil);
            }

            @Override
            public List<SysShop> selectByFilter() {
                return shopMapper.selectShopByCodeAndSubCommercial(projectId, code, finalName, pageUtil);
            }
        }.getList(pageUtil);
    }

    @Override
    public ResultData getShopsGroupBySubCommercial(Long projectId) {
        return new ResultData(new ListResult(shopMapper.selectShopGroupBySubCommercial(projectId)));
    }

    @Override
    public ResultData getAllShopByNameAndCode(Long projectId, String name, String code) {
        if (name != null) {
            name = "%" + name + "%";
        }
        return new ResultData(new ListResult(shopMapper.selectShopByNameAndCode(projectId, name, code)));
    }

    @Override
    public SysShop getShopByProjectIdAndCode(Long projectId, String code) {
        SysShopExample example = new SysShopExample();
        SysShopExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        criteria.andCodeEqualTo(code);
        List<SysShop> sysShops = shopMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(sysShops)) {
            return sysShops.get(0);
        }
        return null;
    }

}
