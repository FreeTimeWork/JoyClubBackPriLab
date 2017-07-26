package com.joycity.joyclub.card_coupon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.joycity.joyclub.card_coupon.mapper.ShopMapper;
import com.joycity.joyclub.card_coupon.modal.MallcooShop;
import com.joycity.joyclub.card_coupon.modal.generated.SysShop;
import com.joycity.joyclub.card_coupon.service.ShopService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.AbstractBatchInsertlUtils;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.mallcoo.service.MallCooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ResultData batchInsertOrUpdate(List<MallcooShop> shops, Long projectId) {
        return new ResultData(new AbstractBatchInsertlUtils() {

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
                return "name = VALUES (name), logo = VALUES (logo), shop_type = VALUES (shop_type), " +
                        "commercial_type_id = VALUES (commercial_type_id) ," +
                        "sub_commercial_type_name = VALUES (sub_commercial_type_name)," +
                        "floor_id = VALUES (floor_id), floor_name = VALUES (floor_name), door_no = VALUES (door_no)";
            }

            @Override
            public String getValues(int index) {
                MallcooShop shop = shops.get(index);
                StringBuilder builder = new StringBuilder();
                builder.append("(")
                .append("'"+projectId+"', ")
                .append("'"+shop.getCrmShopId()+"', ")
                .append("'"+shop.getName()+"', ")
                .append("'"+shop.getLogo()+"', ")
                .append("'"+shop.getShopType()+"', ")
                .append("'"+shop.getCommercialTypeId()+"', ")
                .append("'"+shop.getSubCommercialTypeName()+"', ")
                .append("'"+shop.getFloorId()+"', ")
                .append("'"+shop.getFloorName()+"', ")
                .append("'"+shop.getDoorNo()+"'")
                .append(") ")
                ;

                return builder.toString();
            }
        }.batchInsert(shops.size()));
    }


    @Override
    public ResultData syncMallCooShop(Long projectId) {
        ResultData resultData = new ResultData();
//        Map map = mallcooService.getShops(projectId);
        Map<String, String> map = new HashMap();
        map.put("Data", "  [\n" +
                "      {\n" +
                "        \"ID\": 1000030,\n" +
                "        \"Name\": \"VERO MODA\",\n" +
                "        \"CrmShopID\": \"454524\",\n" +
                "        \"Logo\": \"http://i1.mallcoo.cn/sp_mall/82de37a0-db14-4c23-aca9-58900758c2e7.jpg\",\n" +
                "        \"ShopType\": 1,\n" +
                "        \"CommercialTypeID\": 14024,\n" +
                "        \"SubCommercialTypeName\": \"服饰01\",\n" +
                "        \"FloorID\": 0,\n" +
                "        \"FloorName\": \"1F\",\n" +
                "        \"DoorNo\": null,\n" +
                "        \"UpdateTime\": \"0001-01-01 00:00:00\"\n" +
                "      },\n" +
                "\t  {\n" +
                "        \"ID\": 1000031,\n" +
                "        \"Name\": \"VERO MODA\",\n" +
                "        \"CrmShopID\": \"454525\",\n" +
                "        \"Logo\": \"http://i1.mallcoo.cn/sp_mall/82de37a0-db14-4c23-aca9-58900758c2e7.jpg\",\n" +
                "        \"ShopType\": 1,\n" +
                "        \"CommercialTypeID\": 14024,\n" +
                "        \"SubCommercialTypeName\": \"服饰01\",\n" +
                "        \"FloorID\": 0,\n" +
                "        \"FloorName\": \"1F\",\n" +
                "        \"DoorNo\": null,\n" +
                "        \"UpdateTime\": \"0001-01-01 00:00:00\"\n" +
                "      }\n" +
                "  ]");
        List<MallcooShop> shops = JSONObject.parseArray(map.get("Data").toString(), MallcooShop.class);

        ResultData result = batchInsertOrUpdate(shops, projectId);
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
        return new ResultData(new ListResult(shopMapper.selectShopsByNameAndCode(projectId, name, code)));
    }

}
