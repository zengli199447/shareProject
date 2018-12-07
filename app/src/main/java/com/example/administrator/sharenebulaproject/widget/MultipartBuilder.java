package com.example.administrator.sharenebulaproject.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cjt2325.cameralibrary.util.FileUtil;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.ui.activity.PersonageActivity;
import com.example.administrator.sharenebulaproject.utils.DensityUtils;
import com.example.administrator.sharenebulaproject.utils.FileSizeUtil;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.NetUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/8/20.
 */

public class MultipartBuilder {

    private String TAG = "MultipartBuilder";

    private void getInputFile(File file) {
        String token = "";
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//        create a map of data to pass along
        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("token", tokenBody);
//        Call<ResponseBody> call = service.uploadFileWithPartMap(url, requestBody);
//        执行 call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.v("Upload", "success");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("Upload error:", t.getMessage());
//            }
//        });
    }

    public RequestBody getInputBody(File file) {
        //构建body
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("action", DataClass.IMAGE_SAVE_SET)
//                .addFormDataPart("userid", DataClass.USERID)
                .addFormDataPart("photo", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
//                .addFormDataPart("secondname", "磐石")
//                .addFormDataPart("province", "湖北")
//                .addFormDataPart("city", "武汉")
//                .addFormDataPart("sex", "男")
//                .addFormDataPart("job", "操盘手")
//                .addFormDataPart("moneyin", "1000万")
                .setType(MultipartBody.FORM)
                .build();
        return requestBody;
    }

    public void commitFile(final String fileUrl, final Context context) {
        LogUtil.e(TAG, "fileUrl : " + fileUrl);
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, File> fileparams = new HashMap<String, File>();
                    Map<String, String> textParams = new HashMap<String, String>();
                    // 创建一个URL对象
                    URL url = new URL(DataClass.BASE_URL);
                    textParams = new HashMap<String, String>();
                    fileparams = new HashMap<String, File>();
                    // 要上传的图片文件

                    BitmapFactory.Options options = FileSizeUtil.getBitmapOptions(fileUrl);
                    int screenMax = Math.max(DensityUtils.getWindowWidth((Activity) context),
                            DensityUtils.getWindowHeight((Activity) context));
                    int imgMax = Math.max(options.outWidth, options.outHeight);
                    int inSimpleSize = 1;
                    if (screenMax <= imgMax) {
                        inSimpleSize = Math.max(screenMax, imgMax) / Math.min(screenMax, imgMax);
                    }
                    String ss = FileSizeUtil.compressBitmap((Activity) context, fileUrl, Bitmap.CompressFormat.JPEG,
                            options.outWidth / inSimpleSize,
                            options.outHeight / inSimpleSize,
                            false);
                    File file = new File(ss);
                    fileparams.put("image", file);
                    // 利用HttpURLConnection对象从网络中获取网页数据
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // 设置连接超时（记得设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作）
                    conn.setConnectTimeout(5000);
                    // 设置允许输出（发送POST请求必须设置允许输出）
                    conn.setDoOutput(true);
                    // 设置使用POST的方式发送
                    conn.setRequestMethod("POST");
                    // 设置不使用缓存（容易出现问题）
                    conn.setUseCaches(false);
                    // 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
                    conn.setRequestProperty("ser-Agent", "Fiddler");
                    // 设置contentType
                    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + NetUtil.BOUNDARY);
                    OutputStream os = conn.getOutputStream();
                    DataOutputStream ds = new DataOutputStream(os);
                    NetUtil.writeStringParams(textParams, ds);
                    NetUtil.writeFileParams(fileparams, ds);
                    NetUtil.paramsEnd(ds);
                    // 对文件流操作完,要记得及时关闭
                    os.close();
                    // 服务器返回的响应吗
                    int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
                    // 对响应码进行判断
                    if (code == 200) {// 返回的响应码200,是成功
                        // 得到网络返回的输入流
                        InputStream is = conn.getInputStream();
                        String resultStr = NetUtil.readString(is);
                        RxBus.getDefault().post(new CommonEvent(EventCode.COMMIET_IMG, JsonParsing(resultStr)));
                        LogUtil.e(TAG, "resultStr : " + resultStr);
                    } else {
                        Log.e(TAG, "Throwable : " + "请求错误 code : " + code);
                    }
                } catch (Exception e) {
                    LogUtil.e(TAG, "Exception : " + e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

    private String JsonParsing(String json) {
        String photoUrl = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            // 服务端以字符串“1”作为操作成功标记
            if (jsonObject.optString("status").equals("1")) {
                // 用于拼接发布说说时用到的图片路径
                // 服务端返回的JsonObject对象中提取到图片的网络URL路径
                JSONObject object = jsonObject.getJSONObject("result");
                photoUrl = object.getString("src");
            } else {
                photoUrl = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoUrl;
    }

    public boolean JsonStatus(String json) {
        boolean status = false;
        try {
            JSONObject jsonObject = new JSONObject(json);
            LogUtil.e(TAG, "JsonStatus : " + jsonObject.get("status"));
            if (jsonObject.get("status").equals("1.0")) {
                status = true;
            } else {
                status = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }

    //下载图片
    public void downloadImg(final Context context, final String url) {
        final String fileName = FileSizeUtil.createFile(context.getPackageName());

        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                //下载图片的路径
//                String iPath = "http://e.hiphotos.baidu.com/image/pic/item/d0c8a786c9177f3eeb8bedb57ccf3bc79e3d56ce.jpg";
                String iPath = url;
                try {
                    //对资源链接
                    URL url = new URL(iPath);
                    //打开输入流
                    InputStream inputStream = url.openStream();
                    //对网上资源进行下载转换位图图片
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    //再一次打开
                    inputStream = url.openStream();
                    File file = new File(fileName + SystemUtil.getCurrentTimeText() + "user.jpg");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    int hasRead = 0;
                    while ((hasRead = inputStream.read()) != -1) {
                        fileOutputStream.write(hasRead);
                    }
                    fileOutputStream.close();
                    inputStream.close();
                    commitFile(file.getPath(), context);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    LogUtil.e(TAG, "MalformedURLException : " + e.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.e(TAG, "IOException : " + e.toString());
                }
            }
        });

    }
}
