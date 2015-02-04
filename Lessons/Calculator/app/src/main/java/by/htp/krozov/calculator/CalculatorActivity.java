package by.htp.krozov.calculator;

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

    Computable mComputable;
    TextView mResultView;
    TextView mOperand1View;
    TextView mOperand2View;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mResultView = (TextView) findViewById(R.id.result);
        mOperand1View = (TextView) findViewById(R.id.operand1);
        mOperand2View = (TextView) findViewById(R.id.operand2);

        RadioGroup operatorsView = (RadioGroup) findViewById(R.id.operators);
        mComputable = getOperatorById(operatorsView.getCheckedRadioButtonId());
        operatorsView.setOnCheckedChangeListener(new OperatorCheckedChangeListener());
        findViewById(R.id.compute).setOnClickListener(new OnComputeClickListener());
    }

    private class OnComputeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                mResultView.setText(
                        getString(R.string.result_format, mComputable.compute(
                                getDouble(mOperand1View), getDouble(mOperand2View)
                        ))
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ViewAnimationUtils.createCircularReveal(mResultView,
                            mResultView.getWidth(), mResultView.getHeight(),
                            0, Math.max(mResultView.getHeight(), mResultView.getWidth())
                    )
                            .setDuration(
                                    getResources().getInteger(android.R.integer.config_mediumAnimTime)
                            )
                            .start();
                }
            } catch (IllegalArgumentException e) {
                Toast.makeText(
                        CalculatorActivity.this, R.string.msg_illegal_operand, Toast.LENGTH_SHORT
                ).show();
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

    private class OperatorCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            mComputable = getOperatorById(checkedId);
        }
    }

    private static Computable getOperatorById(int checkedId) {
        switch (checkedId) {
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
