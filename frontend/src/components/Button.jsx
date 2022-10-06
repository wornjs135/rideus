import React, { useEffect, useState } from "react";
import styled, { css } from "styled-components";
import { motion } from "framer-motion";
const StyledButton = styled(motion.button)`
  height: 42px;
  border-radius: 8px;

  font-size: 12px;
  font-family: "gwmd";
  font-weight: 500;
  margin: 10px 5px;

  ${(props) =>
    props.biggreen &&
    css`
      height: 59px;
      border: 0px;
      font-size: 18px;
      color: white;
      background: #64ccbe;
      width: 80vw;
    `}

  ${(props) =>
    props.bigpink &&
    css`
      border: 0px;
      color: white;
      background: #f29393;
      width: 70vw;
    `}

    ${(props) =>
    props.smallpink &&
    css`
      border: 0px;
      color: white;
      background: #f29393;
      width: 20vw;
    `}

    ${(props) =>
    props.mediumpink &&
    css`
      border: 0px;
      color: white;
      background: #f29393;
      width: 40vw;
    `}

  ${(props) =>
    props.mediumgreen &&
    css`
      border: 0px;
      color: white;
      background: #64ccbe;
      width: 40vw;
    `}

    ${(props) =>
    props.smallgreen &&
    css`
      border: 0px;
      color: white;
      background: #64ccbe;
      width: 20vw;
    `}

    ${(props) =>
    props.bigwhite &&
    css`
      height: 59px;
      font-size: 18px;
      border: 1px solid #64ccbe;
      background: white;
      color: #64ccbe;
      width: 80vw;
    `}

    ${(props) =>
    props.mediumwhite &&
    css`
      border: 1px solid #64ccbe;
      background: white;
      color: #64ccbe;
      width: 40vw;
    `}

    ${(props) =>
    props.smallwhite &&
    css`
      border: 1px solid #64ccbe;
      background: white;
      color: #64ccbe;
      width: 20vw;
    `}

    ${(props) =>
    props.info &&
    css`
      height: 29px;
      border: 0px;
      border-radius: 88px;
      background: rgba(255, 255, 255, 0.79);
      color: black;
      width: 15vw;
    `}

    ${(props) =>
    props.infoselect &&
    css`
      height: 29px;
      border: 0px;
      border-radius: 88px;
      background: rgba(67, 150, 82, 0.79);
      color: white;
      width: 15vw;
    `}

    ${(props) =>
    props.custom &&
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
    props.taggreen &&
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
    props.taggray &&
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
  return (
    <StyledButton {...props} whileTap={{ scale: 1.2 }}>
      {children}
    </StyledButton>
  );
}
