package noteapp.nilabh.com.noteapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import noteapp.nilabh.com.noteapp.R;
import noteapp.nilabh.com.noteapp.model.NotesModel;
import noteapp.nilabh.com.noteapp.util.DateUtil;

/**
 * Created by nilabh on 06-01-2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private ArrayList<NotesModel> data;
    private final OnItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView note_title,note_desc,note_date;
        ImageView has_attachment;

        public ViewHolder(View view) {
            super(view);
            note_title = (TextView) view.findViewById(R.id.note_title);
            note_desc = (TextView) view.findViewById(R.id.note_desc);
            note_date = (TextView) view.findViewById(R.id.note_date);
            has_attachment = (ImageView) view.findViewById(R.id.has_attachment);
        }

        public void bind(final int pos, final NotesModel obj, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(pos, obj);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotesAdapter(ArrayList<NotesModel> myDataset, OnItemClickListener mListener) {
        data = myDataset;
        this.listener = mListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_notes_list_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotesModel temp = data.get(position);
        holder.note_title.setText(temp.getTitle());
        holder.note_desc.setText(temp.getDescription());
        DateUtil dateUtil = new DateUtil();
        holder.note_date.setText(dateUtil.getFormattedDate(temp.getCreatedTime()));
        String imgUrl = temp.getImgFileName();
        if(imgUrl != null && !imgUrl.isEmpty())
            holder.has_attachment.setVisibility(View.VISIBLE);
        else
            holder.has_attachment.setVisibility(View.GONE);
        holder.bind(position, temp, listener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, NotesModel object);
    }
}


