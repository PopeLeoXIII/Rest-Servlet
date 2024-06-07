package org.example.service.mapper;

import java.util.List;

public interface TypedMapper<Model, IncomingDto, OutGoingDto, UpdateDto> {
    Model mapIncomingDto(IncomingDto incomingDto);

    Model mapUpdateDto(UpdateDto updateDto);

    OutGoingDto mapModel(Model model);

    List<OutGoingDto> mapModelList(List<Model> modelList);
}
