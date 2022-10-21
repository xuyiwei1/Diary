package comp5216.sydney.edu.au.diarypro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import comp5216.sydney.edu.au.diarypro.dao.UserItemDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.engine.GlideEngine;
import comp5216.sydney.edu.au.diarypro.entity.UserItem;
import comp5216.sydney.edu.au.diarypro.util.UserInfo;

public class EditUserInfoActivity extends AppCompatActivity {

    private Button cancelBtn;
    private Button saveBtn;
    private Button changeImageBtn;
    private Button changePasswordBtn;
    private EditText nicknameInput;
    private ImageView infoImage;
    private int id;
    String imagePath;

    UserInfo userInfo;

    private AppDatabase appDatabase;
    private UserItemDao UserItemDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        //init the components
        cancelBtn = this.findViewById(R.id.cancelBtn);
        saveBtn = this.findViewById(R.id.saveBtn);
        nicknameInput = this.findViewById(R.id.nicknameInput);
        changeImageBtn = this.findViewById(R.id.changeBtn);
        infoImage = this.findViewById(R.id.infoImage);
        changePasswordBtn = this.findViewById(R.id.changePasswordBtn);


        userInfo = (UserInfo)getApplicationContext();
        id = userInfo.getId();
        imagePath = userInfo.getUserItem().getImagePath();

        nicknameInput.setText(userInfo.getUserItem().getNickname());

        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        UserItemDao = appDatabase.userItemDao();

        if (imagePath != null && imagePath.length() > 0) {
            Glide.with(EditUserInfoActivity.this).load(imagePath).into(infoImage);
        }


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

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(EditUserInfoActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        changeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhotoAndAll(infoImage);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserItemDao.changeNickname(id,nicknameInput.getText().toString());

//                UserItem useritem = UserItemDao.getById(id);
//                userInfo.setUserItem(useritem);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserItem useritem = UserItemDao.getById(id);
        userInfo.setUserItem(useritem);
        nicknameInput.setText(userInfo.getUserItem().getNickname());
        imagePath = useritem.getImagePath();
        if (imagePath != null && imagePath.length() > 0) {
            Glide.with(EditUserInfoActivity.this).load(imagePath).into(infoImage);
        }
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
                        Glide.with(EditUserInfoActivity.this).load(result.get(0).getRealPath()).into(imageView);

                        UserItemDao.changeImagePath(id,result.get(0).getRealPath());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(EditUserInfoActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
