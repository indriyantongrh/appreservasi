package com.universedeveloper.mybookingfieldtrip.PaketSingkong;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.universedeveloper.mybookingfieldtrip.Api.JSONResponse;
import com.universedeveloper.mybookingfieldtrip.Api.ModelProfilUser;
import com.universedeveloper.mybookingfieldtrip.Api.RequestInterface;
import com.universedeveloper.mybookingfieldtrip.LoginRegister.LoginUser;
import com.universedeveloper.mybookingfieldtrip.R;
import com.universedeveloper.mybookingfieldtrip.Utility.AppController;
import com.universedeveloper.mybookingfieldtrip.Utility.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import id.voela.iduangs.IDUang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InputDataCustomers extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = InputDataCustomers.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String nama_sekolah, tanggal_booking, jumlah_siswa, jumlah_guru, jumlah_supir, total_harga, jumlah_dp, jumlah_kekurangan, keterangan, id_paket, jenis_paket, status_transaksi;
    String id, String_id_transaksi;
    SharedPreferences sharedpreferences;
    private ArrayList<ModelProfilUser> mArrayListUser;
    String tag_json_obj = "json_obj_req";
    JSONArray string_json = null;
    JSONParser jsonParser = new JSONParser();
    int success;

    Intent intent;
    public final static String TAG_ID_USER = "id";
    public final static String TAG_ID_EMAIL = "email";

    EditText txttanggalbooking, txtnamalengkap, txtnomorhp, txtnamasd, txtjumlahsiswa, txtjumlahguru, txtjumlahsupir ;
    TextView jumlahharga;
    RadioGroup radiogroup;
    RadioButton radioButton,radiobayarfull, radiobayardp;
    ImageButton btntanggal;
    Button btncekharga, btnbayar;
    private int mYear, mMonth, mDay;
    ProgressDialog pDialog;

    public static final String BASE_URL = "http://universedeveloper.com/gudangandroid/";
    private static final String url_ambil_id_transaksi = "http://gudangandroid.universedeveloper.com/fieldtripkandri/User/cek_idtransaksi.php";

    ConnectivityManager conMgr;
    private String url = "http://universedeveloper.com/gudangandroid/fieldtripkandri/User/inputdata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data_customers);

        sharedpreferences = getSharedPreferences(LoginUser.my_shared_preferences, Context.MODE_PRIVATE);
        id = sharedpreferences.getString("id", "0");
        Toast.makeText(this, "ini id ke-"+ id, Toast.LENGTH_SHORT).show();



        btntanggal = findViewById(R.id.btntanggal);
        txttanggalbooking = findViewById(R.id.txttanggalbooking);
        txtnamalengkap = findViewById(R.id.txtnamalengkap);
        ////txtnamalengkap.setEnabled(false);
        txtnomorhp = findViewById(R.id.txtnomorhp);
        ///txtnomorhp.setEnabled(false);
        txtnamasd = findViewById(R.id.txtnamasd);
        txtjumlahsiswa = findViewById(R.id.txtjumlahsiswa);
        txtjumlahguru = findViewById(R.id.txtjumlahguru);
        txtjumlahsupir = findViewById(R.id.txtjumlahsupir);

        jumlahharga = findViewById(R.id.jumlahharga);

        btncekharga = findViewById(R.id.btncekharga);

        radiobayardp = findViewById(R.id.radiobayardp);

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);

      /*  radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radiobayardp:
                        // do operations specific to this selection
                        Locale localeID = new Locale("in", "ID");
                        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                        String b1=jumlahharga.getText().toString();
                        double bayardp = Double.parseDouble(b1);
                        double result = bayardp / 2 ;
                        ///jumlahharga.setText("Rp. " +Double.toString(result));
                        jumlahharga.setText(formatRupiah.format(result));
                        break;
*//*                    case R.id.radio1:
                        // do operations specific to this selection
                        break;
                    case R.id.radio2:
                        // do operations specific to this selection
                        break;*//*
                }
            }
        });
*/
        btnbayar = findViewById(R.id.btnbayar);
        btnbayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nama_lengkap = txtnamalengkap.getText().toString();;
                String nomor_hp = txtnomorhp.getText().toString();
                String nama_sekolah = txtnamasd.getText().toString();
                String tanggal_booking = txttanggalbooking.getText().toString();
                String jumlah_siswa = txtjumlahsiswa.getText().toString();
                String jumlah_guru = txtjumlahguru.getText().toString();
                String jumlah_supir = txtjumlahsupir.getText().toString();
                String total_harga = jumlahharga.getText().toString();
/*                String jumlah_dp = txtnomorhp.getText().toString();
                String jumlah_kekurangan= txtnomorhp.getText().toString();*/
                /*String keterangan= radioButton.getText().toString();*/


           /*     if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    PostDataCustomers(nama_lengkap, nomor_hp, nama_sekolah,tanggal_booking , jumlah_siswa,jumlah_guru,jumlah_supir, total_harga,  keterangan);
                } else {
                    Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                }
*/
                PostDataCustomers(nama_lengkap, nomor_hp, nama_sekolah,tanggal_booking , jumlah_siswa,jumlah_guru,jumlah_supir, total_harga,  keterangan);
                Checkbtnkeputusan(view);
               /// new AmbilIdTransaksiTerakhir().execute();
            }
        });

        ambilProfilUser();

        btntanggal.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btntanggal:

                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                txttanggalbooking.setText( year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );

                            }
                        },mYear , mMonth, mDay);
                datePickerDialog.show();
                break;
        }

        btncekharga = findViewById(R.id.btncekharga);
        btncekharga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((txtjumlahsiswa.getText().length()>0)  )

                {

                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                    double jumlahsiswa = Double.parseDouble(txtjumlahsiswa.getText().toString());
                    double result = jumlahsiswa * 80000;
                    ///jumlahharga.setText("Rp. " +Double.toString(result));
                    jumlahharga.setText(formatRupiah.format(result));


                }


                else {
                    Toast toast = Toast.makeText(InputDataCustomers.this, "Mohon masukkan Jumlah Siswa dengan benar", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        });
    }




    public void ambilProfilUser(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getProfilUser(id);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();

                mArrayListUser = new ArrayList<>(Arrays.asList(jsonResponse.getDatauser()));
                String nama_lengkap = mArrayListUser.get(0).getNama_user();
                String nomor_hp = mArrayListUser.get(0).getTelepon_user();


                txtnamalengkap.setText(nama_lengkap);
                txtnomorhp.setText(nomor_hp);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }


    public void Checkbtnkeputusan(View v){
        int radioid =  radiogroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioid);


        /*Toast.makeText(this,"Pembayaran" + radioButton.getText(), Toast.LENGTH_SHORT).show();*/
    }

    private void PostDataCustomers(final String nama_lengkap,final String nomor_hp, final String nama_sekolah,final String tanggal_booking ,final String jumlah_siswa,final String jumlah_guru,final String jumlah_supir,final String total_harga,final String  keterangan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Silahkan Tunggu ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Registrasi berhasil!", jObj.toString());

                        goToPayment();
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

   /*                     txtnamalengkap.setText("");
                        ///  txtusername.setText("");
                        txtemail.setText("");
                        txtpassword.setText("");
                        /// txtconfirmpassword.setText("");
                        txttelepon.setText("");*/

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("nama_lengkap", nama_lengkap);
                params.put("nomor_hp", nomor_hp);
                params.put("nama_sekolah", txtnamasd.getText().toString());
                params.put("tanggal_booking", txttanggalbooking.getText().toString());
                params.put("jumlah_siswa", txtjumlahsiswa.getText().toString());
                params.put("jumlah_guru", txtjumlahguru.getText().toString());
                params.put("jumlah_supir", txtjumlahsupir.getText().toString());
                params.put("total_harga", jumlahharga.getText().toString());
   /*             params.put("jumlah_dp", jumlah_dp);
                params.put("jumlah_kekurangan", jumlah_kekurangan);*/
                params.put("keterangan", radioButton.getText().toString());
                params.put("id_paket", "01");
                params.put("jenis_paket", "paket singkong");
                params.put("status_transaksi", "Belum Lunas");

                return params;
            }

        };

        // Adding request to request queue

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        ///AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void goToPayment(){

        intent = new Intent(InputDataCustomers.this, PembayaranSingkong.class);
        finish();
        startActivity(intent);
    }

    class AmbilIdTransaksiTerakhir extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(InputDataCustomers.this);
            pDialog.setMessage("Membuat pesanan ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            try {
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                JSONObject json = jsonParser.makeHttpRequest(url_ambil_id_transaksi, "GET", params1);
                string_json = json.getJSONArray("nomor");

                runOnUiThread(new Runnable() {
                    public void run() {


                        try {
                            // ambil objek member pertama dari JSON Array
                            JSONObject ar = string_json.getJSONObject(0);
                            String nomor_id_d = ar.getString("id");
                            Integer tambah = Integer.parseInt(nomor_id_d) + 1;
                            String fix = getDateId() + "-" + String.valueOf(tambah);
                            String_id_transaksi = getDateId() + "-" + String.valueOf(tambah);

                            if (String_id_transaksi != null) {

                            } else {

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            //simpanTransaksi();
            pDialog.dismiss();
        }
    }


    //untuk transaksi terakhir
    private String getDateId() {
        Date current = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("ddMMyy");
        String dateString = frmt.format(current);
        return dateString;
    }
}
