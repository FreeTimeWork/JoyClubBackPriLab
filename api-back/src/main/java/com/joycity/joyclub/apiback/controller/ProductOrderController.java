package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.modal.vo.order.DelieveryVO;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.DateTimeUtil;
import com.joycity.joyclub.commons.utils.ExcelUtility;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.excel.ProductFileExportUtils;
import com.joycity.joyclub.product.modal.ProductOrderStoreInfo;
import com.joycity.joyclub.product.service.ProductStoreOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.OutputStream;
import java.util.Collection;
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
public class ProductOrderController extends BaseUserSessionController {

    private Log logger = LogFactory.getLog(ProductOrderController.class);

    @Autowired
    ProductStoreOrderService productStoreOrderService;

    /**
     * @param receiveType 自提还是快递
     * @param status      订单状态
     * @param pageUtil
     * @param session
     * @return
     */
    @RequestMapping(value = "/product/orders", method = GET)
    public ResultData getList(
            @RequestParam(required = false) Byte receiveType,
            @RequestParam(required = false) Byte status,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam Long infoId,
            PageUtil pageUtil,
            HttpSession session
    ) {

        SysUser sysUser = checkStoreUser(session);
        return productStoreOrderService.getList(infoId, receiveType, status, code, name, phone, pageUtil);
    }

    @RequestMapping(value = "/product/order/{id}", method = GET)
    public ResultData getList(
            @PathVariable Long id,
            HttpSession session
    ) {

        checkStoreUser(session);
        return productStoreOrderService.getInfo(id);
    }

    /**
     * 商家确认自提
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "/product/order/{id}/selffetch", method = POST)
    public ResultData selfFetch(@PathVariable Long id, HttpSession session) {

        checkStoreUser(session);
        return productStoreOrderService.completeSelfFetch(id);
    }

    /**
     * 商家发货
     *
     * @return
     */
    @RequestMapping(value = "/product/order/{id}/delivery", method = POST)
    public ResultData delivery(@PathVariable Long id,
                               @RequestBody DelieveryVO delieveryVO,

                               HttpSession session) {

        checkStoreUser(session);
        return productStoreOrderService.completeDelivery(id, delieveryVO.getDeliveryCompany(), delieveryVO.getDeliveryCode());
    }

    @GetMapping("/product/order/down")
    public ResultData downExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
//        checkPlatformOrProjectOrStoreUser(session);
        HSSFWorkbook workbook = new HSSFWorkbook();
        List<ProductOrderStoreInfo> productOrderStoreInfos =  productStoreOrderService.getProductOrders();
        if (CollectionUtils.isEmpty(productOrderStoreInfos)) {
            return new ResultData();
        }
        ProductFileExportUtils.createProductOrderExcel(workbook, productOrderStoreInfos);
        String fileName = "商品订单-" + DateTimeUtil.formatYYYYMMDDHHMMSS(new Date());
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
