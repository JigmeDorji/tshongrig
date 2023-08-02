package com.spms.mvc.web;

import com.spms.mvc.dto.AccProfitAndLossReportDTO;
import com.spms.mvc.dto.BankReconciliationDTO;
import com.spms.mvc.dto.LedgerDTO;
import com.spms.mvc.dto.PurchaseDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.AddItemService;
import com.spms.mvc.service.VoucherGroupListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Bcass Sawa on 5/19/2019.
 */

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/voucherGroupList")
public class VoucherGroupListController extends BaseController {

    @Autowired
    private VoucherGroupListService voucherGroupListService;
    @Autowired
    private AddItemService addItemService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request, Model model) {
        CurrentUser currentUser = getCurrentUser(request);

        model.addAttribute("fromDate",
                DateUtil.format(DateUtil.toDate(currentUser.getFinancialYearFrom()),
                        DateUtil.DD_MMM_YYYY));

        model.addAttribute("toDate",
                DateUtil.format(new Date(),
                        DateUtil.DD_MMM_YYYY));

        model.addAttribute("businessType",
                currentUser.getBusinessType());

        return new ModelAndView("voucherGroupList");
    }

    @ResponseBody
    @RequestMapping(value = "/getVoucherDetailsByLedgerId", method = RequestMethod.GET)
    public List<AccProfitAndLossReportDTO> getVoucherDetailsByLedgerId(HttpServletRequest request, String ledgerId, Date fromDate, Date toDate) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        List<AccProfitAndLossReportDTO> getVoucherDetailsByLedgerId = voucherGroupListService.getVoucherDetailsByLedgerId(ledgerId, fromDate, toDate, currentUser);
//     check for Depreciation Ledger
        if (isDepreciationLedger(ledgerId, currentUser)) {
            return voucherGroupByParticular(getVoucherDetailsByLedgerId);
        } else {
            return getVoucherDetailsByLedgerId;
        }

    }

    private List<AccProfitAndLossReportDTO> voucherGroupByParticular(List<AccProfitAndLossReportDTO> getVoucherDetailsByLedgerId) {
        List<AccProfitAndLossReportDTO> voucherGroupByParticular = new ArrayList<>();
        // Grouping by voucherTypeName
        Map<String, List<AccProfitAndLossReportDTO>> groupedData = getVoucherDetailsByLedgerId.stream()
                .collect(Collectors.groupingBy(AccProfitAndLossReportDTO::getVoucherTypeName));


        // Print the grouped data

        for (Map.Entry<String, List<AccProfitAndLossReportDTO>> entry : groupedData.entrySet()) {
            AccProfitAndLossReportDTO depCVoucher = new AccProfitAndLossReportDTO();
            List<AccProfitAndLossReportDTO> elements = entry.getValue();

            depCVoucher.setVoucherTypeName(entry.getKey());
            Double totalDrAmount = 0.0;
            int[] voucherNums = new int[elements.size()]; // Initialize the array with the correct size
            int index = 0; // Track the index for adding voucher numbers

            for (AccProfitAndLossReportDTO element : elements) {
                totalDrAmount += element.getDebitAmount();
                depCVoucher.setVoucherCreatedDate(element.getVoucherCreatedDate());
                voucherNums[index] = element.getVoucherNo();
                index++;
            }

            // Sort the voucherNums array in ascending order
            Arrays.sort(voucherNums);

            depCVoucher.setVoucherNo(voucherNums[0]);
            depCVoucher.setDebitAmount(totalDrAmount);

            voucherGroupByParticular.add(depCVoucher);
//            System.out.println("TotalDrAmount: For  " + entry.getKey() + " =  " + totalDrAmount);
        }


        return voucherGroupByParticular;

    }

    private boolean isDepreciationLedger(String ledgerId, CurrentUser currentUser) {
        return voucherGroupListService.isDepreciationLedger(ledgerId, currentUser.getCompanyId());

    }

    @ResponseBody
    @RequestMapping(value = "/getOpeningBalance", method = RequestMethod.GET)
    public LedgerDTO getOpeningBalance(HttpServletRequest request, String ledgerId, Date fromDate, Date toDate) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        return voucherGroupListService.getOpeningBalance(ledgerId, fromDate, toDate, currentUser);
    }

    //
    @ResponseBody
    @RequestMapping(value = "/saveBankReconciliation", method = RequestMethod.POST)
    public ResponseMessage saveBankReconciliation(BankReconciliationDTO bankReconciliationDTO, HttpServletRequest request) throws
            IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return voucherGroupListService.saveBankReconciliation(bankReconciliationDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteLedgerVoucherDetails", method = RequestMethod.GET)
    public ResponseMessage deleteLedgerVoucherDetails(HttpServletRequest request, Integer voucherNo, Integer voucherTypeId) throws
            IOException {
        if (voucherTypeId == null) {
            return new ResponseMessage(0, "Voucher Deletion is Restricted!");
        } else {
            return voucherGroupListService.deleteLedgerVoucherDetails(voucherNo, voucherTypeId, (CurrentUser) request.getSession().getAttribute("currentUser"));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updateSaleDetailTable", method = RequestMethod.GET)
    public ResponseMessage updateSaleDetailTable(HttpServletRequest request, Integer voucherNo) throws
            IOException {
        return voucherGroupListService.updateSaleDetailTable(voucherNo, (CurrentUser) request.getSession().getAttribute("currentUser"));
    }

    @RequestMapping(value = "/navigateToPurchasePage", method = RequestMethod.GET)
    public String navigateToDetail(HttpServletRequest request, Integer voucherNo, RedirectAttributes redirectAttributes) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        PurchaseDTO purchaseDTO = addItemService.getPurchaseDetailByVoucherNo(voucherNo, currentUser);
        redirectAttributes.addFlashAttribute("purchaseId", purchaseDTO.getPurchaseId());
        redirectAttributes.addFlashAttribute("purchaseDate", purchaseDTO.getPurchaseDate());
        redirectAttributes.addFlashAttribute("purchaseVoucherNo", voucherNo);
        return "redirect:/receivedItem";
    }
}
