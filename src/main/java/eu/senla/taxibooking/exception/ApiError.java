package eu.senla.taxibooking.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Data
public class ApiError {

    @Schema(description = "Http status", example = "BAD_REQUEST")
    private HttpStatus status;
    @Schema(description = "Error timestamp", example = "2000-12-15T13:16:10.2525958")
    private LocalDateTime timestamp;
    @Schema(description = "Error description message", example = "Bad request")
    private String message;
}
