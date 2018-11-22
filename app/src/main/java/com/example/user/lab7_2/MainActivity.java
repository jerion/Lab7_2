package com.example.user.lab7_2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected EditText tall;
    protected EditText weigth;
    protected RadioButton boy;
    protected RadioButton girl;
    protected RadioGroup radioGroup;
    protected Button button;
    protected TextView s_weigth;
    protected TextView fat;
    int gender = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tall = (EditText) findViewById(R.id.tall);
        weigth = (EditText) findViewById(R.id.weigth);
        boy = (RadioButton) findViewById(R.id.boy);
        girl = (RadioButton) findViewById(R.id.girl);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        button = (Button) findViewById(R.id.button);
        s_weigth = (TextView) findViewById(R.id.s_weigth);
        fat = (TextView) findViewById(R.id.fat);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.boy:
                        gender = 1;
                        break;
                    case R.id.girl:
                        gender = 2;
                        break;
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAsyncTask();
            }
        });
    }

    private void runAsyncTask(){
        new AsyncTask<Void, Integer, Boolean>()
        {
            private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                dialog.setMessage("計算中...");
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                int progress = 0;
                while (progress <= 100) {
                    try {
                        Thread.sleep(50);
                        publishProgress(Integer.valueOf(progress));
                        progress++;
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                dialog.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Boolean status) {
                dialog.dismiss();
                double c_tall = Double.parseDouble(tall.getText().toString());
                double c_weight = Double.parseDouble(weigth.getText().toString());
                double c_sweight = 0;
                double c_fat = 0;

                if (gender == 1) {
                    c_sweight = 22 * c_tall/100 * c_tall/100;
                    c_fat = (c_weight - (0.88 * c_weight)) / c_weight * 100;
                }

                if (gender == 2) {
                    c_sweight = 22 * c_tall/100 * c_tall/100;
                    c_fat = (c_weight - (0.82 * c_weight)) / c_weight * 100;
                }

                s_weigth.setText(String.format("%.2f", c_sweight));
                fat.setText(String.format("%.2f", c_fat));
            }
        }.execute();
    }
}
