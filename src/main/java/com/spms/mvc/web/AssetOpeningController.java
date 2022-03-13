package com.spms.mvc.web;

import com.spms.mvc.dto.OpeningAndBuyingDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.AssetOpeningService;
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
 * Description: OpeningAndBuyingController
 * Date:  2021-Sep-16
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Sep-16
 * Change Description:
 * Search Tag:
 */

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/assetOpening")
public class AssetOpeningController extends BaseController {

    @Autowired
    private AssetOpeningService assetOpeningService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {

        model.addAttribute("groupList", assetOpeningService.getFixedAssetGroupList());

        Date newDate = new Date();
        String date = new SimpleDateFormat("dd-MMM-yyyy").format(newDate);
        model.addAttribute("date", date);
        return "assetOpening";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(HttpServletRequest request, OpeningAndBuyingDTO openingAndBuyingDTO) throws ParseException {
        return assetOpeningService.save(getCurrentUser(request), openingAndBuyingDTO);
    }

    @ResponseBody
    @RequestMapping(value = "/getItemSuggestionList", method = RequestMethod.GET)
    public List<OpeningAndBuyingDTO> getItemSuggestionList(HttpServletRequest request) {
        return assetOpeningService.getItemSuggestionList(getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/loadAssetOpeningList", method = RequestMethod.GET)
    public List<OpeningAndBuyingDTO> loadAssetOpeningList(HttpServletRequest request, BigInteger faPurchaseId) {
        return assetOpeningService.loadAssetOpeningList(getCurrentUser(request),faPurchaseId);
    }

}
