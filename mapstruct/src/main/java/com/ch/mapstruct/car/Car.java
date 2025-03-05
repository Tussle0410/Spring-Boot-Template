package com.ch.mapstruct.car;

import lombok.Builder;

@Builder
public record Car(
    String brand,
    int numberOfSeats,
    String color,
    String type,
    String model
) {

  public static Car createDefaultCar(){
    return Car.builder()
        .brand("Hyundai")
        .numberOfSeats(5)
        .color("Black")
        .type("SUV")
        .model("Tucson")
        .build();
  }

}
