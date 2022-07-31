package io.everyonecodes.anber.data;

public enum HomeType {
    HOUSE ("House"),
    APARTMENT ("Apartment"),
    GARAGE ("Garage"),
    OFFICE_BUILDING ("Office Building");

    private final String name;

    HomeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
