package com.glup.client.Utils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * Created by Kain on 02/10/2016.
 */

public class CustomRequest<T> extends Request<T>{
    private Response.Listener responseListener;
    private Map<String, String> params;

    public CustomRequest(int method, String url, Map<String, String> params,
                         Response.Listener<T> responseListener, Response.ErrorListener errorListener){
        super(method, url, errorListener);
        this.responseListener = responseListener;
        this.params = params;
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * Ejecutado antes de deliverResponse
     * @param response Response from the network
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return (Response<T>)Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     *
     * @param response The parsed response returned by responseListener
     */
    @Override
    protected void deliverResponse(T response) {
        responseListener.onResponse(response);
    }
}
