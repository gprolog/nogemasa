package com.nogemasa.weixin.wxpay.scanpay.listener;

import com.tencent.business.ScanPayBusiness;
import com.tencent.protocol.pay_protocol.ScanPayResData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tencent.protocol.reverse_protocol.ReverseResData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br/>create at 15-9-24
 *
 * @author liuxh
 * @since 1.0.0
 */
public class ScanPayBusinessResultListener implements ScanPayBusiness.ResultListener {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String ON_FAIL_BY_RETURN_CODE_ERROR = "on_fail_by_return_code_error";
    public static final String ON_FAIL_BY_RETURN_CODE_FAIL = "on_fail_by_return_code_fail";
    public static final String ON_FAIL_BY_SIGN_INVALID = "on_fail_by_sign_invalid";

    public static final String ON_FAIL_BY_QUERY_SIGN_INVALID = "on_fail_by_query_sign_invalid";
    public static final String ON_FAIL_BY_REVERSE_SIGN_INVALID = "on_fail_by_query_service_sign_invalid";

    public static final String ON_FAIL_BY_AUTH_CODE_EXPIRE = "on_fail_by_auth_code_expire";
    public static final String ON_FAIL_BY_AUTH_CODE_INVALID = "on_fail_by_auth_code_invalid";
    public static final String ON_FAIL_BY_MONEY_NOT_ENOUGH = "on_fail_by_money_not_enough";

    public static final String ON_FAIL = "on_fail";
    public static final String ON_SUCCESS = "on_success";

    private ScanPayResData scanPayResData;// 微信API返回结果
    private ScanPayQueryResData scanPayQueryResData;
    private ReverseResData reverseResData;

    private String result = "";
    private String transcationID = "";

    /**
     * API返回ReturnCode不合法，支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问。
     * 遇到这个问题一般是程序没按照API规范去正确地传递参数导致，请仔细阅读API文档里面的字段说明。
     * 这种问题一般是程序逻辑问题，需要做好日志监控，发现问题要及时让工程师进行定位处理。
     *
     * @param scanPayResData 支付请求返回结果
     */
    @Override
    public void onFailByReturnCodeError(ScanPayResData scanPayResData) {
        this.scanPayResData = scanPayResData;
        result = ON_FAIL_BY_RETURN_CODE_ERROR;
        logger.error("程序逻辑问题，微信返回的描述：{}", scanPayResData.getErr_code_des());
    }

    /**
     * API返回ReturnCode为FAIL，支付API系统返回失败，请检测Post给API的数据是否规范合法。
     * 遇到这个问题一般是程序没按照API规范去正确地传递参数导致，请仔细阅读API文档里面的字段说明。
     * 这种问题一般是程序逻辑问题，需要做好日志监控，发现问题要及时让工程师进行定位处理。
     *
     * @param scanPayResData 支付请求返回结果
     */
    @Override
    public void onFailByReturnCodeFail(ScanPayResData scanPayResData) {
        this.scanPayResData = scanPayResData;
        result = ON_FAIL_BY_RETURN_CODE_FAIL;
        logger.error("程序逻辑问题，微信返回的描述：{}", scanPayResData.getErr_code_des());
    }

    /**
     * 支付请求API返回的数据签名验证失败，有可能数据被篡改了。
     * 遇到这种错误建议商户直接告警，做好安全措施。
     * 这种问题一般是程序逻辑问题，需要做好日志监控，发现问题要及时让工程师进行定位处理。
     *
     * @param scanPayResData 支付请求返回结果
     */
    @Override
    public void onFailBySignInvalid(ScanPayResData scanPayResData) {
        this.scanPayResData = scanPayResData;
        result = ON_FAIL_BY_SIGN_INVALID;
        logger.error("程序逻辑问题，微信返回的描述：{}", scanPayResData.getErr_code_des());
    }

    /**
     * 用户用来支付的二维码已经过期，提示收银员重新扫一下用户微信“刷卡”里面的二维码"。
     * 把具体出错信息提示给用户，指导用户进行下一步操作。（具体出错信息可以通过scanPayResData.getErr_code_des()获取得到）
     *
     * @param scanPayResData 支付请求返回结果
     */
    @Override
    public void onFailByAuthCodeExpire(ScanPayResData scanPayResData) {
        this.scanPayResData = scanPayResData;
        result = ON_FAIL_BY_AUTH_CODE_EXPIRE;
        logger.error("用户支付授权号过期，微信返回的描述：{}", scanPayResData.getErr_code_des());
    }

    /**
     * 授权码无效，提示用户刷新一维码/二维码，之后重新扫码支付。
     * 把具体出错信息提示给用户，指导用户进行下一步操作。（具体出错信息可以通过scanPayResData.getErr_code_des()获取得到）
     *
     * @param scanPayResData 支付请求返回结果
     */
    @Override
    public void onFailByAuthCodeInvalid(ScanPayResData scanPayResData) {
        this.scanPayResData = scanPayResData;
        result = ON_FAIL_BY_AUTH_CODE_INVALID;
        logger.error("用户支付授权号无效，微信返回的描述：{}", scanPayResData.getErr_code_des());
    }

    /**
     * 用户余额不足，换其他卡支付或是用现金支付。
     * 把具体出错信息提示给用户，指导用户进行下一步操作。（具体出错信息可以通过scanPayResData.getErr_code_des()获取得到）
     *
     * @param scanPayResData 支付请求返回结果
     */
    @Override
    public void onFailByMoneyNotEnough(ScanPayResData scanPayResData) {
        this.scanPayResData = scanPayResData;
        result = ON_FAIL_BY_MONEY_NOT_ENOUGH;
        logger.error("用户余额不足，微信返回的描述：{}", scanPayResData.getErr_code_des());
    }

    /**
     * 支付失败，其他原因导致，这种情况建议把log记录好
     *
     * @param scanPayResData 支付请求返回结果
     */
    @Override
    public void onFail(ScanPayResData scanPayResData) {
        this.scanPayResData = scanPayResData;
        result = ON_FAIL;
        logger.error("支付失败，其他原因导致，微信返回的描述：{}", scanPayResData.getErr_code_des());
    }

    @Override
    /**
     * 恭喜，支付成功，请返回成功结果
     *
     * @param scanPayResData 支付请求返回结果
     */
    public void onSuccess(ScanPayResData scanPayResData, String transID) {
        this.scanPayResData = scanPayResData;
        result = ON_SUCCESS;
        transcationID = transID;
        logger.info("支付成功，微信返回的描述：{}", scanPayResData.getErr_code_des());
    }

    /**
     * 查询请求API返回的数据签名验证失败，有可能数据被篡改了
     *
     * @param scanPayQueryResData 查询请求返回结果
     */
    @Override
    public void onFailByQuerySignInvalid(ScanPayQueryResData scanPayQueryResData) {
        this.scanPayQueryResData = scanPayQueryResData;
        result = ON_FAIL_BY_QUERY_SIGN_INVALID;
        logger.error("查询请求API返回的数据签名验证失败，微信返回的描述：{}", scanPayQueryResData.getErr_code_des());
    }

    /**
     * 撤销请求API返回的数据签名验证失败，有可能数据被篡改了
     *
     * @param reverseResData 撤销请求返回的结果
     */
    @Override
    public void onFailByReverseSignInvalid(ReverseResData reverseResData) {
        this.reverseResData = reverseResData;
        result = ON_FAIL_BY_REVERSE_SIGN_INVALID;
        logger.error("撤销请求API返回的数据签名验证失败，微信返回的描述：{}", reverseResData.getErr_code_des());
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTranscationID() {
        return transcationID;
    }

    public void setTranscationID(String transcationID) {
        this.transcationID = transcationID;
    }

    public ScanPayResData getScanPayResData() {
        return scanPayResData;
    }

    public ScanPayQueryResData getScanPayQueryResData() {
        return scanPayQueryResData;
    }

    public ReverseResData getReverseResData() {
        return reverseResData;
    }
}
