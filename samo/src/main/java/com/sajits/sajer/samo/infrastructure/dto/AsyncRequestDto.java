package com.sajits.sajer.samo.infrastructure.dto;

import java.util.List;

public class AsyncRequestDto extends SignedRequest {

    private List<PacketDto> packets;

    public List<PacketDto> getPackets() {
        return packets;
    }

    public void setPackets(List<PacketDto> packets) {
        this.packets = packets;
    }
}
