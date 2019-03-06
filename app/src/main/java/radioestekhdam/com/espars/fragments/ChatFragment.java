package radioestekhdam.com.espars.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import radioestekhdam.com.espars.MainActivity;
import radioestekhdam.com.espars.R;
import radioestekhdam.com.espars.backEndRepository.WebApplicationURLs;
import radioestekhdam.com.espars.backEndRepository.volley.request.GettingQuestions;
import radioestekhdam.com.espars.model.chat.Message;
import radioestekhdam.com.espars.model.chat.User;
import radioestekhdam.com.espars.model.es.Item;
import radioestekhdam.com.espars.utilities.CheckNetworkListener;
import radioestekhdam.com.espars.utilities.CustomInMessageViewHolder;
import radioestekhdam.com.espars.utilities.Utils;
import radioestekhdam.com.espars.utilities.cab.CabCallback;
import radioestekhdam.com.espars.utilities.cab.ContextualActionBar;

public class ChatFragment extends Fragment implements MessagesListAdapter.SelectionListener, DateFormatter.Formatter {

    private Activity activity;
    private View view;
    private OnChatFragmentListener mListener;

    private ImageLoader imageLoader;
    private MessagesListAdapter<Message> messagesAdapter;
    private User user_Ghool;
    private User user_You;
    private GettingQuestions gettingQuestions;

    private PopupMenu optionMenu;
    private ContextualActionBar customCab;
    private CabCallback cabCallback;
    private int selectionCount;

    private AVLoadingIndicatorView loadingView;
    private TextView tv_status;
    public boolean bolConnect = false;

    public ChatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoader = (imageView, url, payload) -> Picasso.get().load(url).into(imageView);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);

        ini();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChatFragmentListener) {
            mListener = (OnChatFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChatFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnChatFragmentListener {
        void setActionBar(Toolbar toolbar);
    }

    private void ini() {
        activity = getActivity();
        initMessage();
        iniToolbar();
        iniCheckNetwork();
    }

    private void initMessage() {
        MessagesList mMessagesList = view.findViewById(R.id.messagesList);

        CustomInMessageViewHolder.Payload payload = new CustomInMessageViewHolder.Payload();
        payload.context = activity;
        payload.listener = (questionId, content) -> {
            if (questionId != null) {
                messagesAdapter.addToStart(new Message(getRandomId(), user_You, content), true);
                getQuestions(WebApplicationURLs.QuestionsAddress + questionId);

            }
        };

        MessageHolders holdersConfig = new MessageHolders()
                .setIncomingTextConfig(
                        CustomInMessageViewHolder.class,
                        R.layout.item_custom_in_message,
                        payload);

        messagesAdapter = new MessagesListAdapter<>("0", holdersConfig, imageLoader);
        messagesAdapter.enableSelectionMode(this);
        messagesAdapter.setDateHeadersFormatter(this);
        mMessagesList.setAdapter(messagesAdapter);

        user_Ghool = new User("1", getString(R.string.tv_user_Ghool),
                "android.resource://radioestekhdam.com.espars/" + R.drawable.ghool);
        user_You = new User("0", getString(R.string.tv_your_name), null);

        gettingQuestions = new GettingQuestions(activity);
    }

    @Override
    public String format(Date date) {
        if (DateFormatter.isToday(date)) {
            return getString(R.string.str_date_today);
        } else if (DateFormatter.isYesterday(date)) {
            return getString(R.string.str_date_yesterday);
        } else {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
        }
    }

    private MessagesListAdapter.Formatter<Message> getMessageStringFormatter() {
        return message -> message.getUser().getName() + ": "
                + message.getText() + "\n" + getMessageItemsContent(message.getItems());
    }

    private String getMessageItemsContent(List<Item> items) {
        if (!items.isEmpty()) {
            StringBuilder strAll = new StringBuilder();
            for (Item item : items) {
                strAll.append(item.getContent()).append("\n");
            }
            return strAll.toString();
        }
        return "";
    }

    @Override
    public void onSelectionChanged(int count) {
        this.selectionCount = count;
        if (count > 1)
            customCab.setTitle(count + "");
        else if (count == 1) {
            if (customCab == null) {
                customCab = new ContextualActionBar((AppCompatActivity) activity, R.id.cab_stub)
                        .setMenu(R.menu.chat_actions_menu)
                        .setBackgroundColorRes(R.color.colorWhite)
                        //.setCloseDrawableRes(R.drawable.bg_custom_incoming_message)
                        .start(cabCallback);

            } else if (!customCab.isActive())
                customCab.restore();
            customCab.setTitle(count + "");

        } else
            customCab.finish();
    }

    private void iniToolbar() {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        mListener.setActionBar(toolbar);

        cabCallback = new CabCallback() {
            @Override
            public boolean onCreateCab(ContextualActionBar cab, Menu menu) {
                return true;
            }

            @Override
            public boolean onCabItemClicked(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        messagesAdapter.deleteSelectedMessages();
                        customCab.finish();
                        return true;

                    case R.id.action_copy:
                        messagesAdapter.copySelectedMessagesText(activity, getMessageStringFormatter(), true);
                        Toast.makeText(activity, getString(R.string.toast_copied_message), Toast.LENGTH_SHORT).show();
                        customCab.finish();
                        return true;

                    case R.id.action_share:
                        Utils.messagesShare(activity, messagesAdapter.getSelectedMessagesText(getMessageStringFormatter(), true));
                        customCab.finish();
                        return true;

                    default:
                        return false;
                }
            }

            @Override
            public boolean onDestroyCab(ContextualActionBar cab) {
                if (selectionCount != 0)
                    messagesAdapter.unselectAllItems();

                return true;
            }
        };

        loadingView = view.findViewById(R.id.loadingView);
        tv_status = view.findViewById(R.id.tv_status);

        ImageButton option_menu = view.findViewById(R.id.option_menu);
        optionMenu = new PopupMenu(activity, option_menu);
        MenuInflater inflater = optionMenu.getMenuInflater();
        inflater.inflate(R.menu.option_menu, optionMenu.getMenu());
        optionMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_clear_history:
                    messagesAdapter.clear();
                    getQuestions(WebApplicationURLs.SystemsAddress);
                    return true;

                default:
                    return true;
            }
        });
        option_menu.setOnClickListener(v -> optionMenu.show());
    }

    private void iniCheckNetwork() {
        CheckNetworkListener checkNetworkListener = new CheckNetworkListener() {
            @Override
            public void hasNetwork() {
                bolConnect = true;
                tv_status.setText(R.string.tv_online_status);
                if (messagesAdapter.isEmpty())
                    getQuestions(WebApplicationURLs.SystemsAddress);
            }

            @Override
            public void noNetwork() {
                bolConnect = false;
                setOffline(R.string.tv_offline_status);
            }
        };
        MainActivity.checkNetworkListener = checkNetworkListener;
        bolConnect = MainActivity.bolConnect;
        if (bolConnect)
            checkNetworkListener.hasNetwork();
        else
            checkNetworkListener.noNetwork();
    }

    private String getRandomId() {
        return Long.toString(UUID.randomUUID().getLeastSignificantBits()*-1);
    }

    private boolean isOnline(boolean typing) {
        if (bolConnect) {
            if (typing)
                isShowLoadingView(true);
            else
                isShowLoadingView(false);
            return true;
        }
        return false;
    }

    private void setOffline(int status) {
        loadingView.setVisibility(View.INVISIBLE);
        tv_status.setText(status);
    }

    private void isShowLoadingView(boolean show) {
        //synchronized (ChatFragment.class) {
        if (show) {
            loadingView.setVisibility(View.VISIBLE);
            tv_status.setText(R.string.tv_typing_status);
        } else {
            loadingView.setVisibility(View.INVISIBLE);
            tv_status.setText(R.string.tv_online_status);
        }
    }

    private void getQuestions(String url) {
        if (isOnline(true)) {
            gettingQuestions.getResponse(url, question -> {
                if (question != null) {
                    isOnline(false);

                    if (question.getContent() != null && !question.getItems().isEmpty()) {
                        messagesAdapter.addToStart(new Message(getRandomId(), user_Ghool,
                                question.getContent(), question.getItems()), true);
                    }
                } else
                    setOffline(R.string.tv_connect_status);
            });
        }
    }
}
