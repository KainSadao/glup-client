package com.glup.client.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.glup.client.Api.Glup;
import com.glup.client.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kain on 08/10/2016.
 */

public class RegisterActivity extends AppCompatActivity{

    private EditText nameText;
    private EditText emailText;
    private EditText passText;
    private EditText pass2Text;
    private EditText celText;
    private CheckedTextView termsCheck;
    private ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameText = (EditText)findViewById(R.id.name_reg);
        emailText = (EditText)findViewById(R.id.email_reg);
        passText = (EditText)findViewById(R.id.pass_reg);
        pass2Text = (EditText)findViewById(R.id.pass2_reg);
        celText = (EditText)findViewById(R.id.cel_reg);
        termsCheck = (CheckedTextView)findViewById(R.id.terms_reg);
    }

    private boolean isChecked(){
        boolean name = !nameText.getText().toString().isEmpty();
        boolean email = !emailText.getText().toString().isEmpty();
        boolean pass = !passText.getText().toString().isEmpty();
        boolean pass2 = !pass2Text.getText().toString().isEmpty();
        boolean cel = !celText.getText().toString().isEmpty();
        boolean terms = termsCheck.isChecked();
        if(name && email && pass && pass2 && cel && terms){
            return true;
        }
        return false;
    }

    public void registerNewUser(View v){
        if(passText.getText().toString().trim().equals(pass2Text.getText().toString().trim())){
            if(isChecked()) {
                pd = new ProgressDialog(this);
                pd.setTitle("Iniciando sesión");
                pd.show();
                Glup.registerUser(nameText.getText().toString().trim(), emailText.getText().toString().trim(),
                        passText.getText().toString().trim(), celText.getText().toString().trim(), new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {
                                pd.dismiss();
                                try {
                                    JSONObject responseJson = new JSONObject(response.toString());
                                    String status = responseJson.getString("Status");
                                    if(status.equals("ok")){
                                        Toast.makeText(RegisterActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else if(status.equals("error")){
                                        Toast.makeText(RegisterActivity.this, "Hubo un error al crear el usuario, por favor intente más tarde", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
            }else{
                Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "La contraseña debe coincidir", Toast.LENGTH_SHORT).show();
        }

    }

    public void termsToggle(View v){
        termsCheck.toggle();
    }
}
