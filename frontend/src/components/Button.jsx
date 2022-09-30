import React, { useEffect, useState } from "react";
import styled, { css } from "styled-components";

const StyledButton = styled.button`
  height: 42px;
  border-radius: 8px;

  font-size: 12px;
  font-family: "Noto Sans KR", sans-serif;
  font-weight: 500;
  margin: 10px 5px;

  ${(props) =>
    props.BigGreen &&
    css`
      height: 59px;
      border: 0px;
      font-size: 18px;
      color: white;
      background: #64ccbe;
      width: 80vw;
    `}

  ${(props) =>
    props.BigPink &&
    css`
      border: 0px;
      color: white;
      background: #f29393;
      width: 70vw;
    `}

    ${(props) =>
    props.SmallPink &&
    css`
      border: 0px;
      color: white;
      background: #f29393;
      width: 20vw;
    `}

    ${(props) =>
    props.MediumPink &&
    css`
      border: 0px;
      color: white;
      background: #f29393;
      width: 40vw;
    `}

  ${(props) =>
    props.MediumGreen &&
    css`
      border: 0px;
      color: white;
      background: #64ccbe;
      width: 40vw;
    `}

    ${(props) =>
    props.SmallGreen &&
    css`
      border: 0px;
      color: white;
      background: #64ccbe;
      width: 20vw;
    `}

    ${(props) =>
    props.BigWhite &&
    css`
      height: 59px;
      font-size: 18px;
      border: 1px solid #64ccbe;
      background: white;
      color: #64ccbe;
      width: 80vw;
    `}

    ${(props) =>
    props.MediumWhite &&
    css`
      border: 1px solid #64ccbe;
      background: white;
      color: #64ccbe;
      width: 40vw;
    `}

    ${(props) =>
    props.SmallWhite &&
    css`
      border: 1px solid #64ccbe;
      background: white;
      color: #64ccbe;
      width: 20vw;
    `}

    ${(props) =>
    props.Info &&
    css`
      height: 29px;
      border: 0px;
      border-radius: 88px;
      background: rgba(255, 255, 255, 0.79);
      color: black;
      width: 15vw;
    `}

    ${(props) =>
    props.InfoSelect &&
    css`
      height: 29px;
      border: 0px;
      border-radius: 88px;
      background: rgba(67, 150, 82, 0.79);
      color: white;
      width: 15vw;
    `}

    ${(props) =>
    props.Custom &&
    css`
      border: 0px;
      color: ${props.textColor};
      background: ${props.color};
      width: ${props.bWidth};
      height: ${props.bHeight};
      font-size: ${props.fontSize};
      font-weight: ${props.fontWeight};
    `}

    ${(props) =>
    props.TagGreen &&
    css`
      color: white;
      background-color: #64ccbe;
      padding: 8px;
      font-size: 12px;
      margin: 0px 8px 12px 0px;
      border: 1px solid #ebebeb;
      border-radius: 16px;
    `}
    ${(props) =>
    props.TagGray &&
    css`
      color: #565656;
      background-color: #f4f4f4;
      padding: 8px;
      font-size: 12px;
      margin: 0px 8px 12px 0px;
      border: 1px solid #ebebeb;
      border-radius: 16px;
    `}
`;

export default function Button({ children, ...props }) {
  return <StyledButton {...props}>{children}</StyledButton>;
}
