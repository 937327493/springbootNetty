package com.wzw.nettylog4j2.code;

public class SequenctManager {
    private static int sequence;
    //生成sequence
    public static int generateSequenceForClient(){
        if(sequence < 1){
            sequence = 1;
        }else if(sequence > 65535){
            sequence = 1;
        }
        else {
            ++sequence;
        }
        return sequence;
    }
}
