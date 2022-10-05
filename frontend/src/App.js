import { useEffect } from "react";
import "./App.css";
import { Router } from "./Router";

function App() {
  function setScreenSize() {
    let vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }
  useEffect(() => {
    setScreenSize();
  });
  return (
    <div className="App">
      <Router />
    </div>
  );
}

export default App;
