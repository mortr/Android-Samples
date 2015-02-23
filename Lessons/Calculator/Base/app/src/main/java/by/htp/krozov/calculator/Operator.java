package by.htp.krozov.calculator;

/**
 * @author Kirill Rozov
 * @since 04.02.15.
 */
public enum Operator implements Computable {

    /**
     * Сумма
     */
    SUM(new Computable() {
        @Override
        public double compute(double operand1, double operand2) {
            return operand1 + operand2;
        }
    }),

    /**
     * Разность
     */
    SUBTRACTION(new Computable() {
        @Override
        public double compute(double operand1, double operand2) {
            return operand1 - operand2;
        }
    }),

    /**
     * Деление
     */
    DIVISION(new Computable() {
        private static final double EPS = 10e-15;

        @Override
        public double compute(double operand1, double operand2) {
            if (isOperandValid(operand1) && isOperandValid(operand2)) {
                return operand1 / operand2;
            } else {
                throw new IllegalArgumentException();
            }
        }

        private boolean isOperandValid(double operand) {
            return Math.abs(operand) > EPS
                    && !Double.isInfinite(operand)
                    && !Double.isNaN(operand);
        }
    }),

    /**
     * Умножение
     */
    MULTIPLY(new Computable() {
        @Override
        public double compute(double operand1, double operand2) {
            return operand1 * operand2;
        }
    });

    private final Computable mComputable;

    Operator(Computable computable) {
        mComputable = computable;
    }

    @Override
    public double compute(double operand1, double operand2) {
        return mComputable.compute(operand1, operand2);
    }
}
