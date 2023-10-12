package com.sajits.sajer.samo.infrastructure.api;

import com.sajits.sajer.samo.infrastructure.config.ApiConfig;
import com.sajits.sajer.samo.infrastructure.dto.AsyncResponseModel;
import com.sajits.sajer.samo.infrastructure.dto.PacketDto;
import com.sajits.sajer.samo.infrastructure.dto.SyncResponseModel;

import java.util.List;
import java.util.Map;

public interface TransferApi {

    ApiConfig getConfig();

    AsyncResponseModel sendPackets(List<PacketDto> packets, Map<String, String> headers, boolean encrypt, boolean sign);

    SyncResponseModel sendPacket(PacketDto packet, Map<String, String> headers, boolean encrypt, boolean sign);
}
