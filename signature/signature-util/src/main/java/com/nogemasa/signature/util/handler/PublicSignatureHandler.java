package com.nogemasa.signature.util.handler;

import com.nogemasa.signature.util.json.SignedJsonVerifier;
import com.nogemasa.signature.util.json.SignedJsonVerifyException;
import com.nogemasa.signature.util.keytool.RsaKeyLoader;
import net.sf.json.JSON;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

/**
 * <br/>create at 15-8-26
 *
 * @author liuxh
 * @since 1.0.0
 */
public class PublicSignatureHandler extends SignatureHandler {
    /**
     * 将要发送的JSON内容签名包裹
     *
     * @param json 要签名的JSON内容，可以是 {@link net.sf.json.JSONObject} 、 {@link net.sf.json.JSONArray} 、 {@link net.sf.json.JSONNull}
     * @return 包裹后的Json字符串，请勿重复进行JSON转换，否则会导致签名失效、无法验证
     * @throws java.security.spec.InvalidKeySpecException 数据格式非法。
     * @throws java.io.IOException     如果传入的是私钥文件对象或文件路径有异常，可能会出现这种异常。
     */
    public String verify(JSON json) throws IOException, InvalidKeySpecException, SignedJsonVerifyException {
        return SignedJsonVerifier.verify(json.toString(), RsaKeyLoader.base64String2PubKey(getKeyString()));
    }
}
