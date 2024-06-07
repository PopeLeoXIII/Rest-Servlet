package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.NotFoundException;
import org.example.service.ReservationService;
import org.example.service.impl.ReservationServiceImpl;
import org.example.servlet.dto.reservation.ReservationIncomingDto;
import org.example.servlet.dto.reservation.ReservationOutGoingDto;
import org.example.servlet.dto.reservation.ReservationUpdateDto;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/reservation/*"})
public class ReservationServlet extends Servlet {
    private final transient ReservationService service = ReservationServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);
        String[] pathPart = parseRequest(req);

        try {
            if ("all".equals(pathPart[1])) {
                List<ReservationOutGoingDto> allReservation = service.findAll();
                sendResponse(resp, new Gson().toJson(allReservation), HttpServletResponse.SC_OK);
            } else if (isNumber(pathPart[1])) {
                Long id = getLong(pathPart[1]);

                ReservationOutGoingDto reservation = service.findById(id);
                sendResponse(resp, new Gson().toJson(reservation), HttpServletResponse.SC_OK);
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
            ReservationIncomingDto reservationIncoming = new Gson().fromJson(json, ReservationIncomingDto.class);
            ReservationOutGoingDto reservationOutGoing = service.save(reservationIncoming);
            sendResponse(resp, new Gson().toJson(reservationOutGoing), HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            sendResponse(resp, "Incorrect reservation.", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJsonHeader(resp);
        try {
            String json = getJson(req);
            ReservationUpdateDto reservationUpdate = new Gson().fromJson(json, ReservationUpdateDto.class);
            service.update(reservationUpdate);
        } catch (NotFoundException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, "Incorrect reservation.", HttpServletResponse.SC_BAD_REQUEST);
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
