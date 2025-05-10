package org.example.attendanceai.util;

import jakarta.persistence.AttributeConverter;

public abstract class GenericEnumConverter<E extends Enum<E>> implements AttributeConverter<E, String> {

    private final Class<E> enumClass;

    protected GenericEnumConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        for (E constant : enumClass.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(dbData)) {
                return constant;
            }
        }

        throw new IllegalArgumentException("Unknown enum value: " + dbData +
                ". Available values are: " + java.util.Arrays.toString(enumClass.getEnumConstants()));
    }
}


