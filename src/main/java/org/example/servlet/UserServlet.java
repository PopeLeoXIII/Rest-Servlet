package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.NotFoundException;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.dto.user.UserIncomingDto;
import org.example.servlet.dto.user.UserOutGoingDto;
import org.example.servlet.dto.user.UserUpdateDto;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/user/*"})
public class UserServlet extends Servlet {
    private final transient UserService service = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);
        String[] pathPart = parseRequest(req);

        try {
            if ("all".equals(pathPart[1])) {
                List<UserOutGoingDto> allUser = service.findAll();
                sendResponse(resp, new Gson().toJson(allUser), HttpServletResponse.SC_OK);
            } else if (isNumber(pathPart[1])) {
                Long id = getLong(pathPart[1]);
                UserOutGoingDto user = service.findById(id);
                sendResponse(resp, new Gson().toJson(user), HttpServletResponse.SC_OK);
            }
        } catch (NotFoundException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, "Bad request.", HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);
        try {
            String json = getJson(req);
            UserIncomingDto userIncoming = new Gson().fromJson(json, UserIncomingDto.class);
            UserOutGoingDto userOutGoing = service.save(userIncoming);
            sendResponse(resp, new Gson().toJson(userOutGoing), HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            sendResponse(resp, "Incorrect User.", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);
        try {
            String json = getJson(req);
            UserUpdateDto userUpdate = new Gson().fromJson(json, UserUpdateDto.class);
            service.update(userUpdate);
        } catch (NotFoundException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, "Incorrect User.", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);
        String[] pathPart = parseRequest(req);

        try {
            if (isNumber(pathPart[1])) {
                Long id = getLong(pathPart[1]);

                if (service.delete(id)) {
                    sendResponse(resp, "", HttpServletResponse.SC_NO_CONTENT);
                } else {
                    sendResponse(resp, "", HttpServletResponse.SC_NOT_FOUND);
                }
            }

        } catch (NotFoundException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        }  catch (Exception e) {
            sendResponse(resp, "Bad request.", HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
