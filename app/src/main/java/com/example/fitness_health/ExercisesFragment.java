// Import required packages
package com.example.fitness_health;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.fragment.app.Fragment;

// Define a class that extends Fragment
public class ExercisesFragment extends Fragment {

    // Declare a private instance variable for VideoView
    private VideoView videoView;
    private String videoPath;

    // Define a default constructor
    public ExercisesFragment() {

    }

    // Define a static method for creating a new instance of this Fragment
    public static ExercisesFragment newInstance(String param1, String param2) {
        ExercisesFragment fragment = new ExercisesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // Inflate the layout for this Fragment and return the View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercises, container, false);
    }

    // Once the View has been created, set up the VideoViews
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.video1;
        // Get the first VideoView from the layout and set up a MediaController
        videoView = view.findViewById(R.id.videoView1);
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Set the video URI and start playing the video

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();

        // Repeat the process for the other VideoViews in the layout
        videoView = view.findViewById(R.id.videoView2);
        MediaController mediaController2 = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();

        videoView = view.findViewById(R.id.videoView3);
        MediaController mediaController3 = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();

        videoView = view.findViewById(R.id.videoView4);
        MediaController mediaController4 = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();

        videoView = view.findViewById(R.id.videoView5);
        MediaController mediaController5 = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();

        videoView = view.findViewById(R.id.videoView6);
        MediaController mediaController6 = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();

        videoView = view.findViewById(R.id.videoView7);
        MediaController mediaController7 = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();

        videoView = view.findViewById(R.id.videoView8);
        MediaController mediaController8 = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
    }
}
