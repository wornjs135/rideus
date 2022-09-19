import React from "react";
import { Outlet, Route, Routes, useNavigate } from "react-router-dom";
import { Footer, LogoHeader, NavBar } from "./components/Common.jsx";
import { Course } from "./pages/Course";
import { Main } from "./pages/Main";
import { MyPage } from "./pages/MyPage";
import { Rank } from "./pages/Rank";
import { GpsTest } from "./pages/test/GpsTest";
import MapTest from "./pages/test/MapTest";

const Layout = () => {
  return (
    <div>
      <LogoHeader />
      <Outlet />
      <Footer />
      <NavBar />
    </div>
  );
};

export const Router = () => {
  return (
    <Routes>
      {/* 로고, 푸터, 내브바 */}
      <Route path="/" element={<Layout />}>
        {/* <Route index element={<MapTest />}></Route> */}
        <Route index element={<Main />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/course" element={<Course />} />
        <Route path="/rank" element={<Rank />} />
        <Route path="/gpsTest" element={<GpsTest />} />
      </Route>
    </Routes>
  );
};
