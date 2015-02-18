package by.htp.krozov.calculator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Kirill Rozov
 * @since 04.02.15.
 */
public class CalculatorActivity extends Activity {

    private static final String STATE_RESULT = "result";

    // View для отображения результата
    TextView mResultView;

    // View с первым операндом
    EditText mOperand1View;

    // View со вторым операндом
    EditText mOperand2View;

    // View с выбором оператора
    RadioGroup mOperatorsView;

    private OptDouble mResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_RESULT)) {
            mResult = OptDouble.of(savedInstanceState.getDouble(STATE_RESULT));
        } else {
            mResult = OptDouble.of();
        }

        setContentView(R.layout.activity_calculator);

        // Инициализируем View с которыми вудем работать
        mResultView = (TextView) findViewById(R.id.result);
        mOperand1View = (EditText) findViewById(R.id.operand1);
        mOperand2View = (EditText) findViewById(R.id.operand2);
        mOperatorsView = (RadioGroup) findViewById(R.id.operators);

        // Кнопке "Вычислить" задаётся слушатель
        findViewById(R.id.compute).setOnClickListener(new OnComputeClickListener());
    }

    /**
     * Парсинг числа с плавающей точкой из TextView.
     *
     * @throws java.lang.IllegalArgumentException Происходит в случае если строка,
     *                                            из которой партится число, имеет недопустимый формат.
     */
    private static double getDouble(TextView textView) {
        CharSequence text = textView.getText();
        if (TextUtils.isEmpty(text)) {
            throw new IllegalArgumentException();
        } else {
            try {
                return Double.parseDouble(text.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    /**
     * Метод для получения текущего выбранного оператора в списке.
     *
     * @return Текущий выбранный оператора.
     */
    private Computable getOperatorById() {
        switch (mOperatorsView.getCheckedRadioButtonId()) {
            case View.NO_ID:
                return null;

            case R.id.operator_sum:
                return Operator.SUM;

            case R.id.operator_subtraction:
                return Operator.SUBTRACTION;

            case R.id.operator_multiply:
                return Operator.MULTIPLY;

            case R.id.operator_division:
                return Operator.DIVISION;

            default:
                throw new RuntimeException("Unknown view id for operator");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Сохраняем текст результата
        outState.putCharSequence(STATE_RESULT, mResultView.getText());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Восстанавливаем текст реузльтата
        if (mResult != null && mResult.hasValue()) {
            setComputeResult(mResult.getValue());
        }
    }


    /**
     * Слушатель нажатия на кнопку "Вычислить".
     */
    private class OnComputeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                Computable operator = getOperatorById();
                if (operator == null) { // Оператора не выбран
                    Toast.makeText(CalculatorActivity.this, R.string.msg_illegal_operation, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    double computeResult = operator.compute(getDouble(mOperand1View), getDouble(mOperand2View));
                    mResult = OptDouble.of(computeResult);
                    setComputeResult(computeResult);
                    animateShow();
                }
            } catch (IllegalArgumentException e) {
                // Происходит в случае если введены недопустимые аргументы для вычислений, например
                // пустая строка или недопустимый формат данных.
                if (!TextUtils.isEmpty(mResultView.getText())) {
                    animateHide();
                }
                Toast.makeText(
                        CalculatorActivity.this, R.string.msg_illegal_operand, Toast.LENGTH_SHORT
                ).show();
            }
        }

        private void animateShow() {
            // Анимирование обновления View с Reveal эффектом
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int centerY = mResultView.getHeight() / 2;
                int endRadius = Math.max(mResultView.getHeight(), mResultView.getWidth());
                ViewAnimationUtils.createCircularReveal(mResultView, 0, centerY, 0, endRadius)
                        .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime))
                        .start();
            }
        }

        private void animateHide() {
            // Анимирование сокрытия View с Reveal эффектом
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int centerY = mResultView.getHeight() / 2;
                int startRadius = Math.max(mResultView.getHeight(), mResultView.getWidth());
                Animator animator =
                        ViewAnimationUtils.createCircularReveal(mResultView, 0, centerY, startRadius, 0);
                animator.addListener(
                        new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mResultView.setText(null);
                            }
                        });
                animator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime))
                        .start();
            } else {
                mResultView.setText(null);
            }
        }
    }

    private void setComputeResult(double result) {
        mResultView.setText(getString(R.string.result_format, result));
    }
}
