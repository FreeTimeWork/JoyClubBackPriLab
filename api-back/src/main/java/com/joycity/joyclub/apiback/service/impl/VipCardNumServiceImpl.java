package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SysProjectMapper;
import com.joycity.joyclub.apiback.mapper.manual.SysProjectVipCardRangeMapper;
import com.joycity.joyclub.apiback.mapper.manual.SysVipCardNumMapper;
import com.joycity.joyclub.apiback.modal.generated.SysProjectVipCardRange;
import com.joycity.joyclub.apiback.modal.generated.SysProjectVipCardRangeExample;
import com.joycity.joyclub.apiback.modal.vipcardnum.VipCardFormData;
import com.joycity.joyclub.apiback.service.VipCardNumService;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.joycity.joyclub.apiback.constant.ResultCode.VIP_CARD_MAKE_ERROR;
import static com.joycity.joyclub.commons.constants.ProjectVipCard.VIP_CARD_STATUS_NOT_USED;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
@Service
public class VipCardNumServiceImpl implements VipCardNumService {
    private Log logger = LogFactory.getLog(VipCardNumServiceImpl.class);
    @Autowired
    SysVipCardNumMapper vipCardNumMapper;
    @Autowired
    SysProjectVipCardRangeMapper vipCardRangeMapper;
    @Autowired
    SysProjectMapper projectMapper;
    /**
     * 每次制卡，生成批量插入的最大数目，极限值是11万
     * 所以制卡实际上是分成多个5w次插入操作
     */
    private final int MAX_INSERT_NUM_PER_SQL = 50000;
    /**
     * 单次制卡最大值为20万，其实可以更大
     */
    private final int MAX_INSERT_NUM_PER_TIME = 200000;

    @Override
    public ResultData getList(Long projectId, String batch, String type, Byte status, PageUtil pageUtil) {
        ListResult listResult = new ListResult();
        Long sum = vipCardNumMapper.countWithFilter(projectId, batch, type, status);
        listResult.setSum(sum);
        listResult.setByPageUtil(pageUtil);
        if (sum == 0) {
            listResult.setList(new ArrayList());
        } else {
            listResult.setList(vipCardNumMapper.selectWithFilter(projectId, batch, type, status, pageUtil));

        }
        return new ResultData(listResult);
    }

    /**
     * todo 可能会导致sql注入，加入参数验证
     *
     * @param projectId
     * @param batch
     * @param type
     * @param numNeedToMake
     * @return
     */
    @Override
    public ResultData createCardNum(Long projectId, String batch, String type, Integer numNeedToMake) {
        if (numNeedToMake > MAX_INSERT_NUM_PER_TIME) {
            throw new BusinessException(VIP_CARD_MAKE_ERROR, "最大制卡数目为20万");
        }
        //检查batch批次号是否被用
        Long existBatchNum = vipCardNumMapper.countWithFilter(null, batch, null, null);
        if (existBatchNum > 0) {
            throw new BusinessException(VIP_CARD_MAKE_ERROR, "该批次号已被使用");
        }
        //检查号段
        SysProjectVipCardRangeExample vipCardRangeExample = new SysProjectVipCardRangeExample();
        SysProjectVipCardRangeExample.Criteria criteria = vipCardRangeExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        criteria.andTypeEqualTo(type);

        List<SysProjectVipCardRange> rangelist = vipCardRangeMapper.selectByExample(vipCardRangeExample);
        //在这里把range重复也算作未设置
        if (rangelist == null || rangelist.size() != 1) {
            throw new BusinessException(VIP_CARD_MAKE_ERROR, "该项目还未设置该卡类型的号段");
        }
        //下面确定最大制卡数
        SysProjectVipCardRange range = rangelist.get(0);
        Long start = range.getMin();
        //获取当前的projectId,和type下最大id的卡
        Long maxCardNum = vipCardNumMapper.getMaxCardNum(projectId, type);
        //maxCardNum应该size为0或1
         if (maxCardNum!=null) {
            //如果该号段已经制过卡，设置起始值
            start = maxCardNum+ 1;
        }
        Long maxNum = range.getMax() - start + 1;
        //判断最大制卡数目
        if (maxNum < numNeedToMake) {
            throw new BusinessException(VIP_CARD_MAKE_ERROR, "该号段目前最多只能制卡" + maxNum + "张");
        }
        int insertNum = insertCardNums(start, numNeedToMake, projectId, batch, type);
        return new ResultData(new UpdateResult(insertNum));
    }

    @Override
    public ResultData getFormData() {
        VipCardFormData formData = new VipCardFormData();
        formData.setProjects(projectMapper.getSimpleIdNameList());
        formData.setBatches(vipCardNumMapper.getBatches());
        return new ResultData(formData);
    }

    /**
     * 批量插入操作
     * 可能会导致sql注入
     * 本地测试插入20w数据大概要10s
     *
     * @param start
     * @param numNeedToMake
     * @param projectId
     * @param batch
     * @param type
     * @return 制卡数目
     */
    private int insertCardNums(Long start, int numNeedToMake, long projectId, String batch, String type) {
        StringBuilder sb = new StringBuilder();
        String comma = ",";
        String quotes = "'";
        //拼接sql插入语句
        //当前的插入语句，测试后发现 最大11w插入，不会导致com.mysql.cj.jdbc.exceptions.PacketTooBigException( 4194304)
        //实际大约限制在10w
        Long cardNum = start;
        //j用来控制拼的sql语句最大值，目前设置为最多拼5w个插入
        int insertNum = 0;
        for (int i = 0, j = 0; i < numNeedToMake; i++) {
            //这里拼sql,可能会导致sql注入
            sb.append("(").append(cardNum).
                    append(comma).append(projectId)
                    .append(comma).append(quotes).append(batch).append(quotes)
                    .append(comma).append(type)
                    .append(comma).append(quotes).append(VIP_CARD_STATUS_NOT_USED).append(quotes).append(")");
            //加值之间的分隔符
            if (j != MAX_INSERT_NUM_PER_SQL && i != numNeedToMake - 1) {
                sb.append(comma);
            }
            //判断是否达到单次最大插入数或者达到数据末尾
            if (j == MAX_INSERT_NUM_PER_SQL || i == numNeedToMake - 1) {
                //插入并记录
                insertNum += insertCardNumValues(sb.toString());
                //清空stringBuilder,重置j
                sb.setLength(0);
                j = 0;
            } else {
                j++;
            }
            cardNum++;
        }
        return insertNum;
    }

    private int insertCardNumValues(String valueSql) {
        //try catch防止插入出错
        try {
            return vipCardNumMapper.insertWithValues(valueSql);
        } catch (Exception e) {
            logger.error("批量制卡失败", e);
            throw new BusinessException(VIP_CARD_MAKE_ERROR, "制卡失败");
        }
    }

}
