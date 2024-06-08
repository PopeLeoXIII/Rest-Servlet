package org.example.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.repository.exception.NotFoundException;
import org.example.service.VehicleService;
import org.example.service.impl.VehicleServiceImpl;
import org.example.servlet.dto.vehicle.VehicleIncomingDto;
import org.example.servlet.dto.vehicle.VehicleUpdateDto;
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

public class VehicleServletTest {
    private static MockedStatic<VehicleServiceImpl> mokStaticService;
    private static final VehicleService mokServiceInstance = mock(VehicleService.class);

    private final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    private final HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    private final BufferedReader mockBufferedReader = mock(BufferedReader.class);

    @BeforeEach
    public void setUp() throws IOException {
        mokStaticService = mockStatic(VehicleServiceImpl.class);
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
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn("vehicle/" + id).when(mockRequest).getPathInfo();

        servlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).findById(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -2, -3, -10, 10000})
    void doGetNotFoundTest(int id) throws NotFoundException {
        doThrow(NotFoundException.class).when(mokServiceInstance).findById(Mockito.anyLong());
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn("vehicle/" + id).when(mockRequest).getPathInfo();

        servlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).findById(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doGetAllTest() {
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn("vehicle/all").when(mockRequest).getPathInfo();

        servlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).findAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "get", "get/1"})
    void doGetBadRequestTest(String notId) throws NotFoundException {
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn("vehicle/" + notId).when(mockRequest).getPathInfo();

        servlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance, never()).findById(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "123Name*", "CITY"})
    void doPostTest(String name) throws IOException {
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        String vehicleJson= "{\"name\":\"" + name + "\"}";
        Mockito.doReturn(vehicleJson, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPost(mockRequest, mockResponse);

        ArgumentCaptor<VehicleIncomingDto> argumentCaptor = ArgumentCaptor.forClass(VehicleIncomingDto.class);
        Mockito.verify(mokServiceInstance).save(argumentCaptor.capture());
        VehicleIncomingDto vehicleDto = argumentCaptor.getValue();

        Assertions.assertEquals(name, vehicleDto.getName());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_CREATED);

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "CITY", "1234"})
    void doPostBadRequestTest(String json) throws IOException {
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(json, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doPostIllegalArgTest() throws IOException {
        doThrow(IllegalArgumentException.class).when(mokServiceInstance).save(Mockito.any());
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        String vehicleJson= "{\"id\":\"1\"}";
        Mockito.doReturn(vehicleJson, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }


    @ParameterizedTest
    @ValueSource(longs = {1, 15, 10000})
    void doUpdateTest(long id) throws IOException, NotFoundException {
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        String vehicleJson= "{\"id\":\"" + id + "\"}";
        Mockito.doReturn(vehicleJson, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPut(mockRequest, mockResponse);

        ArgumentCaptor<VehicleUpdateDto> argumentCaptor = ArgumentCaptor.forClass(VehicleUpdateDto.class);
        Mockito.verify(mokServiceInstance).update(argumentCaptor.capture());
        VehicleUpdateDto vehicleDto = argumentCaptor.getValue();

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
        Assertions.assertEquals(id, vehicleDto.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "CITY", "1234"})
    void doUpdateBadRequestTest(String json) throws IOException {
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        Mockito.doReturn(json, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPut(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doUpdateIllegalArgTest() throws IOException, NotFoundException {
        doThrow(IllegalArgumentException.class).when(mokServiceInstance).update(Mockito.any());
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        String vehicleJson= "{\"name\":\"vehicle\"}";
        Mockito.doReturn(vehicleJson, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPut(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }

    @Test
    void doUpdateNotFoundTest() throws IOException, NotFoundException {
        doThrow(NotFoundException.class).when(mokServiceInstance).update(Mockito.any());
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn(mockBufferedReader).when(mockRequest).getReader();
        String vehicleJson= "{\"name\":\"vehicle\"}";
        Mockito.doReturn(vehicleJson, (Object) null).when(mockBufferedReader).readLine();

        servlet.doPut(mockRequest, mockResponse);

        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 100})
    void doDeleteTest(int id) {
        Mockito.doReturn(true).when(mokServiceInstance).delete(Mockito.anyLong());
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn("vehicle/" + id).when(mockRequest).getPathInfo();

        servlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).delete(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -2, -3, -10, 10000})
    void doDeleteNotFoundTest(int id) {
        Mockito.doReturn(false).when(mokServiceInstance).delete(Mockito.anyLong());
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn("vehicle/" + id).when(mockRequest).getPathInfo();

        servlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).delete(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "delete", "delete/1"})
    void doDeleteBadRequestTest(String notId) {
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn("vehicle/" + notId).when(mockRequest).getPathInfo();

        servlet.doDelete(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance, never()).delete(Mockito.anyLong());
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


    @Test
    void doGetAll() {
        Mockito.doReturn(List.of()).when(mokServiceInstance).findAll();
        mokStaticService.when(VehicleServiceImpl::getInstance).thenReturn(mokServiceInstance);
        VehicleServlet servlet = new VehicleServlet();

        Mockito.doReturn("vehicle/all").when(mockRequest).getPathInfo();

        servlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mokServiceInstance).findAll();
    }
}