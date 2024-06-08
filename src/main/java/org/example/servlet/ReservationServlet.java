package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.repository.exception.NotFoundException;
import org.example.service.ReservationService;
import org.example.service.impl.ReservationServiceImpl;
import org.example.servlet.dto.reservation.ReservationIncomingDto;
import org.example.servlet.dto.reservation.ReservationOutGoingDto;
import org.example.servlet.dto.reservation.ReservationUpdateDto;

import java.util.List;

@WebServlet(urlPatterns = {"/reservation/*"})
public class ReservationServlet extends Servlet {
    public static final String BAD_REQUEST = "Bad request.";
    public static final String INCORRECT_RESERVATION = "Incorrect reservation.";
    private final transient ReservationService service = ReservationServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
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
            ReservationIncomingDto reservationIncoming = new Gson().fromJson(json, ReservationIncomingDto.class);
            if (reservationIncoming == null) {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            ReservationOutGoingDto reservationOutGoing = service.save(reservationIncoming);
            sendResponse(resp, new Gson().toJson(reservationOutGoing), HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_ACCEPTABLE);
        } catch (Exception e) {
            sendResponse(resp, INCORRECT_RESERVATION, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        setJsonHeader(resp);
        try {
            String json = getJson(req);
            ReservationUpdateDto reservationUpdate = new Gson().fromJson(json, ReservationUpdateDto.class);
            if (reservationUpdate == null) {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            service.update(reservationUpdate);
            sendResponse(resp, "", HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_ACCEPTABLE);
        } catch (NotFoundException e) {
            sendResponse(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            sendResponse(resp, INCORRECT_RESERVATION, HttpServletResponse.SC_BAD_REQUEST);
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
                    sendResponse(resp, "No Reservation with id: " + id, HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
            }
        }  catch (Exception e) {
            sendResponse(resp, BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
