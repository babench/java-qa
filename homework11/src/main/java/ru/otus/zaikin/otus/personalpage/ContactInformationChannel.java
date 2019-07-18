package ru.otus.zaikin.otus.personalpage;


public enum ContactInformationChannel {
    SKYPE("Skype"),
    FACEBOOK("Facebook"),
    VK("VK"),
    OK("OK"),
    VIBER("Viber"),
    ТELEGRAM("Тelegram"),
    WHATSAPP("WhatsApp");

    private String system;

    ContactInformationChannel(String system) {
        this.system = system;
    }

    public String getSystem() {
        return system;
    }
}
