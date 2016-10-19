package com.glup.client.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.glup.client.Api.Glup;
import com.glup.client.R;
import com.glup.client.Utils.Aes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kain on 02/10/2016.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
    }

    public void login(View v){
        pd = new ProgressDialog(this);
        pd.setTitle("Iniciando sesión");
        pd.show();
        Glup.login(Aes.encrypt(user.getText().toString().trim()), Aes.encrypt(pass.getText().toString().trim()),
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        pd.dismiss();
                        try {
                            JSONObject responseJson = new JSONObject(response.toString());
                            String status = responseJson.getString("Status");
                            if(status.equals("ok")){
                                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(main);
                                finish();
                            }else if(status.equals("error")){
                                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                            }
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void registerUser(View v){
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }
}
