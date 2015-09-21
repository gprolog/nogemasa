package com.nogemasa.signature.agent.aspect;

import com.nogemasa.signature.util.handler.PublicSignatureHandler;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br/>create at 15-8-19
 *
 * @author liuxh
 * @since 1.0.0
 */
public class SignatureVerifyAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String publicKeyPath;

    public SignatureVerifyAspect(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }

    /**
     * 对符合切入点规则的方法，使用Around方式，将记录operation的操作织入。
     *
     * @param joinPoint 连接点
     * @return 直接返回controller方法的结果
     */
    public Object signatureVerify(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String message = (String) args[0];
        if (message == null || message.trim().isEmpty()) {
            message = (String) args[1];
        }
        JSONObject json = new JSONObject();
        JSONObject params;
        String caller;
        String username;
        try {
            params = JSONObject.fromObject(message);
            caller = params.getString("caller");
            username = params.getString("username");
        } catch (Exception e) {
            logger.error("签名验证请求参数异常！", e);
            json.put("success", false);
            json.put("errorCode", 403);
            json.put("message", "请求参数异常，请检查后重试！");
            return json;
        }
        String verifyResult;
        try {
            PublicSignatureHandler handler = new PublicSignatureHandler();
            handler.setCaller(caller);
            handler.setUsername(username);
            handler.setKeyFilePath(publicKeyPath);
            verifyResult = handler.verify(params);
        } catch (Throwable e) {
            logger.error("签名验证失败", e);
            json.put("success", false);
            json.put("errorCode", 500);
            json.put("message", "请求信息签名验证失败！");
            return json;
        }
        if (verifyResult == null) {
            json.put("success", false);
            json.put("errorCode", 403);
            json.put("message", "请求信息签名验证失败");
            return json;
        }
        logger.info("签名验证成功，签名数据为：{}。", message);
        return joinPoint.proceed();
    }
}
