import { useEffect, useState } from "react";
import { Map, MapMarker, Polyline } from "react-kakao-maps-sdk";
import { s, s2 } from "../utils/data";

function MapTest() {
  // const latlng = [];
  // for (let i = 0; i < dataList.length; i++) {
  //   let data = {
  //     lat: dataList[i].split(",")[1],
  //     lng: dataList[i].split(",")[0],
  //   };
  //   latlng.push(data);
  // }
  // const [latlng, setLatlng] = useState([]);
  //
  const [data, setData] = useState({
    latlng: [],
    center: { lng: 127.002158, lat: 37.512847 },
  });

  const makeLine = () => {};

  useEffect(() => {
    const dataList = s.split("\r\n");
    console.log(dataList);
    let i = 0;
    const timerId = setInterval(() => {
      // do something ...
      console.log("interval");
      if (i < dataList.length) {
        console.log(i + "data added!");
        let data = {
          lat: dataList[i].split(",")[1],
          lng: dataList[i].split(",")[0],
        };
        setData((prev) => {
          return {
            latlng: [...prev.latlng, data],
            center: {
              lng: dataList[i].split(",")[0],
              lat: dataList[i].split(",")[1],
            },
          };
        });
        i++;
        // setCenter((prev) => {
        //   console.log("index : " + i);
        //   return (prev = {
        //     lng: dataList[i].split(",")[0],
        //     lat: dataList[i].split(",")[1],
        //   });
        // });
      } else {
        clearInterval(timerId);
      }
    }, 1000);
    return () => clearInterval(timerId);
  }, []);

  return (
    <Map
      center={data.center}
      isPanto={true}
      style={{ width: "100%", height: "500px" }}
    >
      <Polyline
        path={[data.latlng]}
        strokeWeight={5} // 선의 두께 입니다
        strokeColor={"#030ff1"} // 선의 색깔입니다
        strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle={"solid"} // 선의 스타일입니다
      />
      {data.latlng.map((ll, index) => {
        if (index + 1 === 1) {
          return (
            <MapMarker key={index} position={ll}>
              <div style={{ color: "#000" }}>시작점</div>
            </MapMarker>
          );
        }
        if (index + 1 === data.latlng.length) {
          return <MapMarker key={index} position={ll}></MapMarker>;
        }
        if (index + 1 === 962) {
          return (
            <MapMarker key={index} position={ll}>
              <div style={{ color: "#000" }}>종점</div>
            </MapMarker>
          );
        }
        if ((index + 1) % 193 === 0) {
          return (
            <MapMarker key={index} position={ll}>
              <div style={{ color: "#000" }}>체크포인트</div>
            </MapMarker>
          );
        }
      })}
    </Map>
  );
}

export default MapTest;
