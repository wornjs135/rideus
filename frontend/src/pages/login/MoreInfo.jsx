import React, {useState} from "react";
import {Box} from "grommet";
import {Button, TextField} from "@mui/material";


export const MoreInfo = () => {
    let [inputs, setInputs] = useState({
        name: '',
        phone: '',
        nickname: '',
    });

    const {name, phone, nickname} = inputs;

    const onChange = (e) => {
        const {value, name} = e.target; // 우선 e.target 에서 name 과 value 를 추출
        setInputs({
            ...inputs, // 기존의 input 객체를 복사한 뒤
            [name]: value // name 키를 가진 값을 value 로 설정
        });
    };

    const onClick = (e) => {
    }

    return <Box width="100vw" margin="0 auto" gap="small" height={"100vh"} align={"center"}>
        <Box align="center">
            <Box>추가 정보 입력</Box>
        </Box>

        <Box>
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
            <Box>
                <TextField id="filled-basic" name="nickname" label="닉네임" variant="standard" helperText=""
                           onChange={onChange} value={nickname}/>
            </Box>
        </Box>

        <Box>
            <Button variant="contained" style={{width : "70vw", background : "#439652"}} onClick={onClick}>확인</Button>
        </Box>
    </Box>;
};
