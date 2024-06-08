package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.repository.exception.NotFoundException;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.servlet.dto.user.UserIncomingDto;
import org.example.servlet.dto.user.UserOutGoingDto;
import org.example.servlet.dto.user.UserUpdateDto;

import java.util.List;

@WebServlet(urlPatterns = {"/user/*"})
public class UserServlet extends Servlet {
    public static final String BAD_REQUEST = "Bad request.";
    public static final String INCORRECT_USER = "Incorrect User.";
    private final transient UserService service = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
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
            } else {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NotFoundException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setJsonHeader(resp);
        try {
            String json = getJson(req);
            UserIncomingDto userIncoming = new Gson().fromJson(json, UserIncomingDto.class);
            if (userIncoming == null) {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            UserOutGoingDto userOutGoing = service.save(userIncoming);
            sendResponse(resp, new Gson().toJson(userOutGoing), HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_ACCEPTABLE);
        } catch (Exception e) {
            sendResponse(resp, INCORRECT_USER, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        setJsonHeader(resp);
        try {
            String json = getJson(req);
            UserUpdateDto userUpdate = new Gson().fromJson(json, UserUpdateDto.class);
            if (userUpdate == null) {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            service.update(userUpdate);
            sendResponse(resp, "", HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_ACCEPTABLE);
        } catch (NotFoundException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, INCORRECT_USER, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        setJsonHeader(resp);
        String[] pathPart = parseRequest(req);

        try {
            if (isNumber(pathPart[1])) {
                Long id = getLong(pathPart[1]);
                if (service.delete(id)) {
                    sendResponse(resp, "", HttpServletResponse.SC_NO_CONTENT);
                } else {
                    sendResponse(resp, "No user with id: " + id, HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
            }
        }  catch (Exception e) {
            sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
