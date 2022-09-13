// import axios from "axios";
import { createAction, handleActions } from "redux-actions";
// import { useNavigate } from "react-router-dom";
import produce from "immer";
// import { loginApi } from "../../utils/apis/UserAPI";

const SET_USER = "SET_USER";

export const setUser = createAction(SET_USER, (user) => ({ user }));

const initialState = {
  user: {
    userName: "",
    userNickname: "",
    userId: "",
    token: "",
    userEmail: "",
  },
};

// 카카오 로그인
// export const login = (code) => {
//   return (dispatch, getState) => {
//     loginApi(
//       code,
//       (res) => {
//         let token = res.headers["authorization"];
//         console.log(res);
//         dispatch(
//           setUser({
//             userName: res.data.userName,
//             userNickname: res.data.userNickname,
//             userId: res.data.userId,
//             token: token,
//           })
//         );

//         localStorage.setItem("token", token);
//         if (res.status === 200) {
//           console.log("로그인 성공");
//           let token = res.headers["authorization"];
//           // sessionStorage("token", token);
//           // dispatch(setUser);
//           // window.location.href = "/";
//           return "/";
//         } else if (res.status === 201) {
//           //사용자 정보가 없을 때(회원가입 안함) -> 회원가입 페이지로 이동
//           // localStorage.setItem("token", token);
//           // window.location.href = "/signup1";
//           return "/signup1";
//         }
//       },
//       (err) => {
//         console.log(err);
//         return "";
//       }
//     );
//   };
// };

export default handleActions(
  {
    //사용자 정보 설정
    [SET_USER]: (state, { payload: user }) =>
      produce(state, (draft) => {
        draft.user = user;
        // console.log(draft.user);
      }),
  },
  initialState
);

const actionCreators = {
  setUser,
  // login,
  // join,
};

export { actionCreators };
