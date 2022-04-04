package model;

import java.time.format.DateTimeFormatter;

public class HelperClassForModel {

    private HelperClassForModel(){}

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    public static DateTimeFormatter getFormat() {
        return FORMAT;
    }

}
