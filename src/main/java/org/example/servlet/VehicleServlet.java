package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.NotFoundException;
import org.example.service.VehicleService;
import org.example.service.impl.VehicleServiceImpl;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleOutGoingDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/vehicle/*"})
public class VehicleServlet extends Servlet {
    private final transient VehicleService service = VehicleServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            VehicleIncomingDto vehicleIncoming = new Gson().fromJson(json, VehicleIncomingDto.class);
            VehicleOutGoingDto vehicleOutGoing = service.save(vehicleIncoming);
            sendResponse(resp, new Gson().toJson(vehicleOutGoing), HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            sendResponse(resp, "Incorrect vehicle.", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);
        try {
            String json = getJson(req);
            VehicleUpdateDto vehicleUpdate = new Gson().fromJson(json, VehicleUpdateDto.class);
            service.update(vehicleUpdate);
        } catch (NotFoundException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, "Incorrect vehicle.", HttpServletResponse.SC_BAD_REQUEST);
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
        } catch (Exception e) {
            sendResponse(resp, "Bad request.", HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
