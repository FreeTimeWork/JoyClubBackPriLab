package com.joycity.joyclub.api.service;

import com.joycity.joyclub.api.entity.VipUser;
import com.joycity.joyclub.api.entity.base.ResultData;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by amosc on 2017/2/21.
 */
public interface VipUserService {
    ResultData export(HttpServletResponse response);
     List<VipUser> getList();
}
