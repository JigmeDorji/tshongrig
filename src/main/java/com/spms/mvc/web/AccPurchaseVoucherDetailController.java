package com.spms.mvc.web;

import com.spms.mvc.dto.AccProfitAndLossReportDTO;
import com.spms.mvc.service.VoucherGroupListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by jigmePc on 8/13/2019.
 */
@Controller
@RequestMapping("/accPurchaseVoucherDetail")
public class AccPurchaseVoucherDetailController {

    @Autowired
    private VoucherGroupListService voucherGroupListService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("accPurchaseVoucherDetail");
    }

    @ResponseBody
    @RequestMapping(value = "/getInventoryVoucherDetail", method = RequestMethod.GET)
    public List<AccProfitAndLossReportDTO> getVoucherDetailsById(Integer accTypeId) {
        return voucherGroupListService.getInventoryVoucherDetail(accTypeId);
    }


}
