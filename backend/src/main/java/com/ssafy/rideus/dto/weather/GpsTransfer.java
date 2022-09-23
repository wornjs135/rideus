package com.ssafy.rideus.dto.weather;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OptimisticLockType;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GpsTransfer {

    private double lat;
    private double lon;

    private int xLat;
    private int yLon;



    public GpsTransfer(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }


    public void transfer (GpsTransfer gpt, int mode) {

        double RE = 6371.00877;
        double GRID = 5.0;
        double SLAT1 = 30.0;
        double SLAT2 = 60.0;
        double OLON = 126.0;
        double OLAT = 38.0;
        double XO = 43;
        double YO = 136;


        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1*0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro,sn);

        if(mode == 0) {
            double ra = Math.tan(Math.PI * 0.25 + (gpt.getLat() * DEGRAD * 0.5));
            ra = re * sf / Math.pow(ra, sn);
            double theta = gpt.getLon() * DEGRAD - olon;
            if(theta > Math.PI) theta -= 2.0 * Math.PI;
            if(theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            double x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            double y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

            System.out.println("x = " + x);
            System.out.println("y = " + y);
            gpt.setXLat(Integer.parseInt(x+""));
            gpt.setYLon(Integer.parseInt(y+""));
        }
        else {
            double xlat = gpt.getXLat();
            double ylon = gpt.getYLon();
            double xn = xlat - XO;
            double yn = ro - ylon + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if(sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re*sf/ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if(Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if(Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if(xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }

            double alon = theta / sn + olon;
            gpt.setXLat(Integer.parseInt(alat*RADDEG+""));
            gpt.setYLon(Integer.parseInt(alon*RADDEG+""));
        }





    }





}
