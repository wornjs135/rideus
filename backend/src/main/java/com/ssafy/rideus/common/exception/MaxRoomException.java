package com.ssafy.rideus.common.exception;

public class MaxRoomException extends RuntimeException {
    public static final String GROUP_MAX_PARTICIPANTS = "이미 참가한 방입니다.";

    public MaxRoomException(String message) {
        super(message);
    }
}
