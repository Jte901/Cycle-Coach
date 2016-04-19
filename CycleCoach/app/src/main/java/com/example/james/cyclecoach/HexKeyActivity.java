package com.example.james.cyclecoach;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class HexKeyActivity extends AppCompatActivity {

    Button mTimeButton;
    Spinner mReccurenceSpinner, mTimeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hex_key);

        mTimeButton = (Button) findViewById(R.id.timeButton);
        mReccurenceSpinner = (Spinner) findViewById(R.id.recurrenceSpinner);
        mTimeSpinner = (Spinner) findViewById(R.id.timesSpinner);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.notificationTimes, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.delayTimes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mReccurenceSpinner.setAdapter(adapter1);
        mTimeSpinner.setAdapter(adapter2);
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            HexKeyActivity parentActivity = (HexKeyActivity) getActivity();
            if (hourOfDay >=12 ) {
                if (hourOfDay >= 13) hourOfDay -= 12;
                parentActivity.mTimeButton.setText(String.format("%d:%02d pm", hourOfDay, minute));
            }
            else {
                if (hourOfDay == 0) hourOfDay = 12;
                parentActivity.mTimeButton.setText(String.format("%d:%02d am", hourOfDay, minute));
            }
        }
    }
}
