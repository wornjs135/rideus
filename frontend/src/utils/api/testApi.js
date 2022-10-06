import { API_SERVER, axios } from "./api";

const API_SERVER_USER = API_SERVER + "member/";

const instance = axios.create({
  baseURL: API_SERVER_USER,
  headers: {
    contentType: "application/json",
  },
});

const checkNickname = async (nickname, success, fail) => {
  await instance.get(`/check/${nickname}`).then(success).catch(fail);
};

export { checkNickname };
