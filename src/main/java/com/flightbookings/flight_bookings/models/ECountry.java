package com.flightbookings.flight_bookings.models;
/**
 * Enumeration representing various countries.
 */
public enum ECountry {
    ESPAÑA("España"),
    EE_UU("Estados Unidos"),
    CANADA("Canadá"),
    BRASIL("Brasil"),
    MEXICO("México"),
    COLOMBIA("Colombia"),
    REINO_UNIDO("Reino Unido"),
    PAISES_BAJOS("Países Bajos"),
    FRANCIA("Francia"),
    ALEMANIA("Alemania"),
    SUIZA("Suiza"),
    DINAMARCA("Dinamarca"),
    AUSTRIA("Austria"),
    PORTUGAL("Portugal"),
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
