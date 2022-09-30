import React, {useEffect, useState} from "react";
import {Avatar, Box} from "grommet";
import {Button, TextField} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {checkDuplicateNickname, myInfo, updateMoreInfo} from "../utils/api/userApi";
import {StyledText} from "../components/Common";
import {useDispatch, useSelector} from "react-redux";
import {setUser} from "../stores/modules/user";

export const EditProfile = () => {
    let [inputs, setInputs] = useState({
        nickname: "",
        email: "",
        phone: "",
    });
    const dispatch = useDispatch();
    const user = useSelector(state => state.user.user.user);
    useEffect(() => {

        myInfo((data) => {
            const {data: value} = data;
            console.log(value);
            setInputs(
                value,
            );
        })

    }, [])

    const navigate = useNavigate();

    const {email, phone, nickname,profileImageUrl} = inputs;

    const [nicknameLengthError, setNicknameLengthError] = useState(true);
    const [nicknameDupError, setNicknameDupError] = useState(false);

    const hasLengthError = (nickname) =>
        !(nickname.length <= 10 && nickname.length >= 2);

    const onChange = (e) => {
        let {value, name} = e.target; // 우선 e.target 에서 name 과 value 를 추출
        if (name === "nickname") {
            console.log(name);
            setNicknameLengthError(hasLengthError(value));
            checkDuplicateNickname(value, (data) => {
                console.log(data);
                const {data: isDup} = data;
                if (isDup === true) {
                    console.log("닉네임 중복 " + isDup);
                    setNicknameDupError(true);
                } else {
                    console.log("사용 가능 " + isDup);
                    setNicknameDupError(false);
                }
            });


        }
        // else if (name === "phone") {
        //     const regex = /^[0-9\b -]{0,13}$/;
        //     if (!regex.test(e.target.value)) {
        //         value = e.target.value;
        //     }
        // }
        setInputs({
            ...inputs, // 기존의 input 객체를 복사한 뒤
            [name]: value, // name 키를 가진 값을 value 로 설정
        });
    };

    const onClick = (e) => {


        updateMoreInfo(inputs, value => {
            const {status} = value;
            if (status === 200) {
                myInfo((res) => {
                    console.log(res);
                    const {data} = res;
                    dispatch(setUser(data));
                });

            }

        });
    };

    const onBlur = (e) => {
    };

    return (
        <Box>
            <Box pad={{left: "20px"}} style={{marginTop: "5vw"}} alignContent={"start"}>
                <div onClick={() => navigate(-1)}>
                    <StyledText text="Back" weight="bold" size="18px"/>
                </div>
            </Box>

            <Box
                width="100vw"
                margin="0 auto"
                gap="small"
                height={"100vh"}
                align={"center"}
            >

                <Box align="center">

                    <Box
                        style={{
                            marginTop: "10vw",
                        }}
                    >
                        <StyledText text={"마이 프로필"} weight={"bold"} size={"18px"}/>
                    </Box>
                </Box>

                <Box
                    style={{
                        marginTop: "10vw",
                    }}
                >
                    <Box
                        style={{
                            marginTop: "10vw",
                            alignItems: "center"
                        }}>
                        <Avatar name="profileImageUrl" src={profileImageUrl} size={"xlarge"}/>
                    </Box>
                    <Box
                        style={{
                            marginTop: "10vw",
                        }}
                    >
                        <TextField
                            id="filled-basic"
                            name="nickname"
                            label="닉네임"
                            variant="standard"
                            error={nicknameDupError}
                            helperText={
                                nicknameLengthError
                                    ? "닉네임은 2글자 이상 10글자 이하입니다."
                                    : nicknameDupError
                                        ? "닉네임 중복"
                                        : "사용 가능"
                            }
                            onChange={onChange}
                            value={nickname}
                            onBlur={onBlur}
                        />
                    </Box>

                    <Box
                        style={{
                            marginTop: "10vw",
                        }}
                    >
                        <TextField
                            id="filled-basic"
                            name="email"
                            label="E-mail"
                            variant="standard"
                            helperText=""
                            value={email}
                            disabled
                        />
                    </Box>
                    <Box
                        style={{
                            marginTop: "10vw",
                        }}
                    >
                        <TextField
                            id="filled-basic"
                            name="phone"
                            label="전화번호"
                            variant="standard"
                            helperText=""
                            onChange={onChange}
                            value={phone}
                            disabled
                        />
                    </Box>
                </Box>

                <Box
                    style={{
                        marginTop: "10vw",
                    }}
                >
                    <Button
                        variant="contained"
                        style={{width: "70vw", background: "#439652"}}
                        onClick={onClick}
                    >
                        수정
                    </Button>
                </Box>
            </Box>
        </Box>
    );
};
