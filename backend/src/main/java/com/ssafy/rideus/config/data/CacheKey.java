package com.ssafy.rideus.config.data;

public class CacheKey {

    private CacheKey() {}

    // 예시 코드
    public static final int DEFAULT_EXPIRE_SEC = 180;
//
//    public static final String PERSONAL_AUCTION_BOARD = "personal_auction_board";
//    public static final int PERSONAL_AUCTION_EXPIRE_SEC = 300;
//
//    public static final String SPECIAL_AUCTION_BOARD = "special_auction_board";
//    public static final int SPECIAL_AUCTION_BOARD_EXPIRE_SEC = 300;

    public static final int RANK_TOTAL_EXPIRE_SEC = 60 * 60 * 24 * 7;   // 일주일

    public static final String RANK_TOTAL_TIME = "rank_total_time";
    public static final String RANK_TOTAL_DISTANCE = "rank_total_distance";
    public static final String RANK_TOTAL_BEST_SPEED = "rank_total_max_speed";
    public static final String RANK_COURSE_TIME = "rank_course_time";
    public static final String RANK_MEMBER_TIME = "rank_member_time";
    public static final String RANK_MEMBER_DISTANCE = "rank_member_distance";
    public static final String RANK_MEMBER_BEST_SPEED = "rank_member_max_speed";

}
