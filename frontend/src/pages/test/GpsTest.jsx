import React from "react";
import { useEffect, useState } from "react";
import { Map, MapMarker, Polyline } from "react-kakao-maps-sdk";
import { useGeolocated } from "react-geolocated";
export const GpsTest = () => {
  const [data, setData] = useState({
    latlng: [],
    center: { lng: 127.002158, lat: 37.512847 },
  });
  //   const onGeolocationUpdate = (geolocation) => {
  //     // console.log(geolocation);
  //     const gps = {
  //       lat: geolocation.latitude,
  //       lng: geolocation.longitude,
  //     };
  //     console.log(gps);
  //     setData((prev) => {
  //       return {
  //         latlng: [...prev.latlng, gps],
  //         center: gps,
  //       };
  //     });
  //   };
  //   const geolocation = useGeolocation({}, () => {}, false);

  const { coords, isGeolocationAvailable, isGeolocationEnabled } =
    useGeolocated({
      positionOptions: {
        enableHighAccuracy: true,
        maximumAge: 0,
        timeout: Infinity,
      },
      watchPosition: true,
    });
  // const [i, setI] = useState(0.0001);
  useEffect(() => {
    // let i = 0.000001;
    const timerId = setInterval(() => {
      if (isGeolocationAvailable && isGeolocationEnabled) {
        console.log(coords);
        const gps = {
          lat: coords.latitude,
          lng: coords.longitude,
        };
        console.log(gps);
        setData((prev) => {
          return {
            latlng: [...prev.latlng, gps],
            center: gps,
          };
        });
        // setI((prev) => {
        //   return prev + 0.0001;
        // });
      }
    }, 1000);
    return () => clearInterval(timerId);
  });

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
      })}
    </Map>
  );
};
