package com.webframework.util;

/**
 * @Author 洪少聪 【312451021@qq.com】
 * @Date 2016/11/26 21:41
 * @Since 1.0.0
 * @Descript 类型转换工具类
 */

public final class CastUtil {

    public static String castString(Object obj){
        return CastUtil.castString(obj, "");
    }

    private static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    public static double castDouble(Object obj){
        return CastUtil.castDouble(obj, 0);
    }

    private static double castDouble(Object obj, int defaultValue) {
        double doubleValue = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    doubleValue = Double.parseDouble(strValue);
                }catch (NumberFormatException e){
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    public static long castLong(Object obj){
        return CastUtil.castLong(obj, 0);
    }

    private static long castLong(Object obj, int defaultValue) {
        long longValue = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    longValue = Long.parseLong(strValue);
                }catch (NumberFormatException e){
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    public static int castInt(Object property) {
        return CastUtil.castInt(property, 0);
    }

    private static int castInt(Object property, int defaultValue) {
        int intValue = defaultValue;
        if(property != null){
            String strValue = castString(property);
            if (StringUtil.isNotEmpty(strValue)){
                try{
                    intValue = Integer.parseInt(strValue);
                }catch(NumberFormatException e){
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    public static boolean castBoolean(Object property) {
        return CastUtil.castBoolean(property, false);
    }

    private static boolean castBoolean(Object property, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if(property != null){
            String strValue = castString(property);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    booleanValue = Boolean.parseBoolean(strValue);
                }catch (NumberFormatException e){
                    booleanValue = defaultValue;
                }
            }
        }
        return booleanValue;
    }
}
