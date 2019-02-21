package com.kunlab.jpos.commons.secure;

/**
 * 数据脱敏类型
 * @author likun
 */
public enum DataProtectEnum {
    /** 数据对象 */
    OBJECT,

    /** 全字符 */
    ALL,

    /** 银行卡号 */
    BANK_CARD,

    /** 证件号 */
    ID_CARD,

    /** 手机号 */
    MOBILE;
}
