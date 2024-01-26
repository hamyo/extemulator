package msk.extemulator;

import lombok.extern.slf4j.Slf4j;
import msk.extemulator.utils.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.text.MessageFormat;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppError> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error(MessageFormat.format(
                "Internal error ''{0}'', parameters='{'{1}'}'. ",
                request.getDescription(false),
                getParamsString(request)), ex);

        return ex instanceof NoResourceFoundException ?
                new ResponseEntity<>(
                        new AppError(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                        HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(
                new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Внутренняя ошибка"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getParamsString(WebRequest request) {
        try {
            Map<String, String[]> params = request.getParameterMap();
            return params.entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .map(e -> MessageFormat.format("{0}=[{1}]", e.getKey(), String.join(",", e.getValue())))
                    .collect(Collectors.joining(","));
        } catch (Throwable t) {
            log.error("Error in getParamsString.", t);
        }
        return "";
    }
}