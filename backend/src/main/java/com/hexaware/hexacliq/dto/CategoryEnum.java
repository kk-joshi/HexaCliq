package com.hexaware.hexacliq.dto;

import org.apache.poi.xssf.usermodel.XSSFColor;

import java.util.Arrays;

import static com.hexaware.hexacliq.utils.Constants.*;

public enum CategoryEnum {
    HALF_DAY("1/2", COLOR_MAROON),
    FULL_DAY("W", COLOR_NAVY),
    OVERTIME("OT", COLOR_PURPLE),
    LEAVE("EL", COLOR_MAROON),
    HOLIDAY("HD", COLOR_MAROON),
    FURLOUGH("FUR", COLOR_MAROON),
    OTHER("--", COLOR_GRAY);
    private final String shortName;
    private final XSSFColor color;

    CategoryEnum(String shortName, XSSFColor color) {
        this.shortName = shortName;
        this.color = color;
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

    public XSSFColor getColor() {
        return color;
    }
}