package msk.extemulator.dadata;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import msk.extemulator.domain.Response;
import msk.extemulator.service.ResponseService;
import msk.extemulator.utils.FormatUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("suggestions/api/4_1/rs")
@Slf4j
@RequiredArgsConstructor
@Tag(name="Сервис dadata")
public class DadataService {
    private final ResponseService responseService;
    private static final String SERVICE_NAME = "dadata";

    @PostMapping(value = "findById/party", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Поиск организаций по ИНН")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "json с ИНН",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {@ExampleObject(value = "{\"query\": \"231515152622\"}")})})
    @ApiResponse(responseCode = "200", description = "Ответ с данными организаций")
    @ApiResponse(responseCode = "404", description = "Ответ если не удалось найти ответ эмулятора")
    public ResponseEntity<String> findByIdParty(
            @RequestBody String message) {
        return find(message, "party");
    }

    @PostMapping(value = "findById/bank", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Поиск банка по БИК")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "json с БИК",
            content = {@Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {@ExampleObject(value = "{\"query\": \"231515152622\"}")})})
    @ApiResponse(responseCode = "200", description = "Ответ с данными организаций")
    @ApiResponse(responseCode = "404", description = "Ответ если не удалось найти ответ эмулятора")
    public ResponseEntity<String> findByIdBank(
            @RequestBody String message) {
        return find(message, "bank");
    }

    @SneakyThrows
    private ResponseEntity<String> find(String message, String method) {
        log.info("dadata {} started: message={}", method, message);
        Map<String, String> params = FormatUtils.JSON_MAPPER.readValue(message, Map.class);
        String paramKey = "[" + params.getOrDefault("query", "") + "]";

        Response response = responseService.getResponse(SERVICE_NAME, method, paramKey);
        log.info("dadata {} finished", method);
        return response == null ?
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Response for key " + paramKey + " not found") :
                ResponseEntity
                        .status(response.getSettings().getResponseCode())
                        .body(response.getData());
    }
}
