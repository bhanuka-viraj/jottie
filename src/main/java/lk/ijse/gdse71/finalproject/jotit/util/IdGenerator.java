package lk.ijse.gdse71.finalproject.jotit.util;

import org.apache.commons.lang3.RandomStringUtils;

public class IdGenerator {

    public static String generateId(String prefix, int length) {
        return prefix + RandomStringUtils.randomAlphanumeric(length);
    }
}
