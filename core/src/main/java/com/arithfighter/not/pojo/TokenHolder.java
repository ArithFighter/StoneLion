package com.arithfighter.not.pojo;

public class TokenHolder {
    private final int initToken;
    private int token;

    public TokenHolder(int initToken) {
        this.initToken = initToken;
        token = this.initToken;
    }

    public int getInitToken() {
        return initToken;
    }

    public int getToken() {
        return token;
    }

    public void obtainToken(int value) {
        token += value;
    }

    public void lostToken(int value) {
        token -= value;
    }
}
