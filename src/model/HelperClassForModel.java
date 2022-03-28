package model;

import java.time.format.DateTimeFormatter;

public class HelperClassForModel {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    public static DateTimeFormatter getFormat() {
        return format;
    }

}
