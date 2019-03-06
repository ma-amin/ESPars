package radioestekhdam.com.espars.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import radioestekhdam.com.espars.R;

public class NewChatFragment extends Fragment {

    private View view;
    private OnNewChatFragmentListener mListener;

    public NewChatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_chat, container, false);

        ini();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewChatFragmentListener) {
            mListener = (OnNewChatFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewChatFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnNewChatFragmentListener {
        void onNewChatListener();
        void onAboutListener();
    }

    private void ini() {
        TextView btn_newChat = view.findViewById(R.id.btn_newChat);
        btn_newChat.setOnClickListener(v -> mListener.onNewChatListener());
        TextView tv_about = view.findViewById(R.id.tv_about);
        tv_about.setOnClickListener(v -> mListener.onAboutListener());
    }
}
