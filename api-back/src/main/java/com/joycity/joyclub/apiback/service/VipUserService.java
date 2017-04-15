package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.apiback.modal.VipUser;
import com.joycity.joyclub.commons.modal.base.ResultData;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by amosc on 2017/2/21.
 */
public interface VipUserService {
    ResultData export(HttpServletResponse response);
     List<VipUser> getList();
}
