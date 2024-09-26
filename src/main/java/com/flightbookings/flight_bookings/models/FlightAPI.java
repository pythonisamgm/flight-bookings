package com.flightbookings.flight_bookings.models;


import jakarta.persistence.*;

@Entity
@Table(name="flightapi")
public class FlightAPI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityFrom;
    private String cityTo;
    private String localDeparture;
    private String localArrival;
    private double price;
    private String flyFrom;
    private String flyTo;

    public FlightAPI() {
    }

    public FlightAPI(Long id, String cityFrom, String cityTo, String localDeparture, String localArrival, double price, String flyFrom, String flyTo) {
        this.id = id;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.localDeparture = localDeparture;
        this.localArrival = localArrival;
        this.price = price;
        this.flyFrom = flyFrom;
        this.flyTo = flyTo;
    }
}
