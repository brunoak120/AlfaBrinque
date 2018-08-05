package com.book.app.api_pojo;

import android.content.Context;
import android.media.MediaPlayer;

import com.book.app.R;

public class Som {

    public static void bip(Context context){
        MediaPlayer mp = MediaPlayer.create(context, R.raw.default_click_sound);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });
    }
}
