import React from "react";
import {Box} from "grommet";
import {TextField} from "@mui/material";
import Button from "../../components/Button";


export const MoreInfo = () => {
    return <Box width="100vw" margin="0 auto" gap="small" height={"100vh"} align={"center"}>
        <Box align="center">
            <Box>추가 정보 입력</Box>
        </Box>

        <Box>
            <Box>
                <TextField id="filled-basic" label="이름" variant="standard" helperText=""/>
            </Box>
            <Box>
                <TextField id="filled-basic" label="E-mail" variant="standard" helperText=""/>
            </Box>
            <Box>
                <TextField id="filled-basic" label="전화번호" variant="standard" helperText=""/>
            </Box>
            <Box>
                <TextField id="filled-basic" label="닉네임" variant="standard" helperText=""/>
            </Box>
        </Box>

        <Box>
            <Button BigGreen children="완료"/>
        </Box>
    </Box>;
};
