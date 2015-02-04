package by.htp.krozov.calculator;

/**
 * Интерфейс который сопоставляет 2 числам другое число.
 *
 * @author Kirill Rozov
 * @since 04.02.15.
 */
public interface Computable {
    /**
     * @throws java.lang.IllegalArgumentException
     */
    double compute(double operand1, double operand2);
}
