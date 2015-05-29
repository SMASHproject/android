package com.spm.cafe529;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by 성호 on 2015-04-07.
 */
public class AdvertiseFragment extends MainActivity.PlaceholderFragment implements View.OnClickListener{


    VideoView videoView;

    //test
    public final static String VIDEO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_advertise, container, false);

        /* MEDIA PLAYER

        //Video View
        videoView = (VideoView) rootView.findViewById(R.id.advertise_videoview);
        videoView.setVideoURI(Uri.parse(VIDEO_URL));

        //set Media Controller
        MediaController controller = new MediaController(rootView.getContext());
        videoView.setMediaController(controller);

        // 준비하는 과정을 미리함
        videoView.requestFocus();

        // 동영상이 재생준비가 완료되엇을떄를 알수있는 리스너 (실제 웹에서 영상을 다운받아 출력할때 많이 사용됨)
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            // 동영상 재생준비가 완료된후 호출되는 메서드
            @Override
            public void onPrepared(MediaPlayer mp) {

                // 비디오를 처음부터 재생할땐 0
                videoView.seekTo(0);
                // 비디오 재생 시작
                videoView.start();


            }
        });

        // 동영상 재생이 완료된걸 알수있는 리스너
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            // 동영상 재생이 완료된후 호출되는 메서드
            public void onCompletion(MediaPlayer player) {
                // 비디오를 처음부터 재생할땐 0
                videoView.seekTo(0);
                // 비디오 재생 시작
                videoView.start();
            }
        });

        */

        return rootView;


    }

    @Override
    public void onClick(View v) {

    }


}
