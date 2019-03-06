package radioestekhdam.com.espars.backEndRepository;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import radioestekhdam.com.espars.R;

public class ParsingJSON {
    //private static final ParsingJSON mInstance = new ParsingJSON();
    //private final Gson gson = new Gson();
    private GsonBuilder gsonBuilder;
    private Gson mGson;

    private ParsingJSON() {
        mGson = getGSon();
    }

    private static class SingletonHelper{
        private static final ParsingJSON INSTANCE = new ParsingJSON();
    }

    public static ParsingJSON getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private Gson getGSon(){
        if (mGson == null) {
            if (gsonBuilder == null)
                gsonBuilder = new GsonBuilder();
            mGson = gsonBuilder.create();
        }
        return mGson;
    }

    public <T> List<T> getParsingJSONArray(JSONArray json, Class<T[]> classOfT, Context context) {
        try {
            return Arrays.asList(getGSon().fromJson(json.toString(), classOfT));

        } catch (Exception e) {
            getToast(context);
            return null;
        }
    }

    public <T> List<T> getParsingJSONArray(String json, Class<T[]> classOfT, Context context) {
        try {
            return Arrays.asList(getGSon().fromJson(json, classOfT));

        } catch (Exception e) {
            getToast(context);
            return null;
        }
    }

    public <T> T getParsingJSONObject(JSONObject json, Class<T> classOfT, Context context) {
        try {
            return getGSon().fromJson(json.toString(), classOfT);

        } catch (Exception e) {
            getToast(context);
            return null;
        }
    }

    public <T> T getParsingJSONObject(String json, Class<T> classOfT, Context context) {
        try {
            return getGSon().fromJson(json, classOfT);

        } catch (Exception e) {
            getToast(context);
            return null;
        }
    }

    private void getToast(Context mContext){
        Toast.makeText(mContext, R.string.toast_post_error, Toast.LENGTH_SHORT).show();
    }
}
