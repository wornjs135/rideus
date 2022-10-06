import React from "react";
import "../App.css";
import styled, { css } from "styled-components";

//InputBox Styled Component
const InputStyle = styled.input`
  font-weight: bold;
  font-family: "Noto Sans KR", sans-serif;
  /* box-shadow: 0px 4px 3px 0px rgba(0,0,0,0.6); */
  /* border-radius: 5px; */
  border: 0px;
  border-bottom: 1px solid #101010;
  padding: 5px 15px;
  margin: 5px 0px;
  color: black;
  width: ${(props) => props.width || "65vw"};
  height: ${(props) => props.height || "40px"};
`;

const InputStyle2 = styled.input`
  font-weight: bold;
  font-family: "Noto Sans KR", sans-serif;
  width: 60vw;
  height: 30px;
  border: none;
  border-bottom: 1px solid #ccc;
  padding: 5px 15px;
  margin: 5px 0px;
  color: black;
  text-align: right;
`;

const InputStyle3 = styled.input`
  font-weight: bold;
  font-family: "Noto Sans KR", sans-serif;
  width: 40vw;
  height: 30px;
  border: none;
  border-bottom: 1px solid #ccc;
  color: #d00000;
  text-align: right;
  ::placeholder {
    color: #d14b4b;
  }
`;

export const SizeInput = styled.input`
  font-weight: bold;
  font-family: "Noto Sans KR", sans-serif;
  width: 5vw;
  height: 30px;
  border: none;
  border-bottom: 1px solid #ccc;
  padding: 5px 15px;
  color: black;
  text-align: right;
`;

const TextAreaStyle = styled.textarea`
  font-weight: bold;
  font-family: "Noto Sans KR", sans-serif;
  width: 75vw;
  height: 15vh;
  box-shadow: 0px 0px 3px 0px rgba(0, 0, 0, 0.6);
  border-radius: 5px;
  border: 1px rgb(255, 255, 255);
  padding: 5px 15px;
  margin: 5px 0px;
  color: black;
  resize: none;
`;

export function STextArea({ placeholder, value, onChange }) {
  return (
    <TextAreaStyle
      placeholder={placeholder}
      onChange={onChange}
      value={value}
    />
  );
}

export function Input({
  placeholder,
  width,
  height,
  value,
  onChange,
  readOnly,
}) {
  return (
    <InputStyle
      width={width}
      height={height}
      type="text"
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      readOnly={readOnly}
    />
  );
}
export function Input2({ placeholder, onChange, value, disabled, style }) {
  return (
    <InputStyle2
      type="text"
      placeholder={placeholder}
      onChange={onChange}
      value={value}
      disabled={disabled}
      style={style}
    />
  );
}

export function Input3({ placeholder, onChange, value }) {
  return (
    <InputStyle3
      type="text"
      placeholder={placeholder}
      onChange={onChange}
      value={value}
    />
  );
}

//hr 밑줄 Styled Component
const HrStyled = styled.hr`
  background-color: #1f1d1d;
  opacity: 40%;

  ${(props) =>
    props.Thin &&
    css`
      height: 1px;
      width: 80vw;
    `}

  ${(props) =>
    props.ThinFull &&
    css`
      height: 1px;
      width: 100vw;
    `}

    ${(props) =>
    props.Thick &&
    css`
      height: 2px;
      width: 80vw;
    `}

    ${(props) =>
    props.ThickFull &&
    css`
      height: 2px;
      width: 100vw;
    `}
`;

export function Hr({ ...props }) {
  return <HrStyled {...props} />;
}

const StyledDiv = styled.div`
  display: flex;
  ${(props) =>
    props.Column_SB &&
    css`
      flex-direction: column;
      justify-content: space-between;
      align-items: center;
    `}

  ${(props) =>
    props.Column_SA &&
    css`
      flex-direction: column;
      justify-content: space-around;
      align-items: center;
    `}
    ${(props) =>
    props.Column_E &&
    css`
      flex-direction: column;
      justify-content: flex-end;
    `}
${(props) =>
    props.Row_E &&
    css`
      flex-direction: row;
      justify-content: flex-end;
    `}
    ${(props) =>
    props.Row_SB &&
    css`
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
    `}
    ${(props) =>
    props.Column_C &&
    css`
      flex-direction: column;
      justify-content: center;
      align-items: center;
    `}

    ${(props) =>
    props.Row_SA &&
    css`
      flex-direction: row;
      justify-content: space-around;
      align-items: center;
    `}

    ${(props) =>
    props.Row_C &&
    css`
      flex-direction: row;
      justify-content: center;
      align-items: center;
    `}
    ${(props) =>
    props.Row_S &&
    css`
      flex-direction: row;
      justify-content: flex-start;
      align-items: center;
    `}
    ${(props) =>
    props.MainContent &&
    css`
      flex-direction: column;
      justify-content: center;
      align-items: center;
      min-height: 90vh;
    `}

    ${(props) =>
    props.MainContent2 &&
    css`
      flex-direction: column;
      justify-content: center;
      align-items: center;
    `}
    ${(props) =>
    props.Content &&
    css`
      flex-direction: column;
      justify-content: center;
      align-items: center;
    `}
`;

export function FlexBox({ children, ...props }) {
  return <StyledDiv {...props}> {children} </StyledDiv>;
}
