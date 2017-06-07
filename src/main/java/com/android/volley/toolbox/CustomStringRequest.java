package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dane on 9/19/2016.
 */
public class CustomStringRequest extends Request<String> {

    private Response.Listener<String> listener;
    private Map<String, String> params;
    private Map<String, String> setcookie;
    Map<String, String> responseHeaders;

    String cookie = " ";
    private final String TAG = "~=~_~=~-TIM@CRS-~=~_~=~";

    public CustomStringRequest(String url, Map<String, String> params,
                               Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = responseListener;
        this.params = params;

    }

    public CustomStringRequest(int method, String url, Map<String, String> params,
                               Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = responseListener;
        this.params = params;

    }



    protected Map<String, String> getParams()
            throws AuthFailureError {
        return params;
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        Map headers = new HashMap();

        headers.put("cookie", cookie);
        return headers;
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {


            cookie = response.headers.get("set-cookie");

            if(cookie != null) {
                response.headers.put("cookie", cookie);
            }


            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));



            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
        catch (NullPointerException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        // TODO Auto-generated method stub



       /* try {

           // response.putString(1, cookie);


        }
        catch(JSONException e){

        }*/

        listener.onResponse(response);
    }
}

