package org.example.service.impl;

import org.example.exception.NotFoundException;
import org.example.model.Reservation;
import org.example.repository.ReservationRepository;
import org.example.repository.impl.ReservationRepositoryImpl;
import org.example.service.mapper.ReservationMapper;
import org.example.service.mapper.impl.ReservationMapperImpl;
import org.example.service.ReservationService;
import org.example.servlet.dto.reservation.ReservationIncomingDto;
import org.example.servlet.dto.reservation.ReservationOutGoingDto;
import org.example.servlet.dto.reservation.ReservationUpdateDto;

import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository repository = ReservationRepositoryImpl.getInstance();
    private final ReservationMapper mapper = ReservationMapperImpl.getInstance();
    private static ReservationService instance;

    private ReservationServiceImpl() {
    }

    public static synchronized ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationServiceImpl();
        }
        return instance;
    }

    @Override
    public ReservationOutGoingDto save(ReservationIncomingDto incomingDto) {
        Reservation reservation = mapper.mapIncomingDto(incomingDto);
        reservation = repository.save(reservation);
        return mapper.mapModel(reservation);
    }

    @Override
    public void update(ReservationUpdateDto updateDto) throws NotFoundException {
        Reservation reservation = mapper.mapUpdateDto(updateDto);
        repository.update(reservation);
    }

    @Override
    public ReservationOutGoingDto findById(Long id) throws NotFoundException {
        Reservation reservation = repository.findById(id);
        return mapper.mapModel(reservation);
    }

    @Override
    public List<ReservationOutGoingDto> findAll() {
        List<Reservation> reservationList = repository.findAll();
        return mapper.mapModelList(reservationList);
    }

    @Override
    public boolean delete(Long id) {
        return repository.deleteById(id);
    }
}
