package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.repository.exception.NotFoundException;
import org.example.service.VehicleService;
import org.example.service.impl.VehicleServiceImpl;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;

import java.util.List;

@WebServlet(urlPatterns = {"/vehicle/*"})
public class VehicleServlet extends Servlet {
    public static final String BAD_REQUEST = "Bad request.";
    public static final String INCORRECT_USER = "Incorrect Vehicle.";
    private final transient VehicleService service = VehicleServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        setJsonHeader(resp);
        String[] pathPart = parseRequest(req);

        try {
            if ("all".equals(pathPart[1])) {
                List<VehicleOutGoingDto> allVehicle = service.findAll();
                sendResponse(resp, new Gson().toJson(allVehicle), HttpServletResponse.SC_OK);
            } else if (isNumber(pathPart[1])) {
                Long id = getLong(pathPart[1]);
                VehicleOutGoingDto vehicle = service.findById(id);
                sendResponse(resp, new Gson().toJson(vehicle), HttpServletResponse.SC_OK);
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
            VehicleIncomingDto vehicleIncoming = new Gson().fromJson(json, VehicleIncomingDto.class);
            if (vehicleIncoming == null) {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            VehicleOutGoingDto vehicleOutGoing = service.save(vehicleIncoming);
            sendResponse(resp, new Gson().toJson(vehicleOutGoing), HttpServletResponse.SC_CREATED);
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
            VehicleUpdateDto vehicleUpdate = new Gson().fromJson(json, VehicleUpdateDto.class);
            if (vehicleUpdate == null) {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            service.update(vehicleUpdate);
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
                    sendResponse(resp, "No vehicle with id: " + id, HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
            }
        }  catch (Exception e) {
            sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
