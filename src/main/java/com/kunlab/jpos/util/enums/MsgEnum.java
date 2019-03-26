package com.kunlab.jpos.util.enums;

import com.kunlab.jpos.util.Constants;

import java.util.Arrays;

/**
 * 消息枚举
 * @author likun
 */
public enum MsgEnum {
    ECHO            ("回响测试", Constants.MSG_ECHO,
            "0820","00", "301",
            new int[]{0,41,42,60},
            new int[]{0,12,13,39,41,42,60}, new int[]{}),

    CHECK_IN_DES    ("签到(单倍长)", Constants.MSG_CHECK_IN_DES,
            "0800", "00", "001",
            new int[]{0,11,41,42,60,63},
            new int[]{0,11,12,13,32,37,39,41,42,60,62}, new int[]{63}),

    CHECK_IN_TDES   ("签到(TDES双倍长)", Constants.MSG_CHECK_IN_TDES,
            "0800", "00", "003",
            new int[]{0,11,41,42,60,63},
            new int[]{0,11,12,13,32,37,39,41,42,60,62}, new int[]{63}),

    CHECK_IN_TDES_T ("签到(TDES双倍长含磁道密钥)", Constants.MSG_CHECK_IN_TDES_T,
            "0800", "00", "004",
            new int[]{0,11,41,42,60,63},
            new int[]{0,11,12,13,32,37,39,41,42,60,62}, new int[]{63}),

    PARAM_IC_PUBLIC ("IC卡公钥下载", Constants.PARAM_IC_PUBLIC,
            "0800", "00", "370",
            new int[]{0, 41,42,60}, new int[]{0,12,13,37,39,42,60}, new int[]{}),

    PARAM_IC_PUBLIC_F("IC卡公钥下载结束", Constants.PARAM_IC_PUBLIC_FINISH,
            "0800", "00", "371",
            new int[]{0, 41,42,60}, new int[]{0,12,13,37,39,42,60}, new int[]{}),

    PARAM_IC        ("IC卡参数下载", Constants.PARAM_IC,
            "0800", "00", "380",
            new int[]{0, 41,42,60}, new int[]{0,12,13,37,39,42,60}, new int[]{}),

    PARAM_IC_F      ("IC卡参数下载结束", Constants.PARAM_IC_FINISH,
            "0800", "00", "381",
            new int[]{0, 41,42,60}, new int[]{0,12,13,37,39,42,60}, new int[]{}),

    PARAM_NON       ("非接业务控制参数下载", Constants.PARAM_NON,
            "0800", "00", "394",
            new int[]{0, 41, 42, 60}, new int[]{0,12,13,37,39,42,60}, new int[]{}),

    PARAM_NON_F     ("非接业务控制参数下载结束", Constants.PARAM_NON_FINISH,
            "0800", "00", "395",
            new int[]{0, 41, 42, 60}, new int[]{0,12,13,37,39,42,60}, new int[]{}),

    SALE            ("消费", Constants.MSG_SALE,
            "0200", "22", "000",
            new int[]{0,3,4,11,22,25,41,42,49,60,64},
            new int[]{0,2,3,4,11,12,13,15,25,32,37,39,41,42,44,49,55,60,63}, new int[]{22,26,35,36,52}),

    SALE_REVERSAL   ("消费冲正", Constants.MSG_SALE_REVERSAL,
            "0400", "22", "000",
            new int[]{0,3,4,11,22,25,39,41,42,49,60,64},
            new int[]{0,2,3,4,11,12,13,15,25,32,37,39,41,42,44,49,55,60}, new int[]{22,38,62}),

    SALE_CANCEL     ("消费撤销", Constants.MSG_SALE_CANCEL,
            "0200", "23", "000",
            new int[]{0,3,4,11,22,25,37,41,42,49,60,61,64},
            new int[]{0,2,3,4,11,12,13,15,25,32,37,39,41,42,44,49,55,60,63},new int[]{22,26,35,36,52}),

    SALE_CANCEL_REVERSAL("消费撤销冲正", Constants.MSG_SALE_CANCEL_REVERSAL,
            "0400", "23", "000",
            new int[]{0,3,4,11,22,25,39,41,42,49,60,64},
            new int[]{0,2,3,4,11,12,13,15,25,32,37,39,41,42,44,49,55,60}, new int[]{22,38,55,61})
    ;

    private String name;                //业务名称
    private String code;                //业务代码

    private String msgType;             //消息类型(MTI)
    private String transCode;           //交易码(60.1)
    private String netCode;             //网络管理码(60.3)
    private int[] receiveField;         //对应交易必须上送的域
    private int[] sendField;            //对应交易必须返回域
    private int[] sendUnsetField;       //对应交易返回必须屏蔽域

    private MsgEnum(String name, String code,
                    String msgType, String transCode, String netCode,
                    int[] receiveField, int[] sendField, int[] sendUnsetField) {
        this.name = name;
        this.code = code;
        this.msgType = msgType;
        this.transCode = transCode;
        this.netCode = netCode;
        this.receiveField = receiveField;
        this.sendField = sendField;
        this.sendUnsetField = sendUnsetField;
    }

    public static MsgEnum getEnum(String code) {
        for(MsgEnum e : MsgEnum.values()) {
            if(e.getCode().equals(code))
                return e;
        }

        throw new IllegalArgumentException("No Enum Code '" + code + "'. " + MsgEnum.class);
    }

    public static MsgEnum getEnum(String msgType, String transCode, String netCode) {
        for(MsgEnum e : MsgEnum.values()) {
            if(e.getMsgType().equals(msgType) && e.getTransCode().equals(transCode)
                    && e.getNetCode().equals(netCode))
                return e;
        }
        throw new IllegalArgumentException("No Enum msgType '" + msgType + "' and msgCode '" + transCode + "' and '" + netCode + "'. "
                + MsgEnum.class);
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

    public String getTransCode() {
        return transCode;
    }

    public String getNetCode() {
        return netCode;
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
        sb.append("transCode:'").append(transCode).append("', ");
        sb.append("netCode:'").append(netCode).append("', ");
        sb.append("receiveField:").append(Arrays.toString(receiveField)).append(", ");
        sb.append("sendField:").append(Arrays.toString(sendField)).append(", ");
        sb.append("sendUnsetField:").append(Arrays.toString(sendUnsetField));
        sb.append("}");
        return sb.toString();
    }
}
