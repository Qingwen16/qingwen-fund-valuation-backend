package com.wen.constant;

/**
 * @author : rjw
 * @date : 2026-03-12
 */
public class ErrorCode {

    //未知
    public static final int ERROR_CODE_UNKNOWN = 0;

    //请求成功
    public static final int ERROR_CODE_OK = 200;

    //参数错误
    public static final int ErrorCode_ParamError = 400;

    //鉴权失败
    public static final int ErrorCode_UnAuth = 401;

    //禁止访问
    public static final int ErrorCode_Forbidden = 403;
    public static final int ErrorCode_NameTooLong = 420;                          //名称超长
    public static final int ErrorCode_UserNotExist = 421;                         //用户不存在
    public static final int ErrorCode_GroupNotExist = 422;                        //群组不存在
    public static final int ErrorCode_ServerError = 500;                          //服务器错误
    public static final int ErrorCode_DaoError = 520;                             //数据库错误
    public static final int ErrorCode_CacheError = 521;                           //缓存错误
}
