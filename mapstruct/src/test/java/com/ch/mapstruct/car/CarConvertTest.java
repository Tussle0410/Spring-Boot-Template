package com.ch.mapstruct.car;


import static  org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
public class CarConvertTest {

  private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

  @Test
  @DisplayName("CarDto to Car 변환 테스트")
  public void carToCarDtoConvertTest(){

    //given
    Car car = Car.createDefaultCar();

    //when
    CarDto carDto = carMapper.carToCarDto(car);

    //then
    assertThat(carDto.getBrand()).isEqualTo(car.brand());
    assertThat(carDto.getSeatCount()).isEqualTo(car.numberOfSeats());
    assertThat(carDto.getCarType()).isEqualTo(car.type());
    assertThat(carDto.getCarModel()).isEqualTo(car.model());
  }
}
