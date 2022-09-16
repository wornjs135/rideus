import React, { useEffect, useState } from "react";
import styled, { css } from "styled-components";

const StyledButton = styled.button`
  height: 42px;
  border-radius: 5px;
  border: 0px;
  font-size: 12px;
  font-family: "Noto Sans KR", sans-serif;
  font-weight: 500;
  margin: 10px 5px;

  ${(props) =>
    props.BigGreen &&
    css`
      color: white;
      background: #439652;
      width: 70vw;
    `}

  ${(props) =>
    props.MediumGreen &&
    css`
      color: white;
      background: #439652;
      width: 40vw;
    `}

    ${(props) =>
    props.SmallGreen &&
    css`
      color: white;
      background: #439652;
      width: 20vw;
    `}

    ${(props) =>
    props.Custom &&
    css`
      color: props.textColor;
      background: props.color;
      width: props.bWidth;
    `}
`;

export default function Button({ children, ...props }) {
  return <StyledButton {...props}>{children}</StyledButton>;
}
