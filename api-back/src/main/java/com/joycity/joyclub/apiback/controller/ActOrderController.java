package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.act.excel.ActFileExportUtils;
import com.joycity.joyclub.act.modal.ActOrderForBack;
import com.joycity.joyclub.act.service.ActOrderService;
import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.DateTimeUtil;
import com.joycity.joyclub.commons.utils.ExcelUtility;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Administrator on 2017/2/20.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ActOrderController extends BaseUserSessionController {
    private Log logger = LogFactory.getLog(ActOrderController.class);

    @Autowired
    ActOrderService actOrderService;

    /**
     * @param status      订单状态
     * @param pageUtil
     * @param session
     * @param actName 模糊查询
     * @return
     */
    @RequestMapping(value = "/act/orders",method = GET)
    public ResultData getList(
            @RequestParam(required = false) Byte status,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String actName,
            PageUtil pageUtil,
            HttpSession session
    ) {

        SysUser sysUser = checkStoreUser(session);
        return actOrderService.getList(sysUser.getInfoId(), status, code, name, phone,actName, pageUtil);
    }

  /*  @RequestMapping(value = "/act/order/{id}",method = GET)
    public ResultData getInfo(
            @PathVariable Long id,
            HttpSession session
    ) {

        checkStoreUser(session);
        return actOrderService.getInfo(id);
    }
*/

    /**
     * 商家发货
     *
     * @return
     */
    @RequestMapping( value = "/act/order/{id}/check",method = POST)
    public ResultData delivery(@PathVariable Long id,
                               HttpSession session) {

        checkStoreUser(session);
        return actOrderService.checkOrder(id);
    }

    @GetMapping("/act/order/down")
    public ResultData downExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
//        checkPlatformOrProjectOrStoreUser(session);
        List<ActOrderForBack> actOrderForBacks = actOrderService.getActOrderList();
        HSSFWorkbook workbook = new HSSFWorkbook();

        ActFileExportUtils.createActOrderExcel(workbook, actOrderForBacks);
        String fileName = "活动订单-" + DateTimeUtil.formatYYYYMMDDHHMMSS(new Date());
        OutputStream outputStream = null;
        try {
            fileName = ExcelUtility.encodeFileName(fileName, request);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8'zh_cn'" + fileName + ".xls");

            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            logger.error("downloadExcelfile ERROR", e);
            throw new Exception();
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }

        return new ResultData();

    }
}
