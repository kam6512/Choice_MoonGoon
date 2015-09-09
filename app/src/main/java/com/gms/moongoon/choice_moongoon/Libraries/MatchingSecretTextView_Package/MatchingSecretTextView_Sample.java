/*package com.gms.moongoon.choice_moongoon.Libraries.MatchingSecretTextView_Package;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    int index = 1;

    SecretMatchingTextView secretMatchingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.secretMatchingTextView = (SecretMatchingTextView) findViewById(R.id.textview);
        this.secretMatchingTextView.setIsVisible(true);
//        this.secretMatchingTextView.setLines(10);

        this.secretMatchingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secretMatchingTextView.toggle();
            }
        });
        AutofitHelper.create(secretMatchingTextView);
        ((EditText) findViewById(R.id.input)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                secretMatchingTextView.setText(charSequence);

//                Log.e("length", secretMatchingTextView.getText().length() + "");
                if (secretMatchingTextView.getText().length() == (30 * index)) {
                    secretMatchingTextView.setMaxLines(index + 1);
                    index++;
//                    Log.e("length", secretMatchingTextView.getText().length() + " / " + index);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // do nothing
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
*/

/*
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:autofit="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:padding="30dp"
    android:background="@android:color/white"
    >
    <EditText
        android:id="@+id/input"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:singleLine="true"
        android:text="start"/>

    <com.gms.matchingsecrettextview.matchingsecrettextview.SecretMatchingTextView
        android:id="@+id/textview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="텍스트뷰"
        android:textSize="50sp"
        android:gravity="center|top"

        autofit:minTextSize="8sp"
        android:textColor="@android:color/black"
        android:layout_centerHorizontal="true"
        android:layout_below="@+id/input"
        android:layout_alignParentBottom="true" />


</RelativeLayout>

*/