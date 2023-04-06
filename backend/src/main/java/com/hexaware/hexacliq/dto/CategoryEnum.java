package com.hexaware.hexacliq.dto;

import java.util.Arrays;

public enum CategoryEnum {
    HALF_DAY("HD"), FULL_DAY("W"), OVERTIME("OT"), LEAVE("EL"), HOLIDAY("HOLIDAY"), OTHER("UNKNOWN");
    private final String shortName;

    CategoryEnum(String shortName) {
        this.shortName = shortName;
    }

    public static CategoryEnum getCategoryBy(String category) {
        try {
            return CategoryEnum.valueOf(category);
        } catch (Exception e) {
            return Arrays.stream(CategoryEnum.values()).filter(c -> category.equals("" + c.ordinal())).findFirst().orElse(CategoryEnum.OTHER);
        }
    }

    public String getShortName() {
        return shortName;
    }


}