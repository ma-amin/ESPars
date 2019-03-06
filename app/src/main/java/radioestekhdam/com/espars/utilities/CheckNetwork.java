package radioestekhdam.com.espars.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class CheckNetwork extends BroadcastReceiver {

    private Context mContext;
    private OnCheckNetwork onCheckNetwork;

    public CheckNetwork(Context context, OnCheckNetwork onCheckNetwork) {
        this.mContext = context;
        this.onCheckNetwork = onCheckNetwork;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            onCheckNetwork.hasNetwork();

        } else
            onCheckNetwork.noNetwork();
    }

    public interface OnCheckNetwork{
        void hasNetwork();
        void noNetwork();
    }
}
