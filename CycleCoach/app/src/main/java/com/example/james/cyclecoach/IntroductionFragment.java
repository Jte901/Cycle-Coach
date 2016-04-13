package com.example.james.cyclecoach;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IntroductionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IntroductionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroductionFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> _dialogs;
    private TextView _dialogTextView;
    private int _currentDialog;
    private Button skipIntro;
    private Button gotIt;
    public IntroductionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroductionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroductionFragment newInstance(String param1, String param2) {
        IntroductionFragment fragment = new IntroductionFragment();
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

        View view = inflater.inflate(R.layout.fragment_introduction, container, false);
        _dialogs = new ArrayList<>();
        _dialogs.add("Hi! I'm Lance, your new cycling coach!");
        _dialogs.add("I'll keep track of your cycling stats, but more importantly I'll help you set " +
                "and obtain your cycling goals.");
        _dialogs.add("It's up to you whether you'll improve or not!");
        _dialogs.add("But I can help you figure out the best way for you to do that.");
        _dialogs.add("Besides meeting face-to-face, there are 4 areas you should know about.");
        _dialogs.add("The gear will take you to your cycling data. (Remember, I can only keep track " +
                "of things you tell me about.)");
        _dialogs.add("The sports bottle is where you can record your nutrition.");
        _dialogs.add("The whistle is where you can set & check your coaching settings.");
        _dialogs.add("The hex key is where you can set sensors like NFC, cadence, HR, etc. (You'll " +
                "need to make sure I can do that for you, but I'll help when the time comes.)");

        _dialogTextView = (TextView) view.findViewById(R.id.dialogTextView);
        _dialogTextView.setText(_dialogs.get(0));

        skipIntro = (Button) view.findViewById(R.id.skipIntroButton);
        skipIntro.setOnClickListener(this);
        gotIt = (Button) view.findViewById(R.id.gotItButton);
        gotIt.setOnClickListener(this);
        _currentDialog = 1;
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
            case R.id.skipIntroButton:
                try {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.flContent, (Fragment)NameFragment.class.newInstance()).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.gotItButton:
                if (_currentDialog == _dialogs.size() - 1) {
                    gotIt.setText("Finish Intro");
                    skipIntro.setEnabled(false);
                    skipIntro.setVisibility(View.INVISIBLE);
                }
                if (_currentDialog == _dialogs.size()) {
                    try {
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.flContent, (Fragment)NameFragment.class.newInstance()).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    _dialogTextView.setText(_dialogs.get(_currentDialog));
                    ++_currentDialog;
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
