package com.gms.moongoon.choice_moongoon;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by user on 2015-08-09.
 */
public class FirstUserInfo extends AppCompatActivity implements RadioButton.OnClickListener {

    Button startUserinfo;

    RadioGroup radioGroup1, radioGroup2;

    RadioButton sex_women;
    RadioButton sex_men;

    RadioButton age_1, age_2, age_3, age_4, age_5, age_6;

    String sex = null, age = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_first_userinfo);

        radioGroup1 = (RadioGroup) findViewById(R.id.radio1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radio2);
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        radioGroup1.setOnCheckedChangeListener(listener1);
        radioGroup2.setOnCheckedChangeListener(listener2);


        sex_women = (RadioButton) findViewById(R.id.sexWomen);
        sex_women.setOnClickListener(this);
        sex_men = (RadioButton) findViewById(R.id.sexMen);
        sex_men.setOnClickListener(this);
        age_1 = (RadioButton) findViewById(R.id.ageElementry);
        age_1.setOnClickListener(this);
        age_2 = (RadioButton) findViewById(R.id.age_middle);
        age_2.setOnClickListener(this);
        age_3 = (RadioButton) findViewById(R.id.age_high);
        age_3.setOnClickListener(this);
        age_4 = (RadioButton) findViewById(R.id.age_youth);
        age_4.setOnClickListener(this);
        age_5 = (RadioButton) findViewById(R.id.age_middleAge);
        age_5.setOnClickListener(this);
        age_6 = (RadioButton) findViewById(R.id.age_oldAge);
        age_6.setOnClickListener(this);


        startUserinfo = (Button) findViewById(R.id.startMain);
        startUserinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sex != null || age != null) {
                    Intent intent = getIntent();
                    intent.putExtra("sex", sex);
                    intent.putExtra("age", age);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                radioGroup2.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                radioGroup2.clearCheck(); // clear the second RadioGroup!
                radioGroup2.setOnCheckedChangeListener(listener2); //reset the listener

            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                radioGroup1.setOnCheckedChangeListener(null);
                radioGroup1.clearCheck();
                radioGroup1.setOnCheckedChangeListener(listener1);

            }
        }
    };

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Snackbar.make(getWindow().getDecorView(), "사전 설정을 마쳐야 합니다.", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int action = v.getId();

        switch (action) {
            case R.id.sexWomen:
                sex = "1";
                break;
            case R.id.sexMen:
                sex = "2";
                break;
            case R.id.ageElementry:
                age = "0";
                break;
            case R.id.age_middle:
                age = "1";
                break;
            case R.id.age_high:
                age = "2";
                break;
            case R.id.age_youth:
                age = "3";
                break;
            case R.id.age_middleAge:
                age = "4";
                break;
            case R.id.age_oldAge:
                age = "5";
                break;

        }

    }
}