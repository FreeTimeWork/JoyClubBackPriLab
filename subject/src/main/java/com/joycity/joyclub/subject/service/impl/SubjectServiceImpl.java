package com.joycity.joyclub.subject.service.impl;

import com.joycity.joyclub.act.mapper.ActMapper;
import com.joycity.joyclub.act.modal.ActSimple;
import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.modal.ShowCouponLaunchInfo;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.mapper.ProductMapper;
import com.joycity.joyclub.product.modal.ProductSimple;
import com.joycity.joyclub.subject.mapper.SubjectMapper;
import com.joycity.joyclub.subject.mapper.SubjectTypeMapper;
import com.joycity.joyclub.subject.modal.SubjectDetail;
import com.joycity.joyclub.subject.modal.SubjectWithType;
import com.joycity.joyclub.subject.modal.generated.SubjectType;
import com.joycity.joyclub.subject.modal.generated.SubjectTypeExample;
import com.joycity.joyclub.subject.modal.generated.SubjectWithBLOBs;
import com.joycity.joyclub.subject.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private SubjectTypeMapper subjectTypeMapper;
    @Autowired
    private ActMapper actMapper;
    @Autowired
    private CardCouponLaunchMapper launchMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public ResultData createSubject(SubjectWithBLOBs subjectWithBLOBs) {
        subjectMapper.insertSelective(subjectWithBLOBs);
        return new ResultData(new CreateResult(subjectWithBLOBs.getId()));
    }

    @Override
    public ResultData getSubjects(Long projectId,PageUtil pageUtil) {
        return new AbstractGetListData<SubjectWithType>() {
            @Override
            public Long countByFilter() {
                return subjectMapper.countSubjectList(projectId);
            }

            @Override
            public List<SubjectWithType> selectByFilter() {
                return subjectMapper.getSubjectList(projectId,pageUtil);
            }
        }.getList(pageUtil);
    }

    @Override
    public ResultData getSubjectDetail(Long id) {

        SubjectDetail subject = subjectMapper.selectSubjectById(id);
        // 活动
        String[] acts = subject.getContactAct().split(",");
        List<ActSimple> actSimples = actMapper.selectActSimpleListByIds(acts);
        subject.setActs(actSimples);
        String actsName = "";
        for (ActSimple actSimple : actSimples) {
            actsName += actSimple.getName()+",";
        }
        if (actsName.length() > 1) {
            subject.setActsName(actsName.substring(0,actsName.length() - 1));
        }
        // 卡券
        String[] coupons = subject.getContactCoupon().split(",");
        List<ShowCouponLaunchInfo> launchInfos = launchMapper.selectByLaunchIds(coupons);
        subject.setCoupons(launchInfos);
        String couponsName = "";
        for (ShowCouponLaunchInfo info : launchInfos) {
            couponsName += info.getCouponName()+",";
        }
        if (couponsName.length() > 1) {
            subject.setCouponsName(couponsName.substring(0,couponsName.length() - 1));
        }
        //商品
        String[] products = subject.getContactProduct().split(",");
        List<ProductSimple> productSimples = productMapper.selectProductSimpleByIds(products);
        subject.setProducts(productSimples);
        String productsName = "";
        for (ProductSimple simple : productSimples) {
            productsName += simple.getName()+",";
        }
        if (productsName.length() > 1) {
            subject.setProductsName(productsName.substring(0,productsName.length() - 1));
        }
        return new ResultData(subject);
    }

    @Override
    public ResultData getSubjectTypes(Long projectId) {
        SubjectTypeExample example = new SubjectTypeExample();
        SubjectTypeExample.Criteria criteria = example.createCriteria();
        if (projectId != null) {
            criteria.andProjectIdEqualTo(projectId);
        }
        List<SubjectType> subjects = subjectTypeMapper.selectByExample(example);
        return new ResultData(new ListResult(subjects));
    }

    @Override
    public ResultData updateSubjectType(SubjectType subjectType) {
        int num = subjectTypeMapper.updateByPrimaryKeySelective(subjectType);
        return new ResultData(new UpdateResult(num));
    }

}
