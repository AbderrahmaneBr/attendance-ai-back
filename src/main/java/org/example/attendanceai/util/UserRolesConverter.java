package org.example.attendanceai.util;

import jakarta.persistence.Converter;
import org.example.attendanceai.domain.enums.UserRoles;

@Converter(autoApply = true)
public class UserRolesConverter extends GenericEnumConverter<UserRoles> {
    public UserRolesConverter() {
        super(UserRoles.class);
    }
}