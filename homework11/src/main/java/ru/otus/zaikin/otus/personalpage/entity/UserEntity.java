package ru.otus.zaikin.otus.personalpage.entity;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@ToString
public class UserEntity {
    private String firstName;
    private String firstNameLatin;
    private String lastName;
    private String lastNameLatin;
    private String blogName;

    public List<ContactInfoEntity> contactInfoEntities;

    public UserEntity() {
        contactInfoEntities = new ArrayList<>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(firstNameLatin, that.firstNameLatin) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(lastNameLatin, that.lastNameLatin) &&
                Objects.equals(blogName, that.blogName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, firstNameLatin, lastName, lastNameLatin, blogName);
    }
}
