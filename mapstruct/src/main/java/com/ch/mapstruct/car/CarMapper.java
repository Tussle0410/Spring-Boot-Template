package com.ch.mapstruct.car;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

  @Mapping(target = "carType", source = "type")
  @Mapping(target = "carModel", source = "model")
  @Mapping(target = "seatCount", source = "numberOfSeats")
  CarDto carToCarDto(Car car);

}
