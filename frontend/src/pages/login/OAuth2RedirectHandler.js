import React from "react";
import { useNavigate } from 'react-router-dom';

export const OAuth2RedirectHandler = (props) => {
    const navigate = useNavigate();
    const params = new URLSearchParams(window.location.search);

    const accessToken = params.get("accessToken");
    const refreshToken = params.get("refreshToken");
    const register = params.get("register");


    let isRegister = Boolean(register);

    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("refreshToken", refreshToken);

    // 회원가입후 추가 입력 안했으면
    if (isRegister === true) {
        window.location.href = '/moreinfo';
    } else {
        window.location.href = '/';
    }

    return null;
};