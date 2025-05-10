package org.example.attendanceai.util;

import jakarta.persistence.Converter;
import org.example.attendanceai.domain.enums.StudyYear;

@Converter(autoApply = true)
public class StudyYearStatusConverter extends GenericEnumConverter<StudyYear> {
    public StudyYearStatusConverter() {
        super(StudyYear.class);
    }
}