package com.example.james.cyclecoach;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NameFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button ok;
    private EditText name;
    private TextView textView;
    private RelativeLayout _layout;
    private Button cont;
    private boolean _continue;
    public NameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NameFragment newInstance(String param1, String param2) {
        NameFragment fragment = new NameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name, container, false);
        ok = (Button) view.findViewById(R.id.okButton);
        ok.setOnClickListener(this);
        name = (EditText) view.findViewById(R.id.nameEditText);
        textView = (TextView)view.findViewById(R.id.nameDialogView);
        _layout = (RelativeLayout)view.findViewById(R.id.nameLayout);
        _layout.setOnClickListener(this);
        cont = (Button) view.findViewById(R.id.continue_button);
        _continue = false;
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.okButton:
                _layout.setBackgroundResource(R.drawable.happy);
                textView.setText("Nice to meet you, " + name.getText().toString() + "!");
                name.setEnabled(false);
                name.setVisibility(View.INVISIBLE);
                ok.setEnabled(false);
                ok.setVisibility(View.INVISIBLE);
                cont.setVisibility(View.VISIBLE);
                _continue = true;
                break;
            case R.id.nameLayout:
                if (_continue) {

                    Document doc = null;
                    try {
                        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                        // create root: <user>
                        Element root = doc.createElement("user");
                        doc.appendChild(root);

                        // create: <name>
                        Element user_name = doc.createElement("name");
                        root.appendChild(user_name);
                        // add text to child
                        user_name.setTextContent(name.getText().toString());

                        Element user_frequency = doc.createElement("frequency");
                        root.appendChild(user_frequency);

                        Element user_days = doc.createElement("days");
                        root.appendChild(user_days);

                        Element user_distance = doc.createElement("distance");
                        root.appendChild(user_distance);

                        Element last_ride = doc.createElement("last_ride");
                        root.appendChild(last_ride);

                        Element lance_state = doc.createElement("lance_state");
                        root.appendChild(lance_state);
                        lance_state.setTextContent("blue");

                        Element user_lastopened = doc.createElement("lastopened");
                        root.appendChild(user_lastopened);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();
                        user_lastopened.setTextContent(dateFormat.format(date));
                        last_ride.setTextContent(dateFormat.format(date));

                        Element settings = doc.createElement("settings");
                        root.appendChild(settings);

                        Element meetingTime = doc.createElement("meeting_time");
                        meetingTime.setTextContent("12:00 PM");
                        settings.appendChild(meetingTime);

                        Element meetingFrequency = doc.createElement("meeting_frequency");
                        meetingFrequency.setTextContent("Every Day");
                        settings.appendChild(meetingFrequency);

                        Element difficulty = doc.createElement("difficulty");
                        difficulty.setTextContent("medium");
                        settings.appendChild(difficulty);

                        Element smartWatch = doc.createElement("smartwatch");
                        smartWatch.setTextContent("enabled");
                        settings.appendChild(smartWatch);

                        Element nfc = doc.createElement("nfc_enabled");
                        nfc.setTextContent("enabled");
                        settings.appendChild(nfc);

                        Element nfcStartRideDelay = doc.createElement("nfc_start_ride_delay");
                        nfcStartRideDelay.setTextContent("30 seconds");
                        settings.appendChild(nfcStartRideDelay);

                        Transformer transformer = TransformerFactory.newInstance().newTransformer();
                        StringWriter writer = new StringWriter();
                        StreamResult result = new StreamResult(writer);
                        transformer.transform(new DOMSource(doc), result);
                        File externalDir = Environment.getExternalStorageDirectory();
                        File notes = new File(externalDir, "CycleCoach_name.xml");
                        try {
                            FileOutputStream os = new FileOutputStream(notes);
                            os.write(writer.toString().getBytes());
                            os.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent in = new Intent();
                        in.putExtra("intro_name",name.getText().toString());
                        in.putExtra("intro_lance_state", "blue");
                        getActivity().setResult(getActivity().RESULT_OK, in);
                        getActivity().finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
