package by.htp.krozov.calculator;

/**
 * @author Kirill Rozov
 * @since 18.02.15.
 */
public class OptDouble {

    public static OptDouble of() {
        return new OptDouble(0., false);
    }

    public static OptDouble of(double value) {
        return new OptDouble(value, true);
    }

    private double mValue;
    private boolean mHasValue;

    private OptDouble(double value, boolean hasValue) {
        mValue = value;
        mHasValue = hasValue;
    }

    public double getValue() {
        return mValue;
    }

    public boolean hasValue() {
        return mHasValue;
    }
}
