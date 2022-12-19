package eu.senla.taxibooking.service.mapper;

import eu.senla.taxibooking.dto.BookingDTO;
import eu.senla.taxibooking.entity.Booking;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookingDTOMapper {

    BookingDTO bookingToBookingDTO(Booking booking);

    Booking bookingDTOToBooking(BookingDTO dto);

    default Page<BookingDTO> pageBookingToPageBookingDTO(Page<Booking> booking) {
        return new PageImpl<>(booking
                .stream()
                .map(this::bookingToBookingDTO)
                .collect(Collectors.toList()), booking.getPageable(), booking.getTotalElements());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateBookingFromDTO(BookingDTO dto, @MappingTarget Booking entity);

}
