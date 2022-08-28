package com.spms.mvc.web;

import com.spms.mvc.dto.FixedAssetScheduleDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.FixedAssetSaleService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Description: FixedAssetScheduler
 * Date:  2021-Oct-23
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-23
 * Change Description:
 * Search Tag:
 */

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/fixedAssetSchedule")
public class FixedAssetScheduleController extends BaseController {
    @Autowired
    private FixedAssetSaleService fixedAssetSaleService;


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(HttpServletRequest request, Model model) {
        CurrentUser currentUser = getCurrentUser(request);
        model.addAttribute("financialYearFrom", currentUser.getFinancialYearFrom());
        model.addAttribute("asOnDate", DateUtil.format(new Date(), DateUtil.DD_MMM_YYYY));
        return "fixedAssetSchedule";
    }

    @ResponseBody
    @RequestMapping(value = "/getFixedAssetSchedule", method = RequestMethod.GET)
    public List<FixedAssetScheduleDTO> getFixedAssetSchedule(HttpServletRequest request, Date asOnDate) throws JRException, IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return fixedAssetSaleService.getFixedAssetSchedule(asOnDate, currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "/deleteFixedAsset", method = RequestMethod.GET)
    public ResponseMessage deleteFixedAsset(HttpServletRequest request,String particular,Double depCurrentYear) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return fixedAssetSaleService.deleteFixedAsset(currentUser,particular,depCurrentYear);
    }
}
