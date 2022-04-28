package com.arithfighter.not.pojo;

public class TokenHolder {
    private int tokens;

    public int getTokens() {
        return tokens;
    }

    public void gain(int i){
        tokens+=i;
    }

    public void lose(int i){
        tokens-=i;
    }

    public void reset(){
        tokens-=tokens;
    }
}
