package com.example.home;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.home.databinding.ActivityPrintTicketBinding;
//import com.tejpratapsingh.pdfcreator.utils.PDFUtil;

import java.io.File;
import java.io.FileOutputStream;


public class PrintTicket extends AppCompatActivity {
    public static final int REQUEST_STORAGE=101;
    String storagePermission[];
private ActivityPrintTicketBinding binding;
int PERMISSION_REQUEST_CODE=1;
int pdfHeight=1080;
int pdfWidth=720;
private PdfDocument document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityPrintTicketBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        storagePermission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        binding.DownloadTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        GeneratePdfFromView(binding.linearLayout);
                    }
            }
        });
    }

    @RequiresApi(api= Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(storagePermission,REQUEST_STORAGE);
    }

    private boolean checkStoragePermission() {
        boolean result=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }


private void GeneratePdfFromView(View view){

    Bitmap bitmap = getBitmapfromView(view);
    document=new PdfDocument();
    PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(),1 ).create();
    PdfDocument.Page myPage=document.startPage(mypageInfo);
    Canvas canvas =myPage.getCanvas();
    canvas.drawBitmap(bitmap,0,0,null);
    document.finishPage(myPage);
   // createFile();
    long milliseconds = System.currentTimeMillis();
  File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),milliseconds+"MovietimeTicket.pdf");
  try{
      FileOutputStream fos=new FileOutputStream(file);
      document.writeTo(fos);
      fos.close();
      Toast.makeText(this, "PDF saved to local storage"+file.getPath(), Toast.LENGTH_SHORT).show();
  }catch (Exception ex){
      Toast.makeText(this, ""+ex, Toast.LENGTH_SHORT).show();
  }
}
private Bitmap getBitmapfromView(View view){
       Bitmap returnBitmap=Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
       Canvas canvas =new Canvas(returnBitmap);
    Drawable bgDrawable=view.getBackground();
    if(bgDrawable !=null){
        bgDrawable.draw(canvas);
    }else{
        canvas.drawColor(Color.WHITE);
    }
    view.draw(canvas);
        return returnBitmap;
}
}