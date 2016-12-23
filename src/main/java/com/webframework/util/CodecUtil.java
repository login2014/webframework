package com.webframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/28
 * @Since 1.0.0
 * @Descript
 */
public class CodecUtil {

    private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);

    public static String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("decode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static String encodeURL(String source){
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("encode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
