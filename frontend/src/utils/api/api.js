import axios from "axios";
import * as jwt from 'jsonwebtoken';
import {SERVER_URL} from "../data";

export const API_SERVER = "https://j7a603.p.ssafy.io/api/";

const checkToken = async (config) => {
    let accessToken = localStorage.getItem("accessToken");
    const decode = jwt.decode(accessToken);
    const nowDate = new Date().getTime() / 1000;

    // 토큰 만료시간이 지났다면
    if (decode.exp < nowDate) {
        const {data} = await axios.post(`${SERVER_URL}/auth/refresh`, {accessToken});
        // 리프레쉬 토큰 발급 서버 요청

        const res = data.data;

        accessToken = res;
        localStorage.setItem("accessToken", accessToken);
    }

    config.headers["Authorization"] = `Bearer ${accessToken}`;
    return config;
}

export {axios, checkToken};
