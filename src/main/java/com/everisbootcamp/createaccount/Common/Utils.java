package com.everisbootcamp.createaccount.Common;

import com.everisbootcamp.createaccount.Constant.Enums.YesOrNot;
import java.util.Objects;

public class Utils {

    /**
     * Verifica si una cadena de texto es nula o vacia.
     *
     * @param String
     * @return Boolean
     */
    public static Boolean StringEmpty(String value) {
        return value.length() == 0 || Objects.isNull(value) || value.isEmpty();
    }

    /**
     * Convertir un booleano a una cadena de texto(SI/NO).
     *
     * @param Boolean
     * @return String
     */
    public static String BooleanToString(Boolean value) {
        return value ? YesOrNot.YES.getValue() : YesOrNot.NO.getValue();
    }

    /**
     * Convertir un objeto a un valor booleano.
     *
     * @param Object
     * @return Boolean
     */
    public static Boolean ObjectToBoolean(Object value) {
        return ((Boolean) value).booleanValue();
    }

    /**
     * Comparar si una cadena se parece en lo mas minimo a otra.
     *
     * @param String
     * @param String
     * @return Boolean
     */
    public static Boolean equalsOrContains(String value1, String value2) {
        return value1.toUpperCase().contains(value2.toUpperCase());
    }
}
