package com.example.fitness_health;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalculatorFragment extends Fragment {
    EditText height, weight;
    ImageView male, female;
    LinearLayout malelayout, femalelayout;
    Button btn;
    TextView result, condition;

    float h = 0, w = 0, bmi = 0;
    String user = "0";
    String res;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CalculatorFragment() {

    }

    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        // Initialize UI elements
        height = view.findViewById(R.id.height);
        weight = view.findViewById(R.id.weight);
        male = view.findViewById(R.id.male_img);
        female = view.findViewById(R.id.female_img);
        malelayout = view.findViewById(R.id.m_layout);
        femalelayout = view.findViewById(R.id.f_layout);
        btn = view.findViewById(R.id.btn);
        result = view.findViewById(R.id.result);
        condition = view.findViewById(R.id.condition);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heightStr = height.getText().toString();
                String weightStr = weight.getText().toString();

                if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
                    h = Float.parseFloat(heightStr) / 100; // Convert height to meters
                    w = Float.parseFloat(weightStr);

                    bmi = w / (h * h);
                    res = String.format("%.2f", bmi);

                    result.setText("BMI: " + res);

                    // Set condition based on BMI value
                    if (bmi < 18.5) {
                        condition.setText("Underweight");
                    } else if (bmi >= 18.5 && bmi < 24.9) {
                        condition.setText("Normal weight");
                    } else if (bmi >= 24.9 && bmi < 29.9) {
                        condition.setText("Overweight");
                    } else {
                        condition.setText("Obese");
                    }
                }
            }
        });

        return view;
    }
}
