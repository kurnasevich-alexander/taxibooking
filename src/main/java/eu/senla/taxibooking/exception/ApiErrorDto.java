package eu.senla.taxibooking.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDto {
    @Schema(description = "Http status", example = "HTTP STATUS")
    private HttpStatus status;
    @Schema(description = "Error timestamp", example = "2000-12-15T13:16:10.2525958")
    private OffsetDateTime timestamp;
    @Schema(description = "Error description message", example = "Error description example")
    private String message;
}
