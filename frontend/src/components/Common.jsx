import React, { useEffect, useState } from "react";
import styled from "styled-components";
import Logo from "../assets/images/logo.png";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { ReactComponent as CourseIcon } from "../assets/icons/course.svg";
// import ActiveCourseIcon from "../assets/icons/course_active.svg";
import { ReactComponent as HomeIcon } from "../assets/icons/home.svg";
import ActiveHomeIcon from "../assets/images/home_active.png";
import { ReactComponent as RankIcon } from "../assets/icons/rank.svg";
import ActiveRankIcon from "../assets/images/rank_active.png";
import { ReactComponent as MypageIcon } from "../assets/icons/mypage.svg";
import ActiveMypageIcon from "../assets/images/mypage_active.png";
import { PrivateTerms } from "./PrivateTerms";
import { ServiceTerms } from "./ServiceTerms";
import Stars from "../assets/images/stars.png";
import StarsBlank from "../assets/images/stars_blank.png";
import { Box } from "grommet";
import { Map, Polyline } from "react-kakao-maps-sdk";
import { motion } from "framer-motion";
// 공통 컴포넌트들을 정의하는 클래스
// ex) 버튼, 레이아웃, 틀

// Header
const Header = styled.div`
  background-color: white;
  width: 100%;
  max-height: 10vh;
  text-align: center;
  margin: 15px 0px;
`;

export function LogoHeader() {
  const navigate = useNavigate();
  return (
    <Header>
      <motion.img
        whileTap={{ scale: 1.2 }}
        alt="logo"
        src={Logo}
        onClick={() => navigate("/")}
      />
    </Header>
  );
}

//NavBar
const NavBarDiv = styled.div`
  position: fixed;
  bottom: 0;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 100%;
  background-color: white;
  max-width: 500px;
  padding: 5px 0px;
  opacity: ${(props) => props.opacity || "1"};
  transition: all 0.35s;
  visibility: ${(props) => props.isShow || "visible"};
`;
const IconButtonStyle = {
  justifyContent: "center",
  alignItems: "center",
};
let lastScrollTop = 0;
let nowScrollTop = 0;
export const NavBar = () => {
  const [show, handleShow] = useState("visible");
  const [opacity, setOpacity] = useState("1");
  const { pathname } = useLocation();
  useEffect(() => {
    let mounted = true;
    window.addEventListener("scroll", () => {
      if (mounted) {
        nowScrollTop = window.scrollY;
        let fixBoxHeight = "50";
        if (nowScrollTop > lastScrollTop && nowScrollTop > fixBoxHeight) {
          handleShow("hidden");
          setOpacity("0");
          // console.log("down ", show);
        } else {
          if (nowScrollTop + window.innerHeight < document.body.offsetHeight) {
            //Scroll up (하단 고정메뉴 보임)
            handleShow("visible");
            setOpacity("1");
            // console.log("up ", show);
          }
        }
        lastScrollTop = nowScrollTop;
      }
    });

    return () => {
      // window.removeEventListener("scroll", () => { });
      mounted = false;
    };
  }, []);
  return (
    // <NavBarDiv isShow={show} opacity={opacity}>
    //   <div style={IconButtonStyle}>
    //     <Link to="/">
    //       <HomeIcon stroke={pathname === "/" ? "#64CCBE" : "#444444"} />
    //     </Link>
    //   </div>
    //   <div style={IconButtonStyle}>
    //     <Link to="/courseList">
    //       <CourseIcon
    //         fill={pathname === "/courseList" ? "#64CCBE" : "#444444"}
    //       />
    //     </Link>
    //   </div>
    //   <div style={IconButtonStyle}>
    //     <Link to="/rank">
    //       <RankIcon stroke={pathname === "/rank" ? "#64CCBE" : "#444444"} />
    //     </Link>
    //   </div>
    //   <div style={IconButtonStyle}>
    //     <Link to="/mypage">
    //       <MypageIcon stroke={pathname === "/mypage" ? "#64CCBE" : "#444444"} />
    //     </Link>
    //   </div>
    // </NavBarDiv>
    <Box width="100%">
      <NavBarDiv isShow={show} opacity={opacity}>
        <motion.div style={IconButtonStyle} whileTap={{ scale: 1.2 }}>
          <Link to="/">
            <HomeIcon stroke={pathname === "/" ? "#64CCBE" : "#444444"} />
          </Link>
        </motion.div>
        <motion.div style={IconButtonStyle} whileTap={{ scale: 1.2 }}>
          <Link to="/courseList">
            <CourseIcon
              fill={pathname === "/courseList" ? "#64CCBE" : "#444444"}
            />
          </Link>
        </motion.div>
        <motion.div style={IconButtonStyle} whileTap={{ scale: 1.2 }}>
          <Link to="/rank">
            <RankIcon stroke={pathname === "/rank" ? "#64CCBE" : "#444444"} />
          </Link>
        </motion.div>
        <motion.div style={IconButtonStyle} whileTap={{ scale: 1.2 }}>
          <Link to="/mypage">
            <MypageIcon
              stroke={pathname === "/mypage" ? "#64CCBE" : "#444444"}
            />
          </Link>
        </motion.div>
      </NavBarDiv>
    </Box>
  );
};

//텍스트 폼
const TextForm = styled.div`
  color: ${(props) => props.color || "black"};
  font-size: ${(props) => props.size || "14px"};
  font-weight: ${(props) => props.weight || "normal"};
  align-items: end;
  display: flex;
`;

//텍스트 사이즈, 컬러, 웨이트, 글자를 설정할 수 있는 컴포넌트
export function StyledText({ size, color, weight, text, style }) {
  return (
    <TextForm size={size} color={color} weight={weight} style={style}>
      {text}
    </TextForm>
  );
}

//Footer
const FooterContainer = styled.div`
  padding: 15px;
  max-width: 300px;
  align-items: center;
`;

const FooterContent = ({ title, desc, openUserTerm, openServiceTerm }) => {
  return (
    <div style={{ padding: "10px", fontSize: "10px" }}>
      <h3>{title || ""}</h3>
      {desc.map((d) => (
        <p
          key={d}
          onClick={
            d === "이용약관"
              ? () => {
                  openServiceTerm(true);
                }
              : d === "개인정보처리방침"
              ? () => {
                  openUserTerm(true);
                }
              : () => {}
          }
        >
          {d}
        </p>
      ))}
    </div>
  );
};

export function Footer() {
  // const [open, setOpen] = useState(false);
  // const [FAQ, setFAQ] = useState(false);
  // const [guide, openGuid] = useState(false);
  const [userTerm, openUserTerm] = useState(false);
  const [serviceTerm, openServiceTerm] = useState(false);
  function onDismiss() {
    // setOpen(false);
    // setFAQ(false);
    // openGuid(false);
    openServiceTerm(false);
    openUserTerm(false);
  }
  return (
    <Box
      background="light-3"
      margin={{ bottom: "0" }}
      align="center"
      padding="25px"
    >
      <FooterContainer>
        <Box>
          <FooterContent
            title="고객센터 1500-1111"
            desc={[
              "운영시간 평일 11:00 - 18:00 (토, 일, 공휴일 휴무)",
              "점심시간 평일 13:00 - 14:00",
            ]}
          />
        </Box>
        <Box direction="row" align="center">
          <PrivateTerms open={userTerm} onDismiss={onDismiss} />
          <ServiceTerms open={serviceTerm} onDismiss={onDismiss} />
        </Box>
        <Box>
          <FooterContent
            openUserTerm={openUserTerm}
            openServiceTerm={openServiceTerm}
            desc={[
              "이용약관",
              "개인정보처리방침",
              "SSAFY A603",
              "배인수 고유라 문석희 박재권 백승훈 손민지",
              `Git project.ssafy.com`,
            ]}
          />
        </Box>
      </FooterContainer>
    </Box>
  );
}

export const StarBox = ({ score, starView }) => {
  return (
    <Box direction="row" align="center" gap="small" justify="start" width="55%">
      {score >= 0 && <StyledText text={score} weight="bold" size="19px" />}
      <Box style={{ position: "relative" }} justify="center">
        <div
          // align="center"
          style={{
            width: starView,
            height: "16px",
            overflow: "hidden",
          }}
        >
          <img
            className="pointOfStar"
            alt="별"
            src={Stars}
            style={{
              height: "16px",
              width: "80px",
            }}
          />
        </div>
        <img
          className="backgrdoundStar"
          alt="별"
          src={StarsBlank}
          style={{
            position: "absolute",
            width: "80px",
            height: "16px",
          }}
        />
      </Box>
    </Box>
  );
};

export const CourseMap = ({
  course,
  width,
  height,
  marker,
  marker1,
  infoMarkers,
}) => {
  return (
    <Map
      center={course[0]}
      isPanto={true}
      style={{ borderRadius: "25px", width: width, height: height }}
    >
      {course && (
        <Polyline
          path={[course ? course : []]}
          strokeWeight={5} // 선의 두께 입니다
          strokeColor={"#030ff1"} // 선의 색깔입니다
          strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
          strokeStyle={"solid"} // 선의 스타일입니다
        />
      )}
      {marker1}
      {marker}
      {infoMarkers}
    </Map>
  );
};
