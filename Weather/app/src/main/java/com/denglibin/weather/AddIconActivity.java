package com.denglibin.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.denglibin.weather.adapter.IconAdapter;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by DengLibin on 2016/4/6 0006.
 */
public class AddIconActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private int iconClicdedId;
    public ImageView icon_user;
    private SharedPreferences preferences;
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_icon_activity);
        preferences=getSharedPreferences("user",MODE_PRIVATE);
        icon_user= (ImageView) findViewById(R.id.iv_user_icon2);
        int iconId=preferences.getInt("iconId",0);
        String myIcon=preferences.getString("myIcon",null);
        if(TextUtils.isEmpty(myIcon)) {
            if(iconId!=0){
                icon_user.setImageResource(iconId);
            }

        }else{
            Bitmap bm= BitmapFactory.decodeFile(myIcon);
            icon_user.setImageBitmap(bm);
        }


        recyclerView= (RecyclerView) findViewById(R.id.rc_icon);
        /**
         * 设置RecyclerView的布局样式
         * LinearLayoutManager.HORIZONTAL：item会水平显示
         * false:表示不反转（倒序排列）
         */
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));//网格布局4列,竖向不反转
       // recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));//水平，不反转
        IconAdapter adapter=new IconAdapter(this);
        recyclerView.setAdapter(adapter);
        View dialogView=this.getLayoutInflater().inflate(R.layout.add_icon_dialog,null);


        //创建PopupWindow对象
        final PopupWindow popupWindow=new PopupWindow(dialogView,480,360);
        TextView fromCamera= (TextView) dialogView.findViewById(R.id.fromcamera);
        TextView fromPics= (TextView) dialogView.findViewById(R.id.frompics);
        dialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();//关闭
            }
        });
        icon_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(v);//以下拉方式显示
            }
        });
        //拍照
        fromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        //从图库中选择图片
        fromPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_REQUEST_CODE){
            if(data==null){
                return;
            }else{
                Bundle extras=data.getExtras();
                if(extras!=null){
                    Bitmap bm=extras.getParcelable("data");
                    Uri uri = saveBitmapUri(bm);
                    startImageZoom(uri);
                }
            }
        } else if(requestCode == GALLERY_REQUEST_CODE) {
            if(data == null)
            {
                return;
            }
            Uri uri;
            uri = data.getData();
            Uri fileUri = convertUri(uri);
            startImageZoom(fileUri);
        }else if(requestCode == CROP_REQUEST_CODE) {
            if(data == null)
            {
                return;
            }
            Bundle extras = data.getExtras();
            if(extras == null){
                return;
            }
            Bitmap bm = extras.getParcelable("data");

            icon_user.setImageBitmap(bm);
        }
    }
    private Uri saveBitmapUri(Bitmap bm){
        File oldImgDir=new File(Environment.getExternalStorageDirectory()+"/com.weatheruserIcon.png");
        if(oldImgDir.exists()){
            oldImgDir.delete();
        }
        File imgDIr=new File(Environment.getExternalStorageDirectory()+"/com.weather");
        if(!imgDIr.exists()) {
            imgDIr.mkdir();
        }
        File img=new File(imgDIr.getAbsolutePath()+"userIcon.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("myIcon",img.getAbsolutePath());
            editor.commit();
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void startImageZoom(Uri uri)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }
    private Uri convertUri(Uri uri)
    {
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmapUri(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
