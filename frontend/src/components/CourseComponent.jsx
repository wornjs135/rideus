import styled from "styled-components";
import { StyledText } from "./Common.jsx";
import Star from "../assets/images/star.png";
import StarBlank from "../assets/images/star_blank.png";
import { Box } from "grommet";

const CourseBox = styled.div`
  display: flex;
  flex-direction: column;
  position: relative;
  justify-content: space-between;
  align-items: center;
  width: 153px;
  height: 222px;
  margin-right: 15px;
  margin-bottom: 10px;
  margin-top: 5px;
  padding: 10px;
  padding-top: 14px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 4px 4px 4px -4px rgb(0 0 0 / 0.2);
`;

const NewsDiv = styled.div`
  display: flex;
  flex-direction: column;
  position: relative;
  align-items: center;
  width: 153px;
  height: 222px;
  margin-right: 15px;
  margin-bottom: 10px;
  margin-top: 5px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 4px 4px 4px -4px rgb(0 0 0 / 0.2);
`;

export const BestCourse = ({ course }) => {
  return (
    <CourseBox>
      <StyledText text="강촌길" weight="bold" color="#439652" />
      <div>
        <img src={Star} alt="" />
        <img src={Star} alt="" />
        <img src={Star} alt="" />
        <img src={Star} alt="" />
        <img src={StarBlank} alt="" />
      </div>
      <StyledText text="경강교 - 강촌유원지" color="#969696" />
      <StyledText text="오르막길 오르다 죽을뻔.." color="#3C3C43" />
      <StyledText text="오르막길 오르다 죽을뻔.." color="#3C3C43" />
      <StyledText text="오르막길 오르다 죽을뻔.." color="#3C3C43" />
      <Box align="end">
        <StyledText text="총 코스 길이 : 21km" />
        <StyledText text="예상 시간 : 1h 20m" />
      </Box>
    </CourseBox>
  );
};

export const NewsBox = ({ news }) => {
  return (
    <NewsDiv
      onClick={() => {
        window.open(news.link, "_blank");
      }}
    >
      <Box>
        <img
          src={news.image}
          alt=""
          style={{
            borderRadius: "15px 15px 0 0",
          }}
        />
      </Box>
      <Box justify="center" width="100%" style={{ display: "fixed" }}>
        <StyledText
          text={news.title}
          weight="bold"
          color="#439652"
          style={{
            width: "100%",
            overflow: "hidden",
            wordWrap: "break-word",
          }}
        />
      </Box>
    </NewsDiv>
  );
};
