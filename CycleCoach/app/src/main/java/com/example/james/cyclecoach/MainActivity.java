package com.example.james.cyclecoach;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MIME_TEXT_PLAIN = "text/plain";

    RelativeLayout _layout;
    TextView _dialogTextView;
    ImageView lance;
    Button _eyeButton, _gearButton, _waterBottleButton, _whistleButton, _hexKeyButton;
    File nameFile;
    int _eyePressCount;
    UserData data;
    Document doc;
    String lance_state;
    boolean openLanceMood;

    private NfcAdapter mNfcAdapter;

    /**
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter  The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    /**
     * @param activity The corresponding {@link Activity} requesting to stop the foreground dispatch.
     * @param adapter  The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lance = (ImageView) findViewById(R.id.lance_image);
        Intent intent = getIntent();
        lance_state = intent.getStringExtra("LANCE_STATE");
        openLanceMood = intent.getBooleanExtra("OPEN_MOOD", false);
        _layout = (RelativeLayout) findViewById(R.id.main_activity_layout);
        _dialogTextView = (TextView) findViewById(R.id.dialogTextView);
        data = new UserData();
        data.name = intent.getStringExtra("USER_NAME");

        _eyeButton = (Button) findViewById(R.id.eyeButton);
        _gearButton = (Button) findViewById(R.id.gearButton);
        _waterBottleButton = (Button) findViewById(R.id.waterBottleButton);
        _whistleButton = (Button) findViewById(R.id.whistleButton);
        _hexKeyButton = (Button) findViewById(R.id.hexKeyButton);
        _eyePressCount = 0;

        _eyeButton.setOnClickListener(this);
        _gearButton.setOnClickListener(this);
        _waterBottleButton.setOnClickListener(this);
        _whistleButton.setOnClickListener(this);
        _hexKeyButton.setOnClickListener(this);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {

        }

        if (!mNfcAdapter.isEnabled()) {

        }

        handleIntent(getIntent());

        switch (lance_state) {
            case "blue":
                lance.setImageDrawable(getDrawable(R.drawable.lance));
                _dialogTextView.setText("What can I help you with, " + this.data.name + "?");
                break;
            case "orange":
                lance.setImageDrawable(getDrawable(R.drawable.lance_orange));
                _dialogTextView.setText("It's been a while, " + this.data.name + "... you should ride soon.");

                break;
            case "red":
                lance.setImageDrawable(getDrawable(R.drawable.lance_red));
                _dialogTextView.setText("It's been way too long, " + this.data.name + ". You need to ride!");
                break;
        }
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);

            } else {
                Log.d("NFC", "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        data.name = getIntent().getExtras().getString("NAME");
        data.frequency = getIntent().getExtras().getInt("FREQUENCY");
        data.days = getIntent().getExtras().getInt("DAYS");
        data.distance = getIntent().getExtras().getFloat("DISTANCE");
        data.firstTime = getIntent().getExtras().getBoolean("FIRST_TIME");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("NAME", data.name);
        outState.putFloat("DISTANCE", data.distance);
        outState.putInt("DAYS", data.days);
        outState.putInt("FREQUENCY", data.frequency);
        outState.putBoolean("FIRST_TIME", data.firstTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nameFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "CycleCoach_name.xml");
        if (!nameFile.exists()) {
            Intent intent = new Intent(this, IntroductionActivity.class);
            startActivityForResult(intent, 1);
        } else {
            if (openLanceMood) {
                openLanceMood = false;
                Intent intent = new Intent(this, LanceMood.class);
                intent.putExtra("lance_base_mood", lance_state);
                intent.putExtra("username", this.data.name);
                startActivity(intent);
            }
        }

        /**
         * It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.
         */
        setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {
        /**
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         */
        stopForegroundDispatch(this, mNfcAdapter);

        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        handleIntent(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gearButton:
                Intent gearIntent = new Intent(this, GearActivity.class);
                gearIntent.putExtra("NAME", data.name);
                gearIntent.putExtra("DISTANCE", data.distance);
                gearIntent.putExtra("DAYS", data.days);
                gearIntent.putExtra("FREQUENCY", data.frequency);
                gearIntent.putExtra("FIRST_TIME", data.firstTime);
                startActivity(gearIntent);
                break;
            case R.id.waterBottleButton:
                _dialogTextView.setText("You clicked the water bottle!");
                break;
            case R.id.whistleButton:
                Intent progressIntent = new Intent(this, ProgressActivity.class);
                startActivity(progressIntent);
                break;
            case R.id.hexKeyButton:
                Intent hexKeyIntent = new Intent(this, HexKeyActivity.class);
                startActivity(hexKeyIntent);
                break;
            case R.id.eyeButton:
                ++_eyePressCount;
                if (_eyePressCount == 10 && lance_state.equals("blue")) {
                    _dialogTextView.setText("Stop poking my eye!");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        lance.setImageDrawable(getDrawable(R.drawable.lance_eyepoked));
                        fixEye();
                    }
                    _eyePressCount = 0;
                }
            default:
                break;
        }
    }

    private void fixEye() {
        try {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                lance.setImageDrawable(getDrawable(R.drawable.lance));
                                _dialogTextView.setText("");
                            }
                        }
                    });
                }
            }, 2000);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                lance_state = data.getStringExtra("intro_lance_state");
                this.data.name = data.getStringExtra("intro_name");
                _dialogTextView.setText("What can I help you with, " + this.data.name + "?");
            }
        }
    }

    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e("NFC", "Unsupported Encoding", e);
                    }
                }
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

            byte[] payload = record.getPayload();

            // Get the Text Encoding
            String textEncoding;
            if ((payload[0] & 128) == 0) textEncoding = "UTF-8";
            else textEncoding = "UTF-16";

            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                System.out.println(result);
            }
        }
    }
}
