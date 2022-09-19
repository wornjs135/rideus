import React from "react";
import {Box} from "grommet";
import KakaoLogin from "../assets/images/kakao_login_medium_wide.png"
import Logo from "../assets/images/logo.png"
import Button from "../components/Button";
import {AUTH_URL, OAUTH2_REDIRECT_URI} from "../utils/data";

export const Login = () => {
  return <Box width="100vw" justify="evenly" margin="0 auto" gap="small" height={"100vh"} align={"center"} >

    <Box align="center">
      <img src={Logo}/>
    </Box>
    <Box align="center" justify="center">
    <a id="custom-login-btn" href={AUTH_URL + OAUTH2_REDIRECT_URI}>
      <img
          src={KakaoLogin}
          width="222"
          alt="카카오 로그인 버튼"
      />
    </a>
    </Box>
  </Box>;
};
