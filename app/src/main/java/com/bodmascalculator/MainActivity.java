package com.bodmascalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Calculations calculations = new Calculations(this);

    private final String[] ANGLES = new String[]{"rad", "deg", "grad"};
    private int angle_index = 0;
    private boolean HYP = false;

    private TextView sub;
    private TextView main;

    private Button mc;
    private Button mr;
    private Button madd;
    private Button msub;
    private Button ms;
    private Button angle;
    private ToggleButton hyp;

    private Button sin;
    private Button cos;
    private Button tan;
    private Button sin_inv;
    private Button cos_inv;
    private Button tan_inv;

    private Button ten_raisedto_x;
    private Button e_raisedto_x;
    private Button one_div_x;
    private Button parent_open;
    private Button parent_close;
    private Button mod;

    private Button sq;
    private Button e;
    private Button ce;
    private Button c;
    private Button bs;
    private Button div;

    private Button cb;
    private Button pi;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button mul;

    private Button indice;
    private Button fact;
    private Button four;
    private Button five;
    private Button six;
    private Button plus;

    private Button sqrt;
    private Button ln;
    private Button one;
    private Button two;
    private Button three;
    private Button minus;

    private Button root;
    private Button log;
    private Button negate;
    private Button zero;
    private Button decimal;
    private Button equal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sub = findViewById(R.id.scientific_tv_sub);
        main = findViewById(R.id.scientific_tv_main);


        angle = findViewById(R.id.scientific_btn_angle);
        angle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angle_index++;
                angle_index %= ANGLES.length;
                angle.setText(ANGLES[angle_index]);
            }
        });
        hyp = findViewById(R.id.scientific_btn_hyp);
        hyp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HYP = !HYP;
                if (HYP){
                    sin.setText("sinh");
                    cos.setText("cosh");
                    tan.setText("tanh");
                    sin_inv.setText("sinh-1");
                    cos_inv.setText("cosh-1");
                    tan_inv.setText("tanh-1");
                } else {
                    sin.setText("sin");
                    cos.setText("cos");
                    tan.setText("tan");
                    sin_inv.setText("sin-1");
                    cos_inv.setText("cos-1");
                    tan_inv.setText("tan-1");
                }
            }
        });

        sin = findViewById(R.id.scientific_btn_sin);
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HYP){
                    calculations.operatorClicked("sinh");
                } else {
                    calculations.operatorClicked("sin");
                }
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        cos = findViewById(R.id.scientific_btn_cos);
        cos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HYP){
                    calculations.operatorClicked("cosh");
                } else {
                    calculations.operatorClicked("cos");
                }
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        tan = findViewById(R.id.scientific_btn_tan);
        tan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HYP){
                    calculations.operatorClicked("tanh");
                } else {
                    calculations.operatorClicked("tan");
                }
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        sin_inv = findViewById(R.id.scientific_btn_sin_1);
        sin_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HYP){
                    calculations.operatorClicked("sinh-1");
                } else {
                    calculations.operatorClicked("sin-1");
                }
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        cos_inv = findViewById(R.id.scientific_btn_cos_1);
        cos_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HYP){
                    calculations.operatorClicked("cosh-1");
                } else {
                    calculations.operatorClicked("cos-1");
                }
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        tan_inv = findViewById(R.id.scientific_btn_tan_1);
        tan_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HYP){
                    calculations.operatorClicked("tanh-1");
                } else {
                    calculations.operatorClicked("tan-1");
                }
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        ten_raisedto_x = findViewById(R.id.scientific_btn_10_raisedto_x);
        ten_raisedto_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("10^x");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });
        e_raisedto_x = findViewById(R.id.scientific_btn_e_raisedto_x);
        e_raisedto_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("e^x");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });
        one_div_x = findViewById(R.id.scientific_btn_1_div_x);
        one_div_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("1/x");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });
        parent_open = findViewById(R.id.scientific_btn_parent_open);
        parent_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.parent_openClicked();
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });
        parent_close = findViewById(R.id.scientific_btn_parent_close);
        parent_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.parent_closeClicked();
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });
        mod = findViewById(R.id.scientific_btn_mod);
        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("mod");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        sq = findViewById(R.id.scientific_btn_sq);
        sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("sq");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        e = findViewById(R.id.scientific_btn_e);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("e");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        ce = findViewById(R.id.scientific_btn_ce);
        ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.ce();
                main.setText("0");
                sub.setText(calculations.calc(calculations.numbers));
            }
        });
        c = findViewById(R.id.scientific_btn_c);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.clear();
                main.setText("0");
                sub.setText(calculations.calc(calculations.numbers));
            }
        });
        bs = findViewById(R.id.scientific_btn_bs);
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.bs();
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });
        div = findViewById(R.id.scientific_btn_div);
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("/");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        cb = findViewById(R.id.scientific_btn_cb);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("cb");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        pi = findViewById(R.id.scientific_btn_pi);
        pi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("pi");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        seven = findViewById(R.id.scientific_btn_7);
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.numberClicked("7");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        eight = findViewById(R.id.scientific_btn_8);
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.numberClicked("8");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        nine = findViewById(R.id.scientific_btn_9);
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.numberClicked("9");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        mul = findViewById(R.id.scientific_btn_mul);
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("*");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        indice = findViewById(R.id.scientific_btn_indice);
        indice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("^");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        fact = findViewById(R.id.scientific_btn_fact);
        fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("!");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        four = findViewById(R.id.scientific_btn_4);
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.numberClicked("4");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        five = findViewById(R.id.scientific_btn_5);
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.numberClicked("5");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        six = findViewById(R.id.scientific_btn_6);
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.numberClicked("6");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        plus = findViewById(R.id.scientific_btn_add);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("+");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        sqrt = findViewById(R.id.scientific_btn_sqrt);
        sqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("sqrt");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        ln = findViewById(R.id.scientific_btn_ln);
        ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("ln");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        one = findViewById(R.id.scientific_btn_1);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.numberClicked("1");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        two = findViewById(R.id.scientific_btn_2);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.numberClicked("2");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        three = findViewById(R.id.scientific_btn_3);
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.numberClicked("3");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        minus = findViewById(R.id.scientific_btn_sub);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("-");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        root = findViewById(R.id.scientific_btn_y_rt_x);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("rt");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        log = findViewById(R.id.scientific_btn_log);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("log");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        negate = findViewById(R.id.scientific_btn_negate);
        negate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.operatorClicked("±");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        zero = findViewById(R.id.scientific_btn_0);
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.numberClicked("0");
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        decimal = findViewById(R.id.scientific_btn_decimal);
        decimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations.decimalClicked();
                main.setText(calculations.getCurrentNumber());
                sub.setText(calculations.calc(calculations.numbers));
            }
        });

        equal = findViewById(R.id.scientific_btn_equal);
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> expression = calculations.numbers;
                calculations.evaluateAnswer();
                main.setText(calculations.answer);
                sub.setText("");
            }
        });
    }
}
