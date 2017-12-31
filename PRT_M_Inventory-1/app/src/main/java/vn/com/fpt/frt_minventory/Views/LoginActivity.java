package vn.com.fpt.frt_minventory.Views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.fpt.frt_minventory.Libs.UIHelper;
import vn.com.fpt.frt_minventory.MainActivity;
import vn.com.fpt.frt_minventory.Model.DataUserLogin;
import vn.com.fpt.frt_minventory.Model.LoginResult;
import vn.com.fpt.frt_minventory.Model.Shop;
import vn.com.fpt.frt_minventory.Model.ShopByUser;
import vn.com.fpt.frt_minventory.Model.upload;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Services.APIService;
import vn.com.fpt.frt_minventory.Services.KeyApi;
import vn.com.fpt.frt_minventory.Services.Url;

public class LoginActivity extends AppCompatActivity {

    EditText edtUs, edtPass;
    Button btn_login;
    Context context;
    public static String user;
    public static String userName;
    private Retrofit retrofit;
    List<Shop> lvRegion = new ArrayList<>();
    List<Shop> lvDomain = new ArrayList<>();
    List<Shop> lvArea = new ArrayList<>();
    List<Shop> lvShop = new ArrayList<>();
    LoginResult results1;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        retrofit = getConnect();
        edtUs = (EditText) findViewById(R.id.edt_User);
        edtPass = (EditText) findViewById(R.id.edt_Pass);
        btn_login = (Button) findViewById(R.id.btn_submit);
        edtUs.setText(DataUserLogin.Username);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        edtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

    }

    public void getShop() {
        KeyApi api = retrofit.create(KeyApi.class);
        upload up = new upload();
        up.setUserCode(DataUserLogin.Username);
        up.setCodeCountry("");
        Call<ShopByUser> call = api.InvetoryGetShopByUser(up);
        call.enqueue(new Callback<ShopByUser>() {
            @Override
            public void onResponse(Call<ShopByUser> call, Response<ShopByUser> response) {
                List<Shop> list = response.body().getShopByUser();
                if (list != null && list.size() > 0) {
                    for (Shop shop : list) {
                        if (shop.getRootID() != null) {
                            if (shop.getRootID().equals("00001")) {
                                lvRegion.add(shop);
                                Log.e("Region", shop.getNAME());
                            }
                        }
                    }
                    for (Shop shop : list) {
                        if (shop.getRootID() != null) {
                            for (int i = 0; i < lvRegion.size(); i++) {
                                if (shop.getRootID().equals(lvRegion.get(i).getID())) {
                                    lvDomain.add(shop);
                                    i = lvRegion.size();
                                }
                            }
                        }
                    }

                    for (Shop shop : list) {
                        if (shop.getRootID() != null) {
                            for (int i = 0; i < lvDomain.size(); i++) {
                                if (shop.getRootID().equals(lvDomain.get(i).getID())) {
                                    lvArea.add(shop);
                                    i = lvDomain.size();
                                }
                            }
                        }
                    }
                    for (Shop shop : list) {
                        if (shop.getRootID() != null) {
                            for (int i = 0; i < lvArea.size(); i++) {
                                if (shop.getRootID().equals(lvArea.get(i).getID())) {
                                    lvShop.add(shop);
                                    // Log.e("Shop", shop.getNAME());

                                    i = lvArea.size();
                                }
                            }
                        }
                    }
                    /** Login success */
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //Authentication.saveLoginInfo(LoginActivity.this,results);
                    intent.putExtra("EmployeeName", results1.getEmployeeName());
                    intent.putExtra("ShopName", results1.getShopName());
                    intent.putExtra("JobTitle_name", results1.getJobTitle_name());
                    intent.putExtra("lvRegion", (Serializable) lvRegion);
                    intent.putExtra("lvDomain", (Serializable) lvDomain);
                    intent.putExtra("lvArea", (Serializable) lvArea);
                    intent.putExtra("lvShop", (Serializable) lvShop);
                    DataUserLogin.Username = edtUs.getText().toString();
                    DataUserLogin.EmployeeName = results1.getEmployeeName();
                    startActivity(intent);
                    dialog.dismiss();
                    finish();


                }
            }

            @Override
            public void onFailure(Call<ShopByUser> call, Throwable t) {
                Log.e("Loi", t.toString());
            }
        });
    }

    public void login() {
        final String username = edtUs.getText().toString();
        final String password = edtPass.getText().toString();
        user = edtUs.getText().toString();
        // final String passwordCrypted = Authentication.encryptPassword(password);

        if (username.length() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Error!");
            alertDialog.setMessage("Vui lòng nhập Tên đăng nhập");
            alertDialog.setIcon(R.drawable.ic_dialog_close_light);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }
//          Kiem tra password
//        if (password.length() == 0) {
//            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
//            alertDialog.setTitle("Error!");
//            alertDialog.setMessage("Vui lòng nhập Password");
//            alertDialog.setIcon(R.drawable.ic_dialog_close_light);
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();
//           return;
//        }

        /** Process login */
        dialog = ProgressDialog.show(context, "Xin chờ", "Đang xử lý đăng nhập....", true, false);
        dialog.setCancelable(false);

        new AsyncTask<String, Void, LoginResult>() {
            @Override
            protected LoginResult doInBackground(String... params) {
                LoginResult results = APIService.getLogin(username, password);
                return results;
            }

            @Override
            protected void onPostExecute(LoginResult results) {


                if (results != null) {
                    if (results.getResult() == 0) {
                        results1 = results;
                        DataUserLogin.Shopcode = results.getShopCode();
                        userName = results.getEmployeeName();
                        DataUserLogin.Username = edtUs.getText().toString();
                        DataUserLogin.EmployeeName = results1.getEmployeeName();
                        if (results.getShopCode() == null) {
                            getShop();
                        } else {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //Authentication.saveLoginInfo(LoginActivity.this,results);
                            intent.putExtra("EmployeeName", results1.getEmployeeName());
                            intent.putExtra("ShopName", results1.getShopName());
                            intent.putExtra("JobTitle_name", results1.getJobTitle_name());
                            intent.putExtra("lvRegion", (Serializable) lvRegion);
                            intent.putExtra("lvDomain", (Serializable) lvDomain);
                            intent.putExtra("lvArea", (Serializable) lvArea);
                            intent.putExtra("lvShop", (Serializable) lvShop);

                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }


                    }
                } else {
                    UIHelper.showAlertDialog(context, "Đăng nhập", "Máy chủ không phản hồi!", "Đóng", R.drawable.ic_dialog_close_light);
                }
            }
        }.execute();
    }
}
