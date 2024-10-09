package com.flightbookings.flight_bookings.models;

/**
 * Enumeration representing various airports with details like airport code, name, city, and country.
 */
public enum EAirport {
    MAD("MAD", "Madrid-Barajas Adolfo Suárez", "Madrid", ECountry.ESPAÑA),
    BCN("BCN", "Barcelona-El Prat", "Barcelona", ECountry.ESPAÑA),
    VLC("VLC", "Valencia", "Valencia", ECountry.ESPAÑA),
    AGP("AGP", "Malaga-Costa del Sol", "Málaga", ECountry.ESPAÑA),
    ALC("ALC", "Alicante-Elche", "Alicante", ECountry.ESPAÑA),
    SVQ("SVQ", "Sevilla", "Sevilla", ECountry.ESPAÑA),
    BIO("BIO", "Bilbao", "Bilbao", ECountry.ESPAÑA),
    PMI("PMI", "Palma de Mallorca", "Palma de Mallorca", ECountry.ESPAÑA),
    LPA("LPA", "Gran Canaria", "Las Palmas", ECountry.ESPAÑA),
    TFS("TFS", "Tenerife Sur", "Tenerife", ECountry.ESPAÑA),
    LAX("LAX", "Los Angeles International Airport", "Los Ángeles", ECountry.EE_UU),
    JFK("JFK", "John F. Kennedy International Airport", "Nueva York", ECountry.EE_UU),
    ORD("ORD", "O'Hare International Airport", "Chicago", ECountry.EE_UU),
    ATL("ATL", "Hartsfield-Jackson Atlanta International Airport", "Atlanta", ECountry.EE_UU),
    DFW("DFW", "Dallas/Fort Worth International Airport", "Dallas", ECountry.EE_UU),
    YYZ("YYZ", "Toronto Pearson International Airport", "Toronto", ECountry.CANADA),
    GRU("GRU", "São Paulo/Guarulhos International Airport", "São Paulo", ECountry.BRASIL),
    MEX("MEX", "Mexico City International Airport", "Ciudad de México", ECountry.MEXICO),
    BOG("BOG", "Bogotá El Dorado International Airport", "Bogotá", ECountry.COLOMBIA),
    CUN("CUN", "Cancún International Airport", "Cancún", ECountry.MEXICO),
    LHR("LHR", "London Heathrow Airport", "Londres", ECountry.REINO_UNIDO),
    AMS("AMS", "Amsterdam Airport Schiphol", "Ámsterdam", ECountry.PAISES_BAJOS),
    CDG("CDG", "Charles de Gaulle Airport", "París", ECountry.FRANCIA),
    FRA("FRA", "Frankfurt Airport", "Fráncfort", ECountry.ALEMANIA),
    MUC("MUC", "Munich Airport", "Múnich", ECountry.ALEMANIA),
    ZRH("ZRH", "Zurich Airport", "Zúrich", ECountry.SUIZA),
    CPH("CPH", "Copenhagen Airport", "Copenhague", ECountry.DINAMARCA),
    VIE("VIE", "Vienna International Airport", "Viena", ECountry.AUSTRIA),
    LIS("LIS", "Lisbon Portela Airport", "Lisboa", ECountry.PORTUGAL),
    BRU("BRU", "Brussels Airport", "Bruselas", ECountry.BELGICA);

    private final String airportCode;
    private final String airportName;
    private final String airportCity;
    private final ECountry airportCountry;

    /**
     * Constructor for EAirport enumeration.
     *
     * @param airportCode   The unique code for the airport.
     * @param airportName   The name of the airport.
     * @param airportCity   The city where the airport is located.
     * @param airportCountry The country where the airport is located.
     */
    EAirport(String airportCode, String airportName, String airportCity, ECountry airportCountry) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
    }

    /**
     * Gets the airport code.
     *
     * @return The airport code.
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * Gets the airport name.
     *
     * @return The airport name.
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     * Gets the airport city.
     *
     * @return The city where the airport is located.
     */
    public String getAirportCity() {
        return airportCity;
    }

    /**
     * Gets the airport country.
     *
     * @return The country where the airport is located.
     */
    public ECountry getAirportCountry() {
        return airportCountry;
    }
}
