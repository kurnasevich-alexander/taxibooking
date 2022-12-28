package eu.senla.taxibooking.api.service.consumer;

import eu.senla.taxibooking_model.dto.BookingDto;

public interface BookingAddConsumer {
    void addBooking(BookingDto booking);
}
