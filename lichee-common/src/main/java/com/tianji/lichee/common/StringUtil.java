package com.tianji.lichee.common;

public final class StringUtil {

    private static final char PACKAGE_SEPARATOR_CHAR = '.';

    public static String simpleClassName(Class<?> clazz){
        String className = ObjectUtil.checkNotEmpty(clazz,"clazz").getName();
        final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
        if(lastDotIdx >= 0){
            return className.substring(lastDotIdx + 1);
        }
        return className;
    }
}
