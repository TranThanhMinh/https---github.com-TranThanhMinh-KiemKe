package vn.com.fpt.frt_minventory.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.com.fpt.frt_minventory.Model.PropertiesArea_Calendar;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by ADMIN on 12/12/2017.
 */

public class   AdapterArea_Calendar extends ArrayAdapter<PropertiesArea_Calendar> {
    List<PropertiesArea_Calendar> myArray;
    List<PropertiesArea_Calendar> Filter_Vung_myArray;
    Context context;
    CheckBox check;


    public interface _Onclick{
        void click(String _id,String _name);
    }

    public AdapterArea_Calendar(@NonNull Context context, List<PropertiesArea_Calendar> myArrArea) {
        super(context, -1, myArrArea);
        this.context = context;
        this.Filter_Vung_myArray = myArrArea;


    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        final PropertiesArea_Calendar chill = Filter_Vung_myArray.get(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_area_activity, null);
        }
            check = (CheckBox) convertView.findViewById(R.id.checkbox_Area);
            if(chill.isCheck()==true){
                check.setChecked(true);
                Log.e("name1",chill.getNAME());
            }
            else {
                Log.e("name2",chill.getNAME());
                check.setChecked(false);
            }
            check.setText(chill.getNAME().toString());
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(chill.isCheck()==true){
                        Filter_Vung_myArray.get(position).setCheck(false);
                    }else {
                        Filter_Vung_myArray.get(position).setCheck(true);
//
                    }
                    notifyDataSetChanged();



                }
            });

        return convertView;//trả về View này, tức là trả luôn
        //về các thông số mới mà ta vừa thay đổi
    }




}
