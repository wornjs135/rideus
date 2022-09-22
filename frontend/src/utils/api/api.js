import axios from "axios";

export const API_SERVER = "https://j7a603.p.ssafy.io/api";

export const SERVER_URL = "http://localhost:8080/api";

export const CLIENT_URL = "https://j7a603.p.ssafy.io";

export const AUTH_URL = API_SERVER + '/oauth2/authorize/kakao'

export const OAUTH2_REDIRECT_URI = `?redirect_uri=${CLIENT_URL}/oauth2/redirect`
//
// const checkToken = async (config) => {
//     let accessToken = localStorage.getItem("accessToken");
//     const decode = jwt.decode(accessToken);
//     const nowDate = new Date().getTime() / 1000;
//
//     // 토큰 만료시간이 지났다면
//     if (decode.exp < nowDate) {
//         const {data} = await axios.post(`${API_SERVER}/auth/refresh`, {accessToken});
//         // 리프레쉬 토큰 발급 서버 요청
//
//         const res = data.data;
//
//         accessToken = res;
//         localStorage.setItem("accessToken", accessToken);
//     }
//
//     config.headers["Authorization"] = `Bearer ${accessToken}`;
//     return config;
// }

export {axios};
