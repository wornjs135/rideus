import React from "react";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";

export const Course = () => {
  const navigate = useNavigate();
  return (
    <div>
      <Button
        children="코스 상세"
        onClick={() => {
          navigate(`/courseDetail/${18}`, {
            state: { courseName: "마포-정서점" },
          });
        }}
      />
    </div>
  );
};
