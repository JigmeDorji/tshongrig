package com.spms.mvc.dto;

/**
 * Created by jigme.dorji on 5/1/2021.
 */
public class TDSSlabDTO {
    private Double fromAmount;
    private Double toAmount;
    private Double tDSAmount;

    public Double getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(Double fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Double getToAmount() {
        return toAmount;
    }

    public void setToAmount(Double toAmount) {
        this.toAmount = toAmount;
    }

    public Double gettDSAmount() {
        return tDSAmount;
    }

    public void settDSAmount(Double tDSAmount) {
        this.tDSAmount = tDSAmount;
    }
}
