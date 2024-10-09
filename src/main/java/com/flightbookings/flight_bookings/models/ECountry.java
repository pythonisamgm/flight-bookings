package com.flightbookings.flight_bookings.models;

/**
 * Enumeration representing various countries.
 */
public enum ECountry {
    /** Spain. */
    ESPAÑA("España"),
    /** United States of America. */
    EE_UU("Estados Unidos"),
    /** Canada. */
    CANADA("Canadá"),
    /** Brazil. */
    BRASIL("Brasil"),
    /** Mexico. */
    MEXICO("México"),
    /** Colombia. */
    COLOMBIA("Colombia"),
    /** United Kingdom. */
    REINO_UNIDO("Reino Unido"),
    /** Netherlands. */
    PAISES_BAJOS("Países Bajos"),
    /** France. */
    FRANCIA("Francia"),
    /** Germany. */
    ALEMANIA("Alemania"),
    /** Switzerland. */
    SUIZA("Suiza"),
    /** Denmark. */
    DINAMARCA("Dinamarca"),
    /** Austria. */
    AUSTRIA("Austria"),
    /** Portugal. */
    PORTUGAL("Portugal"),
    /** Belgium. */
    BELGICA("Bélgica");

    private final String displayName;

    /**
     * Constructor for ECountry enumeration.
     *
     * @param displayName The display name of the country.
     */
    ECountry(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retrieves the display name of the country.
     *
     * @return The display name of the country.
     */
    public String getDisplayName() {
        return displayName;
    }
}
