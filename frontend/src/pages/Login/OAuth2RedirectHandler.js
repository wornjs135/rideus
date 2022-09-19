import React from "react";
import { useNavigate } from 'react-router-dom';

export const OAuth2RedirectHandler = (props) => {
    const navigate = useNavigate();
    const params = new URLSearchParams(window.location.search);

    const accessToken = params.get("accessToken");
    const refreshToken = params.get("refreshToken");

    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("refreshToken", refreshToken);

    navigate("/");
    return null;
};