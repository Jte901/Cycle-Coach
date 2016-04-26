package com.example.james.cyclecoach;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class LanceMood extends AppCompatActivity implements View.OnClickListener {
    ImageView lance;
    UserData data;
    CommHandler commHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));

        setContentView(R.layout.activity_lance_mood);
        TextView text = (TextView) findViewById(R.id.lance_mood_text);
        lance = (ImageView) findViewById(R.id.lance_mood_image);
        lance.setOnClickListener(this);
        Intent intent = getIntent();
        String lance_mood = intent.getStringExtra("lance_base_mood");
        data = (UserData)intent.getSerializableExtra("DATA");
        String name = data.name;
        int currhour;
        int lasthour;
        Document doc = null;
        commHandler = new CommHandler(this);
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
                Calendar cal = Calendar.getInstance();
                Date date = new Date();
                cal.setTime(date);
                currhour = cal.get(Calendar.HOUR_OF_DAY);
                int currday = cal.get(Calendar.DAY_OF_MONTH);
                int currmonth = cal.get(Calendar.MONTH);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date lastdate = dateFormat.parse(doc.getElementsByTagName("last_ride").item(0).getTextContent());
                cal.setTime(lastdate);
                int lastday = cal.get(Calendar.DAY_OF_MONTH);
                int lastmonth = cal.get(Calendar.MONTH);


                if (currmonth != lastmonth || currday - lastday > 1) {
                    if (currhour > 0 && currhour <= 4) { // 12am - 5am
                        text.setText("ZzzzZzzzz... wuh... are you... gonna go for a ride... now?");
                        lance.setImageDrawable(getDrawable(R.drawable.lance_sleeping));
                    } else if (currhour > 4 && currhour <= 14) { //5am - 2pm
                        text.setText("Lets go for a ride!");
                        lance.setImageDrawable(getDrawable(R.drawable.lance_green_goride));
                        commHandler.sendToWatch(0);
                    } else if (currhour > 14 && currhour <= 18) { // 2pm - 6pm
                        text.setText("I think its a good time to go for a ride!");
                        lance.setImageDrawable(getDrawable(R.drawable.lance_yellow));
                        commHandler.sendToWatch(1);
                    } else if (currhour > 18 && currhour <= 20) { //6pm - 8pm
                        text.setText("It's getting kinda late, you should get that ride in!");
                        lance.setImageDrawable(getDrawable(R.drawable.lance_getting_late));
                    } else if (currhour > 20 && currhour <= 23) { //8pm - 12am
                        text.setText("Ah it's night, try to get a ride in earlier next time...");
                        lance.setImageDrawable(getDrawable(R.drawable.lance_latenight));
                    }
                } else {
                    text.setText("Good work riding today!");
                    lance.setImageDrawable(getDrawable(R.drawable.lance_goodjob));
                    commHandler.sendToWatch(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (lance_mood.equals("blue")) {

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == lance.getId()) {
            finish();
        }
    }
}
