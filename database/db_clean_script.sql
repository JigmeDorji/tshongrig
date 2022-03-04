truncate tbl_acc_bank;
truncate tbl_acc_voucher_count;
truncate tbl_acc_bank_reconciliation;
truncate tbl_acc_depreciation;
truncate tbl_acc_depreciation_item_details;
truncate tbl_acc_depreciation_item_setup;
truncate tbl_acc_inv_voucherdetail;
truncate tbl_acc_ledger;
truncate tbl_acc_ledger_a;
truncate tbl_acc_loan;
truncate tbl_acc_money_receipt;
truncate tbl_acc_party_detail;
truncate tbl_acc_sale_invoice;
truncate tbl_acc_sale_invoice_counter;
truncate tbl_acc_sale_invoice_detail;
truncate tbl_acc_voucher_entries;
truncate tbl_acc_voucher_entries_detail;
truncate tbl_hr_employeesetup;
truncate tbl_hr_employeeadvance;
truncate tbl_hr_other_remittance;
truncate tbl_hr_salary_remittance;
truncate tbl_hr_salary_sheet;
truncate tbl_hr_statutory_remittance;
truncate tbl_inv_discount;
truncate tbl_inv_item_code_counter;
truncate tbl_inv_purchase;
truncate tbl_inv_purchase_a;
truncate tbl_inv_purchase_credit_supplier;
truncate tbl_inv_purchase_ledger;
truncate tbl_inv_purchase_ledger_a;
truncate tbl_inv_receipt_counter;
truncate tbl_inv_replace_item;
truncate tbl_inv_return_item;
truncate tbl_inv_sale_record;
truncate tbl_inv_sale_record_detail;
truncate tbl_inv_supplier;
truncate tbl_item;
truncate tbl_item_code;
truncate tbl_locationsetup;
truncate tbl_receiptcounter;
truncate tbl_acc_boq;
truncate tbl_acc_boq_detail;
truncate tbl_acc_boq_ra_bill;
truncate tbl_acc_boq_ra_bill_detail;
truncate tbl_common_company;
truncate tbl_user;
truncate tbl_company_mapping;

#insert to default company
INSERT INTO `tbl_common_company`(`id`,`companyName`,`mailingAddress`,
`mobileNo`,`email`,`website`,`fnYrStart`,`businessType`,`pfPercentage`) VALUES
('1', 'default company', 'Location A', '7862222', 'tshongrig3@gmail.com', '', '2022-01-01', '4', '5');

#inser superadmin user
INSERT INTO tbl_user
(`userId`,`username`,`userFullName`,
`userMobileNo`,`saltValue`,`userPassword`,
`emailId`,`userStatus`,`userRoleTypeId`,`companyId`,`updatedBy`,`updatedDate`,`createdBy`,`createdDate`)
VALUES (1,	'superadmin','Developer Account','17970903', '6i64UM',
'$2a$10$nSwMuN3WP.BPotcf.VnLhuGvg8Y0o4g1.LX6JFbn.4pE9r0sbWjSy','tshongrig3@gmail.com','A',4,null,'',null,'tshongrig',curdate());

INSERT INTO tbl_user
(`userId`,`username`,`userFullName`,
`userMobileNo`,`saltValue`,`userPassword`,
`emailId`,`userStatus`,`userRoleTypeId`,`companyId`,`updatedBy`,`updatedDate`,`createdBy`,`createdDate`)
VALUES (2,'admin','Administrator','17970903', '6i64UM',
'$2a$10$nSwMuN3WP.BPotcf.VnLhuGvg8Y0o4g1.LX6JFbn.4pE9r0sbWjSy','tshongrig3@gmail.com','A',1,null,'',null,'tshongrig',curdate());


INSERT INTO `tbl_company_mapping`(`id`,`userId`,`companyId`)VALUES(1,1,1);
INSERT INTO `tbl_company_mapping`(`id`,`userId`,`companyId`)VALUES(2,2,1);

SELECT * FROM tbl_common_company;
SELECT * FROM tbl_acc_voucher_count;
SELECT * FROM tbl_acc_sale_invoice_counter;