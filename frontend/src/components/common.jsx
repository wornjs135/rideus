import React, { useEffect, useState } from "react";
import styled from "styled-components";
import Logo from "../assets/images/logo.png";
import { Link, useLocation, useNavigate } from "react-router-dom";
import CourseIcon from "../assets/images/course.png";
import ActiveCourseIcon from "../assets/images/course_active.png";
import HomeIcon from "../assets/images/home.png";
import ActiveHomeIcon from "../assets/images/home_active.png";
import RankIcon from "../assets/images/rank.png";
import ActiveRankIcon from "../assets/images/rank_active.png";
import MypageIcon from "../assets/images/mypage.png";
import ActiveMypageIcon from "../assets/images/mypage_active.png";
// 공통 컴포넌트들을 정의하는 클래스
// ex) 버튼, 레이아웃, 틀

// Header
const Header = styled.img`
  /* background-color: #1f1d1d; */
  max-width: 32vw;
  min-width: 32vw;
  padding: 0vw 34vw;
  max-height: 10vh;
  margin-top: 10px;
`;
export function LogoHeader() {
  const navigate = useNavigate();
  return <Header alt="logo" src={Logo} onClick={() => navigate("/")}></Header>;
}

//Footer
const FooterContainer = styled.div`
  padding: 15px;
  max-width: 300px;
  align-items: center;
`;

//NavBar
const NavBarDiv = styled.div`
  position: fixed;
  bottom: 0;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 100vw;
  background-color: white;
  padding: 5px 0px;
  opacity: ${(props) => props.opacity || "1"};
  transition: all 0.35s;
  visibility: ${(props) => props.isShow || "visible"};
`;
const IconButtonStyle = {
  display: "flex",
  flexDirection: "column",
  justifyContent: "center",
  alignItems: "center",
};
let lastScrollTop = 0;
let nowScrollTop = 0;
export function NavBar() {
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
    <NavBarDiv isShow={show} opacity={opacity}>
      <div style={IconButtonStyle}>
        <Link to="/">
          <img alt="홈" src={pathname === "/" ? ActiveHomeIcon : HomeIcon} />
        </Link>
      </div>
      <div style={IconButtonStyle}>
        <Link to="/course">
          <img
            alt="코스"
            src={pathname === "/course" ? ActiveCourseIcon : CourseIcon}
          />
        </Link>
      </div>
      <div style={IconButtonStyle}>
        <Link to="/rank">
          <img
            alt="랭킹"
            src={pathname === "/rank" ? ActiveRankIcon : RankIcon}
          />
        </Link>
      </div>
      <div style={IconButtonStyle}>
        <Link to="/mypage">
          <img
            alt="마이페이지"
            src={pathname === "/mypage" ? ActiveMypageIcon : MypageIcon}
          />
        </Link>
      </div>
    </NavBarDiv>
  );
}

//텍스트 폼
const TextForm = styled.div`
  color: ${(props) => props.color || "black"};
  font-size: ${(props) => props.size || "14px"};
  font-weight: ${(props) => props.weight || "normal"};
  font-family: "Noto Sans KR", sans-serif;
  align-items: center;
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
