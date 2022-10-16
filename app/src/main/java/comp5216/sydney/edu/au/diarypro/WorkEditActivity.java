package comp5216.sydney.edu.au.diarypro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.util.ArrayList;

import comp5216.sydney.edu.au.diarypro.dao.WorkStudyEventDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.engine.GlideEngine;
import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;

public class WorkEditActivity extends AppCompatActivity {

    private EditText workEditText;
    private ImageView workImageView;
    private AppDatabase appDatabase;
    private WorkStudyEventDao workStudyEventDao;
    private String path;
    // judge which page the data come from using to judge insert or update data
    private int stateCode;

    // for log
    private static final String TAG = "WorkEditActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_edit);
        // init the layout
        workEditText = this.findViewById(R.id.workEditView);
        workImageView = this.findViewById(R.id.workUploadImageView);
        //init the database
        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        workStudyEventDao = appDatabase.workStudyEventItemDao();

        //show the information when user click the item in Home page
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        String imagePath = intent.getStringExtra("imagePath");
        stateCode = intent.getIntExtra("fromHomePage", -1);
        // load the photo and text
        workEditText.setText(content);
        if (imagePath != null && imagePath.length() > 0) {
            Glide.with(WorkEditActivity.this).load(imagePath).into(workImageView);
        }

    }

    /**
     * when user click the cancel, cancel to save diary item
     *
     * @param view
     */
    public void cancelEditBtn(View view) {
        //check whether user want to cancel to save info
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkEditActivity.this);
        builder.setTitle(R.string.dialog_cancel_title)
                .setMessage(R.string.dialog_cancel_edit_add_item_msg)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // discard the data and jump to the main page
                        workEditText.setText("");
                        finish();
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // users click cancel button, noting to do
                    }
                });
        builder.create().show();
    }

    /**
     * click to upload the image
     *
     * @param view
     */
    public void uploadImage(View view) {
        selectPhotoAndAll(workImageView);
    }

    /**
     * select photo from gallery
     */
    private void selectPhotoAndAll(ImageView imageView) {
        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine()).setMaxSelectNum(1)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        Log.e("leo", "图片路径" + result.get(0).getPath());
                        Log.e("leo", "绝对路径" + result.get(0).getRealPath());
                        Glide.with(WorkEditActivity.this).load(result.get(0).getPath()).into(imageView);
                        //将bitmap图片传入后端
                        //imageUpLoad(result.get(0).getRealPath());
                        submitPicture(result.get(0).getRealPath());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(WorkEditActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * upload image to the sd card
     *
     * @param path
     */
    private void submitPicture(String path) {
        //E/leo: 图片路径/storage/emulated/0/DCIM/Camera/IMG_20221016031948104.jpeg
        //E/leo: 绝对路径/storage/emulated/0/DCIM/Camera/IMG_20221016031948104.jpeg
        // save the image path to sqlite
        this.path = path;
        System.out.println(path);
    }


    /**
     * click the save button to save the information
     *
     * @param view
     */
    public void clickWorkSave(View view) {
        Intent intentFromPrevious = getIntent();
        String dateDiary = intentFromPrevious.getExtras().getString("dateDiary");
        Log.d(TAG, "clickWorkSave: dateDiary " + dateDiary);
        //check update or insert data
        if (stateCode == 666) {
            // need id to update
            Intent intent = getIntent();
            int id = intent.getExtras().getInt("id");
            Glide.with(WorkEditActivity.this).load(path).into(workImageView);
            workStudyEventDao.update(new WorkStudyEventItem(id,workEditText.getText().toString(), path, "work", dateDiary));
        } else {
            workStudyEventDao.insertItem(new WorkStudyEventItem(workEditText.getText().toString(), path, "work", dateDiary));
        }
        //back to the Home Page
        Toast.makeText(this, "save successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(WorkEditActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
