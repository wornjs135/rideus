import React from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setUser } from "../../stores/modules/user";

export const OAuth2RedirectHandler = (props) => {
  const navigate = useNavigate();
  const params = new URLSearchParams(window.location.search);
  const dispatch = useDispatch();
  const accessToken = params.get("accessToken");
  // const refreshToken = params.get("refreshToken");
  const register = params.get("register");

  let isRegister = JSON.parse(register);
  console.log(isRegister);

  localStorage.setItem("accessToken", accessToken);
  // localStorage.setItem("refreshToken", refreshToken);

  // 회원가입후 추가 입력 안했으면
  if (isRegister === true) {
    window.location.href = "/moreinfo";
  } else {
    // redux에 로그인 정보 저장
    //member/me API 호출
    // redux에 저장
    // dispatch(
    //   setUser({
    //     email: "",
    //     id: 0,
    //     name: "",
    //     nickname: "",
    //     phone: "",
    //     profileImageUrl: "",
    //     role: "",
    //   })
    // );
    window.location.href = "/";
  }

  return null;
};
