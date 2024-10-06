package com.flightbookings.flight_bookings;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // Desactiva la configuración de docker compose de la base de datos en los tests
class FlightBookingsApplicationTests {

	@Test
	void contextLoads() {
	}

}
