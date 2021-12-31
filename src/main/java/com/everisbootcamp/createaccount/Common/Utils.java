package com.everisbootcamp.createaccount.Common;

import com.everisbootcamp.createaccount.Constant.Enums.YesOrNot;

public class Utils {

    public static Boolean StringEmpty(String value) {
        return value.length() == 0 || value == null || value.isEmpty();
    }

    public static String BooleanToString(Boolean value) {
        return value ? YesOrNot.YES.getValue() : YesOrNot.NO.getValue();
    }

    public static Boolean ObjectToBoolean(Object value) {
        return ((Boolean) value).booleanValue();
    }
}
