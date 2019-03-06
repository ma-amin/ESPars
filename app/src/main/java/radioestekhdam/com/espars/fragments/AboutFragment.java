package radioestekhdam.com.espars.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
import radioestekhdam.com.espars.R;

public class AboutFragment extends DialogFragment {

    public AboutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new AboutPage(getContext())
                .isRTL(true)
                .setImage(R.drawable.logo_es_pars)
                .setDescription(getString(R.string.tv_description_about))
                .addItem(new Element().setTitle("نسخه 1.0"))
                .addGroup(getString(R.string.tv_contact))
                .addEmail("mohammadasgharpoor6@gmail.com", "Mohammad.A's Email")
                .addEmail("parsam.dev@gmail.com", "AliReza.P's Email")
                .addGitHub("MAmmad-eAMin", "GitHub for Android Application")
                .addGitHub("alirezapars", "GitHub for Web Application")
                .create();
    }
}
