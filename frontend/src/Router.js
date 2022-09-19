import React from "react";
import {Outlet, Route, Routes, useNavigate} from "react-router-dom";
import {Footer, LogoHeader, NavBar} from "./components/Common.jsx";
import {Course} from "./pages/Course";
import {Main} from "./pages/Main";
import {MyPage} from "./pages/MyPage";
import {Rank} from "./pages/Rank";
import {GpsTest} from "./pages/test/GpsTest";
import {Login} from "./pages/Login/Login"
import {OAuth2RedirectHandler} from "./pages/Login/OAuth2RedirectHandler"
import MapTest from "./pages/test/MapTest";

const Layout = () => {
    return (
        <div>
            <LogoHeader/>
            <Outlet/>
            <Footer/>
            <NavBar/>
        </div>
    );
};

const WithOutHeaderNav = () => {
    return (
        <div>
            <Outlet/>
        </div>
    )
}

export const Router = () => {
    return (
        <Routes>
            {/* 로고, 푸터, 내브바 */}
            <Route path="/" element={<Layout/>}>
                {/* <Route index element={<MapTest />}></Route> */}
                <Route index element={<Main/>}/>
                <Route path="/mypage" element={<MyPage/>}/>
                <Route path="/course" element={<Course/>}/>
                <Route path="/rank" element={<Rank/>}/>
                <Route path="/gpsTest" element={<GpsTest/>}/>
            </Route>
            {/* 헤더, 내브바 없는 곳*/}
            <Route element={<WithOutHeaderNav/>}>
                <Route path="/login" element={<Login/>}/>
                <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler/>}/>
            </Route>
        </Routes>
    );
};
