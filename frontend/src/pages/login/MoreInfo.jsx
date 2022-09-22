import React, {useEffect, useState} from "react";
import {Box} from "grommet";
import {Button, TextField} from "@mui/material";
import {checkDuplicateNickname, updateMoreInfo} from "../../utils/api/userApi";
import {useNavigate} from "react-router-dom";


export const MoreInfo = () => {
    let [inputs, setInputs] = useState({
        name: '', phone: '', nickname: '',
    });
    const navigate = useNavigate();

    const {name, phone, nickname} = inputs;


    const [nicknameLengthError, setNicknameLengthError] = useState(true);
    const [nicknameDupError, setNicknameDupError] = useState(false);


    const hasLengthError = (nickname) => !(nickname.length <= 10 && nickname.length >= 2);

    const onChange = (e) => {
        let {value, name} = e.target; // 우선 e.target 에서 name 과 value 를 추출
        if (name === "nickname") {
            console.log(name);
            setNicknameLengthError(hasLengthError(value));
            let res = checkDuplicateNickname(value);

            res.then(val => {
                if (val === true) {
                    console.log("닉네임 중복 " + val);
                    setNicknameDupError(true);
                } else {
                    console.log("사용 가능" + val);
                    setNicknameDupError(false);
                }

            })
        }
        // else if (name === "phone") {
        //     const regex = /^[0-9\b -]{0,13}$/;
        //     if (!regex.test(e.target.value)) {
        //         value = e.target.value;
        //     }
        // }
        setInputs({
            ...inputs, // 기존의 input 객체를 복사한 뒤
            [name]: value // name 키를 가진 값을 value 로 설정
        });
    };

    const onClick = (e) => {

        if (name === '') {

        }

        let status = updateMoreInfo(inputs);
        status.then(value => {
            if (value === 200) {
                navigate("/");
            }

        })
    }

    const onBlur = (e) => {

    }

    return <Box width="100vw" margin="0 auto" gap="small" height={"100vh"} align={"center"}>
        <Box align="center">
            <Box>추가 정보 입력</Box>
        </Box>

        <Box>
            <Box>
                <TextField id="filled-basic" name="nickname" label="닉네임" variant="standard"
                           error={nicknameDupError}
                           helperText={nicknameLengthError ? "닉네임은 2글자 이상 10글자 이하입니다." : nicknameDupError ? "닉네임 중복" : "사용 가능"}
                           onChange={onChange} value={nickname} onBlur={onBlur}/>
            </Box>
            <Box>
                <TextField id="filled-basic" name="name" label="이름" variant="standard" helperText="" onChange={onChange}
                           value={name}/>
            </Box>
            <Box>
                <TextField id="filled-basic" label="E-mail" variant="standard" helperText="" disabled/>
            </Box>
            <Box>
                <TextField id="filled-basic" name="phone" label="전화번호" variant="standard" helperText=""
                           onChange={onChange} value={phone}/>
            </Box>
        </Box>

        <Box>
            <Button variant="contained" style={{width: "70vw", background: "#439652"}} onClick={onClick}>확인</Button>
        </Box>
    </Box>;
};
