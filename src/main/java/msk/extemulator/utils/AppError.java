package msk.extemulator.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AppError {
    private final int statusCode;
    private final String message;
}
