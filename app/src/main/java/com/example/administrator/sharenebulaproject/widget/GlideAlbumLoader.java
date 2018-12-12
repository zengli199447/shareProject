package com.example.administrator.sharenebulaproject.widget;

import android.webkit.URLUtil;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yanzhenjie.album.task.DefaultAlbumLoader;

import java.io.File;

/**
 * Created by Administrator on 2018/8/25.
 */

public class GlideAlbumLoader implements AlbumLoader {

    @Override
    public void loadAlbumFile(ImageView imageView, AlbumFile albumFile, int viewWidth, int viewHeight) {
        int mediaType = albumFile.getMediaType();
        if (mediaType == AlbumFile.TYPE_IMAGE) {
            Glide.with(imageView.getContext())
                    .load(albumFile.getPath())
                    .error(R.drawable.banner_off)
                    .into(imageView);
        } else if (mediaType == AlbumFile.TYPE_VIDEO) {
            DefaultAlbumLoader.getInstance()
                    .loadAlbumFile(imageView, albumFile, viewWidth, viewHeight);
        }
    }

    @Override
    public void loadImage(ImageView imageView, String imagePath, int viewWidth, int viewHeight) {
        if (URLUtil.isNetworkUrl(imagePath)) {
            Glide.with(imageView.getContext())
                    .load(imagePath)
                    .error(R.drawable.banner_off)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(new File(imagePath))
                    .error(R.drawable.banner_off)
                    .into(imageView);
        }
    }

}
