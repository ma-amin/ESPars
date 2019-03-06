package radioestekhdam.com.espars;

import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import radioestekhdam.com.espars.backEndRepository.volley.VolleySingleton;
import radioestekhdam.com.espars.fragments.AboutFragment;
import radioestekhdam.com.espars.fragments.ChatFragment;
import radioestekhdam.com.espars.fragments.NewChatFragment;
import radioestekhdam.com.espars.utilities.CheckNetwork;
import radioestekhdam.com.espars.utilities.CheckNetworkListener;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements ChatFragment.OnChatFragmentListener, NewChatFragment.OnNewChatFragmentListener {

    private CheckNetwork checkNetwork;
    public static CheckNetworkListener checkNetworkListener;
    public static boolean bolConnect = false;
    private FragmentManager fragmentManager;
    private long oldCurrentTimeMillis;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCheckNetwork();
        VolleySingleton.getInstance(MainActivity.this);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout_Main, new NewChatFragment()).commit();
    }

    private void setCheckNetwork() {
        checkNetwork = new CheckNetwork(this, new CheckNetwork.OnCheckNetwork() {
            @Override
            public void hasNetwork() {
                if (checkNetworkListener != null)
                    checkNetworkListener.hasNetwork();
                    bolConnect = true;
            }

            @Override
            public void noNetwork() {
                if (checkNetworkListener != null)
                    checkNetworkListener.noNetwork();
                    bolConnect = false;
            }
        });
        registerReceiver(checkNetwork, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void showFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right,
                R.anim.slide_in_back_from_right, R.anim.slide_out_back_to_left);
        ft.replace(R.id.frameLayout_Main, fragment).addToBackStack(tag).commit();
    }

    @Override
    public void onNewChatListener() {
        showFragment(new ChatFragment(), "ChatFragment");
    }

    @Override
    public void onAboutListener() {
        showFragment(new AboutFragment(), "AboutFragment");
    }

    @Override
    public void setActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        int time_interval = 2000;
        if (fragmentManager.getBackStackEntryCount() > 0)
            fragmentManager.popBackStack();
        else if (oldCurrentTimeMillis + time_interval < System.currentTimeMillis()) {
                Toast.makeText(getBaseContext(), R.string.toast_close_main, Toast.LENGTH_SHORT).show();
                oldCurrentTimeMillis = System.currentTimeMillis();
            } else
                super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(checkNetwork);
        bolConnect = false;

        super.onDestroy();
    }
}
