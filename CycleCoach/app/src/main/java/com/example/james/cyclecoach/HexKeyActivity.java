package com.example.james.cyclecoach;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class HexKeyActivity extends AppCompatActivity implements View.OnClickListener {

    Button mTimeButton, mSetItButton;
    Spinner mReccurenceSpinner, mTimeSpinner;
    Document doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hex_key);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));

        initFile();

        mTimeButton = (Button) findViewById(R.id.timeButton);
        mSetItButton = (Button) findViewById(R.id.setItButton);
        mSetItButton.setOnClickListener(this);

        RadioGroup difficulty = (RadioGroup) findViewById(R.id.radioGroupDifficulty);
        RadioGroup smartWatch = (RadioGroup) findViewById(R.id.radioGroupSmartwatch);
        RadioGroup nfc = (RadioGroup) findViewById(R.id.radioGroupNfc);

        RadioButton mediumButton = (RadioButton) findViewById(R.id.mediumRadioButton);
        mediumButton.setOnClickListener(this);
        RadioButton hardButton = (RadioButton) findViewById(R.id.hardRadioButton);
        hardButton.setOnClickListener(this);

        RadioButton enabledWatchButton = (RadioButton) findViewById(R.id.smartWatchEnabledRadioButton);
        enabledWatchButton.setOnClickListener(this);
        RadioButton disabledWatchButton = (RadioButton) findViewById(R.id.smartWatchDisabledRadioButton);
        disabledWatchButton.setOnClickListener(this);

        RadioButton enabledNfcButton = (RadioButton) findViewById(R.id.nfcEnabledRadioButton);
        enabledNfcButton.setOnClickListener(this);
        RadioButton disabledNfcButton = (RadioButton) findViewById(R.id.nfcDisabledRadioButton);
        disabledNfcButton.setOnClickListener(this);

        difficulty.check(mediumButton.getId());
        smartWatch.check(enabledWatchButton.getId());
        nfc.check(enabledNfcButton.getId());

        mReccurenceSpinner = (Spinner) findViewById(R.id.recurrenceSpinner);
        mTimeSpinner = (Spinner) findViewById(R.id.timesSpinner);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.notificationTimes, R.layout.spinner_layout);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.delayTimes, R.layout.spinner_layout);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mReccurenceSpinner.setAdapter(adapter1);
        mTimeSpinner.setAdapter(adapter2);
    }

    private void initFile() {
        File nameFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "CycleCoach_name.xml");
        if (nameFile.exists()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                FileInputStream fis = new FileInputStream(nameFile);
                byte[] d = new byte[(int) nameFile.length()];
                fis.read(d);
                fis.close();
                //  this.data.name = new String(d, "UTF-8");
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(new String(d, "UTF-8")));
                doc = db.parse(is);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mediumRadioButton) {
            doc.getElementsByTagName("difficulty").item(0).setTextContent("medium");
        } else if (v.getId() == R.id.hardRadioButton) {
            doc.getElementsByTagName("difficulty").item(0).setTextContent("hard");
        } else if (v.getId() == R.id.smartWatchEnabledRadioButton) {
            doc.getElementsByTagName("smartwatch").item(0).setTextContent("enabled");
        } else if (v.getId() == R.id.smartWatchDisabledRadioButton) {
            doc.getElementsByTagName("smartwatch").item(0).setTextContent("disabled");
        } else if (v.getId() == R.id.nfcEnabledRadioButton) {
            doc.getElementsByTagName("nfc_enabled").item(0).setTextContent("enabled");
        } else if (v.getId() == R.id.nfcDisabledRadioButton) {
            doc.getElementsByTagName("nfc_enabled").item(0).setTextContent("enabled");
        }
        if (v.getId() == R.id.setItButton) {
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);
                transformer.transform(new DOMSource(doc), result);
                File externalDir = Environment.getExternalStorageDirectory();
                File notes = new File(externalDir, "CycleCoach_name.xml");
                FileOutputStream os = new FileOutputStream(notes);
                os.write(writer.toString().getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                finish();
            }
        }
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
            if (hourOfDay >= 12) {
                if (hourOfDay >= 13) hourOfDay -= 12;
                parentActivity.mTimeButton.setText(String.format("%d:%02d pm", hourOfDay, minute));
            } else {
                if (hourOfDay == 0) hourOfDay = 12;
                parentActivity.mTimeButton.setText(String.format("%d:%02d am", hourOfDay, minute));
            }
        }
    }
}
