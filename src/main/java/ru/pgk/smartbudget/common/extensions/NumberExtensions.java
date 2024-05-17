package ru.pgk.smartbudget.common.extensions;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberExtensions {

    public static Double round(Double value) {
        return round(value, 3);
    }

    public static Double round(Double value, Integer scale) {
        if (value == null) return null;
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }
}
