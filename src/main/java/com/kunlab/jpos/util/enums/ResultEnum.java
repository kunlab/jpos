package com.kunlab.jpos.util.enums;


/**
 * @author likun
 */
public enum ResultEnum {
    RS_00("00", "成功或受理"),
    RS_01("01", "查发卡行"),
    RS_03("03", "无效商户"),
    RS_04("04", "没收卡"),
    RS_05("05", "身份认证失败，发卡行不予承兑"),
    RS_10("10", "部分金额批准，请收取余额"),
    RS_11("11", "此为VIP客户"),
    RS_12("12", "无效的关联交易"),
    RS_13("13", "无效金额"),
    RS_14("14", "无效卡号（无此账号）"),
    RS_15("15", "无此发卡方"),
    RS_21("21", "卡未初始化"),
    RS_22("22", "故障怀疑，关联交易错误"),
    RS_25("25", "找不到原始交易"),
    RS_30("30", "报文格式错 | {}"),
    RS_34("34", "有作弊嫌疑"),
    RS_38("38", "超过允许的PIN试输入"),
    RS_40("40", "功能尚不支持"),
    RS_41("41", "挂失卡"),
    RS_43("43", "被窃卡"),
    RS_45("45", "不允许降级交易"),
    RS_51("51", "资金不足"),
    RS_54("54", "过期的卡"),
    RS_55("55", "不正确的PIN"),
    RS_57("57", "不允许持卡人进行的交易"),
    RS_58("58", "该商户不允许进行的交易"),
    RS_59("59", "卡片校验错"),
    RS_61("61", "超出金额限制"),
    RS_62("62", "受限制的卡"),
    RS_64("64", "原始金额错误"),
    RS_65("65", "超出消费次数限制"),
    RS_68("68", "交易超时"),
    RS_75("75", "允许的输入PIN次数超限"),
    RS_90("90", "正在日终处理"),
    RS_91("91", "发卡方不能操作"),
    RS_92("92", "金融机构或中间网络设施找不到或无法达到"),
    RS_94("94", "重复交易"),
    RS_96("96", "系统异常"),
    RS_97("97", "POS终端号找不到"),
    RS_98("98", "银联处理中心收不到发卡方应答"),
    RS_99("99", "PIN格式错，请重新签到"),
    RS_A0("A0", "MAC校验错，请重新签到"),
    RS_A1("A1", "转账货币不一致"),
    RS_A2("A2", "有缺陷的成功"),
    RS_A3("A3", "资金到账行账号不正确"),
    RS_A4("A4", "有缺陷的成功"),
    RS_A5("A5", "有缺陷的成功"),
    RS_A6("A6", "有缺陷的成功"),
    RS_A7("A7", "安全处理失败")
    ;

    private String code;
    private String desc;

    private ResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ResultEnum getEnum(String code) {
        for(ResultEnum e: ResultEnum.values()) {
            if(e.code.equals(code))
                return e;
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
