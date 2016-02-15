package com.zhuangjy.framework.config;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

public class CustomEnumEditorSupport<T extends Enum> extends PropertyEditorSupport {

    private final String methodName;
    private final boolean allowEmpty;
    private final Class<T> clazz;
    private final String default_method = "valueOf";

    public CustomEnumEditorSupport(boolean allowEmpty, Class<T> clazz) {
        this.methodName = default_method;
        this.allowEmpty = allowEmpty;
        this.clazz = clazz;
    }

    public CustomEnumEditorSupport(String methodName, boolean allowEmpty, Class<T> clazz) {
        if (!StringUtils.isEmpty(methodName)) {
            this.methodName = methodName;
        }else {
            this.methodName = default_method;
        }
        this.allowEmpty = allowEmpty;
        this.clazz = clazz;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
        }
        else {
            try {
                setValue(clazz.getMethod(methodName, Object.class).invoke(clazz, text));
            }
            catch (Exception ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
            }
        }
    }

    @Override
    public String getAsText() {
        Enum e = (Enum) getValue();
        return e==null?"0":e.ordinal() + "";
    }
}
