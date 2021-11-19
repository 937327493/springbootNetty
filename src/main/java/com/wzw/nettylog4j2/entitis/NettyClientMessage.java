package com.wzw.nettylog4j2.entitis;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class NettyClientMessage {
    private List<PlcMessage> plcMessageList = new ArrayList<>();
    private int start = 0xFFFF;
    private int sequence;
    private int length;
    private int version = 0x0000;
}
