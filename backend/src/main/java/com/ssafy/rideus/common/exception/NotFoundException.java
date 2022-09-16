package com.ssafy.rideus.common.exception;

public class NotFoundException extends RuntimeException{

    public static final String USER_NOT_FOUND = "존재하지 않는 회원입니다.";
    public static final String RIDEROOM_NOT_FOUND = "존재하지 않는 그룹방입니다.";


    public NotFoundException(String message) {
        super(message);
    }

}
