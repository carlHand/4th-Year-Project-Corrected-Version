package com.projectyr4x00091174.carl.traingain;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

//import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ExerciseSteps extends DialogFragment {
    ArrayList<Exercise> eList;
    String currExercise;
    Context context;
    String username;
    String accessToken;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    ArrayList<ExerciseImage> exerImg;
    TextView title;
    TextView stepText;
    Button xButton;
    boolean show;

    public static Bitmap getBitmap(String URLsrc)
    {
        try {
            URL url = new URL(URLsrc);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch(IOException e)
        {
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_exercise_steps, container, false);
        final GestureDetector detector = new GestureDetector((GestureDetector.OnGestureListener) new SwipeGestureDetector());
        mViewFlipper = (ViewFlipper) rootView.findViewById(R.id.view_flipper);
        xButton = (Button) rootView.findViewById(R.id.xBtn);
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        final Bundle mArgs = getArguments();
        exerImg = new ArrayList<ExerciseImage>();
        if(mArgs.containsKey("username") && mArgs.containsKey("accessToken"))
        {
            System.out.println("Exercise Step has username and access token");
            username = mArgs.getString("username");
            accessToken = mArgs.getString("accessToken");
        }
        if(mArgs.containsKey("userShow")) {
            show = mArgs.getBoolean("userShow");
        }
        if(mArgs.containsKey("exerciseList")) {
            System.out.println("It contains eList in dialog");
            currExercise = "";
            currExercise = mArgs.getString("exercise");

            eList = mArgs.getParcelableArrayList("exerciseList");
            System.out.println("EXER NEW SIZEE: " + eList.size());
           // imgText1.setText(currExercise);
            /*
            exerImg1 = eList.get(0).ExerciseImageL.get(0).Image;
            bitmap1 = BitmapFactory.decodeByteArray(exerImg1, 0, exerImg1.length);
            img1.setImageBitmap(bitmap1);
            step1.setText(eList.get(0).StepL.get(0).StepDes);
            //img3.setImageBitmap(bitmap3);

            exerImg2 = eList.get(0).ExerciseImageL.get(1).Image;
            bitmap2 = BitmapFactory.decodeByteArray(exerImg2, 0, exerImg2.length);
            img2.setImageBitmap(bitmap2);
            step2.setText(eList.get(0).StepL.get(1).StepDes);

            exerImg3 = eList.get(0).ExerciseImageL.get(2).Image;
            bitmap3 = BitmapFactory.decodeByteArray(exerImg3, 0, exerImg3.length);
            img3.setImageBitmap(bitmap3);
            step3.setText(eList.get(0).StepL.get(2).StepDes);
*/
            for(int i = 0; i < eList.size(); i++)
            {
                for(int j = 0; j < eList.get(i).ExerciseImageL.size(); j++)
                {
                    System.out.println("EXERCISE STEPS NEW IMAGET" + eList.get(i).ExerciseImageL.get(j).URLimg.toString());
                    ExerciseImage img = eList.get(i).ExerciseImageL.get(j);
                    exerImg.add(img);
                }
             //   eList.get(i).ExerciseImageL = exerImg;
            }
            final RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.relativeImages);
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);

            for(int i = 0; i < eList.size(); i++) {
                System.out.println("exerciseResult ES NAme: " + eList.get(i).getName());
                for (int j = 0; j < eList.get(i).ExerciseImageL.size(); j++) {
                    System.out.println("exerciseResult ES Image: " + eList.get(i).ExerciseImageL.get(j).URLimg);
                }
                for(int k = 0; k < eList.get(i).StepL.size(); k++)
                {
                    System.out.println("exerciseResult ES STEPL: " + eList.get(i).StepL.get(k).StepDes);
                }
            }

            for(int i = 0; i < eList.size(); i++) {
                if (eList.get(i).getName().equals(currExercise)) {
                    System.out.println("EXERCISE SELECTED: " + eList.get(i).getName());
                    for (int j = 0; j < eList.get(i).ExerciseImageL.size(); j++) {
                        RelativeLayout rNew = new RelativeLayout(getActivity());
                        RelativeLayout.LayoutParams rlpNew = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
                        RelativeLayout.LayoutParams rImg = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        ImageView imageView = new ImageView(getActivity());
                    /*
                    byte[] imgByte = eList.get(i).ExerciseImageL.get(j).Image;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    bitmap3 = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, options);
                   // Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap3, 40, 40, false);
                   */
                        Bitmap bitmap = getBitmap(eList.get(i).ExerciseImageL.get(j).URLimg);
                        //Picasso.with(getActivity().getBaseContext()).load(eList.get(i).ExerciseImageL.get(j).URLimg).into(imageView);
                        imageView.setImageBitmap(bitmap);
                        // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setPadding(0,0,0,15);
                        imageView.setId(j);
                        rNew.addView(imageView);
                        // RelativeLayout.LayoutParams imgLayout = new RelativeLayout.LayoutParams(100,100);
                        //imageView.setLayoutParams(imgLayout);
                        // imageView.requestLayout();
                        // mViewFlipper.addView(imageView);

                        RelativeLayout.LayoutParams rlt = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        rlt.addRule(RelativeLayout.ALIGN_PARENT_TOP, imageView.getId());
                        //rlt.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        rlt.addRule(RelativeLayout.ABOVE, imageView.getId());
                       // rlt.addRule(RelativeLayout.CENTER_IN_PARENT);
                        title = new TextView(getActivity());
                        title.setText(eList.get(i).getName());
                        title.setTextSize(35);
                        title.setId(j);
                        //    title.setGravity(Gravity.TOP);
                        title.setLayoutParams(rlt);
                        //  mViewFlipper.addView(title);
                        // mViewFlipper.addView(title);
                        rNew.addView(title);

                        RelativeLayout.LayoutParams rls = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        rls.addRule(RelativeLayout.BELOW, imageView.getId());
                        stepText = new TextView(getActivity());
                        rls.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, imageView.getId());//ALIGN_PARENT_BOTTOM);
                        stepText.setPadding(0,0,0,20);
                        stepText.setText(eList.get(i).StepL.get(j).StepDes);
                        stepText.setGravity(Gravity.BOTTOM);
                        rls.addRule(RelativeLayout.BELOW, imageView.getId());
                        stepText.setTextSize(12);
                        stepText.setLayoutParams(rls);
                        //mViewFlipper.addView(stepText);
                        rNew.addView(stepText);
/*
                        RelativeLayout.LayoutParams rlsButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        Button cancelB = new Button(getActivity());
                        cancelB.setWidth(5);
                        rlsButton.addRule(RelativeLayout.ALIGN_PARENT_TOP, imageView.getId());
                        rlsButton.addRule(RelativeLayout.RIGHT_OF, title.getId());
                        rlsButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, title.getId());
                        //rlsButton.addRule(RelativeLayout.ALIGN_PARENT_END, title.getId());
                        cancelB.setText("X");
                        cancelB.setGravity(Gravity.RIGHT);
                        cancelB.setLayoutParams(rlsButton);
                        if (j == 0) {
                            cancelB.setWidth(rlsButton.WRAP_CONTENT);
                        }
                        rNew.addView(cancelB);

                        cancelB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismiss();
                            }
                        });
  */
                        mViewFlipper.addView(rNew);
                        //  rl.addView(mViewFlipper);

                    }
                }
                    else {
                        System.out.println("EXERCISE NOT SELECTED: " + eList.get(i).getName());
                    }

            }
        }
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
        return rootView;
    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                int count = 0;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.left_in));
                                mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.left_out));
                                mViewFlipper.showNext();
                                title.setText(currExercise);
                                return true;

                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.right_in));
                                mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.right_out));
                                mViewFlipper.showPrevious();
                                title.setText(currExercise);
                               // imgText3.setText(currExercise);
                                /*
                                exerImg3 = eList.get(0).ExerciseImageL.get(2).Image;
                                bitmap3 = BitmapFactory.decodeByteArray(exerImg3, 0, exerImg3.length);
                                img3.setImageBitmap(bitmap3);
                                step3.setText(eList.get(i).StepL.get(j+2).StepDes);
                                */
                                return true;
                            }



            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle mArgs = getArguments();
        if(mArgs.containsKey("exerciseList")) {
            System.out.println("It contains eList in dialog");
            String currExrcise = mArgs.getString("exercise");
            eList = mArgs.getParcelableArrayList("exerciseList");
            for (int i = 0; i < eList.size(); i++) {
                if (currExrcise.equals(eList.get(i).getName())) {
                    Toast.makeText(getActivity().getBaseContext(), "Exercise selected: " + eList.get(i).getName() + "\nLIFTVAL: " + eList.get(i).getLiftValue(), Toast.LENGTH_LONG).show();
                }
                else{
                    System.out.println("ELSE NOt EQUAL");
                }
            }
        }
        else{
            System.out.println("NOPE NO EList");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_steps, container, false);
    }
   // private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_exercise_steps.
     */
    /*
    // TODO: Rename and change types and number of parameters
    public static fragment_exercise_steps newInstance(String param1, String param2) {
        fragment_exercise_steps fragment = new fragment_exercise_steps();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public fragment_exercise_steps() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_steps, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    */

}
