package com.spms.mvc.web;

import com.spms.mvc.dto.ViewPrintCopyDTO;
import com.spms.mvc.service.ViewPrintCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * Created by Bcass Sawa on 4/9/2019.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/viewPrintCopy")
public class ViewPrintCopyController {

    @Autowired
    private ViewPrintCopyService viewPrintCopyService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "viewPrintCopy";
    }

    @ResponseBody
    @RequestMapping(value = "/getParentNodeJsTree", method = RequestMethod.GET)
    public ViewPrintCopyDTO getParentNodeJsTree() {
        return viewPrintCopyService.getParentNodeJsTree();
    }

    @ResponseBody
    @RequestMapping(value = "/getSpecificReportsDetailsByNodeId/{id}", method = RequestMethod.GET)
    public List<ViewPrintCopyDTO> getSpecificReportsDetailsByNodeId(@PathVariable("id") Integer id) {
        return viewPrintCopyService.getSpecificReportsDetailsByNodeId(id);
    }

    @ResponseBody
    @RequestMapping(value = "/displayPDFFile/{pdfFileName}", produces = "application/pdf", method = RequestMethod.GET)
    public HttpEntity<byte[]> displayPDFFile(@PathVariable("pdfFileName") String pdfFileName) throws IOException {
        return viewPrintCopyService.displayPDFFile(pdfFileName);
    }

}
