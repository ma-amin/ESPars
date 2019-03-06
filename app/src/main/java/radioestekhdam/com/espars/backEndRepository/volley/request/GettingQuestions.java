package radioestekhdam.com.espars.backEndRepository.volley.request;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONObject;

import radioestekhdam.com.espars.backEndRepository.volley.VolleySingleton;
import radioestekhdam.com.espars.model.es.Question;
import radioestekhdam.com.espars.backEndRepository.ParsingJSON;

public class GettingQuestions {
    private Context mContext;
    private int time_interval = 8000;
    private long oldCurrentTimeMillis;
    //private final String TAG = "GettingQuestions";

    public GettingQuestions(Context context) {
        mContext = context;
    }

    public void getResponse(final String url, final OnQuestions onQuestions) {

        Response.Listener<JSONObject> listener = response -> {
            //Log.i("Mammad", "onQuestions response>> " + url);
            //Log.i("Mammad", "onQuestions response>> " + response);
            onQuestions.getQuestions(ParsingJSON.getInstance().getParsingJSONObject(response, Question.class, mContext));
        };

        Response.ErrorListener errorListener = error -> {
            if (oldCurrentTimeMillis == 0)
                oldCurrentTimeMillis = System.currentTimeMillis();
            if (oldCurrentTimeMillis + time_interval > System.currentTimeMillis()) {
                //Log.e("Mammad", "onQuestions>> error");
                onQuestions.getQuestions(null);
                getResponse(url, onQuestions);
            }

            /*NetDialog.getInstance(mContext).setOnClickListeners(new NetDialog.OnClickListeners() {
                @Override
                public void onIgnore() {
                    onQuestions.getQuestions(null);
                }
                @Override
                public void onTryAgain() {
                    getResponse(url, onQuestions);
                }
            }).ShowingNetDialog();*/
        };

        VolleySingleton.getInstance(mContext).setRequestJSONObject(mContext,url, false, listener, errorListener);
    }

    public interface OnQuestions {
        void getQuestions(Question question);
    }
}
