package com.example.administrator.sharenebulaproject.widget;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.global.EnumCodeClass;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;

import static android.R.attr.bitmap;

/**
 * Created by Administrator on 2018/8/28.
 * UM Tools
 */

public class UmShareListenerBuilder {

    private Activity activity;
    private ToastUtil toastUtil;
    private String TAG = "UmShareListenerBuilder";

    public UmShareListenerBuilder(Activity activity, ToastUtil toastUtil) {
        this.activity = activity;
        this.toastUtil = toastUtil;
    }

    public void initUmImageShare(int shareType, Bitmap bitmap) {
        UMImage umImage = new UMImage(activity, bitmap);
        switch (shareType) {
            case 0:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.QQ)//传入QQ平台
                        .withMedia(umImage)
                        .setCallback(shareListener)
                        .share();
                break;
            case 1:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入微信平台
                        .withMedia(umImage)
                        .setCallback(shareListener)
                        .share();
                break;
            case 2:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入微信朋友圈平台
                        .withMedia(umImage)
                        .setCallback(shareListener)
                        .share();
                break;
        }
    }

    //分享链接可以使用UMWeb进行分享：
    public void initUmUrlShare(int shareType, Bitmap bitmap, String img_url, String url, String title, String description) {
//        url = "http://e.hiphotos.baidu.com/image/pic/item/d0c8a786c9177f3eeb8bedb57ccf3bc79e3d56ce.jpg";
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        if (img_url.isEmpty()) {
            web.setThumb(new UMImage(activity, bitmap));  //缩略图
        } else {
            web.setThumb(new UMImage(activity, img_url));  //缩略图
        }
        web.setDescription(description);//描述
        switch (shareType) {
            case 0:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.QQ)//传入QQ平台
                        .withMedia(web)
                        .setCallback(shareListener)
                        .share();
                break;
            case 1:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入微信平台
                        .withMedia(web)
                        .setCallback(shareListener)
                        .share();
                break;
            case 2:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入微信朋友圈平台
                        .withMedia(web)
                        .setCallback(shareListener)
                        .share();
                break;
        }
    }

    public void initUMShare(String text) {
//        平台
//        枚举变量
//        QQ SHARE_MEDIA.QQ
//        Qzone SHARE_MEDIA.QZONE
//        微信还有 SHARE_MEDIA.WEIXIN
//        微信朋友圈 SHARE_MEDIA.WEIXIN_CIRCLE
//        微信收藏 SHARE_MEDIA.WEIXIN_FAVORITE

//        UMImage image = new UMImage(activity, "imageurl");//网络图片
//        UMImage image = new UMImage(activity, file);//本地文件
//        UMImage image = new UMImage(activity, R.drawable.ic_launcher);//资源文件
//        UMImage image = new UMImage(activity, bitmap);//bitmap文件
//        UMImage image = new UMImage(activity, byte[]);//字节流

//        new ShareAction(activity).withText("hello").withMedia(image).share();

//        推荐使用网络图片和资源文件的方式，平台兼容性更高。对于部分平台，分享的图片需要设置缩略图，缩略图的设置规则为：
//        UMImage thumb =  new UMImage(this, R.drawable.thumb);
//        image.setThumb(thumb);
//        用户设置的图片大小最好不要超过250k，缩略图不要超过18k，如果超过太多（最好不要分享1M以上的图片，压缩效率会很低），图片会被压缩。用户可以设置压缩的方式：
//        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//        压缩格式设置
//        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

//        分享链接可以使用UMWeb进行分享：
//        UMWeb  web = new UMWeb(Defaultcontent.url);
//        web.setTitle("This is music title");//标题
//        web.setThumb(thumb);  //缩略图
//        web.setDescription("my description");//描述
//        new ShareAction(ShareActivity.this)
//                .withMedia(web)
//                .share();
    }

    public UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
//            Toast.makeText(MyApplication.getInstance(), "成功了", Toast.LENGTH_LONG).show();
            LogUtil.e(TAG, "分享成功");
            onShareListener.successful();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(MyApplication.getInstance(), "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
            LogUtil.e(TAG, "分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(MyApplication.getInstance(), "取消了", Toast.LENGTH_LONG).show();

        }
    };

    public void initUmLogin(int status) {
        UMShareAPI.get(activity).setShareConfig(new UMShareConfig().isNeedAuthOnGetUserInfo(true));
        switch (status) {
            case 0:
                UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.QQ, authListener);
                break;
            case 1:
                UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }

    public void deleteOauth(int status) {
        switch (status) {
            case 1:
                UMShareAPI.get(activity).deleteOauth(activity, SHARE_MEDIA.QQ, authListener);
                break;
            case 2:
                UMShareAPI.get(activity).deleteOauth(activity, SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }

    //登陆
    private UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            LogUtil.e(TAG, "授权成功的回调  action : " + action);
//            LogUtil.e(TAG, "授权成功的回调  data.size() : " + data.size());
            if (onCompleteListener != null)
                onCompleteListener.comlete(data);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            LogUtil.e(TAG, "授权失败的回调 : " + t.getMessage());
            if (onNotReachListener != null)
                onNotReachListener.notReach();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            LogUtil.e(TAG, "授权取消的回调");
            if (onNotReachListener != null)
                onNotReachListener.notReach();
        }
    };


    public interface onCompleteListener {
        void comlete(Map<String, String> data);
    }

    public interface onShareListener {
        void successful();
    }

    public interface onNotReachListener {
        void notReach();
    }

    private onCompleteListener onCompleteListener;

    private onShareListener onShareListener;

    private onNotReachListener onNotReachListener;

    public void setOnCompleteListener(onCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public void setOnShareListener(onShareListener onShareListener) {
        this.onShareListener = onShareListener;
    }

    public void setOnNotReachListener(onNotReachListener onNotReachListener) {
        this.onNotReachListener = onNotReachListener;
    }

}
