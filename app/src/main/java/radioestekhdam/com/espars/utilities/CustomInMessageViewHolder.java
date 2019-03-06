package radioestekhdam.com.espars.utilities;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stfalcon.chatkit.messages.MessageHolders;


import java.util.List;

import radioestekhdam.com.espars.R;
import radioestekhdam.com.espars.adapters.QuestionItemsAdapter;
import radioestekhdam.com.espars.model.chat.Message;
import radioestekhdam.com.espars.model.es.Item;

public class CustomInMessageViewHolder extends MessageHolders.IncomingTextMessageViewHolder<Message> {

    private RecyclerView recyclerView;

    public CustomInMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
        recyclerView = itemView.findViewById(R.id.recylItems);
    }

    private void iniRecyclerView(final Payload payload, List<Item> itemsList) {
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(payload.context));

        QuestionItemsAdapter questionItemsAdapter = new QuestionItemsAdapter(itemsList, (questionId, content) -> {
            if (questionId == null)
                bubble.setBackgroundResource(R.drawable.shape_message_con);
            else
                payload.listener.onItemsClick(questionId, content);
        });

        recyclerView.setAdapter(questionItemsAdapter);
    }

        @Override
    public void onBind(Message message) {
        super.onBind(message);

        final Payload payload = (Payload) this.payload;

        iniRecyclerView(payload, message.getItems());
    }

    public static class Payload {
        public Context context;
        public OnCustomInMessageViewHolderListener listener;
    }

    public interface OnCustomInMessageViewHolderListener {
        void onItemsClick(String questionId, String content);
    }
}
