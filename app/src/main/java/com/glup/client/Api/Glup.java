package com.glup.client.Api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.glup.client.Utils.CustomRequest;
import com.glup.client.Utils.VolleyController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kain on 02/10/2016.
 */

public class Glup {

    private static String GLUP_SERVER = "http://192.168.100.7/glup/index.php/api";
    private Context context;

    public Glup(Context context){
        this.context = context;
    }

    public static void login(String user, String pass, Response.Listener response, Response.ErrorListener error){
        Map<String, String> params = new HashMap<>();
        params.put("DataType", "login");
        params.put("user", user);
        params.put("pass", pass);
        performJSONObjectRequest(params, response, error);
    }

    /**
     * Realiza una petición POST con parámetros
     * @param params
     * @param responseListener
     * @param errorListener
     */
    private static void performJSONObjectRequest(Map<String,String> params,
                                                 Response.Listener<String> responseListener, Response.ErrorListener errorListener){
        final String TAG_JSON = "Json_tag";
        CustomRequest<String> req = new CustomRequest<String>(Request.Method.POST,GLUP_SERVER,params,responseListener,errorListener);
        VolleyController.getInstance().addToRequestQueue(req, TAG_JSON);
    }
}
