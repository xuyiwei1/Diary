package comp5216.sydney.edu.au.diarypro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import comp5216.sydney.edu.au.diarypro.engine.GlideEngine;

public class EditUserInfoActivity extends AppCompatActivity {

    private Button cancelBtn;
    private Button saveBtn;
    private EditText imageEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        //init the components
        cancelBtn = this.findViewById(R.id.cancelBtn);
        saveBtn = this.findViewById(R.id.saveBtn);
        imageEdit = this.findViewById(R.id.userNickNmaeEdit);

        //set listener
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check whether user want to cancel to save info
                AlertDialog.Builder builder = new AlertDialog.Builder(EditUserInfoActivity.this);
                builder.setTitle(R.string.dialog_cancel_title)
                        .setMessage(R.string.dialog_cancel_edit_add_item_msg)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // discard the data and jump to the main page
                                userNickNameEdit.setText("");
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
        });

        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO save the user info to the database

                //jump to the main page
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO save the user info to the database

                //jump to the main page
                finish();
            }
        });
    }

    private void selectPhotoAndAll(ImageView imageView) {
        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine()).setMaxSelectNum(1)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        Log.e("leo", "图片路径" + result.get(0).getPath());
                        Log.e("leo", "绝对路径" + result.get(0).getRealPath());
                        Glide.with(EditUserInfoActivity.this).load(result.get(0).getPath()).into(imageView);
                        //将bitmap图片传入后端
                        //imageUpLoad(result.get(0).getRealPath());
//                        submitPicture(result.get(0).getRealPath());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(EditUserInfoActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
