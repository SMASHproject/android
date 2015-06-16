package com.spm.cafe529;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Created by 성호 on 2015-04-07.
 */
public class AdvertiseFragment extends MainActivity.PlaceholderFragment implements View.OnClickListener {

    //test
    public final static String VIDEO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";
    public final static int URL = 1;
    public final static int SDCARD = 2;
    VideoView videoView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_advertise, container, false);

        //VideoView
        videoView = (VideoView) rootView.findViewById(R.id.advertise_videoview);

        int type = URL;
        switch (type) {
            case URL:
                videoView.setVideoURI(Uri.parse(VIDEO_URL));
                break;

            case SDCARD:
                String path = Environment.getExternalStorageDirectory() + "/TestVideo6.mp4";
                videoView.setVideoPath(path);
                break;
        }

        MediaController controller = new MediaController(rootView.getContext());
        videoView.setMediaController(controller);

        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Toast.makeText(rootView.getContext(), "재생버튼을 누르세요.", Toast.LENGTH_SHORT).show();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(rootView.getContext(), "동영상재생완료", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;


    }

    @Override
    public void onClick(View v) {


    }


}
