package com.example.administrator.sharenebulaproject.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.model.bean.PickerJsonBean;
import com.example.administrator.sharenebulaproject.model.bean.AssetsJsonFileReader;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/15.
 * PickerView 提供
 */

public class PickerViewBuilder {

    private Context context;

    private ArrayList<PickerJsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ArrayList<String> GenderList = new ArrayList<>();
    public SelectListener selectListener;

    public PickerViewBuilder(Context context) {
        this.context = context;
    }

    public void showOptionsPickerView(final TextView view) {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                String text = options1Items.get(options1).getPickerViewText() + options2Items.get(options1).get(options2) + options3Items.get(options1).get(options2).get(options3);
                String text = new StringBuffer().append(options1Items.get(options1).getPickerViewText()).append(" - ").append(options2Items.get(options1).get(options2)).toString();
                view.setText(text);
            }
        }).setTitleText("")
                .setCancelText("")//取消按钮文字
                .setTitleBgColor(context.getResources().getColor(R.color.white))
                .setSubmitColor(context.getResources().getColor(R.color.black_overlay))
                .setCancelColor(context.getResources().getColor(R.color.white))
                .setDividerColor(context.getResources().getColor(R.color.gray_light))
                .setTextColorCenter(context.getResources().getColor(R.color.black_overlay))
                .setContentTextSize(16)
                .setOutSideCancelable(true)
                .build();
          /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    //时间选择
    public void showTimePickView(final TextView view, final boolean status) {
        TimePickerView timePickerView = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                if (df.format(new Date()).equals(df.format(date)) && status){
                    view.setText(context.getString(R.string.today));
                }else {
                    view.setText(df.format(date));
                }
                if (selectListener != null)
                    selectListener.selectListener(df.format(date));
            }
        }).setCancelText("")//取消按钮文字
                .setContentSize(16)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setTitleBgColor(context.getResources().getColor(R.color.white))
                .setSubmitColor(context.getResources().getColor(R.color.black_overlay))
                .setCancelColor(context.getResources().getColor(R.color.white))
                .setDividerColor(context.getResources().getColor(R.color.gray_light))
                .setTextColorCenter(context.getResources().getColor(R.color.black_overlay))
                .setOutSideCancelable(true)
                .setRange(Calendar.getInstance().get(Calendar.YEAR) - 100, Calendar.getInstance().get(Calendar.YEAR) + 100)//默认是1900-2100年
                .setDate(Calendar.getInstance())// 默认是系统时间*/
                .setLabel("", "", "", "", "", "")
                .build();
        timePickerView.show();
    }

    public void GenderPickView(final TextView view) {
        GenderList.clear();
        GenderList.add("男");
        GenderList.add("女");
        OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = GenderList.get(options1);
                view.setText(tx);
            }
        }).setTitleText("")
                .setCancelText("")//取消按钮文字
                .setTitleBgColor(context.getResources().getColor(R.color.white))
                .setSubmitColor(context.getResources().getColor(R.color.black_overlay))
                .setCancelColor(context.getResources().getColor(R.color.white))
                .setDividerColor(context.getResources().getColor(R.color.gray_light))
                .setTextColorCenter(context.getResources().getColor(R.color.black_overlay))
                .setContentTextSize(16)
                .setOutSideCancelable(true).build();
        optionsPickerView.setPicker(GenderList);
        optionsPickerView.show();
    }


    public void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String JsonData = AssetsJsonFileReader.getJson(context, "province_data.json");
        ArrayList<PickerJsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<PickerJsonBean> parseData(String result) {//Gson 解析
        ArrayList<PickerJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                PickerJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), PickerJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    public interface SelectListener {
        void selectListener(String content);
    }

    public void initSelectListener(SelectListener selectListener) {
        this.selectListener = selectListener;
    }

}
