package radioestekhdam.com.espars.utilities;

import android.content.Context;
import android.content.Intent;

import radioestekhdam.com.espars.R;

public class Utils {

    public static void messagesShare(Context mContext, String messages) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messages);
        mContext.startActivity(Intent.createChooser(intent, mContext.getString(R.string.str_messages_share)));
    }
}
