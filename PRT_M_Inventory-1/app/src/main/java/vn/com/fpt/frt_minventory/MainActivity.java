package vn.com.fpt.frt_minventory;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.fpt.frt_minventory.Adapter.AdapterChooseShop;
import vn.com.fpt.frt_minventory.Model.DataUserLogin;
import vn.com.fpt.frt_minventory.Model.Shop;
import vn.com.fpt.frt_minventory.Model.ShopByUser;
import vn.com.fpt.frt_minventory.Model.upload;
import vn.com.fpt.frt_minventory.Services.KeyApi;
import vn.com.fpt.frt_minventory.Services.Url;
import vn.com.fpt.frt_minventory.Views.Calendar_Inventory;
import vn.com.fpt.frt_minventory.Views.CameraActivity;
import vn.com.fpt.frt_minventory.Views.InventoriedList;
import vn.com.fpt.frt_minventory.Views.InventoryOnDay;
import vn.com.fpt.frt_minventory.Views.LoginActivity;
import vn.com.fpt.frt_minventory.Views.SearchJob;
import vn.com.fpt.frt_minventory.Views.UnlockInventory;

public class MainActivity extends AppCompatActivity implements AdapterChooseShop.Choose {
    ImageView img1, img2, logout, img3, img4, img5;
    TextView txt1, txt2, txt3;

    RecyclerView lvChooseShop;
    List<Shop> lvRegion = new ArrayList<>();
    List<Shop> lvDomain = new ArrayList<>();
    List<Shop> lvArea = new ArrayList<>();
    List<Shop> lvShop = new ArrayList<>();
    List<Shop> list_search = new ArrayList<>();
    Dialog dialog;
    EditText tvSearch;
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView) findViewById(R.id.img_Inventory_OnDay);
        img2 = (ImageView) findViewById(R.id.img_Invetoried_List);
        img3 = (ImageView) findViewById(R.id.img_unlock);
        img4 = (ImageView) findViewById(R.id.img_calendar);
        img5 = (ImageView) findViewById(R.id.img_search_job);
        logout = (ImageView) findViewById(R.id.img_logout);
        txt1 = (TextView) findViewById(R.id.txt_User_Login);
        txt2 = (TextView) findViewById(R.id.txt_Code_Main);
        txt3 = (TextView) findViewById(R.id.txt_JobTitle);
        final Intent intent = getIntent();

        String EmployeeName = intent.getStringExtra("EmployeeName");
        String ShopName = intent.getStringExtra("ShopName");
        String JobTitleb = intent.getStringExtra("JobTitle_name");
        lvRegion = (List<Shop>) intent.getSerializableExtra("lvRegion");
        lvDomain = (List<Shop>) intent.getSerializableExtra("lvDomain");
        lvArea = (List<Shop>) intent.getSerializableExtra("lvArea");
        lvShop = (List<Shop>) intent.getSerializableExtra("lvShop");
        txt1.setText(EmployeeName);
        txt2.setText(ShopName);
        txt3.setText(JobTitleb);
        if (DataUserLogin.Shopcode == null) {
            dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.activity_choose_shop);
            dialog.setCancelable(false);
            lvChooseShop = (RecyclerView) dialog.findViewById(R.id.lvChooseShop);
            tvSearch = (EditText) dialog.findViewById(R.id.tvSearch);
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            lvChooseShop.setHasFixedSize(true);
            lvChooseShop.setLayoutManager(manager);

            AdapterChooseShop adapter = new AdapterChooseShop(MainActivity.this, lvShop, MainActivity.this);
            lvChooseShop.setAdapter(adapter);
            tvSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(final Editable editable) {
                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    doSearch(editable.toString());
                                }
                            });
                        }
                    }, 1000 * 1);

                }
            });

            tvSearch.requestFocus();



            dialog.show();
        }
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InventoryOnDay.class);
                startActivity(intent);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InventoriedList.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UnlockInventory.class);
                startActivity(intent);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calendar_Inventory.class);
                intent.putExtra("lvRegion", (Serializable) lvRegion);
                intent.putExtra("lvDomain", (Serializable) lvDomain);
                intent.putExtra("lvArea", (Serializable) lvArea);
                intent.putExtra("lvShop", (Serializable) lvShop);
                startActivity(intent);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchJob.class);
                startActivity(intent);
            }
        });
    }
    private void doSearch(String search) {
        list_search = new ArrayList<>();
        if (search != null && search.length() > 0) {
            for (Shop companyExist : lvShop) {
                if ((companyExist.getNAME().toLowerCase()).contains((search).toLowerCase())) {
                    list_search.add(companyExist);
                }
            }
            AdapterChooseShop adapter = new AdapterChooseShop(MainActivity.this, list_search, MainActivity.this);
            lvChooseShop.setAdapter(adapter);
        } else {
            list_search = new ArrayList<>();
            AdapterChooseShop adapter = new AdapterChooseShop(MainActivity.this, lvShop, MainActivity.this);
            lvChooseShop.setAdapter(adapter);
        }
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

    }

    @Override
    public void click(String id, String name) {
        String[] shopcode = id.split("_");
        DataUserLogin.Shopcode = shopcode[1];
        txt2.setText(name);
        dialog.cancel();
    }
}
