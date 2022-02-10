-- CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_get_trail_balance_detail`(paramCompanyId INT(11), paramFinancialYearId INT(11), fromDate Date,toDate Date)
-- BEGIN
--
-- DROP TABLE IF EXISTS temp_acc_trial_balance_report;
-- DROP TABLE IF EXISTS temp_voucher_details;
-- DROP TABLE IF EXISTS temp_previous_voucher_details;
-- DROP TABLE IF EXISTS temp_acc_parent;
--
-- CREATE TEMPORARY TABLE temp_acc_trial_balance_report(ledgerId varchar(11),
-- particular varchar(250),amount double,groupLevel int,accTypeId int,groupId int);
--
-- CREATE TEMPORARY TABLE temp_voucher_details(ledgerId varchar(11),particular varchar(250),
-- amount double,groupId int,accTypeId int);
--
--     #CREATE TABLE TABLE
--     CREATE TEMPORARY TABLE temp_previous_voucher_details(ledgerId varchar(11),particular varchar(250),
--     amount double,groupId int,accTypeId int);
--
--     #GET ALL THE TOTAL CLOSING BALANCE OF PREVIOUS YEAR
--     INSERT INTO temp_previous_voucher_details
-- 	SELECT * FROM (SELECT b.ledgerId as ledgerId, a.accTypeName as particular, SUM(c.drcrAmount) as amount,
-- 	a.groupId as groupId, a.accTypeId as accTypeId FROM tbl_acc_acctype a
-- 	LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId
-- 	LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId
-- 	LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId
-- 	WHERE d.companyId=paramCompanyId AND d.voucherEntryDate <= fromDate
-- 	AND a.groupId NOT IN(6,7,8,9)
-- 	GROUP BY a.accTypeId
-- 	UNION
-- 	SELECT  l.ledgerId as ledgerId, a.accTypeName as particular, 0 as amount, a.groupId as groupId, a.accTypeId as accTypeId
-- 	FROM tbl_acc_ledger l INNER JOIN tbl_acc_acctype a ON l.accTypeId=a.accTypeId
-- 	WHERE l.companyId=paramCompanyId AND a.groupId NOT IN(6,7,8,9)
-- 	AND l.ledgerId NOT IN(SELECT x.ledgerId FROM tbl_acc_ledger x
-- 	INNER JOIN tbl_acc_voucher_entries_detail y ON x.ledgerId=y.ledgerId
-- 	INNER JOIN tbl_acc_voucher_entries z ON z.voucherId=y.voucherId
-- 	WHERE z.companyId=paramCompanyId   AND z.voucherEntryDate <= fromDate
-- 	GROUP BY x.accTypeId) GROUP by l.accTypeId) A  GROUP BY A.accTypeId;
--
--     # CURRENT ACTIVE YEAR CLOSING BALANCE
-- 	INSERT INTO temp_voucher_details
-- 	SELECT * FROM (SELECT b.ledgerId as ledgerId, a.accTypeName as particular,SUM(c.drcrAmount) as amount,
-- 	a.groupId as groupId, a.accTypeId as accTypeId FROM tbl_acc_acctype a
-- 	LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId
-- 	LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId
-- 	LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId
-- 	WHERE d.companyId=paramCompanyId AND d.financialYearId=paramFinancialYearId AND d.voucherEntryDate >= fromDate AND d.voucherEntryDate <=toDate
-- 	GROUP BY a.accTypeId
-- 	UNION
-- 	SELECT  l.ledgerId as ledgerId, a.accTypeName as particular, 0 as amount, a.groupId as groupId, a.accTypeId as accTypeId
-- 	FROM tbl_acc_ledger l INNER JOIN tbl_acc_acctype a ON l.accTypeId=a.accTypeId
-- 	WHERE l.companyId=paramCompanyId AND l.ledgerId NOT IN(SELECT x.ledgerId FROM tbl_acc_ledger x
-- 	INNER JOIN tbl_acc_voucher_entries_detail y ON x.ledgerId=y.ledgerId
-- 	INNER JOIN tbl_acc_voucher_entries z ON z.voucherId=y.voucherId
-- 	WHERE z.companyId=paramCompanyId AND  z.financialYearId=paramFinancialYearId AND z.voucherEntryDate >= fromDate AND  z.voucherEntryDate <= toDate
-- 	GROUP BY x.accTypeId) GROUP by l.accTypeId) A  GROUP BY A.accTypeId;
--
-- 	SET @innerCounter=(SELECT COUNT(*) FROM temp_previous_voucher_details);
--
--     WHILE @innerCounter>0 DO
--
-- 	SET SQL_SAFE_UPDATES = 0;
--     SET @ledgerId=(SELECT ledgerId FROM temp_previous_voucher_details LIMIT 1);
--     UPDATE temp_voucher_details a set amount= amount + (SELECT amount FROM temp_previous_voucher_details b where b.ledgerId=@ledgerId)
--     WHERE a.ledgerId=@ledgerId;
--
--      DELETE FROM temp_previous_voucher_details WHERE ledgerId=@ledgerId;
--      SET @innerCounter=(SELECT COUNT(*) FROM temp_previous_voucher_details);
--     END WHILE;
--
--
--    CREATE TEMPORARY TABLE temp_acc_parent SELECT * FROM tbl_acc_group;
--
--    SET @counter=(SELECT COUNT(*) FROM temp_acc_parent);
--    WHILE @counter>0 DO
--    SET @var_groupId=(SELECT groupId FROM temp_acc_parent ORDER BY groupId asc LIMIT 1);
--
--     INSERT INTO temp_acc_trial_balance_report
--     SELECT '', groupName,
--     (-1 * (SELECT IFNULL(SUM((v.amount  * -1) +(SELECT SUM(b.openingBal)
--     FROM tbl_acc_ledger b where b.accTypeId=v.accTypeId AND b.companyId=paramCompanyId
--     GROUP BY b.accTypeId)),0) FROM temp_voucher_details v
--     WHERE v.groupId=@var_groupId)), 1, null, @var_groupId FROM temp_acc_parent WHERE groupId=@var_groupId;
--
--    INSERT INTO temp_acc_trial_balance_report
--    SELECT a.ledgerId, a.particular,
--    (-1 * (IFNULL(SUM(a.amount) * -1, 0) + (SELECT SUM(b.openingBal) FROM tbl_acc_ledger b where b.accTypeId=a.accTypeId  AND b.companyId=paramCompanyId GROUP BY b.accTypeId))),
--    2, a.accTypeId,null FROM temp_voucher_details a WHERE a.groupId=@var_groupId
--    GROUP BY a.accTypeId;
--
--    SET SQL_SAFE_UPDATES = 0;
--    DELETE FROM temp_acc_parent WHERE groupId=@var_groupId;
--    SET @counter=(SELECT COUNT(*) FROM temp_acc_parent);
--    END WHILE;
--
--    SELECT * from temp_acc_trial_balance_report;
-- END

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_get_trail_balance_detail`(paramCompanyId INT(11), paramFinancialYearId INT(11), fromDate Date,toDate Date,paramGroupId int(11))
BEGIN

DROP TABLE IF EXISTS temp_acc_trial_balance_report;
DROP TABLE IF EXISTS temp_voucher_details;
DROP TABLE IF EXISTS temp_previous_voucher_details;
DROP TABLE IF EXISTS temp_acc_parent;

CREATE TEMPORARY TABLE temp_acc_trial_balance_report(ledgerId varchar(11),
particular varchar(250),amount double,groupLevel int,accTypeId int,groupId int);

CREATE TEMPORARY TABLE temp_voucher_details(ledgerId varchar(11),particular varchar(250),
amount double,groupId int,accTypeId int);

	SET @previousYearToDate=(SELECT financialYearTo FROM tbl_financial_year_setup
	where companyId=paramCompanyId and financialYearId<(select financialYearId from tbl_financial_year_setup where status='A' and companyId=paramCompanyId)
	order by financialYearId desc limit 1);

    #CREATE TABLE TABLE
    CREATE TEMPORARY TABLE temp_previous_voucher_details(ledgerId varchar(11),particular varchar(250),
    amount double,groupId int,accTypeId int);

    #GET ALL THE TOTAL CLOSING BALANCE OF PREVIOUS YEAR
    INSERT INTO temp_previous_voucher_details
	SELECT * FROM (SELECT b.ledgerId as ledgerId, a.accTypeName as particular, SUM(c.drcrAmount) as amount,
	a.groupId as groupId, a.accTypeId as accTypeId FROM tbl_acc_acctype a
	LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId
	LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId
	LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId
	WHERE d.companyId=paramCompanyId AND d.voucherEntryDate <= @previousYearToDate
	AND a.groupId NOT IN(6,7,8,9)
	GROUP BY a.accTypeId
	UNION
	SELECT  l.ledgerId as ledgerId, a.accTypeName as particular, 0 as amount, a.groupId as groupId, a.accTypeId as accTypeId
	FROM tbl_acc_ledger l INNER JOIN tbl_acc_acctype a ON l.accTypeId=a.accTypeId
	WHERE l.companyId=paramCompanyId AND a.groupId NOT IN(6,7,8,9)
	AND l.ledgerId NOT IN(SELECT x.ledgerId FROM tbl_acc_ledger x
	INNER JOIN tbl_acc_voucher_entries_detail y ON x.ledgerId=y.ledgerId
	INNER JOIN tbl_acc_voucher_entries z ON z.voucherId=y.voucherId
	WHERE z.companyId=paramCompanyId   AND z.voucherEntryDate <= @previousYearToDate
	GROUP BY x.accTypeId) GROUP by l.accTypeId) A  GROUP BY A.accTypeId;

    # CURRENT ACTIVE YEAR CLOSING BALANCE
	INSERT INTO temp_voucher_details
	SELECT * FROM (SELECT b.ledgerId as ledgerId, a.accTypeName as particular,SUM(c.drcrAmount) as amount,
	a.groupId as groupId, a.accTypeId as accTypeId FROM tbl_acc_acctype a
	LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId
	LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId
	LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId
	WHERE d.companyId=paramCompanyId AND d.financialYearId=paramFinancialYearId AND d.voucherEntryDate >= fromDate AND d.voucherEntryDate <=toDate
	GROUP BY a.accTypeId
	UNION
	SELECT  l.ledgerId as ledgerId, a.accTypeName as particular, 0 as amount, a.groupId as groupId, a.accTypeId as accTypeId
	FROM tbl_acc_ledger l INNER JOIN tbl_acc_acctype a ON l.accTypeId=a.accTypeId
	WHERE l.companyId=paramCompanyId AND l.ledgerId NOT IN(SELECT x.ledgerId FROM tbl_acc_ledger x
	INNER JOIN tbl_acc_voucher_entries_detail y ON x.ledgerId=y.ledgerId
	INNER JOIN tbl_acc_voucher_entries z ON z.voucherId=y.voucherId
	WHERE z.companyId=paramCompanyId AND  z.financialYearId=paramFinancialYearId AND z.voucherEntryDate >= fromDate AND  z.voucherEntryDate <= toDate
	GROUP BY x.accTypeId) GROUP by l.accTypeId) A  GROUP BY A.accTypeId;

	SET @innerCounter=(SELECT COUNT(*) FROM temp_previous_voucher_details);

    WHILE @innerCounter>0 DO

	SET SQL_SAFE_UPDATES = 0;
    SET @ledgerId=(SELECT ledgerId FROM temp_previous_voucher_details LIMIT 1);
    UPDATE temp_voucher_details a set amount= amount + (SELECT amount FROM temp_previous_voucher_details b where b.ledgerId=@ledgerId)
    WHERE a.ledgerId=@ledgerId;

     DELETE FROM temp_previous_voucher_details WHERE ledgerId=@ledgerId;
     SET @innerCounter=(SELECT COUNT(*) FROM temp_previous_voucher_details);
    END WHILE;


   CREATE TEMPORARY TABLE temp_acc_parent SELECT * FROM tbl_acc_group where groupId=paramGroupId;

   SET @counter=(SELECT COUNT(*) FROM temp_acc_parent);
   WHILE @counter>0 DO
   SET @var_groupId=(SELECT groupId FROM temp_acc_parent ORDER BY groupId asc LIMIT 1);

    INSERT INTO temp_acc_trial_balance_report
    SELECT '', groupName,
    (-1 * (SELECT IFNULL(SUM((v.amount  * -1) +(SELECT SUM(b.openingBal)
    FROM tbl_acc_ledger b where b.accTypeId=v.accTypeId AND b.companyId=paramCompanyId
    GROUP BY b.accTypeId)),0) FROM temp_voucher_details v
    WHERE v.groupId=@var_groupId)), 1, null, @var_groupId FROM temp_acc_parent WHERE groupId=@var_groupId;

   INSERT INTO temp_acc_trial_balance_report
   SELECT a.ledgerId, a.particular,
   (-1 * (IFNULL(SUM(a.amount) * -1, 0) + (SELECT SUM(b.openingBal) FROM tbl_acc_ledger b where b.accTypeId=a.accTypeId  AND b.companyId=paramCompanyId GROUP BY b.accTypeId))),
   2, a.accTypeId,null FROM temp_voucher_details a WHERE a.groupId=@var_groupId
   GROUP BY a.accTypeId;

   SET SQL_SAFE_UPDATES = 0;
   DELETE FROM temp_acc_parent WHERE groupId=@var_groupId;
   SET @counter=(SELECT COUNT(*) FROM temp_acc_parent);
   END WHILE;

   SELECT * from temp_acc_trial_balance_report;
END

CREATE TABLE `tbl_acc_voucher_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `voucherSerial` int(11) NOT NULL,
  `voucherTypeId` int(11) NOT NULL,
  `companyId` int(11) NOT NULL,
  `financialYearId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;


DELIMITER $$

ALTER DEFINER=`root`@`localhost` EVENT `ev_fa_depreciation_schedule_event` ON SCHEDULE EVERY 30 SECOND STARTS '2021-11-22 18:29:23' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN

	DECLARE total_depreciation_amount DOUBLE DEFAULT 0.00;

	DROP TEMPORARY TABLE IF EXISTS temp_fixed_asset_detail;
	CREATE TEMPORARY TABLE temp_fixed_asset_detail
	(assetClassId INT,
	faPurchaseId BIGINT(11),
	assetDetailId BIGINT(11),
	particular VARCHAR(250),
	purchaseDate DATE,
	rate DOUBLE(18,2),
	rateOfDep DOUBLE,
	asOnStartFinancialYear DOUBLE,
	addition DOUBLE,
	disposal DOUBLE,
	asOnEndFinancialYear DOUBLE,
	depAsOnStartFinancialYear DOUBLE,
	depCurrentYear DOUBLE,
	depAsOnEndFinancialYear DOUBLE,
	netValue DOUBLE, depreciatedValue
	DOUBLE, groupId INT);

	CALL sp_fa_fixed_asset_schedule(17,CURDATE());
	INSERT INTO temp_fixed_asset_detail
	SELECT assetClassId ,faPurchaseId ,
	assetDetailId , particular, purchaseDate,
	rate, rateOfDep,
	SUM(IFNULL(asOnStartFinancialYear,0)),
	SUM(IFNULL(addition,0)),
	SUM(IFNULL(disposal,0)),
	SUM(IFNULL(asOnEndFinancialYear,0)),
	SUM(IFNULL(depAsOnStartFinancialYear,0)),
	SUM(IFNULL(depCurrentYear,0)),
	SUM(IFNULL(depAsOnEndFinancialYear,0)),
	SUM(IFNULL(netValue,0)),
	SUM(IFNULL(depreciatedValue,0)),
	groupId FROM temp_final_fixed_asset_schedule
	WHERE faPurchaseId IS NOT NULL GROUP BY assetClassId;

	SET @var_company_id = 17;
	SET @var_voucher_type_id = 4; #Journal voucher without cash and bank involvement
	SET @var_ledger_name = 'Depreciation';
	SET @var_financial_year_id=(SELECT financialYearId FROM tbl_financial_year_setup
	WHERE STATUS='A' AND companyId=@var_company_id);

	#CREATE LEDGER Depreciation if not exists

	SET @var_is_ledger_exits=(SELECT COUNT(*) FROM tbl_acc_ledger b WHERE b.ledgerName=@var_ledger_name
	AND companyId=@var_company_id);

	SET @var_max_ledger_id = NULL;

	IF(@var_is_ledger_exits=0)THEN
		SET @var_max_ledger_id=(SELECT MAX(CAST(ledgerId AS SIGNED))  FROM tbl_acc_ledger);
		SET @var_max_ledger_id=@var_max_ledger_id + 1;

		INSERT INTO tbl_acc_ledger VALUES(CAST(@var_max_ledger_id AS CHAR(250)),@var_ledger_name,'15',0.00,
		@var_company_id, CURDATE(),"System", NULL);
	ELSE
		SET @var_max_ledger_id=(SELECT ledgerId FROM tbl_acc_ledger WHERE companyId=@var_company_id
		AND ledgerName=@var_ledger_name);
	END IF;

	SET @var_voucher_id=(SELECT voucherId FROM tbl_acc_voucher_entries ORDER BY voucherId DESC LIMIT 1);
	SET @var_voucher_id=IFNULL(@var_voucher_id,0) + 1;

	SET @var_voucher_no=(SELECT voucherSerial FROM tbl_acc_voucher_count WHERE voucherTypeId=@var_voucher_type_id
			AND companyId=@var_company_id AND financialYearId=@var_financial_year_id);
	SET @var_voucher_no = @var_voucher_no + 1;

        SET @counter=(SELECT COUNT(*) FROM temp_fixed_asset_detail);
	WHILE @counter>0 DO

		SET @var_asset_class_id=(SELECT assetClassId FROM temp_fixed_asset_detail LIMIT 1);
		SET @var_dep_current_year=(SELECT depCurrentYear FROM temp_fixed_asset_detail LIMIT 1);
		SET @var_acc_type_id=(SELECT accTypeId FROM tbl_fa_group WHERE assetClassId=@var_asset_class_id);

		INSERT INTO tbl_acc_voucher_entries_detail VALUES(NULL, @var_voucher_id,@var_max_ledger_id,@var_dep_current_year);

		SET total_depreciation_amount = total_depreciation_amount + @var_dep_current_year;

		SET SQL_SAFE_UPDATES = 0;
		DELETE FROM temp_fixed_asset_detail LIMIT 1;
		SET @counter=(SELECT COUNT(*) FROM temp_fixed_asset_detail);
	END WHILE;

	INSERT INTO tbl_acc_voucher_entries_detail VALUES(NULL, @var_voucher_id,@var_max_ledger_id, (-1 * total_depreciation_amount));
	INSERT INTO tbl_acc_voucher_entries VALUES(@var_voucher_id,@var_voucher_type_id, @var_voucher_no, CURDATE(),@var_company_id,@var_financial_year_id, CURDATE(),"System","Depreciation schedule by system");

END$$

DELIMITER ;

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_acc_get_inv_item_total_amount`(paramCompanyId INT(11),
paramItemCode varchar(250), paramQty decimal(18,2))
BEGIN
		Declare totalAmount DOUBLE(18,2) default 0.00;

		DROP TABLE IF EXISTS temp_item_code_purchase;

		CREATE TEMPORARY TABLE temp_item_code_purchase(id bigint(11), itemCode varchar(250),
		qty decimal(18,1), costPrice  double(18,2), cmdFlag char(1));

		INSERT INTO temp_item_code_purchase
		select a.id, a.itemCode, a.qty, a.costPrice,cmdFlag from tbl_inv_purchase_a a
		where a.companyId=paramCompanyId and a.itemCode=paramItemCode  order by itemCode, a.purchaseDate asc;

		SET @saleQty=ifnull((SELECT sum(a.qty) from tbl_inv_sale_record_detail a
		inner join tbl_inv_sale_record b on a.saleRecordId=b.saleRecordId
		where b.companyId=paramCompanyId and a.itemCode=paramItemCode group by a.itemCode),0);

		SET @counterInner=(SELECT COUNT(*) FROM temp_item_code_purchase);
        SET @issueQty=paramQty;

	   WHILE @counterInner > 0 DO

		SET @purchaseQty=(SELECT qty FROM temp_item_code_purchase
        ORDER BY itemCode asc LIMIT 1);

        IF @saleQty >= @purchaseQty THEN
            SET  @saleQty= @saleQty-@purchaseQty;
            DELETE FROM temp_item_code_purchase order by itemCode limit 1;
			SET @counterInner=(SELECT COUNT(*) FROM temp_item_code_purchase);
		ELSE
            SET @costPrice=(SELECT costPrice FROM temp_item_code_purchase
            ORDER BY itemCode asc LIMIT 1);

			IF((@purchaseQty=@issueQty AND @saleQty=0) OR (@purchaseQty >= (@issueQty + @saleQty)))THEN
				SET totalAmount=totalAmount+(@costPrice *  @issueQty);
				SET @counterInner=0;
			END IF;

			IF((@issueQty + @saleQty)  > @purchaseQty ) THEN

               SET @remaingQty=(@purchaseQty-@saleQty);
				SET totalAmount=totalAmount+(@costPrice *  @remaingQty);
				SET @issueQty=@issueQty-@remaingQty;
				SET @saleQty=0;

				DELETE FROM temp_item_code_purchase order by itemCode limit 1;
				SET @counterInner=(SELECT COUNT(*) FROM temp_item_code_purchase);
			  END IF;
        END IF;
	END WHILE;

    SELECT totalAmount;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_fa_fixed_asset_schedule`(paramCompanyId INT(11),paramAsOnDate Date)
BEGIN
DROP TEMPORARY TABLE IF EXISTS temp_fa_class_group;
CREATE TEMPORARY TABLE temp_fa_class_group(assetClassId int,  accTypeName varchar(250));

DROP TEMPORARY TABLE IF EXISTS temp_fa_class_sub_group;
CREATE TEMPORARY TABLE temp_fa_class_sub_group(assetId bigint(11), assetDetailId bigint(11),  particular varchar(250));

DROP TEMPORARY TABLE IF EXISTS temp_final_fixed_asset_schedule;
CREATE TEMPORARY TABLE temp_final_fixed_asset_schedule(
		assetClassId int,faPurchaseId bigint(11),
		assetDetailId bigint(11), particular varchar(250),
		purchaseDate date,
		rate double(18,2), rateOfDep double,
		asOnStartFinancialYear double,
		addition double,
		disposal double,
		asOnEndFinancialYear double,
		depAsOnStartFinancialYear double,
		depCurrentYear double,
		depAsOnEndFinancialYear double,
		netValue double,
		groupId int);

DROP TEMPORARY TABLE IF EXISTS temp_fixed_asset_schedule;
CREATE TEMPORARY TABLE temp_fixed_asset_schedule(assetClassId int,faPurchaseId bigint(11),
	assetDetailId bigint(11),
	particular varchar(250), purchaseDate date,
	rate double(18,2),
	rateOfDep double,
	asOnStartFinancialYear double,
	addition double,
	disposal double, asOnEndFinancialYear double,
	depAsOnStartFinancialYear double, depCurrentYear double,
	depAsOnEndFinancialYear double,
	netValue double,depreciatedValue double, groupId int);

	INSERT INTO temp_fa_class_group
	SELECT
	a.assetClassId,
	b.accTypeName FROM
	tbl_fa_group a
	INNER JOIN tbl_acc_acctype b ON a.accTypeId=b.accTypeId
	INNER JOIN (SELECT  assetClassId, assetId FROM tbl_fa_item_setup group by assetClassId) c ON c.assetClassId=a.assetClassId
	INNER JOIN tbl_fa_item_setup_detail d ON c.assetId=d.assetId
	INNER JOIN tbl_fa_purchase e ON e.assetDetailId=d.assetDetailId GROUP BY a.assetClassId;

	SET @financialYearTo=(SELECT financialYearTo FROM tbl_financial_year_setup
	where companyId=paramCompanyId and status='A');

	SET @financialYearFrom=(SELECT financialYearFrom FROM tbl_financial_year_setup
	where companyId=paramCompanyId and status='A');

	SET @noOfDaysInYear= (SELECT DAYOFYEAR(@financialYearTo));
	SET @asOnDateDayDiff= (select datediff(paramAsOnDate,DATE_ADD(@financialYearFrom, INTERVAL -1 DAY)));

	SET @itemExists=(SELECT COUNT(*) FROM tbl_fa_purchase a
	INNER JOIN tbl_fa_purchase_mater b ON a.purchaseMasterId=b.purchaseMasterId
	INNER JOIN tbl_fa_purchase_detail c ON c.faPurchaseId=a.faPurchaseId
	where b.companyId=paramCompanyId and c.status <> 'S');

    IF(@itemExists>0) THEN
    SET @counter=(SELECT COUNT(*) FROM temp_fa_class_group);
	WHILE @counter>0 DO

	   SET @var_class_id=(SElECT assetClassId FROM temp_fa_class_group limit 1);

       INSERT INTO temp_final_fixed_asset_schedule
       select assetClassId, null,null, accTypeName, null, null,
       null, null, null, null, null, null, null, null, null,1
	   FROM temp_fa_class_group WHERE assetClassId=@var_class_id;

		# Opening
		INSERT INTO temp_fixed_asset_schedule
		SELECT d.assetClassId, a.faPurchaseId,
		a.assetDetailId, b.particular,
		a.purchaseDate, rate AS rate,
		CASE WHEN d.accTypeId=24 THEN  0.03 ELSE 0.15 END AS rateOfDep,
		a.openingBalance AS asOnStartFinancialYear,
		null AS addition, null AS disposal,
		null AS asOnEndFinancialYear,null AS depAsOnStartFinancialYear,
		null AS depCurrentYear, null AS depAsOnEndFinancialYear,
		null AS netValue, depreciatedValue AS depreciatedValue, 2 AS groupId
		FROM tbl_fa_purchase a
		INNER JOIN tbl_fa_item_setup_detail b ON a.assetDetailId=b.assetDetailId
		INNER JOIN tbl_fa_item_setup c ON c.assetId=b.assetId
		INNER JOIN tbl_fa_group d ON c.assetClassId=d.assetClassId
		INNER JOIN  (SELECT faPurchaseId FROM tbl_fa_purchase_detail WHERE status='N' group by faPurchaseId) e ON e.faPurchaseId=a.faPurchaseId
		WHERE a.openingBalance IS NOT NULL AND d.assetClassId=@var_class_id AND c.companyId=paramCompanyId
		GROUP BY a.assetDetailId, a.rate
        UNION
		# addition
		SELECT d.assetClassId,
        a.faPurchaseId, a.assetDetailId,
        b.particular, a.purchaseDate, rate AS rate,
        CASE WHEN d.accTypeId=24 THEN  0.03 ELSE 0.15 END AS rateOfDep,
        null, null  AS addition, null, null, null, null AS depCurrentYear,
        null AS depAsOnEndFinancialYear, null AS netValue, null, 2 AS groupId
  		FROM tbl_fa_purchase a
		INNER JOIN tbl_fa_item_setup_detail b ON a.assetDetailId=b.assetDetailId
		INNER JOIN tbl_fa_item_setup c ON c.assetId=b.assetId
		INNER JOIN tbl_fa_group d ON c.assetClassId=d.assetClassId
		INNER JOIN  (SELECT  faPurchaseId FROM tbl_fa_purchase_detail WHERE status='N' group by faPurchaseId) e ON e.faPurchaseId=a.faPurchaseId
		WHERE a.openingBalance IS NULL AND d.assetClassId=@var_class_id AND c.companyId=paramCompanyId and a.purchaseDate <= paramAsOnDate
		GROUP BY a.assetDetailId, a.rate;

	    SET @inner_counter=(SELECT COUNT(*) FROM temp_fixed_asset_schedule);
	    WHILE @inner_counter>0 DO

		SET @var_fa_purchase_id=(SELECT faPurchaseId FROM temp_fixed_asset_schedule LIMIT 1);
        SET @var_asset_class_id=(SELECT assetClassId FROM temp_fixed_asset_schedule WHERE faPurchaseId=@var_fa_purchase_id);
        SET @var_particular=(SELECT particular FROM temp_fixed_asset_schedule WHERE faPurchaseId=@var_fa_purchase_id);
        SET @var_group_id=(SELECT groupId FROM temp_fixed_asset_schedule WHERE faPurchaseId=@var_fa_purchase_id);
        SET @var_asset_detail_id=(SELECT assetDetailId FROM temp_fixed_asset_schedule WHERE faPurchaseId=@var_fa_purchase_id);
        SET @var_purchase_date=(SELECT purchaseDate FROM temp_fixed_asset_schedule WHERE faPurchaseId=@var_fa_purchase_id);
        SET @var_rate_Of_dep=(SELECT rateOfDep FROM temp_fixed_asset_schedule WHERE faPurchaseId=@var_fa_purchase_id);
        SET @var_qty=(SELECT COUNT(*) FROM tbl_fa_purchase_detail WHERE status='N' and faPurchaseId=@var_fa_purchase_id group by faPurchaseId);
        SET @var_sold_qty=IFNULL((SELECT COUNT(*) FROM tbl_fa_purchase_detail m inner join tbl_fa_purchase n ON m.faPurchaseId=n.faPurchaseId where n.faPurchaseId=@var_fa_purchase_id and status='S' GROUP BY n.faPurchaseId),0);
        SET @var_rate=(SELECT rate FROM temp_fixed_asset_schedule WHERE faPurchaseId=@var_fa_purchase_id);
		SET @var_total_amount=(@var_qty * @var_rate);
        SET @var_as_on_start_financial_year=(SELECT asOnStartFinancialYear FROM temp_fixed_asset_schedule WHERE faPurchaseId=@var_fa_purchase_id);
		SET @var_depreciated_value=(SELECT depreciatedValue FROM temp_fixed_asset_schedule WHERE faPurchaseId=@var_fa_purchase_id);
        SET @var_total_qty=(SELECT COUNT(*) FROM tbl_fa_purchase_detail m inner join tbl_fa_purchase n ON m.faPurchaseId=n.faPurchaseId
        where n.faPurchaseId=@var_fa_purchase_id GROUP BY n.faPurchaseId);

        SET @var_total_sold_qty=IFNULL((SELECT COUNT(*) FROM tbl_fa_purchase_detail m inner join tbl_fa_purchase n ON m.faPurchaseId=n.faPurchaseId where n.faPurchaseId=@var_fa_purchase_id and status='S' GROUP BY n.faPurchaseId),0);
        SET @var_disposal=(SELECT SUM(netAmount) FROM tbl_fa_sale_record_detail x INNER JOIN tbl_fa_purchase_detail y on x.assetCode=y.assetCode
		INNER JOIN tbl_fa_purchase z on y.faPurchaseId=z.faPurchaseId WHERE z.faPurchaseId=@var_fa_purchase_id GROUP BY z.faPurchaseId);

		SET @var_as_on_end_financial_year=ROUND(((@var_total_amount * @var_rate_Of_dep) / @noOfDaysInYear) * @asOnDateDayDiff,2);
        SET @var_dep_as_on_start_financial_year=ROUND(((@var_depreciated_value - ((@var_depreciated_value / @var_total_qty)) * @var_total_sold_qty)),2);
        SET @var_dep_current_year=ROUND(((@var_total_amount * @var_rate_Of_dep) / @noOfDaysInYear) * @asOnDateDayDiff,2);
        SET @var_dep_as_on_end_financial_year=ROUND((@var_dep_as_on_start_financial_year  + @var_as_on_end_financial_year),2);

		IF( @var_as_on_start_financial_year IS NULL) THEN
        		SET @var_as_on_end_financial_year=null;
                SET @var_addition=@var_total_amount;
				SET @var_dep_current_year=ROUND(((@var_total_amount * @var_rate_Of_dep) / @noOfDaysInYear) * (select DATEDIFF(paramAsOnDate, @var_purchase_date)),2);
				SET @var_dep_as_on_end_financial_year= @var_dep_current_year;
				SET @var_net_value=ROUND((@var_total_amount - @var_dep_as_on_end_financial_year),2);
		ELSE
        		SET @var_addition=null;
				SET @var_dep_current_year=ROUND((((@var_rate * @var_qty) * @var_rate_Of_dep) / @noOfDaysInYear) * @asOnDateDayDiff,2);
				SET @var_dep_as_on_end_financial_year=ROUND((@var_dep_as_on_start_financial_year  + @var_as_on_end_financial_year),2);
				SET @var_net_value=ROUND((@var_total_amount - @var_dep_as_on_end_financial_year),2);
		END IF;

        INSERT INTO temp_final_fixed_asset_schedule values(@var_asset_class_id,
        @var_fa_purchase_id,@var_asset_detail_id,@var_particular,
        @var_purchase_date,@var_total_amount, @var_rate_Of_dep, @var_as_on_start_financial_year,
		@var_addition,@var_disposal,@var_as_on_end_financial_year,@var_dep_as_on_start_financial_year,
		@var_dep_current_year,@var_dep_as_on_end_financial_year,@var_net_value,@var_group_id);

        SET SQL_SAFE_UPDATES = 0;
		DELETE FROM temp_fixed_asset_schedule LIMIT 1;
		SET @inner_counter=(SELECT COUNT(*) FROM temp_fixed_asset_schedule);
	   END WHILE;

		SET SQL_SAFE_UPDATES = 0;
		DELETE FROM temp_fa_class_group WHERE assetClassId=@var_class_id;
		SET @counter=(SELECT COUNT(*) FROM temp_fa_class_group);
	END WHILE;
    END IF;
  SELECT * FROM temp_final_fixed_asset_schedule; -- GROUP BY assetDetailId, rate;
END