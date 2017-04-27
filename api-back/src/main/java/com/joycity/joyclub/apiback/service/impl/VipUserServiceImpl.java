package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.apiback.modal.VipUser;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.VipUserMapper;
import com.joycity.joyclub.apiback.service.VipUserService;
import com.joycity.joyclub.apiback.util.ExcelExporter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.joycity.joyclub.commons.constant.Global.LOGGER_HEADER;

/**
 * Created by amosc on 2017/2/21.
 */
@Service
public class VipUserServiceImpl implements VipUserService {
    private final Log log = LogFactory.getLog(VipUserServiceImpl.class);
    @Autowired
    VipUserMapper vipUserMapper;
    private static final String[] USER_TABLE_HEADERS = {"ID", "姓名", "手机号", "卡类型", "会员号码", "卡面编号", "会员积分"};

    @Override
    public ResultData export(HttpServletResponse response) {
        List<VipUser> list = vipUserMapper.getList();
        ExcelExporter<VipUser> excelExporter = new ExcelExporter<>();
        try {
            excelExporter.exportExcelToResponse(response, "会员表", USER_TABLE_HEADERS, list);
        } catch (IOException e) {
            log.error(LOGGER_HEADER + "export VipUser excel error and list size is " + list.size());
            e.printStackTrace();
           throw new BusinessException(ResultCode.ERR_EXPORT_EXCEL);
        }
        return new ResultData(ResultCode.SUCCESS);
    }

    @Override
    public List<VipUser> getList() {
        return vipUserMapper.getList();
    }
}
