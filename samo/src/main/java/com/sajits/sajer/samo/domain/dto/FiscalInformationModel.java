package com.sajits.sajer.samo.domain.dto;

import com.sajits.sajer.samo.domain.enumeration.FiscalStatus;

public class FiscalInformationModel {

    private String nameTrade;
    private FiscalStatus fiscalStatus;
    private String codeEconomic;

    public String getNameTrade() {
        return nameTrade;
    }

    public void setNameTrade(String nameTrade) {
        this.nameTrade = nameTrade;
    }

    public FiscalStatus getFiscalStatus() {
        return fiscalStatus;
    }

    public void setFiscalStatus(FiscalStatus fiscalStatus) {
        this.fiscalStatus = fiscalStatus;
    }

    public String getCodeEconomic() {
        return codeEconomic;
    }

    public void setCodeEconomic(String codeEconomic) {
        this.codeEconomic = codeEconomic;
    }

    @Override
    public String toString() {
        return "FiscalInformationModel{" +
                "nameTrade='" + nameTrade + '\'' +
                ", fiscalStatus=" + fiscalStatus +
                ", codeEconomic='" + codeEconomic + '\'' +
                '}';
    }
}
