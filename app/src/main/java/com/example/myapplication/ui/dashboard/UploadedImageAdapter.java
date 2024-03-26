package com.example.myapplication.ui.dashboard;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.mysql.data.UploadedImage;

import java.util.List;

public class UploadedImageAdapter extends RecyclerView.Adapter<UploadedImageAdapter.UploadedImageViewHolder> {

    private List<UploadedImage> dataList;
    public UploadedImageAdapter(List<UploadedImage> dataList) {
        this.dataList = dataList;
    }
    public void setData(List<UploadedImage> newDataList) {
        this.dataList = newDataList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UploadedImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dashboard_item, parent, false);
        return new UploadedImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadedImageViewHolder holder, int position) {
        UploadedImage uploadedImage = dataList.get(position);
        holder.textViewFilename.setText(uploadedImage.getFilename());
        holder.textViewUploaderId.setText(uploadedImage.getUploaderId());
        holder.textViewUploadTime.setText(uploadedImage.getUploadTime());
        holder.textViewResult.setText(uploadedImage.getResult());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class UploadedImageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFilename, textViewUploaderId, textViewUploadTime, textViewResult;

        public UploadedImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFilename = itemView.findViewById(R.id.textView11);
            textViewUploaderId = itemView.findViewById(R.id.textView12);
            textViewUploadTime = itemView.findViewById(R.id.textView13);
            textViewResult = itemView.findViewById(R.id.textView14);
        }
    }
}
