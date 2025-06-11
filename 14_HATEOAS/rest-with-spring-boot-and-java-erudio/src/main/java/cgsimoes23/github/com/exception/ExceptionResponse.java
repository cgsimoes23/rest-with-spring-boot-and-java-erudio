package cgsimoes23.github.com.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {

}
