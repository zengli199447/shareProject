package com.example.administrator.sharenebulaproject.widget;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.example.administrator.sharenebulaproject.R;

import java.io.IOException;

/**
 * 作者：真理 Created by Administrator on 2018/12/14.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MediaPlayBuilder implements MediaPlayer.OnCompletionListener {

    private MediaPlayer mPlayer;

    public void playFromRawFile(Context mContext) {
        try {
            mPlayer = new MediaPlayer();
            mPlayer.setOnCompletionListener(this);
            AssetFileDescriptor file = mContext.getResources().openRawResourceFd(R.raw.prompt);
            try {
                mPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                if (!mPlayer.isPlaying()) {
                    mPlayer.prepare();
                    mPlayer.start();
                }
            } catch (IOException e) {
                mPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = null;
    }

}
