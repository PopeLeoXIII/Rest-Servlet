package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.repository.exception.NotFoundException;
import org.example.service.ReservationService;
import org.example.service.impl.ReservationServiceImpl;
import org.example.servlet.dto.reservation.ReservationIncomingDto;
import org.example.servlet.dto.reservation.ReservationUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import static org.mockito.Mockito.*;

public class ReservationServletTest {
    private static MockedStatic<ReservationServiceImpl> mokStaticService;
    private static final ReservationService mokServiceInstance = mock(ReservationService.class);

    private final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    private final HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    private final BufferedReader mockBufferedReader = mock(BufferedReader.class);

    @BeforeEach
    public void setUp() throws IOException {
        mokStaticService = mockStatic(ReservationServiceImpl.class);
        Mockito.doReturn(new PrintWriter(Writer.nullWriter())).when(mockResponse).getWriter();
    }

    @AfterEach
    public void tearDown() {
        mokStaticService.close();
        Mockito.reset(mokServiceInstance);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 100})
    void doGetTest(int id) throws NotFoundException {
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn("reservation/" + id).when(mockRequest).getPathInfo();

        servlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).findById(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -2, -3, -10, 10000})
    void doGetNotFoundTest(int id) throws NotFoundException {
        doThrow(NotFoundException.class).when(mokServiceInstance).findById(Mockito.anyLong());
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn("reservation/" + id).when(mockRequest).getPathInfo();

        servlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).findById(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doGetAllTest() {
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn("reservation/all").when(mockRequest).getPathInfo();

        servlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).findAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "get", "get/1"})
    void doGetBadRequestTest(String notId) throws NotFoundException {
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn("reservation/" + notId).when(mockRequest).getPathInfo();

        servlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance, never()).findById(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2016-06-22 19:10:25-07", "2040-06-22 19:10:25-07", "1600-06-22 19:10:25-07"})
    void doPostTest(String dateTime) throws IOException {
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        String reservationJson= "{\"startDatetime\":\"" + dateTime + "\"}";
        Mockito.doReturn(reservationJson, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPost(mockRequest, mockResponse);

        ArgumentCaptor<ReservationIncomingDto> argumentCaptor = ArgumentCaptor.forClass(ReservationIncomingDto.class);
        Mockito.verify(mokServiceInstance).save(argumentCaptor.capture());
        ReservationIncomingDto reservationDto = argumentCaptor.getValue();

        Assertions.assertEquals(dateTime, reservationDto.getStartDatetime());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_CREATED);

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "CITY", "1234"})
    void doPostBadRequestTest(String json) throws IOException {
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(json, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doPostIllegalArgTest() throws IOException {
        doThrow(IllegalArgumentException.class).when(mokServiceInstance).save(Mockito.any());
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        String reservationJson= "{\"id\":\"1\"}";
        Mockito.doReturn(reservationJson, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }


    @ParameterizedTest
    @ValueSource(longs = {1, 15, 10000})
    void doUpdateTest(long id) throws IOException, NotFoundException {
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        String reservationJson= "{\"id\":\"" + id + "\"}";
        Mockito.doReturn(reservationJson, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPut(mockRequest, mockResponse);

        ArgumentCaptor<ReservationUpdateDto> argumentCaptor = ArgumentCaptor.forClass(ReservationUpdateDto.class);
        Mockito.verify(mokServiceInstance).update(argumentCaptor.capture());
        ReservationUpdateDto reservationDto = argumentCaptor.getValue();

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
        Assertions.assertEquals(id, reservationDto.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "CITY", "1234"})
    void doUpdateBadRequestTest(String json) throws IOException {
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(json, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPut(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doUpdateIllegalArgTest() throws IOException, NotFoundException {
        doThrow(IllegalArgumentException.class).when(mokServiceInstance).update(Mockito.any());
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        String reservationJson= "{\"name\":\"reservation\"}";
        Mockito.doReturn(reservationJson, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPut(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }

    @Test
    void doUpdateNotFoundTest() throws IOException, NotFoundException {
        doThrow(NotFoundException.class).when(mokServiceInstance).update(Mockito.any());
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        String reservationJson= "{\"name\":\"reservation\"}";
        Mockito.doReturn(reservationJson, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPut(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 100})
    void doDeleteTest(int id) {
        Mockito.doReturn(true).when(mokServiceInstance).delete(Mockito.anyLong());
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn("reservation/" + id).when(mockRequest).getPathInfo();

        servlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).delete(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -2, -3, -10, 10000})
    void doDeleteNotFoundTest(int id) {
        Mockito.doReturn(false).when(mokServiceInstance).delete(Mockito.anyLong());
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn("reservation/" + id).when(mockRequest).getPathInfo();

        servlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).delete(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "delete", "delete/1"})
    void doDeleteBadRequestTest(String notId) {
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn("reservation/" + notId).when(mockRequest).getPathInfo();

        servlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance, never()).delete(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


    @Test
    void doGetAll() {
        Mockito.doReturn(List.of()).when(mokServiceInstance).findAll();
        mokStaticService.when(ReservationServiceImpl::getInstance).thenReturn(mokServiceInstance);
        ReservationServlet servlet = new ReservationServlet();

        Mockito.doReturn("reservation/all").when(mockRequest).getPathInfo();

        servlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).findAll();
    }
}