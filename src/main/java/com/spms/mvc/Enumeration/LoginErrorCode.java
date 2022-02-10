package com.spms.mvc.Enumeration;

/**
 * Component Name: Spare part management system
 * Name: LoginErrorCode
 * Description: See the description at the top of class declaration
 * Project: G2C
 * @author:bikash.rai
 * Creation: 04-May-16
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */


public enum LoginErrorCode {
    //region enum
    FAILED("userLogins.failed"),
    LOCKED("userLogins.locked"),
    MAX_SESSION("userLogins.maxSession");
    //endregion
//region private variables
    private final String code;
    //endregion

    //region method
    private LoginErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    //endregion
}
