package eu.senla.taxibooking.dto.mapper;

import eu.senla.taxibooking.dto.BookingDTO;
import eu.senla.taxibooking.entity.Booking;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookingDTOMapper {

    BookingDTO bookingToDTO(Booking booking);

    Booking bookingDTOToEntity(BookingDTO dto);

    default Page<BookingDTO> bookingPageToDTO(Page<Booking> booking) {
        return new PageImpl<>(booking
                .stream()
                .map(this::bookingToDTO)
                .collect(Collectors.toList()), booking.getPageable(), booking.getTotalElements());
    }

    default Page<Booking> bookingDTOPageToEntity(Page<BookingDTO> booking) {
        return new PageImpl<>(booking
                .stream()
                .map(this::bookingDTOToEntity)
                .collect(Collectors.toList()), booking.getPageable(), booking.getTotalElements());
    }
}