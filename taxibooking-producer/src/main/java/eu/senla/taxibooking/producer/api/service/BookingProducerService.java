package eu.senla.taxibooking.producer.api.service;

import eu.senla.taxibooking.model.dto.BookingDto;

public interface BookingProducerService {

    void addBooking(BookingDto bookingDto);

    void updateBooking(BookingDto bookingDto);

    void deleteBooking(Long id);

}
