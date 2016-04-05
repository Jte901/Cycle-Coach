package com.example.james.cyclecoach;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity implements View.OnClickListener{

    EditText name;
    Button ok;
    TextView textView;
    RelativeLayout _layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        name = (EditText)findViewById(R.id.nameEditText);
        ok = (Button)findViewById(R.id.okButton);
        ok.setOnClickListener(this);
        textView = (TextView)findViewById(R.id.nameDialogView);
        _layout = (RelativeLayout)findViewById(R.id.nameLayout);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == ok.getId()) {
            _layout.setBackgroundResource(R.drawable.happy);
            textView.setText("Nice to meet you, " + name.getText().toString() + "!");
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(NameActivity.this, MainActivity.class);
                    intent.putExtra("NAME", name.getText().toString());
                    startActivity(intent);
                }
            }, 2000);

        }

    }
}
