package radioestekhdam.com.espars.backEndRepository.volley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.request.GZipRequest;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;

    private VolleySingleton(Context context) {
        mRequestQueue = getRequestQueue(context);
    }

    public static VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            synchronized (VolleySingleton.class) {
                if (mInstance == null)
                    mInstance = new VolleySingleton(context);
            }
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(Context mContext) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public void setRequestJSONArray(Context contextTag, String url, boolean cache,
                                    Response.Listener<JSONArray> listener, Response.ErrorListener errorListener){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        request.setTag(contextTag);
        request.setRetryPolicy(setRetryPolicy(16000, 1));
        request.setShouldCache(cache);
        getRequestQueue(contextTag).add(request);
    }

    public void setRequestJSONObject(Context contextTag, String url, boolean cache,
                                     Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        request.setTag(contextTag);
        request.setRetryPolicy(setRetryPolicy(16000, 1));
        request.setShouldCache(cache);
        getRequestQueue(contextTag).add(request);
    }

    public void setGZipRequest(Context contextTag, String url, boolean cache,
                               Response.Listener<String> listener, Response.ErrorListener errorListener){
        GZipRequest request = new GZipRequest(Request.Method.GET, url, listener, errorListener);
        request.setTag(contextTag);
        request.setRetryPolicy(setRetryPolicy(16000, 1));
        request.setShouldCache(cache);
        getRequestQueue(contextTag).add(request);
        //clearCache();
    }

    private DefaultRetryPolicy setRetryPolicy(int timeOut, int maxRetries){
        return new DefaultRetryPolicy(timeOut, maxRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public void clearCache(final Context contextTag, boolean RequestFinished) {
        if (RequestFinished)
            getRequestQueue(contextTag).getCache().clear();
        else
            getRequestQueue(contextTag).addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    getRequestQueue(contextTag).getCache().clear();
                }
            });
    }

    public void CancelRequest(Context contextTag) {
        getRequestQueue(contextTag).cancelAll(contextTag);
    }
}
