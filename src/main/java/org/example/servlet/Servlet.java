package org.example.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.vehicle.VehiclePlaneDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Servlet extends HttpServlet {

    static void setJsonHeader(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    //TODO переписать
    static String getJson(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader postData = req.getReader();
        String line;
        while ((line = postData.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    static String[] parseRequest(HttpServletRequest req) {
        return req.getPathInfo().split("/");
    }

    static void sendResponse(HttpServletResponse resp, String responseAnswer, int status ) throws IOException {
        resp.setStatus(status);

        if (!responseAnswer.isEmpty()) {
            PrintWriter out = resp.getWriter();
            out.write(responseAnswer);
            out.flush();
        }
    }

    static boolean isNumber(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static Long getLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    final static CityOutGoingDto CITY_OUT_GOING_DTO = new CityOutGoingDto(
            1L,
            "saratov",
            Arrays.asList(
                    new VehiclePlaneDto(1L, "velo 1"),
                    new VehiclePlaneDto(2L, "velo 2")
            )
    );
    
}
