package com.kunlab.jpos.util;

/**
 * @author likun
 */
public class Constants {

    public static final String INTERNAL_ZPK             = "internal.zpk";

    //消息类型
    public static final String MSG_ECHO                 = "echo";                   //回响测试
    public static final String MSG_CHECK_IN             = "check_in";               //签到
    public static final String MSG_SALE                 = "sale";                   //消费
    public static final String MSG_SALE_REVERSAL        = "sale_reversal";          //消费冲正
    public static final String MSG_SALE_CANCEL          = "sale_cancel";            //消费撤销
    public static final String MSG_SALE_CANCEL_REVERSAL = "sale_cancel_reversal";   //消费撤销冲正
    public static final String MSG_BALANCE              = "balance";                //余额查询

    //Context信息
    public static final String CTX_TIMESTAMP            = "timestamp";
    public static final String CTX_SOURCE               = "source";
    public static final String CTX_REQUEST              = "request";
    public static final String CTX_RESPONSE             = "response";
    public static final String CTX_RESULT               = "result";
    public static final String CTX_MID                  = "mid";
    public static final String CTX_TID                  = "tid";
    public static final String CTX_MSG_TYPE             = "msg_type";
    //签购单信息
    public static final String CTX_SIGNINFO_MID         = "signinfo_mid";       //商户编号
    public static final String CTX_SIGNINFO_TID         = "signinfo_tid";       //终端编号
    public static final String CTX_SIGNINFO_BATCHNO     = "signinfo_batchNo";   //批次号
    public static final String CTX_SIGNINFO_TRACENO     = "signinfo_traceNo";   //凭证号
    public static final String CTX_SIGNINFO_REFNO       = "signinfo_refNo";     //系统参考号
    public static final String CTX_SIGNINFO_AUTHNO      = "signinfo_authNo";    //授权码
    public static final String CTX_SIGNINFO_ACQ_        = "signinfo_acq";       //收单机构
    public static final String CTX_SIGNINFO_ISSUER      = "signinfo_issuer";    //发卡行


    //服务点输入方式代码
    public static final String POS_PIN_TRUE             = "1";
    public static final String POS_PIN_FALSE            = "2";

    public static final String POS_021                  = "021";                //刷卡,有PIN
    public static final String POS_022                  = "022";                //刷卡,无PIN
    public static final String POS_051                  = "051";                //IC 卡读入，卡数据可靠，有 PIN
    public static final String POS_052                  = "052";                //IC 卡读入，卡数据可靠，无 PIN
    public static final String POS_071                  = "071";                //PBOC借贷记 IC 卡读入，有 PIN
    public static final String POS_072                  = "072";                //PBOC借贷记 IC 卡读入，无 PIN



}