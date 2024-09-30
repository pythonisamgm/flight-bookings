package com.flightbookings.flight_bookings.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime timeStamp = LocalDateTime.now();

    @Column
    private double temperature;

    @Column
    private String weatherCondition;

    @Column
    private double windSpeed;

    @Column
    private String city;

    public Weather(Long id, LocalDateTime timeStamp, double temperature, String weatherCondition, double windSpeed, String city) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.windSpeed = windSpeed;
        this.city = city;
    }

    public Weather() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
