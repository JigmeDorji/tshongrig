package com.spms.mvc.web;

import com.spms.mvc.dto.OpeningAndBuyingDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.AssetOpeningService;
import com.spms.mvc.service.MoneyReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Description: AssetBuyingController
 * Date:  2021-Oct-04
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-04
 * Change Description:
 * Search Tag:
 */

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/assetBuying")
public class AssetBuyingController extends BaseController {

    @Autowired
    private AssetOpeningService assetOpeningService;

    @Autowired
    private MoneyReceiptService moneyReceiptService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {

        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        Date newDate = new Date();
        String date = new SimpleDateFormat("dd-MMM-yyyy").format(newDate);
        model.addAttribute("date", date);

        model.addAttribute("bankList",
                moneyReceiptService.getBankList(currentUser.getCompanyId()));

        return "assetBuying";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(HttpServletRequest request, OpeningAndBuyingDTO openingAndBuyingDTO) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return assetOpeningService.save(currentUser, openingAndBuyingDTO);
    }

    @ResponseBody
    @RequestMapping(value = "/loadGroupList", method = RequestMethod.GET)
    public List<DropdownDTO> loadGroupList() {
        return assetOpeningService.getFixedAssetGroupList();
    }


    @ResponseBody
    @RequestMapping(value = "/loadAssetBuyingList", method = RequestMethod.GET)
    public List<OpeningAndBuyingDTO> loadAssetBuyingList(BigInteger purchaseMasterId) {
        return assetOpeningService.loadAssetBuyingList(purchaseMasterId);
    }
}
