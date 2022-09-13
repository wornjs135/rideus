import React from "react";
import { Outlet, Route, Routes, useNavigate } from "react-router-dom";

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
      <Route path="/" element={<Layout />}></Route>
    </Routes>
  );
};
