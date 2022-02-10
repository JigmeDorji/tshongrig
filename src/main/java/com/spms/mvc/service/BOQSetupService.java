package com.spms.mvc.service;

import com.spms.mvc.dao.BOQSetupDao;
import com.spms.mvc.dto.BOQDetailsDTO;
import com.spms.mvc.dto.BOQDetailsListDTO;
import com.spms.mvc.entity.BOQ;
import com.spms.mvc.entity.BOQDetail;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Description: BOQOperationSetupService
 * Date:  2022-Jan-06
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-06
 * Change Description:
 * Search Tag:
 */

@Service
public class BOQSetupService {

    @Autowired
    private BOQSetupDao boqSetupDao;

    public ResponseMessage importExcelFile(BOQDetailsDTO boqDetailsDTO) throws IOException {

        ResponseMessage responseMessage = new ResponseMessage();
        MultipartFile multipartFile = boqDetailsDTO.getExcelMultipartFile();

        if (multipartFile != null) {
            if (!Objects.equals(multipartFile.getOriginalFilename(), "")) {
                //validate excel format
                XSSFWorkbook workbook = new XSSFWorkbook(boqDetailsDTO.getExcelMultipartFile().getInputStream());
                XSSFSheet worksheet = workbook.getSheetAt(0);
                Row headerRow = worksheet.getRow(0);

                // LIst of headers from excel
                List<String> headers = new ArrayList<String>();
                Iterator<Cell> cells = headerRow.cellIterator();

                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    RichTextString value = cell.getRichStringCellValue();
                    if (!value.getString().equals("")) {
                        headers.add(value.getString());
                    }
                }
                // validate the template
                Object[] headerValidation = validateTemplate(headers);

                // if validation fails then write back the message to user.
                if (headers.size() != 9) {
                    responseMessage.setStatus(0);
                    responseMessage.setText("Please upload the right format of excel sheet");
                    return responseMessage;
                }
            }
        }

        List<BOQDetailsListDTO> boqDetailsDTOS = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(boqDetailsDTO.getExcelMultipartFile().getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        final FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                BOQDetailsListDTO boqDetailsListDTO = new BOQDetailsListDTO();
                XSSFRow row = worksheet.getRow(index);
                if (row != null) {
                    if (!getCellValue(row, 0, evaluator).equals("")) {
                        boqDetailsListDTO.setCode(getCellValue(row, 1, evaluator));
                        boqDetailsListDTO.setDescription(getCellValue(row, 2, evaluator));
                        boqDetailsListDTO.setUnitOfMeasurement(getCellValue(row, 3, evaluator));
                        boqDetailsListDTO.setQty(convertStringToDecimal(getCellValue(row, 4, evaluator)));
                        boqDetailsListDTO.setRate(convertStringToDecimal(getCellValue(row, 5, evaluator)));
                        boqDetailsListDTO.setRateInWords(getCellValue(row, 6, evaluator));
                        boqDetailsListDTO.setAmount(convertStringToDecimal(getCellValue(row, 7, evaluator)));
                        boqDetailsListDTO.setTotalAmountInWords(getCellValue(row, 8, evaluator));
                        boqDetailsDTOS.add(boqDetailsListDTO);
                    }
                }
            }
        }
        responseMessage.setStatus(1);
        responseMessage.setDTO(boqDetailsDTOS);
        return responseMessage;
    }

    private String getCellValue(Row row, int cellNo, FormulaEvaluator evaluator) {
        DataFormatter formatter = new DataFormatter();
        Cell cell = row.getCell(cellNo);
        CellValue cellValue = evaluator.evaluate(cell);

        if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return String.valueOf(cellValue.getNumberValue());
        } else {
            return formatter.formatCellValue(cell);
        }
    }

    private BigDecimal convertStringToDecimal(String str) {
        BigDecimal result = BigDecimal.ZERO;
        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return result;
        }
        return new BigDecimal(str);
    }

    public ResponseMessage save(BOQDetailsDTO boqDetailsDTO, CurrentUser currentUser) throws IOException {

        BOQ boq = new BOQ();
        BigInteger boqId;
        ResponseMessage responseMessage = new ResponseMessage();

        if (!boqSetupDao.checkIsWorkOrderNoExists(boqDetailsDTO.getWorkOrderNo()) && boqDetailsDTO.getBoqId() == null) {
            responseMessage.setStatus(0);
            responseMessage.setText("Work Order already exists.");
            return responseMessage;
        }

        if (boqDetailsDTO.getBoqId() == null) {
            boqId = boqSetupDao.getMaxBOQId();
            boqId = boqId == null ? BigInteger.ONE : boqId.add(BigInteger.ONE);
        } else {
            boqId = boqDetailsDTO.getBoqId();
        }
        boq.setBoqId(boqId);
        boq.setEmployingAgency(boqDetailsDTO.getEmployingAgency());
        boq.setNameOfWork(boqDetailsDTO.getNameOfWork());
        boq.setWorkOrderNo(boqDetailsDTO.getWorkOrderNo());
        boq.setCompletionDate(boqDetailsDTO.getCompletionDate());
        boq.setWorkOrderDate(boqDetailsDTO.getWorkOrderDate());
        boq.setWorkStartDate(boqDetailsDTO.getWorkStartDate());
        boq.setCompanyId(currentUser.getCompanyId());
        boq.setFinancialYearId(currentUser.getFinancialYearId());
        boq.setCreatedBy(currentUser.getLoginId());
        boq.setCreatedDate(currentUser.getCreatedDate());
        boqSetupDao.saveBoq(boq);

        MultipartFile multipartFile = boqDetailsDTO.getExcelMultipartFile();
        if (multipartFile != null) {
            if (!Objects.equals(multipartFile.getOriginalFilename(), "")) {

                XSSFWorkbook workbook = new XSSFWorkbook(boqDetailsDTO.getExcelMultipartFile().getInputStream());
                XSSFSheet worksheet = workbook.getSheetAt(0);
                final FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

                for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
                    if (index > 0) {

                        BOQDetail boqDetail = new BOQDetail();
                        XSSFRow row = worksheet.getRow(index);
                        if (row != null) {
                            if (!getCellValue(row, 0, evaluator).equals("")) {

                                BigInteger boqDetailId = boqSetupDao.getMaxDetailBOQId();
                                boqDetailId = boqDetailId == null ? BigInteger.ONE : boqDetailId.add(BigInteger.ONE);

                                boqDetail.setBoqId(boqId);
                                boqDetail.setBoqDetailId(boqDetailId);
                                boqDetail.setCode(getCellValue(row, 1, evaluator));
                                boqDetail.setDescription(getCellValue(row, 2, evaluator));
                                boqDetail.setUnit(getCellValue(row, 3, evaluator));
                                boqDetail.setQty(convertStringToDecimal(getCellValue(row, 4, evaluator)));
                                boqDetail.setRate(convertStringToDecimal(getCellValue(row, 5, evaluator)));
                                boqDetail.setRateInWords(getCellValue(row, 6, evaluator));
                                boqDetail.setTotalAmountInWords(getCellValue(row, 8, evaluator));
                                boqDetail.setCreatedBy(currentUser.getLoginId());
                                boqDetail.setCreatedDate(currentUser.getCreatedDate());
                                boqSetupDao.saveBOQDetail(boqDetail);
                            }
                        }
                    }
                }
            }
        }
        responseMessage.setStatus(1);
        responseMessage.setText("BOQ saved Successfully");
        return responseMessage;
    }

    private Object[] validateTemplate(List<String> headers) {
        return new Object[0];
    }

    public List<BOQDetailsDTO> getGeneratedBOQList(Integer companyId) {
        return boqSetupDao.getGeneratedBOQList(companyId);
    }

    public BOQDetailsDTO getDetailByBOQId(BigInteger boqId) {
        return boqSetupDao.getDetailByBOQId(boqId);
    }

    public List<BOQDetailsListDTO> getBOQList(BigInteger boqId) {
        return boqSetupDao.getBOQList(boqId);
    }

    public ResponseMessage delete(BigInteger boqId) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (!boqSetupDao.isWorkOrderNoUsedInRABill(boqId)) {
            responseMessage.setStatus(0);
            responseMessage.setText("You cannot delete this work order No.");
            return responseMessage;
        }
        boqSetupDao.deleteFromDetail(boqId);
        boqSetupDao.deleteFromMainTable(boqId);
        responseMessage.setStatus(1);
        responseMessage.setText("Deleted Successfully");
        return responseMessage;
    }
}
