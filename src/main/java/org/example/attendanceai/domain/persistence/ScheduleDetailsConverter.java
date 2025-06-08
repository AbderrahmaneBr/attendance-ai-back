package org.example.attendanceai.domain.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.attendanceai.domain.model.ScheduleDetails;

@Converter
public class ScheduleDetailsConverter implements AttributeConverter<ScheduleDetails, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ScheduleDetails attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid schedule details", e);
        }
    }

    @Override
    public ScheduleDetails convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, ScheduleDetails.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse schedule details", e);
        }
    }
}
