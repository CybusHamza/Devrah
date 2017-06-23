package com.app.devrah.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.devrah.R;

/**
 * Created by AQSA SHaaPARR on 6/22/2017.
 */

public class FilesHolder extends RecyclerView.ViewHolder {

    public TextView tvFileName,fileSize,datePosted;

    public FilesHolder(View itemView) {
        super(itemView);

        tvFileName  = (TextView)itemView.findViewById(R.id.fileName);
    //    fileSize = (TextView)itemView.findViewById(R.id.fileSize);
        datePosted = (TextView)itemView.findViewById(R.id.fileDatePosted);

        }
}
