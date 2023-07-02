package Enums;

public enum Errors {
    USERNAME_FORMAT_ERROR("Username contains\ninvalid characters!"),
    USERNAME_EXIST("Username already exists!"),
    USERNAME_EMPTY("Username can't be empty!"),
    SLOGAN_EMPTY("Slogan can't be empty!");

    public String getErrorText() {
        return errorText;
    }

    private String errorText;

    Errors(String errorText) {
        this.errorText = errorText;
    }
}
