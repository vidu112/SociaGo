package com.deeba.sociago;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText passwordfield,NIC;
    private ProgressDialog progressDialog;
    String driverId,companyId,actualDriverFname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        NIC=(EditText) findViewById(R.id.vehical_Licen_plate3);
        passwordfield =(EditText) findViewById(R.id.password);
    }
    public void Login(View view){
        final String Nic= NIC.getText().toString();
        final String password= passwordfield.getText().toString();
        progressDialog.setMessage("Checking szdsadadsmnnnm");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constants.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Log.i("",jsonObject.toString());
                            String status=jsonObject.getString("status").toString();
                            if(status.equals("true")){
                                Intent welcome = new Intent(getApplicationContext(), QR_NIC.class);

                                startActivity(welcome);

                            }else{
                                //  Toast.makeText(getApplicationContext(),"Fail", Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                String query="select * from driver where driverNic='"+Nic + "' AND driverPassword='"+password+"'";
                Log.i("queryloging",query);
                params.put("query",query);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
