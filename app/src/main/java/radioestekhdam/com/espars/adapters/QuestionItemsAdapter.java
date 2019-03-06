package radioestekhdam.com.espars.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import radioestekhdam.com.espars.R;
import radioestekhdam.com.espars.model.es.Item;
import radioestekhdam.com.espars.utilities.Utils;

public class QuestionItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Item> itemList;
    private QuestionItemsAdapterListener questionItemsAdapterListener;

    public QuestionItemsAdapter(List<Item> itemList, final QuestionItemsAdapterListener questionItemsAdapterListener) {
        this.itemList = itemList;
        this.questionItemsAdapterListener = questionItemsAdapterListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        return new QuestionItemsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_questions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Item items = itemList.get(position);
        QuestionItemsViewHolder postsViewHolder = (QuestionItemsViewHolder) holder;

        postsViewHolder.tv_itemTitle.setText(items.getContent());

        if (items.getPosition().equals("con")) {
            questionItemsAdapterListener.onItemClickListener(null, items.getContent());
            postsViewHolder.tv_itemTitle.setBackground(context.getResources().getDrawable(R.drawable.shape_item_con));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                postsViewHolder.tv_itemTitle.setForeground(null);
            postsViewHolder.img_itemShare.setVisibility(View.VISIBLE);
            postsViewHolder.img_itemShare.setOnClickListener(v -> Utils.messagesShare(context, items.getContent()));
            holder.itemView.setOnClickListener(null);
        } else
            holder.itemView.setOnClickListener(v -> questionItemsAdapterListener.onItemClickListener(items.getID(), items.getContent()));
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    public interface QuestionItemsAdapterListener {
        void onItemClickListener(String questionId, String content);
    }

    private class QuestionItemsViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_itemTitle;
        private ImageView img_itemShare;

        private QuestionItemsViewHolder(View view) {
            super(view);
            tv_itemTitle = itemView.findViewById(R.id.tv_itemTitle);
            img_itemShare = itemView.findViewById(R.id.img_itemShare);
        }
    }
}
