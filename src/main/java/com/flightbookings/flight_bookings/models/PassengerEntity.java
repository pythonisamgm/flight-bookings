package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
/**
 * Represents a passenger entity in the system.
 * Contains details about the passenger's identity, contact information, and associated bookings.
 */
@Entity
@Table(name = "passenger")
@Schema(description = "All details about the Passenger entity.")
public class PassengerEntity {

    /**
     * The unique identifier of the passenger generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The database generated passenger ID")
    private Long passengerId;

    /**
     * The name of the passenger.
     */
    @Column
    @Schema(description = "The name of the passenger")
    private String passengerName;

    /**
     * The identity document number of the passenger.
     */
    @Column
    @Schema(description = "The identity document number of the passenger")
    private String identityDoc;

    /**
     * The telephone number of the passenger.
     */
    @Column
    @Schema(description = "The telephone number of the passenger")
    private Long telephone;

    /**
     * The nationality of the passenger.
     */
    @Column
    @Schema(description = "The nationality of the passenger")
    private String nationality;

    /**
     * The booking associated with this passenger.
     */
    @OneToOne(mappedBy = "passenger", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference(value = "booking-passenger")
    @Schema(description = "The booking associated with this passenger")
    private BookingEntity booking;

    /**
     * Default constructor for Passenger.
     */
    public PassengerEntity() {
    }

    /**
     * Constructs a new Passenger with the specified details.
     *
     * @param passengerId    The passenger ID.
     * @param passengerName  The name of the passenger.
     * @param identityDoc    The identity document number.
     * @param telephone      The telephone number.
     * @param nationality    The nationality of the passenger.
     * @param booking        The associated booking.
     */
    public PassengerEntity(Long passengerId, String passengerName, String identityDoc, Long telephone, String nationality, BookingEntity booking) {
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.identityDoc = identityDoc;
        this.telephone = telephone;
        this.nationality = nationality;
        this.booking = booking;
    }

    /**
     * Gets the passenger ID.
     *
     * @return The passenger ID.
     */
    public Long getPassengerId() {
        return passengerId;
    }

    /**
     * Sets the passenger ID.
     *
     * @param passengerId The passenger ID.
     */
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    /**
     * Gets the name of the passenger.
     *
     * @return The name of the passenger.
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * Sets the name of the passenger.
     *
     * @param passengerName The name of the passenger.
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /**
     * Gets the identity document number of the passenger.
     *
     * @return The identity document number.
     */
    public String getIdentityDoc() {
        return identityDoc;
    }

    /**
     * Sets the identity document number.
     *
     * @param identityDoc The identity document number.
     */
    public void setIdentityDoc(String identityDoc) {
        this.identityDoc = identityDoc;
    }

    /**
     * Gets the telephone number of the passenger.
     *
     * @return The telephone number.
     */
    public Long getTelephone() {
        return telephone;
    }

    /**
     * Sets the telephone number of the passenger.
     *
     * @param telephone The telephone number.
     */
    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    /**
     * Gets the nationality of the passenger.
     *
     * @return The nationality.
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the nationality of the passenger.
     *
     * @param nationality The nationality.
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Gets the booking associated with this passenger.
     *
     * @return The booking.
     */
    public BookingEntity getBooking() {
        return booking;
    }

    /**
     * Sets the booking associated with this passenger.
     *
     * @param booking The booking.
     */
    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }
}