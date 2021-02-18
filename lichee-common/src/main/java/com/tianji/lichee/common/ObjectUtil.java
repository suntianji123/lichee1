package com.tianji.lichee.common;

public final class ObjectUtil {

    public static<T> T checkNotEmpty(T arg,String text){
        if(arg == null){
            throw new NullPointerException(text);
        }

        return arg;
    }
}
