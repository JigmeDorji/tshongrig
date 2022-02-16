package com.spms.mvc.service;

import com.spms.mvc.dao.AssetSetupDao;
import com.spms.mvc.dto.AssetSetupDTO;
import com.spms.mvc.entity.FaGroupSerial;
import com.spms.mvc.entity.FaItemSetup;
import com.spms.mvc.entity.FaItemSetupDetail;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Description: AssetSetupService
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

@Service("assetSetupService")
public class AssetSetupService {
    @Autowired
    private AssetSetupDao assetSetupDao;

    ResponseMessage responseMessage = new ResponseMessage();

    public String getAssetCodeSerialNo(Integer groupId, CurrentUser currentUser) {

        String assetNo = "";

        AssetSetupDTO assetSetupDTO = assetSetupDao.getParentAssetCodeByGroupId(groupId);

        if (assetSetupDao.checkIsAssetCodeExists(groupId, currentUser.getCompanyId())) {

            FaGroupSerial faGroupSerial = new FaGroupSerial();

            Integer assetSerialId = assetSetupDao.getMaxAssetSerialId();
            assetSerialId = assetSerialId == null ? 1 : assetSerialId + 1;

            assetNo = String.format("%04d", 1);
            faGroupSerial.setAssetSerialId(assetSerialId);
            faGroupSerial.setAssetClassId(assetSetupDTO.getAssetClassId());
            faGroupSerial.setCompanyId(currentUser.getCompanyId());
            faGroupSerial.setAssetNoSerial(1);
            assetSetupDao.save(faGroupSerial);

        } else {
            BigInteger maxGroupSerialNo = assetSetupDao.getMaxGroupSerialNo(currentUser.getCompanyId(),
                    assetSetupDTO.getAssetClassId());
            assetNo = String.format("%04d", maxGroupSerialNo);
        }
        return assetSetupDTO.getParentAssetCode().concat(assetNo);
    }

    public ResponseMessage saveAssetClass(AssetSetupDTO assetSetupDTO, CurrentUser currentUser) {
        BigInteger assetId;
        FaItemSetup faItemSetup = new FaItemSetup();

        AssetSetupDTO assetSetupDTODB = assetSetupDao.getParentAssetCodeByGroupId(assetSetupDTO.getGroupId());

        if (assetSetupDTO.getAssetId() == null) {
            assetId = assetSetupDao.getMaxAssetId();
            assetId = assetId == null ? BigInteger.ONE : assetId.add(BigInteger.ONE);
        } else {
            assetId = assetSetupDTO.getAssetId();
        }

        faItemSetup.setAssetId(assetId);
        faItemSetup.setAssetNo(assetSetupDTO.getAssetNo());
        faItemSetup.setDescription(assetSetupDTO.getDescription());
        faItemSetup.setCompanyId(currentUser.getCompanyId());
        faItemSetup.setCreatedBy(currentUser.getLoginId());
        faItemSetup.setCreatedDate(currentUser.getCreatedDate());
        faItemSetup.setAssetClassId(assetSetupDTODB.getAssetClassId());
        assetSetupDao.saveAssetItem(faItemSetup);

        assetSetupDao.updateGroupSerialNo(assetSetupDao.getMaxGroupSerialNo(currentUser.getCompanyId(),
                assetSetupDTODB.getAssetClassId()).add(BigInteger.ONE), currentUser.getCompanyId(),
                assetSetupDTODB.getAssetClassId());
        responseMessage.setStatus(1);
        responseMessage.setText("You have successfully saved.");
        return responseMessage;
    }

    public List<DropdownDTO> getDescriptionList(CurrentUser currentUser) {
        return assetSetupDao.getDescriptionList(currentUser.getCompanyId());
    }

    public ResponseMessage saveAssetSubClassCategories(AssetSetupDTO assetSetupDTO, CurrentUser currentUser) {
        BigInteger assetDetailId;
        FaItemSetupDetail faItemSetupDetail = new FaItemSetupDetail();

        if (assetSetupDTO.getAssetDetailId() == null) {
            assetDetailId = assetSetupDao.getMaxAssetDetailId();
            assetDetailId = assetDetailId == null ? BigInteger.ONE : assetDetailId.add(BigInteger.ONE);
        } else {
            assetDetailId = assetSetupDTO.getAssetDetailId();
        }

        faItemSetupDetail.setAssetDetailId(assetDetailId);
        faItemSetupDetail.setAssetId(assetSetupDTO.getAssetId());
        faItemSetupDetail.setParticular(assetSetupDTO.getParticular());
        faItemSetupDetail.setCreatedBy(currentUser.getLoginId());
        faItemSetupDetail.setCreatedDate(currentUser.getCreatedDate());
        assetSetupDao.saveAssetItemDetail(faItemSetupDetail);

        responseMessage.setStatus(1);
        responseMessage.setText("You have successfully saved.");
        return responseMessage;
    }

    public List<AssetSetupDTO> getFixedAssetDetail(CurrentUser currentUser) {
        return assetSetupDao.getFixedAssetDetail(currentUser.getCompanyId());
    }

    public List<AssetSetupDTO> getAssetItemDetail(BigInteger faPurchaseId) {
        return assetSetupDao.getAssetItemDetail(faPurchaseId);
    }

    public List<AssetSetupDTO> getAssetItemTxnDetail(BigInteger assetDetailId) {
        return assetSetupDao.getAssetItemTxnDetail(assetDetailId);
    }
}
