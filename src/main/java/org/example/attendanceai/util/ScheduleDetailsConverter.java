package org.example.attendanceai.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.attendanceai.domain.model.ScheduleDetails;

@Converter
public class ScheduleDetailsConverter implements AttributeConverter<ScheduleDetails, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // Register JavaTimeModule to handle Java 8 Date/Time types like LocalTime
        mapper.registerModule(new JavaTimeModule());
        // Optionally, you might want to disable this feature if you want to allow serialization of classes with no properties
        // mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public String convertToDatabaseColumn(ScheduleDetails attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid schedule details: Failed to convert to JSON string", e);
        }
    }

    @Override
    public ScheduleDetails convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, ScheduleDetails.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse schedule details from JSON string", e);
        }
    }
}