package com.sajits.sajer.samo.application;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.sajits.sajer.samo.domain.PreInvoice;
import com.sajits.sajer.samo.domain.api.DefaultTaxApiClient;
import com.sajits.sajer.samo.domain.api.TaxApi;
import com.sajits.sajer.samo.domain.dto.InquiryResultModel;
import com.sajits.sajer.samo.domain.dto.InvoiceBodyDto;
import com.sajits.sajer.samo.domain.dto.InvoiceDto;
import com.sajits.sajer.samo.domain.dto.InvoiceHeaderDto;
import com.sajits.sajer.samo.domain.dto.PaymentDto;
import com.sajits.sajer.samo.domain.dto.UidAndFiscalId;
import com.sajits.sajer.samo.infrastructure.api.ObjectTransferApiImpl;
import com.sajits.sajer.samo.infrastructure.api.TransferApi;
import com.sajits.sajer.samo.infrastructure.config.ApiConfig;
import com.sajits.sajer.samo.infrastructure.dto.AsyncResponseModel;
import com.sajits.sajer.samo.infrastructure.dto.PacketResponse;
import com.sajits.sajer.samo.infrastructure.impl.encrypter.DefaultEncrypter;
import com.sajits.sajer.samo.infrastructure.impl.signatory.InMemorySignatory;

import static org.camunda.spin.Spin.JSON;

@Component("sendInvoices")
public class SendInvoices implements JavaDelegate {

    private TaxApi taxApi;

    private static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDu1qsvRm/K4ODsRzX1ZCijdZyUpi9PjwjJ+sivn+Y1EsuKGwVANRk6T+9RyEHFIhKj7GAw3AwVs2yq8qVhc7RtD8zUtn2TIcGpuVx7JG54+N5pDYbLE2y6xV4CuT65E+9SnCn24WU3yGY4sVs1VaZyUNP9dLhgq8927uwYWkHcADxs/ogz9pXYvg9a8mmfPPRUmUDmhM8NjE7GzWqVUOk0z+5xd6s8IFLphzqsLYIJwUi9GnKNjINuITuNybeFp9rm5a/6R8PMLeHzvfVr6MO9yuZXkN0Q2nz9LSxW/CtGktn+P0cbDN7lOilyMj3090vZqH7lPvbu6utv4iQFFWAzAgMBAAECggEAWJYe4GYLyQAn7PtstBv2Rsq0zR3mpmezJtdx4FI6nKzf+dWT2QDY3pwPdBIG8wt0RoDCHN1BTjtCrxVY6hB0d49Itg0J/qyinEU4XCRjJVOJiwWNXX1S8uOJvxDeht8yAT4TApMPa+VUpOqDngCqv/Pi7TuBt7mCNebggxfXHbXxKzI1CUhdeOmlNGcikESxG6yACViqSK12pEZ7l2I+YwhJdZBz0vh+of+8fCdIJ74kjxd/Rps8zhbUs0vTez8J1Wh7id1xX2PatuPWtMmpnLzjgrpEhRpXMhLb4zHvAhGDZ5LfNncA8BmP0d6CUaCzxFz8Ay/75tRvOwK3pIAfYQKBgQD/roxHIQ+O+6JK8u91EJ65zj24S/VPbLWTS5OJDObTmy88zZZlydM7gavLiTgaTCOL0PEVbP03jhnGTsfStprTze4IkKjtrP/8SlQ+9bDRJAovNydz1oXF1N0Oatc2ffup5S1ZGpbYf1FLXG3H1hzH6G/8Vzoq22ke4t1z/W06BwKBgQDvIsFHvDE5UTeXGWaYI4qgIQcIJFjy/1SHJHoKVVaafo38GXnLii7pi9mYt1Q6TAUnfQhP4KDI7vISGeOIpZKDiH4nB5/7WjoiNcrL5bjpj3KcbDhMzKWNXTOeeDG9QutMT5lrezFob1HN6iR29iuNHVGc90qxoi811GbFcCyNdQKBgCmImzT6w+JAMi7mo8tqdb5NKG2FuUW7rQtOXZsHw39gWB4Sb3n9fRjPia64jaqH78ZInMEUQVYC+WDjb0Zlb2U0sJQnm6lwTu0WzfSkSKLQre7ZkMkacgSe/YHYDTnG4VjJ58Hvtc1ZcUVCsHqj52z57ycYrFRF7B+eTscGxfWFAoGBAIOfNZMuWkmaEmbwbC/BIEQS6bPpjavyLaxUv3eAkTJoBe6Edy042alieFo+TfXocc3SXAjbMqRmIIcmCL7lo0cSkZ0Z7UyD1QuPYBfqUWT8t7CxQCvFWDn+2rKWpYnuk2RlS6SFIiTBYN1dysRPqpj5Ujp7QnmTMzkFt6tkmqEhAoGBAOVFPu/uzmH2KCjiCF7FeOXTuXUp9fQRle6ndfhqTV3aT3fG8Gbv2YvDN32nGAli1kUL1iY5UyoJK8EoWFDg8jLJPO/Vsto/vSsNCmQxW7t1+42aYlv+4ryAoIh9MClfRvyeG1l7MBPXuinqM4i9ngR5/JCszp1UqIHz1e3vVUqO";
    private static final String ORG_PUBLIC_KEY = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAxdzREOEfk3vBQogDPGTMqdDQ7t0oDhuKMZkA+Wm1lhzjjhAGfSUOuDvOKRoUEQwP8oUcXRmYzcvCUgcfoRT5iz7HbovqH+bIeJwT4rmLmFcbfPke+E3DLUxOtIZifEXrKXWgSVPkRnhMgym6UiAtnzwA1rmKstJoWpk9Nv34CYgTk8DKQN5jQJqb9L/Ng0zOEEtI3zA424tsd9zv/kP4/SaSnbbnj0evqsZ29X6aBypvnTnwH9t3gbWM4I9eAVQhPYClawHTqvdaz/O/feqfm06QBFnCgL+CBdjLs30xQSLsPICjnlV1jMzoTZnAabWP6FRzzj6C2sxw9a/WwlXrKn3gldZ7Ctv6Jso72cEeCeUI1tzHMDJPU3Qy12RQzaXujpMhCz1DVa47RvqiumpTNyK9HfFIdhgoupFkxT14XLDl65S55MF6HuQvo/RHSbBJ93FQ+2/x/Q2MNGB3BXOjNwM2pj3ojbDv3pj9CHzvaYQUYM1yOcFmIJqJ72uvVf9Jx9iTObaNNF6pl52ADmh85GTAH1hz+4pR/E9IAXUIl/YiUneYu0G4tiDY4ZXykYNknNfhSgxmn/gPHT+7kL31nyxgjiEEhK0B0vagWvdRCNJSNGWpLtlq4FlCWTAnPI5ctiFgq925e+sySjNaORCoHraBXNEwyiHT2hu5ZipIW2cCAwEAAQ==";
    private static final String ORG_KEY_ID = "6a2bcd88-a871-4245-a393-2843eafe6e02";
    private static final String CLIENT_ID = "A1638O";

    public void execute(DelegateExecution delegate) {
        Object invo = delegate.getVariable("preInvoice");
        if (invo != null) {
            System.out.println(invo);
            PreInvoice inv = JSON(invo).mapTo(PreInvoice.class);

            // Long t1 = 91743119L;
            // Long t2 = 8256881L;
            // Long t3 = 20000000L;
            Long t1 = inv.getTadis();
            Long t2 = inv.getTvam();
            Long t3 = t1 + t2;

            ApiConfig apiConfig = new ApiConfig().encrypter(new DefaultEncrypter(ORG_PUBLIC_KEY, ORG_KEY_ID))
                    .signatory(new InMemorySignatory(PRIVATE_KEY, null));
            TransferApi transferApi = new ObjectTransferApiImpl(apiConfig);
            this.taxApi = new DefaultTaxApiClient(transferApi, CLIENT_ID);
            taxApi.requestToken();
            // dbb57208-bb32-4986-bbb5-81c8b112b4fe
            // 69dde9f8-f9f4-47c9-91dc-978b077439ba
            // c5d9d268-f613-4f7e-a76b-405d9405973f
            // 261d5f72-3296-4c5d-b6b4-d4d33416d1c9
            // 5e5e3f70-170c-4fd7-b8ce-c2ff0da2fda2
            // 33bc7a8c-610c-4cd9-8af5-f17b9d187133
            ////Pending 1e9f6b9e-71b9-4dca-a4d1-5b02a457d20a   7
            ////Pending ce6682bc-126a-4faa-9251-5b76df5c4279   8
            // 6a736e27-f97d-4d16-af58-3d5bb22d5b0f   9
            //69d5f3ef-0e13-49ff-b5a0-577a13551939   7
            ////Pending b708bd71-537d-4639-b09d-e1cb56c45af0   7
            ////Pending 2900d1ec-e46f-4c8b-b126-37696e98367a   7
            //14ce2db6-5a3b-4dc8-a80f-7e2538f358fc   8
            //b2d6c543-0df1-45d8-b188-845113b8506f   10
            //57aed756-8b14-462e-a9c3-8424302305c2   11
            //e1c6f667-0e44-4437-bc70-8638f9265a72   12
            //ad5d9308-4154-4c25-9465-d18e36b93469   13
            //19f14b95-8eaa-4c9c-8b29-483135b16ab3   14
            // Random random = new Random();
            long time1 = inv.getTime();
            long randomSerialDecimal = 14;// random.nextInt(999999999);
            Instant invoiceCreatedDate = Instant.ofEpochMilli(time1);
            Instant invoiceCreatedDate2 = Instant.ofEpochMilli(time1 + 60000 * 5);// Instant.now();
            String taxId = TaxUtils.generateTaxId(CLIENT_ID, randomSerialDecimal, invoiceCreatedDate2);
            InvoiceHeaderDto header = new InvoiceHeaderDto();
            header.setIndatim(invoiceCreatedDate.toEpochMilli());
            // header.setIndati2m(invoiceCreatedDate2.toEpochMilli());
            header.setTaxid(taxId);// shumare monhaser be fard maliyati
            header.setInty(2);// Noe surat hesab 1
            header.setInp(1);// Olguye surat hesab 1
            header.setInno(inv.getInno());// serial surat hesab daxeli hafezeye maliyati
            header.setIns(1);
            header.setTins(inv.getTins());
            // header.setTob(5);// Noe shaxs xaridar
            header.setTprdis(BigDecimal.valueOf(t1));// majmo gabl az taxfif
            header.setTdis(BigDecimal.ZERO);// majmo taxfif
            header.setTadis(BigDecimal.valueOf(t1));// majmo pas az taxfif
            header.setTvam(BigDecimal.valueOf(t2));// majmoe maliyat bar arzesh afzude
            header.setTodam(BigDecimal.ZERO);// majmoe sayer vojuh ganuni
            header.setTbill(BigDecimal.valueOf(t3));// majmoe surat hesab
            // header.setSetm(1);
            // header.setCap(BigDecimal.valueOf(20000000));
            // header.setInsp(BigDecimal.ZERO);
            // header.setTvop(BigDecimal.ZERO);
            // header.setTax17(BigDecimal.ZERO);

            // header.setDpvb(1);
            int tAm = 1;

            InvoiceBodyDto body = new InvoiceBodyDto();
            body.setSstid(inv.getSstid());
            body.setSstt(inv.getSstt());
            body.setMu("1627");
            body.setAm(((double) tAm));
            body.setFee(BigDecimal.valueOf(t1 / tAm));
            body.setPrdis(BigDecimal.valueOf(t1));
            body.setDis(BigDecimal.ZERO);
            body.setAdis(BigDecimal.valueOf(t1));
            body.setVra(BigDecimal.valueOf(9));
            body.setVam(BigDecimal.valueOf(t2));
            body.setTsstam(BigDecimal.valueOf(t3));
            // body.setVop(0D);

            PaymentDto payment = new PaymentDto();
            payment.setIinn("581672141");
            payment.setAcn("00000095769253");
            payment.setTrmn("92798062");
            payment.setTrn("141024536838");// shomareye peygiri
            payment.setPdt(invoiceCreatedDate.toEpochMilli());

            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setBody(Collections.singletonList(body));
            // invoiceDto.setBody(new ArrayList<InvoiceBodyDto>());
            invoiceDto.setHeader(header);
            invoiceDto.setPayments(Collections.singletonList(payment));

            AsyncResponseModel responseModel = taxApi.sendInvoices(Collections.singletonList(invoiceDto));

            if (responseModel.getResult() != null &&
                    !responseModel.getResult().isEmpty()) {
                System.out.println("success send invoice, response" +
                        responseModel.getResult());
                PacketResponse packetResponse = responseModel.getResult().get(0);

                // List<String> refrences = new ArrayList<>();
                // refrences.add(packetResponse.getReferenceNumber());
                // List<InquiryResultModel> inquiryResultModels =
                // this.taxApi.inquiryByReferenceId(refrences);
                try {
                    Thread.sleep(34000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<UidAndFiscalId> uidAndFiscalIds = new ArrayList<>();
                UidAndFiscalId uid1 = new UidAndFiscalId();
                uid1.setFiscalId(CLIENT_ID);
                uid1.setUid(packetResponse.getUid());
                uidAndFiscalIds.add(uid1);
                List<InquiryResultModel> inquiryResultModels = this.taxApi.inquiryByUidAndFiscalId(uidAndFiscalIds);
                System.out.println(inquiryResultModels);
                // System.out.println(inquiryResultModels.get(0).getData());
                delegate.setVariable("results", org.camunda.spin.Spin.JSON(
                        inquiryResultModels.get(0).getData()).toString());

            } else {
                System.out.println(responseModel.getErrors());
            }
        }
        System.out.println("sendInvoices Task Executed");
    }
}