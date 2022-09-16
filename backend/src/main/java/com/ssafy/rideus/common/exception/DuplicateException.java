package com.ssafy.rideus.common.exception;

public class DuplicateException extends RuntimeException{

    public static final String GROUP_PARTICIPATE_DUPLICATE = "이미 참가한 방입니다.";
    public DuplicateException(String message) {
        super(message);
    }
}
