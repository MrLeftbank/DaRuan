package com.sx.oesb.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

public class IndexCache<T> {
    private T obj;
    private Date expire = new Date(0);

    @Value("${cache:expire-min}")
    private long expireMin;

    public T getObj() {
        return obj;
    }

    public Date getExpire() {
        return expire;
    }

    public void setObj(T obj) {
        this.obj = obj;
        this.expire = new Date(new Date().getTime() + expireMin);
    }
}
