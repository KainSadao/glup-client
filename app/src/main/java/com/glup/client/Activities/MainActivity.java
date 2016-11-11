package com.glup.client.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.glup.client.Api.Glup;
import com.glup.client.R;
import com.glup.client.Utils.Aes;
import com.glup.client.Utils.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pd;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setDrawer();
        pd = new ProgressDialog(this);
        pd.setMessage("Cargando productos...");
        pd.show();
        Glup.getAllProducts(new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONObject responseJson = new JSONObject(response.toString());
                    JSONArray productsArray = responseJson.getJSONArray("Products");
                    LinearLayout parent = (LinearLayout)findViewById(R.id.parent_layout1);
                    View view;
                    LayoutInflater li = getLayoutInflater();
                    for(int i=0;i<=productsArray.length()-1;i++){
                        view = li.inflate(R.layout.inflate_view_product, parent, false);
                        NetworkImageView netImage = (NetworkImageView)view.findViewById(R.id.product_image);
                        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.product_layout);
                        TextView productName = (TextView)view.findViewById(R.id.product_label);

                        ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();
                        JSONObject product = productsArray.getJSONObject(i);
                        String imageUrl = product.getString("img_url");
                        String name = product.getString("name");

                        netImage.setImageUrl(imageUrl,imageLoader);
                        productName.setText(name);
                        parent.addView(relativeLayout);
                        setPreferences();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Toolbar getToolbar() {
        return toolbar;
    }

    private void setToolbar() {
        toolbar = (Toolbar)findViewById(R.id.bar_container);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private DrawerLayout getDrawer() {
        return drawer;
    }

    private void setDrawer() {
        ListView lv;
        String[] names;

        names = getResources().getStringArray(R.array.names);
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        lv = (ListView)findViewById(R.id.drawer_left);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: enableTutorial();
                        break;
                    case 1: logout();
                        break;
                }
            }
        });
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, names));
    }

    private void setPreferences(){
        preferences = this.getSharedPreferences(getString(R.string.ini_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLogged", true);
        editor.putBoolean("enableTutorial", false);
        editor.apply();
    }

    private void logout(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLogged", false);
        editor.apply();
    }

    private void enableTutorial(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("enableTutorial", true);
        editor.apply();
    }
}
