package vn.com.fpt.frt_minventory.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import vn.com.fpt.frt_minventory.Model.PropertiesArea_Calendar;
import vn.com.fpt.frt_minventory.Model.Shop;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by minhtran on 12/29/17.
 */

public class AdapterCalendar extends RecyclerView.Adapter<AdapterCalendar.MyViewHolder> {
    private Context context;
    private List<Shop> Filter_Vung_myArray;

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    private Check check;

    public interface Check {
        void click(int i, boolean ck);
    }

    public AdapterCalendar(Context context, List<Shop> myArrArea, Check check) {

        this.context = context;
        this.Filter_Vung_myArray = myArrArea;
        this.check = check;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_area_activity, null);
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Shop chill = Filter_Vung_myArray.get(position);
        if (chill.isCheck())
            holder.check.setChecked(true);
        else
            holder.check.setChecked(false);
        //holder.tvName.setText(chill.getNAME());
        holder.check.setText(chill.getNAME().toString());
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chill.isCheck() == false) {
                    Filter_Vung_myArray.get(position).setCheck(true);
                    // check.click(position, true);
                } else {
                    Filter_Vung_myArray.get(position).setCheck(false);
                    //  check.click(position, false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return Filter_Vung_myArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox check;
        //private TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            check = (CheckBox) itemView.findViewById(R.id.checkbox_Area);
            //tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }
}
