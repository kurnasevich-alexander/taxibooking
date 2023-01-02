package eu.senla.taxibooking.api.service.consumer;

import eu.senla.taxibooking.model.dto.BookingDto;

public interface BookingEditConsumer {
    void updateBooking(BookingDto booking);
}
