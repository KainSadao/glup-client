package com.glup.client.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
    private SharedPreferences preferences;
    private String encryptedEmail;
    private String encryptedPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
        user.requestFocus();
        //getPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferences = this.getSharedPreferences(getString(R.string.ini_preferences), Context.MODE_PRIVATE);
        if(isLogged()){
            pd = new ProgressDialog(this);
            pd.setTitle("Iniciando sesión");
            pd.show();
            String emailString = preferences.getString("email", "null");
            String passString = preferences.getString("pass", "null");
            Glup.login(emailString, passString,
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
    }

    public void login(View v){
        pd = new ProgressDialog(this);
        pd.setTitle("Iniciando sesión");
        pd.show();
        encryptedEmail = Aes.encrypt(user.getText().toString().trim());
        encryptedPass = Aes.encrypt(pass.getText().toString().trim());
        Glup.login(encryptedEmail, encryptedPass,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        pd.dismiss();
                        try {
                            JSONObject responseJson = new JSONObject(response.toString());
                            String status = responseJson.getString("Status");
                            if(status.equals("ok")){
                                setPreferences();
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

    private void setPreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", encryptedEmail);
        editor.putString("pass", encryptedPass);
        editor.apply();
    }

    private boolean isLogged(){
        return preferences.getBoolean("isLogged", false); //Si no encuentra muestra el segundo parámetro
    }
}
