package com.example.testselectdirectory;

import 	android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {

    private ArrayList<String> subFilesPathsList;
    private OnItemClickListener itemClickListener;

    public FilesAdapter(ArrayList<String> subFilesPathsList) {
        this.subFilesPathsList = subFilesPathsList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.files_recycler_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.fileItemTextView.setText(subFilesPathsList.get(position));
        // handle text view click events
        holder.fileItemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(view, position);
                // after an item is clicked, refresh the recycler view
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return subFilesPathsList == null ? 0 : subFilesPathsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView fileItemTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            fileItemTextView = itemView.findViewById(R.id.file_item_text_view);
        }
    }



    // handle click events
    // when the textview is clicked, go into the sub directories
    public void setOnIemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
