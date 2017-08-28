package com.app.devrah.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.devrah.Holders.FilesHolder;
import com.app.devrah.R;
import com.app.devrah.pojo.AttachmentsPojo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.app.devrah.R.id.position;


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

     private void DownloadFiles(String name){

        try {
            String url = "http://m1.cybussolutions.com/kanban/uploads/card_uploads/"+attachmentList.get(position).getNameOfFile();

            URL u = new URL(url);
            InputStream is = u.openStream();

            DataInputStream dis = new DataInputStream(is);

            byte[] buffer = new byte[1024];
            int length;

            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" + name));
            while ((length = dis.read(buffer))>0) {
                fos.write(buffer, 0, length);
            }

        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        }
    }




    @Override
    public void onBindViewHolder(FilesHolder holder, final int position) {
        //if(!(attachmentList.get(position).getNameOfFile().equals("")) && attachmentList.get(position).getNameOfFile()!=null) {
            holder.datePosted.setText(attachmentList.get(position).getDateUpload());
            //   holder.fileSize.setText(String.valueOf(attachmentList.get(position).getSizeOfFile()));
            holder.tvFileName.setText(attachmentList.get(position).getNameOfFile());
        holder.tvFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openFile(activity,url);

                    String url = attachmentList.get(position).nameOfFile.replace(" ","%20");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m1.cybussolutions.com/kanban/uploads/card_uploads/"+url));
                    activity. startActivity(browserIntent);
                }


        });

        //}


    }

    @Override
    public int getItemCount() {
        return attachmentList.size();
    }


    public void openFile(Context context, File url) throws IOException {
        // Create URI
        File file=url;
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if(url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if(url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if(url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if(url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if(url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if(url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if(url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if(url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if(url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if(url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
