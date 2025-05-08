package org.example.attendanceai.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.attendanceai.domain.enums.UserRoles;

@Converter(autoApply = true)
public class UserRolesConverter implements AttributeConverter<UserRoles, String> {

    @Override
    public String convertToDatabaseColumn(UserRoles attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public UserRoles convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        // Try case-insensitive matching
        for (UserRoles role : UserRoles.values()) {
            if (role.name().equalsIgnoreCase(dbData)) {
                return role;
            }
        }

        // If no match, throw exception with helpful message
        throw new IllegalArgumentException("Unknown role: " + dbData +
                ". Available roles are: " + java.util.Arrays.toString(UserRoles.values()));
    }
}
