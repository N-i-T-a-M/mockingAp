package Enums;

public enum Avatars {
    AVATAR1(Avatars.class.getResource("/Avatars/avatar1.png").toExternalForm()),
    AVATAR2(Avatars.class.getResource("/Avatars/avatar2.png").toExternalForm());
    ;
    private String address;

    Avatars(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
