package by.htp.krozov.calculator;

import android.animation.Animator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Kirill Rozov
 * @since 04.02.15.
 */
public class CalculatorActivity extends Activity {

    TextView mResultView;
    TextView mOperand1View;
    TextView mOperand2View;
    RadioGroup mOperatorsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mResultView = (TextView) findViewById(R.id.result);
        mOperand1View = (TextView) findViewById(R.id.operand1);
        mOperand2View = (TextView) findViewById(R.id.operand2);

        mOperatorsView = (RadioGroup) findViewById(R.id.operators);
        findViewById(R.id.compute).setOnClickListener(new OnComputeClickListener());
    }

    private class OnComputeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                Computable operator = getOperatorById();
                if (operator == null) {
                    Toast.makeText(CalculatorActivity.this, R.string.msg_illegal_operand, Toast.LENGTH_SHORT)
                            .show();
                }
                mResultView.setText(
                        getString(R.string.result_format, operator.compute(
                                getDouble(mOperand1View), getDouble(mOperand2View)
                        ))
                );
                showResult();
            } catch (IllegalArgumentException e) {
                hideResult();
                Toast.makeText(
                        CalculatorActivity.this, R.string.msg_illegal_operand, Toast.LENGTH_SHORT
                ).show();
            }
        }

        private void showResult() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ViewAnimationUtils.createCircularReveal(mResultView,
                        0, mResultView.getHeight() / 2,
                        0, Math.max(mResultView.getHeight(), mResultView.getWidth())
                )
                        .setDuration(
                                getResources().getInteger(android.R.integer.config_mediumAnimTime)
                        )
                        .start();
            }
        }

        private void hideResult() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Animator animator = ViewAnimationUtils.createCircularReveal(mResultView,
                        0, mResultView.getHeight() / 2,
                        Math.max(mResultView.getHeight(), mResultView.getWidth()), 0
                );
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mResultView.setText(null);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.setDuration(
                        getResources().getInteger(android.R.integer.config_mediumAnimTime)
                ).start();
            }
        }
    }

    private static double getDouble(TextView textView) {
        CharSequence text = textView.getText();
        if (TextUtils.getTrimmedLength(text) > 0) {
            return Double.parseDouble(text.toString());
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Computable getOperatorById() {
        switch (mOperatorsView.getCheckedRadioButtonId()) {
            case 0:
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
}
