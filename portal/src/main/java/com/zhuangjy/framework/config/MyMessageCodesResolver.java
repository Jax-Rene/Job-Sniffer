package com.zhuangjy.framework.config;

import org.springframework.validation.DefaultMessageCodesResolver;

public class MyMessageCodesResolver extends DefaultMessageCodesResolver {

    /**
     * FIXME wangq  not work here!
     * 编码为类似于: NotEmpty.alarmInfo.alarmName
     * @param errorCode
     * @param objectName
     * @param field
     * @param fieldType
     * @return
     */
    @Override
    public String[] resolveMessageCodes(String errorCode, String objectName, String field, Class fieldType) {
        String formatCode = errorCode + CODE_SEPARATOR + objectName + CODE_SEPARATOR + field;
        return new String[]{formatCode};
    }
}

