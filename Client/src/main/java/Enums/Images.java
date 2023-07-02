package Enums;

public enum Images {
    PLAIN1(Images.class.getResource("/MapImages/Plain1.jpg").toExternalForm()),
    BACK_GROUND2(Images.class.getResource("/BackGrounds/BackGround2.jpg").toExternalForm()),
    BACK_GROUND4(Images.class.getResource("/BackGrounds/BackGround4.jpg").toExternalForm()),
    SEA1(Images.class.getResource("/MapImages/Sea1.jpg").toExternalForm());
    public String getAddress() {
        return address;
    }

    ;
    private String address;

    Images(String address) {
        this.address = address;
    }
}
