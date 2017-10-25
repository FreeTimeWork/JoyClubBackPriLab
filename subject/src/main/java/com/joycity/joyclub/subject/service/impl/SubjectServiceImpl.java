package com.joycity.joyclub.subject.service.impl;

import com.joycity.joyclub.act.mapper.ActMapper;
import com.joycity.joyclub.act.modal.ActSimple;
import com.joycity.joyclub.act.service.ActService;
import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.modal.ShowCouponLaunchInfo;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.mapper.ProductMapper;
import com.joycity.joyclub.product.modal.ProductSimple;
import com.joycity.joyclub.subject.mapper.SubjectMapper;
import com.joycity.joyclub.subject.modal.SubjectDetail;
import com.joycity.joyclub.subject.modal.SubjectWithType;
import com.joycity.joyclub.subject.modal.generated.Subject;
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
    public ResultData getSubjects(PageUtil pageUtil) {
        return new AbstractGetListData<SubjectWithType>() {
            @Override
            public Long countByFilter() {
                return subjectMapper.countSubjectList();
            }

            @Override
            public List<SubjectWithType> selectByFilter() {
                return subjectMapper.getSubjectList(pageUtil);
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
        // 卡券
        String[] coupons = subject.getContactCoupon().split(",");
        List<ShowCouponLaunchInfo> launchInfos = launchMapper.selectByLaunchIds(coupons);
        subject.setCoupons(launchInfos);
        //商品
        String[] products = subject.getContactProduct().split(",");
        List<ProductSimple> productSimples = productMapper.selectProductSimpleByIds(products);
        subject.setProducts(productSimples);

        return new ResultData(subject);
    }
}
