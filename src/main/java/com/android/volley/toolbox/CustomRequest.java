package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TM on 9/16/2016.
 */
public class CustomRequest extends Request<JSONObject> {

    private Response.Listener<JSONObject> listener;
    private Response.Listener<String> listener2;
    private Map<String, String> params;
    private Map<String, String> setcookie;
    Map<String, String> responseHeaders;

    String cookie = " ";
    private final String TAG = "~=~_~=~-TIM@CRS-~=~_~=~";

    public CustomRequest(String url, Map<String, String> params,
                         Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = responseListener;
        this.params = params;

    }

    public CustomRequest(int method, String url, Map<String, String> params, String sid,
                         Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = responseListener;
        this.params = params;
        this.cookie = sid;

    }

   /* public CustomRequest(int method, String url, Map<String, String> params,
                         Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener2 = responseListener;
        this.params = params;

    } */



    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        Map headers = new HashMap();

        headers.put("cookie", cookie);
        return headers;
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {


            cookie = response.headers.get("set-cookie");

            if(cookie != null) {
                response.headers.put("cookie", cookie);
            }


            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));



            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
        catch (NullPointerException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        // TODO Auto-generated method stub



        try {

            response.put("set-cookie", cookie);

        }
        catch(JSONException e){

        }

        listener.onResponse(response);
    }
}