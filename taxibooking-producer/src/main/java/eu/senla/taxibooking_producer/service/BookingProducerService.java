package eu.senla.taxibooking_producer.service;

import eu.senla.taxibooking_model.dto.BookingDto;

public interface BookingProducerService {

    void addBooking(BookingDto bookingDto);

    void updateBooking(BookingDto bookingDto);

    void deleteBooking(Long id);

}
