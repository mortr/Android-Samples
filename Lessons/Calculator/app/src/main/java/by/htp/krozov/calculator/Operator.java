package by.htp.krozov.calculator;

/**
 * @author Kirill Rozov
 * @since 04.02.15.
 */
public enum Operator implements Computable {
    SUM(new Computable() {
        @Override
        public double compute(double operand1, double operand2) {
            return operand1 + operand2;
        }
    }),
    SUBTRACTION(new Computable() {
        @Override
        public double compute(double operand1, double operand2) {
            return operand1 - operand2;
        }
    }),
    DIVISION(new Computable() {
        @Override
        public double compute(double operand1, double operand2) {
            return operand1 / operand2;
        }
    }),
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
