package com.app.devrah.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Holders.FilesHolder;
import com.app.devrah.R;
import com.app.devrah.pojo.AttachmentsPojo;

import java.util.List;


public class FilesAdapter extends RecyclerView.Adapter<FilesHolder> {

    private List<AttachmentsPojo> attachmentList;
    Activity activity;




   public FilesAdapter(List<AttachmentsPojo> mAttachmentList, Activity mActivity){

        this.attachmentList = mAttachmentList;
        this.activity = mActivity;

    }

    @Override
    public FilesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_files_rv,parent,false);
        FilesHolder holder = new FilesHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(FilesHolder holder, int position) {

        holder.datePosted.setText(attachmentList.get(position).getDateUpload());
     //   holder.fileSize.setText(String.valueOf(attachmentList.get(position).getSizeOfFile()));
        holder.tvFileName.setText(attachmentList.get(position).getNameOfFile());


    }

    @Override
    public int getItemCount() {
        return attachmentList.size();
    }
}
