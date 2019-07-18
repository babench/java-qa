package ru.otus.zaikin.otus.personalpage;

import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class ContactInformationChannelTest {

    @Test
    public void shouldRead() {
        String system = "faCebook";
        ContactInformationChannel contactInformationChannel = ContactInformationChannel.valueOf(system.toUpperCase());
        assertThat(contactInformationChannel).isEqualTo(ContactInformationChannel.FACEBOOK);
        log.debug("contactInformationChannel = " + contactInformationChannel);
    }
}