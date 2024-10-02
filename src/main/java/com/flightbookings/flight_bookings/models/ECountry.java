package com.flightbookings.flight_bookings.models;

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

    ECountry(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
