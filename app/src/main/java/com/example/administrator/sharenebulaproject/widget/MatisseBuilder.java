package com.example.administrator.sharenebulaproject.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/8/15.
 */

public class MatisseBuilder {

    private String TAG = "MatisseBuilder";

    public static MatisseBuilder matisseBuilder = new MatisseBuilder();

    public static MatisseBuilder getInstence() {
        return matisseBuilder;
    }


    //相册单选
    public void singlePictureSelect(final Activity activity) {
        Album.image(activity)
                .singleChoice()
                .camera(false)
                .requestCode(EventCode.MATISSE)
                .columnCount(3)
                .widget(initWidget(activity))
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        for (int i = 0; i < result.size(); i++) {
                            String path = result.get(i).getPath();
                            Log.e(TAG, "绝对路径 path : " + "file://" + path);
                            returnSelectMedia.selectMediaListener(path);
                        }
                    }
                }).onCancel(new Action<String>() {
            @Override
            public void onAction(int requestCode, @NonNull String result) {
            }
        }).start();
    }

    //样式
    private Widget initWidget(Context context) {
        Widget widget = Widget.newDarkBuilder(context)
                .title("媒体浏览")
                .statusBarColor(Color.WHITE)
                .toolBarColor(Color.WHITE)
                .navigationBarColor(Color.WHITE)
                .mediaItemCheckSelector(Color.TRANSPARENT, Color.TRANSPARENT)
                .bucketItemCheckSelector(Color.TRANSPARENT, Color.TRANSPARENT)
                .buttonStyle(Widget.ButtonStyle.newDarkBuilder(context).setButtonSelector(Color.WHITE, Color.WHITE).build()).build();

        return widget;
    }

    public interface selectMediaListrner{
        void selectMediaListener(String path);
    }

    public selectMediaListrner returnSelectMedia;

    public void setSelectMediaListener(selectMediaListrner returnSelectMedia){
        this.returnSelectMedia = returnSelectMedia;
    }

}
