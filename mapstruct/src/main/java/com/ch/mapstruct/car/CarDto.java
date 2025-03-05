package com.ch.mapstruct.car;

import lombok.Getter;

@Getter
public class CarDto {
  private String brand;
  private int seatCount;
  private String carType;
  private String carModel;
  private int genderCode;

  public CarDto(String brand, int seatCount, String carType, String carModel, int genderCode) {
    this.brand = brand;
    this.seatCount = seatCount;
    this.carType = carType;
    this.carModel = carModel;
    this.genderCode = genderCode;
  }

  @Override
  public String toString() {
    return String.format("CarDto = [brand='%s', seatCount='%s', carType='%s', carModel='%s', genderCode='%d']",
        brand, seatCount, carType, carModel, genderCode);
  }

}
