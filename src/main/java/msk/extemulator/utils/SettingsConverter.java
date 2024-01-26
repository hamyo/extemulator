package msk.extemulator.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import msk.extemulator.domain.Settings;

import java.io.IOException;

@Converter(autoApply = true)
public class SettingsConverter implements AttributeConverter<Settings, String> {
    @Override
    public String convertToDatabaseColumn(Settings object) {
        if (object == null) {
            return null;
        }
        try {
            return FormatUtils.JSON_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            return null;
            // or throw an error
        }
    }

    @Override
    public Settings convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return Settings.ofDefault();
        }
        try {
            return FormatUtils.JSON_MAPPER.readValue(dbData, Settings.class);
        } catch (IOException ex) {
            // logger.error("Unexpected IOEx decoding json from database: " + dbData);
            return null;
        }
    }
}
