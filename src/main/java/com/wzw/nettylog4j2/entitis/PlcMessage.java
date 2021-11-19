package com.wzw.nettylog4j2.entitis;

import lombok.Data;

@Data
public class PlcMessage {
    private String DataContext;
    private byte[] sendBytes;
    private int messageType;
    private int length;
}
