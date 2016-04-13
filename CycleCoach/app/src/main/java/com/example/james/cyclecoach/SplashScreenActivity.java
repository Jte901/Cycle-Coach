package com.example.james.cyclecoach;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Austin on 3/22/2016.
 */
public class SplashScreenActivity extends Activity {
    private static int SPLASH_TIME_OUT = 1000;
    /**
     * Called when the activity is first created.
     */
    Thread splashTread;
    Document doc;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));

        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(animationSet);
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    //load data
                    String name = doc.getElementsByTagName("name").item(0).getTextContent();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    Date lastdate = dateFormat.parse(doc.getElementsByTagName("lastopened").item(0).getTextContent());
                    String lance_state = "blue";
                    if (doc.getElementsByTagName("lance_state").item(0).getTextContent().equals("blue")) {
                        lance_state = "blue";
                    } else if (doc.getElementsByTagName("lance_state").item(0).getTextContent().equals("orange")) {
                        lance_state = "orange";
                    } else if (doc.getElementsByTagName("lance_state").item(0).getTextContent().equals("red")) {
                        lance_state = "red";
                    }
                    if (date.getTime() - lastdate.getTime() > TimeUnit.DAYS.toMillis(1) &&
                            date.getTime() - lastdate.getTime() < TimeUnit.DAYS.toMillis(2) ) {
                        lance_state = "orange";
                        doc.getElementsByTagName("lance_state").item(0).setTextContent("orange");

                    } else if (date.getTime() - lastdate.getTime() > TimeUnit.DAYS.toMillis(2) ) {
                        lance_state = "red";
                        doc.getElementsByTagName("lance_state").item(0).setTextContent("red");

                    }
                    //change tag text
                    Date d = new Date();
                    doc.getElementsByTagName("lastopened").item(0).setTextContent(dateFormat.format(d));
                    //write xml
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    StringWriter writer = new StringWriter();
                    StreamResult result = new StreamResult(writer);
                    transformer.transform(new DOMSource(doc), result);
                    File externalDir = Environment.getExternalStorageDirectory();
                    File notes = new File(externalDir, "CycleCoach_name.xml");
                    FileOutputStream os = new FileOutputStream(notes);
                    os.write(writer.toString().getBytes());
                    os.close();
                    // Splash screen pause time
                    Thread.sleep(SPLASH_TIME_OUT);
                    Intent intent = new Intent(SplashScreenActivity.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("USER_NAME", name);
                    intent.putExtra("LANCE_STATE", lance_state);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    SplashScreenActivity.this.finish();
                } catch (Exception e) {
                    // do nothing
                } finally {
                    SplashScreenActivity.this.finish();
                }
            }
        };
        splashTread.start();

    }
}
