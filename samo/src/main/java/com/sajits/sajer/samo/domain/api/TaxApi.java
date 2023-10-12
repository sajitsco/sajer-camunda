package com.sajits.sajer.samo.domain.api;

import com.sajits.sajer.samo.domain.dto.*;
import com.sajits.sajer.samo.infrastructure.dto.AsyncResponseModel;
import com.sajits.sajer.samo.infrastructure.exception.TaxApiException;

import java.util.List;

public interface TaxApi {

    AsyncResponseModel sendInvoices(List<InvoiceDto> invoices) throws TaxApiException;

    TokenModel requestToken() throws TaxApiException;

    ServerInformationModel getServerInformation() throws TaxApiException;

    List<InquiryResultModel> inquiryByUidAndFiscalId(List<UidAndFiscalId> uidAndFiscalIds) throws TaxApiException;

    List<InquiryResultModel> inquiryByTime(String persianTime) throws TaxApiException;

    List<InquiryResultModel> inquiryByTimeRange(String startDatePersian, String toDatePersian) throws TaxApiException;

    List<InquiryResultModel> inquiryByReferenceId(List<String> referenceIds) throws TaxApiException;

    FiscalInformationModel getFiscalInformation(String fiscalId) throws TaxApiException;

    SearchResultModel<ServiceStuffModel> getServiceStuffList(SearchDto searchDTO) throws TaxApiException;

    EconomicCodeModel getEconomicCodeInformation(String economicCode) throws TaxApiException;
}
