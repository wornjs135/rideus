import React from "react";
import { Outlet, Route, Routes, useNavigate } from "react-router-dom";
import { GpsTest } from "./pages/test/GpsTest";
import MapTest from "./pages/test/MapTest";

const Layout = () => {
  return (
    <div>
      hello
      <Outlet />
    </div>
  );
};

export const Router = () => {
  return (
    <Routes>
      {/* 로고, 푸터, 내브바 */}
      <Route path="/" element={<Layout />}>
        {/* <Route index element={<MapTest />}></Route> */}
        <Route index element={<GpsTest />}></Route>
      </Route>
    </Routes>
  );
};
