package eu.senla.taxibooking.controller;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.entity.Booking;
import eu.senla.taxibooking.entity.Waypoint;
import eu.senla.taxibooking.repository.BookingRepository;
import eu.senla.taxibooking.util.OffsetDateTimeAlmostEqualMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Container
    public static PostgreSQLContainer postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
                .withUsername("root")
                .withPassword("root");
        postgreSQLContainer.start();
    }

    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private MockMvc mvc;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @BeforeEach
    public void deleteAllInDB() {
        bookingRepository.deleteAll();
    }

    @Test
    public void testGetBookings() throws Exception {
        Booking booking = addConstantBookingToDB();

        mvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(booking.getId()))
                .andExpect(jsonPath("$.content[0].passengerName").value(booking.getPassengerName()))
                .andExpect(jsonPath("$.content[0].passengerContactNumber")
                        .value(booking.getPassengerContactNumber()))
                .andExpect(jsonPath("$.content[0].asap").value(booking.getAsap()))
                .andExpect(jsonPath("$.content[0].waitingTime").value(booking.getWaitingTime()))
                .andExpect(jsonPath("$.content[0].numberOfPassengers").value(booking.getNumberOfPassengers()))
                .andExpect(jsonPath("$.content[0].price").value(booking.getPrice()))
                .andExpect(jsonPath("$.content[0].rating").value(booking.getRating()))
                .andExpect(jsonPath("$.content[0].lastModifiedOn",
                        new OffsetDateTimeAlmostEqualMatcher(booking.getLastModifiedOn(), MILLIS, 1)))
                .andExpect(jsonPath("$.content[0].pickupTime",
                        new OffsetDateTimeAlmostEqualMatcher(booking.getPickupTime(), MILLIS, 1)))
                .andExpect(jsonPath("$.content[0].createdOn",
                        new OffsetDateTimeAlmostEqualMatcher(booking.getCreatedOn(), MILLIS, 1)))
                .andExpect(jsonPath("$.content[0].tripWaypoints", notNullValue()))
                .andExpect(jsonPath("$.content[0].tripWaypoints[0].id")
                        .value(booking.getTripWaypoints().get(0).getId()))
                .andExpect(jsonPath("$.content[0].tripWaypoints[0].locality")
                        .value(booking.getTripWaypoints().get(0).getLocality()))
                .andExpect(jsonPath("$.content[0].tripWaypoints[0].latitude")
                        .value(booking.getTripWaypoints().get(0).getLatitude()))
                .andExpect(jsonPath("$.content[0].tripWaypoints[0].longitude")
                        .value(booking.getTripWaypoints().get(0).getLongitude()));
    }

    @Test
    public void testGetBookingById() throws Exception {
        Booking booking = addConstantBookingToDB();

        mvc.perform(get("/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(booking.getId()))
                .andExpect(jsonPath("$.passengerName").value(booking.getPassengerName()))
                .andExpect(jsonPath("$.passengerContactNumber").value(booking.getPassengerContactNumber()))
                .andExpect(jsonPath("$.asap").value(booking.getAsap()))
                .andExpect(jsonPath("$.waitingTime").value(booking.getWaitingTime()))
                .andExpect(jsonPath("$.numberOfPassengers").value(booking.getNumberOfPassengers()))
                .andExpect(jsonPath("$.price").value(booking.getPrice()))
                .andExpect(jsonPath("$.rating").value(booking.getRating()))
                .andExpect(jsonPath("$.lastModifiedOn",
                        new OffsetDateTimeAlmostEqualMatcher(booking.getLastModifiedOn(), MILLIS, 1)))
                .andExpect(jsonPath("$.pickupTime",
                        new OffsetDateTimeAlmostEqualMatcher(booking.getPickupTime(), MILLIS, 1)))
                .andExpect(jsonPath("$.createdOn",
                        new OffsetDateTimeAlmostEqualMatcher(booking.getCreatedOn(), MILLIS, 1)))
                .andExpect(jsonPath("$.tripWaypoints", notNullValue()))
                .andExpect(jsonPath("$.tripWaypoints[0].id").value(booking.getTripWaypoints().get(0).getId()))
                .andExpect(jsonPath("$.tripWaypoints[0].locality")
                        .value(booking.getTripWaypoints().get(0).getLocality()))
                .andExpect(jsonPath("$.tripWaypoints[0].latitude")
                        .value(booking.getTripWaypoints().get(0).getLatitude()))
                .andExpect(jsonPath("$.tripWaypoints[0].longitude")
                        .value(booking.getTripWaypoints().get(0).getLongitude()));
    }

    @Test
    public void testGetBookingById_NotFound() throws Exception {
        mvc.perform(get("/bookings/112"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Booking not found id: 112"))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void testPostBooking() throws Exception {
        String json = """
                {
                  "passengerName": "name",
                  "passengerContactNumber": 999,
                  "pickupTime": "2000-10-17T09:03:03+02:00",
                  "asap": true,
                  "waitingTime": 69,
                  "numberOfPassengers": 4,
                  "price": 9.9,
                  "rating": 4.5,
                  "tripWaypoints": [
                    {
                      "locality": "loc",
                      "latitude": 9.9,
                      "longitude": 9.9
                    }
                  ]
                }""";
        Booking booking = addConstantBookingToDB();

        mvc.perform(post("/bookings")
                        .content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.passengerName").value(booking.getPassengerName()))
                .andExpect(jsonPath("$.passengerContactNumber").value(booking.getPassengerContactNumber()))
                .andExpect(jsonPath("$.asap").value(booking.getAsap()))
                .andExpect(jsonPath("$.waitingTime").value(booking.getWaitingTime()))
                .andExpect(jsonPath("$.numberOfPassengers").value(booking.getNumberOfPassengers()))
                .andExpect(jsonPath("$.price").value(booking.getPrice()))
                .andExpect(jsonPath("$.rating").value(booking.getRating()))
                .andExpect(jsonPath("$.lastModifiedOn", notNullValue()))
                .andExpect(jsonPath("$.pickupTime",
                        new OffsetDateTimeAlmostEqualMatcher(booking.getPickupTime(), MILLIS, 1)))
                .andExpect(jsonPath("$.createdOn", notNullValue()))
                .andExpect(jsonPath("$.tripWaypoints", notNullValue()))
                .andExpect(jsonPath("$.tripWaypoints[0].id", notNullValue()))
                .andExpect(jsonPath("$.tripWaypoints[0].locality")
                        .value(booking.getTripWaypoints().get(0).getLocality()))
                .andExpect(jsonPath("$.tripWaypoints[0].latitude")
                        .value(booking.getTripWaypoints().get(0).getLatitude()))
                .andExpect(jsonPath("$.tripWaypoints[0].longitude")
                        .value(booking.getTripWaypoints().get(0).getLongitude()));
    }

    @Test
    public void testPostBooking_NotValid() throws Exception {
        String json = """
                {
                          "passengerName": "name",
                          "passengerContactNumber": 999,
                          "pickupTime": "2000-10-17T09:03:03+02:00",
                          "waitingTime": 69,
                          "numberOfPassengers": 4,
                          "price": 9.9,
                          "rating": 4.5,
                          "tripWaypoints": [
                            {
                              "locality": "loc",
                              "latitude": 9.9,
                              "longitude": 9.9
                            }
                          ]
                        }""";

        mvc.perform(post("/bookings")
                        .content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("asap:must not be null"))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void testPutBooking() throws Exception {
        String json = "{\"waitingTime\": 77}";
        Booking booking = addConstantBookingToDB();

        mvc.perform(put("/bookings/{id}", booking.getId())
                        .content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(booking.getId()))
                .andExpect(jsonPath("$.passengerName").value(booking.getPassengerName()))
                .andExpect(jsonPath("$.passengerContactNumber").value(booking.getPassengerContactNumber()))
                .andExpect(jsonPath("$.asap").value(booking.getAsap()))
                .andExpect(jsonPath("$.waitingTime").value(77))
                .andExpect(jsonPath("$.numberOfPassengers").value(booking.getNumberOfPassengers()))
                .andExpect(jsonPath("$.price").value(booking.getPrice()))
                .andExpect(jsonPath("$.rating").value(booking.getRating()))
                .andExpect(jsonPath("$.lastModifiedOn", notNullValue()))
                .andExpect(jsonPath("$.pickupTime",
                        new OffsetDateTimeAlmostEqualMatcher(booking.getPickupTime(), MILLIS, 1)))
                .andExpect(jsonPath("$.createdOn",
                        new OffsetDateTimeAlmostEqualMatcher(booking.getCreatedOn(), MILLIS, 1)))
                .andExpect(jsonPath("$.tripWaypoints", notNullValue()))
                .andExpect(jsonPath("$.tripWaypoints[0].id").value(booking.getTripWaypoints().get(0).getId()))
                .andExpect(jsonPath("$.tripWaypoints[0].locality")
                        .value(booking.getTripWaypoints().get(0).getLocality()))
                .andExpect(jsonPath("$.tripWaypoints[0].latitude")
                        .value(booking.getTripWaypoints().get(0).getLatitude()))
                .andExpect(jsonPath("$.tripWaypoints[0].longitude")
                        .value(booking.getTripWaypoints().get(0).getLongitude()));
    }

    @Test
    public void testPutBooking_NotFound() throws Exception {
        String json = "{\"waitingTime\": 77}";

        mvc.perform(put("/bookings/112")
                        .content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Booking not found id: 112"))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void testPutBooking_NotValid() throws Exception {
        String json = "{\"waitingTime\": -17}";

        mvc.perform(put("/bookings/1")
                        .content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("waitingTime:must be greater than 0"))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        Booking booking = addConstantBookingToDB();

        mvc.perform(delete("/bookings/{id}", booking.getId())).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteBooking_NotFound() throws Exception {
        mvc.perform(delete("/bookings/112"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Booking not found id: 112"))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    private Booking addConstantBookingToDB() {
        List<Waypoint> givenWaypoints = new ArrayList<>();
        givenWaypoints.add(new Waypoint(1L, "loc", 9.9, 9.9, null));
        Booking given = Booking.builder()
                .passengerName("name")
                .passengerContactNumber(999L)
                .pickupTime(OffsetDateTime.now())
                .asap(true)
                .waitingTime(69)
                .numberOfPassengers(4)
                .price(new BigDecimal("9.9"))
                .rating(4.5f)
                .tripWaypoints(givenWaypoints)
                .build();
        return bookingService.addBooking(given);
    }

}
