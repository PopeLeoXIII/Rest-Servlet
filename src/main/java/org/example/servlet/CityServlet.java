package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.repository.exception.NotFoundException;
import org.example.service.CityService;
import org.example.service.impl.CityServiceImpl;
import org.example.servlet.dto.city.CityIncomingDto;
import org.example.servlet.dto.city.CityOutGoingDto;
import org.example.servlet.dto.city.CityUpdateDto;

import java.util.List;

@WebServlet(urlPatterns = {"/city/*"})
public class CityServlet extends Servlet {
    public static final String BAD_REQUEST = "Bad request.";
    public static final String INCORRECT_CITY = "Incorrect city.";
    private final transient CityService service = CityServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        setJsonHeader(resp);
        String[] pathPart = parseRequest(req);

        try {
            if ("all".equals(pathPart[1])) {
                List<CityOutGoingDto> allCity = service.findAll();
                sendResponse(resp, new Gson().toJson(allCity), HttpServletResponse.SC_OK);
            } else if (isNumber(pathPart[1])) {
                Long id = getLong(pathPart[1]);
                CityOutGoingDto city = service.findById(id);
                sendResponse(resp, new Gson().toJson(city), HttpServletResponse.SC_OK);
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
            CityIncomingDto cityIncoming = new Gson().fromJson(json, CityIncomingDto.class);
            if (cityIncoming == null) {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
            }
            CityOutGoingDto cityOutGoing = service.save(cityIncoming);
            sendResponse(resp, new Gson().toJson(cityOutGoing), HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_ACCEPTABLE);
        } catch (Exception e) {
            sendResponse(resp, INCORRECT_CITY, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        setJsonHeader(resp);
        try {
            String json = getJson(req);
            CityUpdateDto cityUpdate = new Gson().fromJson(json, CityUpdateDto.class);
            if (cityUpdate == null) {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
            }
            service.update(cityUpdate);
            sendResponse(resp, "", HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_ACCEPTABLE);
        } catch (NotFoundException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, INCORRECT_CITY, HttpServletResponse.SC_BAD_REQUEST);
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
                    sendResponse(resp, "No city with id: " + id, HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
