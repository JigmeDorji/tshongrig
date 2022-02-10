package com.spms.mvc.Enumeration;

/**
 * Created by Bcass Sawa on 10/26/2019.
 */
public enum AccountTypeEnum {

    /*Top parent Id*/
    NON_CURRENT_ASSET(1, "Non Current Asset"),
    CURRENT_ASSET(2, "Current Asset"),
    CAPITAL(3, "Capital"),
    NON_CURRENT_LIABILITY(4, "Non current Liability "),
    CURRENT_LIABILITY(5, "current Liability "),
    INCOME(6, "Income"),
    EXPENDITURE(7, "Expenditure"),
    PARENT_PURCHASE(8, "Purchase"),
    PARENT_SALE(9, "Sale"),

    /*Account type under top parent*/
    FURNITURE_FIXTURE(1, "Furniture Fixture "),
    RECEIVABLE(2, "Receivable"),
    BANK(3, "Bank"),
    CASH(4, "Cash"),
    INVENTORY(5, "Inventory"),
    EQUITY_OR_CAPITAL(6, "Equity/Capital"),
    SECURED_LOAN(7, "Loans"),
    UN_SECURED_LOAN(8, "Unsecured Loans"),
    PAYABLE(9, "Payable"),
    SHORT_TERM_LOAN(10, "Short term loans"),
    BANK_OVER_DRAFT(11, "Bank over draft"),
    DIRECT_INCOME(12, "Direct Income"),
    OTHER_INCOME(13, "Other Income"),
    DIRECT_COST(14, "Direct Cost"),
    INDIRECT_COST(15, "Indirect Cost"),
    PURCHASE(16, "Purchase"),
    SALES(17, "Sales"),
    EMPLOYEE_ADVANCE(18, "Employee Advance"),
    PARTY_ADVANCE_PAID(19, "Party Advance Paid"),
    PARTY_ADVANCE_RECEIVED(20, "Party Advance Received"),
    PLANT_AND_MACHINERY(21, "Plant and machinery"),
    VEHICLES(22, "Vehicles"),
    OFFICE_EQUIPMENT(23, "Office Equipment"),
    BUILDING_AND_AMENITIES(24, "Building and Amenities"),
    MATERIAL(25, "Material"),
    RAW_MATERIAL(26, "Raw Material"),
    RETENTION(27, "Retention"),
    MOBILIZATION_ADV(28, "Mobilization Advance"),
    MATERIAL_ADV(29, "Material Advance");

    private final Integer value;
    private final String text;

    AccountTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public static String find(Integer value) {
        for (AccountTypeEnum accountTypeEnum : values()) {
            if (accountTypeEnum.getValue().equals(value)) {
                return accountTypeEnum.getText();
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
