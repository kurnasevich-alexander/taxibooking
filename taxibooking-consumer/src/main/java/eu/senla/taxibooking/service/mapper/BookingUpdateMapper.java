package eu.senla.taxibooking.service.mapper;

import eu.senla.taxibooking.entity.Booking;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookingUpdateMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "lastModifiedOn", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateBookingFromBooking(Booking booking, @MappingTarget Booking updated);
}
