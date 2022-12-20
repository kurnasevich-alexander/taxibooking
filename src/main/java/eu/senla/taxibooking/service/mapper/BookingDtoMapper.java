package eu.senla.taxibooking.service.mapper;

import eu.senla.taxibooking.dto.BookingDto;
import eu.senla.taxibooking.entity.Booking;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookingDtoMapper {

    BookingDto bookingToBookingDto(Booking booking);

    Booking bookingDtoToBooking(BookingDto dto);

    default Page<BookingDto> pageBookingToPageBookingDto(Page<Booking> booking) {
        return new PageImpl<>(booking
                .stream()
                .map(this::bookingToBookingDto)
                .collect(Collectors.toList()), booking.getPageable(), booking.getTotalElements());
    }

}
