package com.wcs.base.ql.util;

import java.util.Date;

public enum TypeEnum {

    S(String.class), I(Integer.class), L(Long.class), B(Boolean.class), F(Float.class), N(Double.class), D(Date.class);

    private Class<?> clazz;

    TypeEnum(Class<?> clazz) {
            this.clazz = clazz;
    }

    public Class<?> getValue() {
            return clazz;
    }
}