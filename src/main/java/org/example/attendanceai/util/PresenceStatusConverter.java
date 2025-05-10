package org.example.attendanceai.util;

import jakarta.persistence.Converter;
import org.example.attendanceai.domain.enums.PresenceStatus;

@Converter(autoApply = true)
public class PresenceStatusConverter extends GenericEnumConverter<PresenceStatus> {
    public PresenceStatusConverter() {
        super(PresenceStatus.class);
    }
}