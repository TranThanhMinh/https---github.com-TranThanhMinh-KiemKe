package vn.com.fpt.frt_minventory.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import vn.com.fpt.frt_minventory.Adapter.AdapterArea_Calendar;
import vn.com.fpt.frt_minventory.Adapter.AdapterCalendar;
import vn.com.fpt.frt_minventory.Model.PropertiesArea_Calendar;
import vn.com.fpt.frt_minventory.Model.Shop;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Services.APIService;

/**
 * Created by ADMIN on 12/12/2017.
 */

public class SelectAreaCalendar extends Activity implements AdapterCalendar.Check{
    RecyclerView listView;
    Context context = this;
    TextView txt_check_all;
    String _name = "";
    String _id = "";
    int fType;
    Button btn_Vung;
    AdapterCalendar adapterArea_calendar;
    ArrayList<Shop> arr_propertiesArea_calendar = new ArrayList<>();
    List<PropertiesArea_Calendar> lvCheck = new ArrayList<>();
    List<Shop> lv = new ArrayList<>();
    String fID = "";
    boolean checkAll = false;
    boolean check = true;
    String frootID = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        Intent intent = getIntent();
        lv = (List<Shop>) intent.getSerializableExtra("lv");
        frootID = (String) intent.getSerializableExtra("rootID");
        Log.e("frootID", frootID);
        for (Shop shop : lv) {
            // Log.e("check.getID",shop.getID());
            // Log.e("check.getRootID", shop.getRootID());
            String[] lvID = frootID.split(",");
            for (int i = 0; i < lvID.length; i++) {
                if (shop.getRootID().equals(lvID[i])) {
                    Shop pro = new Shop();
                    pro.setCheck(shop.isCheck());
                    pro.setID(shop.getID());
                    pro.setNAME(shop.getNAME());
                    pro.setRootID(shop.getRootID());
                    arr_propertiesArea_calendar.add(pro);
                    i = lvID.length;
                } else if (frootID == "00001") {
                    Shop pro = new Shop();
                    pro.setCheck(shop.isCheck());
                    pro.setID(shop.getID());
                    pro.setNAME(shop.getNAME());
                    pro.setRootID(shop.getRootID());
                    arr_propertiesArea_calendar.add(pro);
                    i = lvID.length;
                }


            }


        }
        listView = (RecyclerView) findViewById(R.id.lv_Area_Calendar);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(manager);

        txt_check_all = (TextView) findViewById(R.id.txt_check_all_Area);
        btn_Vung = (Button) findViewById(R.id.btn_vung);

        txt_check_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAll == false) {
                    for (Shop check : arr_propertiesArea_calendar) {
                        check.setCheck(true);
                        _name += check.getNAME() + ", ";
                        _id += check.getID() + ",";
                    }
                    checkAll = true;
                } else {
                    for (Shop check : arr_propertiesArea_calendar) {
                        check.setCheck(false);
                        _name = "";
                        _id = "";
                    }
                    checkAll = false;
                }
                adapterArea_calendar = new AdapterCalendar(context, arr_propertiesArea_calendar,SelectAreaCalendar.this);
                listView.setAdapter(adapterArea_calendar);
            }
        });
        if(check ==true) {
            adapterArea_calendar = new AdapterCalendar(context, arr_propertiesArea_calendar, SelectAreaCalendar.this);
            listView.setAdapter(adapterArea_calendar);
        }
        btn_Vung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Shop> list = new ArrayList<>();
                for (Shop pro : arr_propertiesArea_calendar) {
                    if (pro.isCheck() == true) {
                        list.add(pro);
                    }
                }
                Intent intent = getIntent();
                intent.putExtra("Name", _name.length() > 0 ? _name.substring(0, _name.length() - 2) : ""); // xóa dấu ", " ở cuối tên
                intent.putExtra("Type", fType);
                intent.putExtra("ID", _id);
                //  intent.putExtra("myBundle", bundle);
                intent.putExtra("lv", (Serializable) list);
                setResult(Calendar_Inventory.AREA_CODE, intent);
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //SelectArea();
    }

    protected void SelectArea() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Xin chờ", "Đang tải thông tin...", true, false);
        new AsyncTask<Void, Void, List<PropertiesArea_Calendar>>() {
            @Override
            protected List<PropertiesArea_Calendar> doInBackground(Void... params) {
                try {

                    List<PropertiesArea_Calendar> propertiesArea_calendars = APIService.getPropertiesArea_calendars();
                    return propertiesArea_calendars;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(List<PropertiesArea_Calendar> propertiesArea_calendars) {
                arr_propertiesArea_calendar.clear();
                progressDialog.dismiss();
                if (propertiesArea_calendars != null) {
                    if (propertiesArea_calendars.size() == 0) {
                        Toasty.warning(SelectAreaCalendar.this, "Danh sách trống!", 1).show();
                    } else {
                     //   arr_propertiesArea_calendar.addAll(propertiesArea_calendars);
                    }
                    if (fID != null && fID.length() > 0) {
                        String[] lvID = fID.split(",");
                        ArrayList<PropertiesArea_Calendar> arr = new ArrayList<>();
                        for (Shop check : arr_propertiesArea_calendar) {

                            for (int i = 0; i < lvID.length; i++) {
                                if (check.getID().equals(lvID[i])) {
                                    check.setCheck(true);
                                    i = lvID.length;
                                } else {
                                    check.setCheck(false);
                                }

                            }
                            //arr.add(check);
                        }
                        //  adapterArea_calendar = new AdapterArea_Calendar(context, arr, SelectAreaCalendar.this);
                        // listView.setAdapter(adapterArea_calendar);
                    } else {
                        // adapterArea_calendar = new AdapterArea_Calendar(context, arr_propertiesArea_calendar, SelectAreaCalendar.this);
                        // listView.setAdapter(adapterArea_calendar);
                    }
                    //  adapterArea_calendar.filter(frootID);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SelectAreaCalendar.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Kết nối internet bị gián đoạn");
                    alertDialog.setIcon(R.drawable.ic_dialog_close_light);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void SetIDSelected(String l_ID) {
        List<String> l = Arrays.asList(l_ID.split(","));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("id", _id);
        Log.d("name", _name);
        Intent intent = getIntent();
        intent.putExtra("Name", _name.length() > 0 ? _name.substring(0, _name.length() - 2) : ""); // xóa dấu ", " ở cuối tên
        intent.putExtra("Type", fType);
        intent.putExtra("ID", _id);
        //  intent.putExtra("myBundle", bundle);
        intent.putExtra("lv", (Serializable) arr_propertiesArea_calendar);
        setResult(Calendar_Inventory.AREA_CODE, intent);
        finish();
    }

    @Override
    public void click(int i, boolean ck) {
        arr_propertiesArea_calendar.get(i).setCheck(ck);
    }
}
