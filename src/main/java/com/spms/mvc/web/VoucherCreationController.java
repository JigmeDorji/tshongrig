package com.spms.mvc.web;

import com.spms.mvc.dto.VoucherDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.VoucherCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * Created by jigmePc on 5/7/2019.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/voucherCreation")
public class VoucherCreationController extends BaseController {

    @Autowired
    private VoucherCreationService voucherCreationService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("voucherTypeList", voucherCreationService.getAccTypeList());
//        model.addAttribute("currentVoucherNo", voucherCreationService.getCurrentVoucherNo());
        return "voucherCreation";
    }

    @ResponseBody
    @RequestMapping(value = "getVoucherNo", method = RequestMethod.GET)
    public Integer getVoucherNo(HttpServletRequest request, Integer voucherTypeId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return voucherCreationService.getCurrentVoucherNo(voucherTypeId, currentUser.getCompanyId(),currentUser.getFinancialYearId());
    }

    @ResponseBody
    @RequestMapping(value = "getLedgerList", method = RequestMethod.GET)
    public List<DropdownDTO> getLedgerList(HttpServletRequest request, Model model) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return voucherCreationService.getLedgerList(currentUser.getCompanyId());
    }


    @ResponseBody
    @RequestMapping(value = "getParticularList", method = RequestMethod.GET)
    public List<DropdownDTO> getParticularList(HttpServletRequest request, Model model) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return voucherCreationService.getParticularList(currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "getVoucherDetailsByVoucherNo", method = RequestMethod.GET)
    public List<VoucherDetailDTO> getVoucherDetailsByVoucherNo(HttpServletRequest request, Integer voucherNo, Integer voucherTypeId) {
        return voucherCreationService.getVoucherDetailsByVoucherNo(voucherNo, voucherTypeId, getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "deleteVoucherDetail", method = RequestMethod.GET)
    public ResponseMessage deleteVoucherDetail(Integer voucherDetailId) {
        return voucherCreationService.deleteVoucherDetail(voucherDetailId);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(HttpServletRequest request, VoucherDTO voucherDTO) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return voucherCreationService.save(voucherDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "checkAccountHeadType", method = RequestMethod.GET)
    public Boolean checkAccountHeadType(HttpServletRequest request, Integer ledgerId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return voucherCreationService.checkAccountHeadType(ledgerId, currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "getRateOfDepreciation", method = RequestMethod.GET)
    public Integer getRateOfDepreciation(HttpServletRequest request, Integer particularId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return voucherCreationService.getRateOfDepreciation(particularId, currentUser.getCompanyId());
    }
}
