package uz.pdp.myappfigma.enums;

public enum Gender {

    MAIL("Male"),

    FMAIL("Female");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String  getDisplayName(){
        return displayName;
    }
}
