package org.example.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Servlet extends HttpServlet {

    static void setJsonHeader(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    static String getJson(HttpServletRequest req) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append(System.lineSeparator());
        }
        return buffer.toString();
    }

    static String[] parseRequest(HttpServletRequest req) {
        return req.getPathInfo().split("/");
    }

    static void sendResponse(HttpServletResponse resp, String responseAnswer, int status ) {
        resp.setStatus(status);
        try {
            if (responseAnswer != null && !responseAnswer.isEmpty()) {
                PrintWriter out = resp.getWriter();
                out.write(responseAnswer);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
}
