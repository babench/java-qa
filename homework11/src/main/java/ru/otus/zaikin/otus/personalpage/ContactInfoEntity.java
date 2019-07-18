package ru.otus.zaikin.otus.personalpage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactInfoEntity {
    ContactInformationChannel channel;
    String value;
    boolean preferableChannel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactInfoEntity that = (ContactInfoEntity) o;
        return channel == that.channel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel);
    }
}
