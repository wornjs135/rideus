package com.ssafy.rideus.common.exception;

public class NotMatchException extends RuntimeException{

    public static final String TOKEN_NOT_MATCH = "토큰 정보가 잘못되었습니다.";
    public static final String MEMBER_RECORD_NOT_MATCH = "본인의 기록이 아닙니다.";
    public NotMatchException(String message) {
        super(message);
    }
}
