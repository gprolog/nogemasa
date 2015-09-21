package com.nogemasa.signature.util.handler;

import com.nogemasa.signature.util.exception.CallerNotFoundException;
import org.apache.commons.lang.StringUtils;

import java.io.*;

/**
 * <br/>create at 15-8-26
 *
 * @author liuxh
 * @since 1.0.0
 */
public abstract class SignatureHandler {
    private String caller;// 调用方声明
    private String username;// 调用方登录用户名
    private String keyFilePath;// 密钥文件路径
    private File keyFile;// 密钥文件对象
    private String keyString;// 密钥的Base64文本

    /**
     * 获取文件内容
     *
     * @return 文件内容
     * @throws java.io.IOException
     */
    public String getKeyString() throws IOException {
        if (StringUtils.isBlank(keyString)) {
            keyString = readKeyString(keyFile);
        }
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    /**
     * 获取密钥文件路径
     *
     * @return 密钥文件路径
     */
    public String getKeyFilePath() {
        return keyFilePath;
    }

    /**
     * 设置密钥文件路径
     *
     * @param keyFilePath 密钥文件路径
     * @throws java.io.FileNotFoundException 如果输入路径为空或对应路径文件不存在，抛出该异常。
     */
    public void setKeyFilePath(String keyFilePath) throws FileNotFoundException {
        if (StringUtils.isBlank(keyFilePath)) {
            throw new FileNotFoundException("Caused by the path of file is not exist!");
        }
        keyFile = new File(keyFilePath);
        if (!keyFile.exists()) {
            throw new FileNotFoundException("Caused by the file '" + keyFilePath + "' is not found");
        }
        this.keyFilePath = keyFilePath;
    }

    /**
     * 获取调用者信息
     *
     * @return 调用者信息
     */
    public String getCaller() {
        return caller;
    }

    /**
     * 设置调用者信息
     *
     * @param caller 调用者信息
     * @throws CallerNotFoundException 如果输入的调用者信息为空，抛出该异常。
     */
    public void setCaller(String caller) throws CallerNotFoundException {
        if (StringUtils.isBlank(caller)) {
            throw new CallerNotFoundException("Caused by caller is null");
        }
        this.caller = caller;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 从提供的文件中读取内容，并以字符串格式返回。
     *
     * @param file 待读取文件
     * @return 文件内容字符串。当传入的file对象为{@code null}，或file对象指向文件不存在，或file对象指向文件夹，直接返回{@code null}。
     * @throws java.io.IOException 文件读取异常。
     */
    protected String readKeyString(File file) throws IOException {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}
