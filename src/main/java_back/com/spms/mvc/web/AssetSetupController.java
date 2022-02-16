package com.spms.mvc.web;

import com.spms.mvc.dto.AssetSetupDTO;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.AssetOpeningService;
import com.spms.mvc.service.AssetSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Description: AssetSetupController
 * Date:  2021-Oct-08
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-08
 * Change Description:
 * Search Tag:
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/assetSetup")
public class AssetSetupController extends BaseController {

    @Autowired
    private AssetOpeningService assetOpeningService;

    @Autowired
    private AssetSetupService assetSetupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        model.addAttribute("groupList", assetOpeningService.getFixedAssetGroupList());
        model.addAttribute("descriptionList", assetSetupService.getDescriptionList(getCurrentUser(request)));
        return "assetSetup";
    }


    @ResponseBody
    @RequestMapping(value = "/loadDescriptionList", method = RequestMethod.GET)
    public List<DropdownDTO> loadDescriptionList(HttpServletRequest request) {
        return assetSetupService.getDescriptionList(getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getAssetCodeSerialNo", method = RequestMethod.GET)
    public String getAssetCodeSerialNo(HttpServletRequest request, Integer groupId) throws IOException {
        return assetSetupService.getAssetCodeSerialNo(groupId, getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getFixedAssetDetail", method = RequestMethod.GET)
    public List<AssetSetupDTO> getFixedAssetDetail(HttpServletRequest request) throws IOException {
        return assetSetupService.getFixedAssetDetail(getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getAssetItemDetail", method = RequestMethod.GET)
    public List<AssetSetupDTO> getAssetItemDetail(BigInteger faPurchaseId) throws IOException {
        return assetSetupService.getAssetItemDetail(faPurchaseId);
    }

    @ResponseBody
    @RequestMapping(value = "/getAssetItemTxnDetail", method = RequestMethod.GET)
    public List<AssetSetupDTO> getAssetItemTxnDetail(BigInteger assetDetailId) throws IOException {
        return assetSetupService.getAssetItemTxnDetail(assetDetailId);
    }

    @ResponseBody
    @RequestMapping(value = "/saveAssetClass", method = RequestMethod.POST)
    public ResponseMessage saveAssetClass(HttpServletRequest request, AssetSetupDTO assetSetupDTO) {
        return assetSetupService.saveAssetClass(assetSetupDTO, getCurrentUser(request));
    }


    @ResponseBody
    @RequestMapping(value = "/saveAssetSubClassCategories", method = RequestMethod.POST)
    public ResponseMessage saveAssetSubClassCategories(HttpServletRequest request, AssetSetupDTO assetSetupDTO) {
        return assetSetupService.saveAssetSubClassCategories(assetSetupDTO, getCurrentUser(request));
    }

}
