package com.cktv.util.exception;

/**
 * Created by admin on 2016/4/18.
 */
public class MessageException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private boolean isShow = true;

    private long error_code;
    public MessageException(){}
    public MessageException(long error_code,String msg) {
        super(msg);
        this.error_code=error_code;
    }
    public MessageException(String msg){
        super(msg);
    }
    public String getMsg(){
        return isShow?getMessage():"";
    }
    public long getError_code(){return error_code;}
}
