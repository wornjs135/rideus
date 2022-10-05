import React from "react";
import {Box} from "grommet";
import KakaoLogin from "../../assets/images/kakao_login_medium_wide.png"
import Logo from "../../assets/images/logo.png"
import MainBike from "../../assets/images/main_bike.png"
import Button from "../../components/Button";
import {AUTH_URL, OAUTH2_REDIRECT_URI} from "../../utils/api/api";
import {StyledText} from "../../components/Common";

export const Login = () => {
    return <Box width="100vw" margin="0 auto" gap="small" height={"100vh"} align={"center"}>
        <Box style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            minHeight: "100vh"
        }}>
            <div style={{
                display:"flex",
                justifyContent: "space-evenly",
                flexDirection:"column",
                marginBottom:"60px"
                }}>
                <div style={{display:"flex",alignItems:"center", justifyContent:"center",marginBottom:"40px"}}>
                    <img src={Logo}/>
                </div>
                <div style={{display:"flex",alignItems:"center", justifyContent:"center",marginBottom:"20px"}}>
                    <img src={MainBike}/>
                </div>
                <div>
                <StyledText
                    color="#FFFFF"
                    text="즐거운 자전거 여행, 달려볼까요?"
                    weight="bold"
                    size="18px"
                />
                </div>
            </div>
            <div>
                <a id="custom-login-btn" href={AUTH_URL + OAUTH2_REDIRECT_URI}>
                    <img
                        src={KakaoLogin}
                        width="100%"
                        alt="카카오 로그인 버튼"
                    />
                </a>
            </div>
        </Box>
        <Box align="center" justify="center">

        </Box>
    </Box>;
};
