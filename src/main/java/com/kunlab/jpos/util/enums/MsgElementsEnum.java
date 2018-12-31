package com.kunlab.jpos.util.enums;

import com.kunlab.jpos.util.Constants;

import java.util.Arrays;

/**
 * 交易要素
 * @author likun
 */
public enum MsgElementsEnum {
    ECHO            ("回响测试", Constants.MSG_ECHO,"0820","00",
            new int[]{0,41,42,60},
            new int[]{0,12,13,39,41,42,60}, new int[]{}),

    CHECK_IN        ("签到",Constants.MSG_CHECK_IN,"0800","00",
            new int[]{0,11,41,42,60,63},
            new int[]{0,11,12,13,32,37,39,41,42,60,62}, new int[]{63}),

    SALE            ("消费",Constants.MSG_SALE,"0200","22",
            new int[]{0,3,4,11,22,25,41,42,49,60,64},
            new int[]{0,2,3,4,11,12,13,15,25,32,37,39,41,42,44,49,60,63}, new int[]{22,26,35,36,52}),

    SALE_REVERSAL   ("消费冲正", Constants.MSG_SALE_REVERSAL, "0400","22",
            new int[]{0,3,4,11,22,25,39,41,42,49,60,64},
            new int[]{0,2,3,4,11,12,13,15,25,32,37,39,41,42,44,49,60}, new int[]{22,38,62}),

    SALE_CANCEL     ("消费撤销",Constants.MSG_SALE_CANCEL,"0200","23",
            new int[]{0,3,4,11,22,25,37,41,42,49,60,61,64},
            new int[]{0,2,3,4,11,12,13,15,25,32,37,39,41,42,44,49,60,63},new int[]{22,26,35,36,52,55}),

    SALE_CANCEL_REVERSAL("消费撤销冲正", Constants.MSG_SALE_CANCEL_REVERSAL, "0400","23",
            new int[]{0,3,4,11,22,25,39,41,42,49,60,64},
            new int[]{0,2,3,4,11,12,13,15,25,32,37,39,41,42,44,49,60}, new int[]{22,38,55,61})
    ;

    private String name;
    private String code;

    private String msgType;             //消息类型(MTI)
    private String transType;           //交易类型码(60.1域)
    private int[] receiveField;         //对应交易必须上送的域
    private int[] sendField;            //对应交易必须返回域
    private int[] sendUnsetField;       //对应交易返回必须屏蔽域

    private MsgElementsEnum(String name, String code, String msgType, String transType,
                            int[] receiveField, int[] sendField, int[] sendUnsetField) {
        this.name = name;
        this.code = code;
        this.msgType = msgType;
        this.transType = transType;
        this.receiveField = receiveField;
        this.sendField = sendField;
        this.sendUnsetField = sendUnsetField;
    }

    /**
     * @param code
     * @return null if no Enum
     */
    public static MsgElementsEnum getEnum(String code) {
        for(MsgElementsEnum e : MsgElementsEnum.values()) {
            if(e.getCode().equals(code))
                return e;
        }

        return null;
    }

    /**
     * @param msgType
     * @param transType
     * @return null if no Enum
     */
    public static MsgElementsEnum getEnum(String msgType, String transType) {
        for(MsgElementsEnum e : MsgElementsEnum.values()) {
            if(e.getMsgType().equals(msgType) && e.getTransType().equals(transType))
                return e;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getMsgType() {
        return msgType;
    }

    public String getTransType() {
        return transType;
    }

    public int[] getReceiveField() {
        return receiveField;
    }

    public int[] getSendField() {
        return sendField;
    }

    public int[] getSendUnsetField() {
        return sendUnsetField;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getName());
        sb.append("|{");
        sb.append("name:'").append(name).append("', ");
        sb.append("code:'").append(code).append("', ");
        sb.append("msgType:'").append(msgType).append("', ");
        sb.append("transType:'").append(transType).append("', ");
        sb.append("receiveField:").append(Arrays.toString(receiveField)).append(", ");
        sb.append("sendField:").append(Arrays.toString(sendField));
        sb.append("sendUnsetField:").append(Arrays.toString(sendUnsetField));
        sb.append("}");
        return sb.toString();
    }
}
