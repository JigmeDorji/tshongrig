package com.spms.mvc.Enumeration;

/**
 * Description: TDSTypeEnum
 * Date:  2021-May-15
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-May-15
 * Change Description:
 * Search Tag:
 */
public enum TDSTypeEnum {

    BHUTANESE_CONTRACT(1, "Bhutanese Contract"),
    HIRING(2, "Hiring"),
    REAL_STATE(3, "Real Estate"),
    INTERNAL_CONTRACT(4, "International contract"),
    NOT_APPLICABLE(5, "Not applicable");

    private final Integer value;
    private final String text;

    TDSTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
