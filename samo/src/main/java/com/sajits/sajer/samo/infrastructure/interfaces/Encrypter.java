package com.sajits.sajer.samo.infrastructure.interfaces;

import com.sajits.sajer.samo.infrastructure.dto.PacketDto;
import com.sajits.sajer.samo.infrastructure.exception.TaxApiException;

import java.util.List;

public interface Encrypter {

    void encrypt(List<PacketDto> packets) throws TaxApiException;
}
