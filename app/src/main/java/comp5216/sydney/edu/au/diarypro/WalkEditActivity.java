package comp5216.sydney.edu.au.diarypro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.Calendar;


import comp5216.sydney.edu.au.diarypro.dao.WorkStudyEventDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.engine.GlideEngine;
import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;
import comp5216.sydney.edu.au.diarypro.util.DateConvertUtil;

public class WalkEditActivity extends AppCompatActivity {

    private EditText walkEditText;
    private ImageView walkImageView;
    private AppDatabase appDatabase;
    private WorkStudyEventDao walkStudyEventDao;
    private String path;
    private static String dateDiary;
    // judge which page the data come from using to judge insert or update data
    private int stateCode;

    // for log
    private static final String TAG = "WalkEditActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_edit);
        // init the layout
        walkEditText = this.findViewById(R.id.walkEditView);
        walkImageView = this.findViewById(R.id.walkUploadImageView);
        //init the database
        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        walkStudyEventDao = appDatabase.workStudyEventItemDao();


        //show the information when user click the item in Home page
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        String imagePath = intent.getStringExtra("imagePath");
        stateCode = intent.getIntExtra("fromHomePage", -1);
        // load the photo and text
        walkEditText.setText(content);
        if (imagePath != null && imagePath.length() > 0) {
            Glide.with(WalkEditActivity.this).load(imagePath).into(walkImageView);
        }

    }

    /**
     * when user click the cancel, cancel to save diary item
     *
     * @param view
     */
    public void cancelEditBtn(View view) {
        //check whether user want to cancel to save info
        AlertDialog.Builder builder = new AlertDialog.Builder(WalkEditActivity.this);
        builder.setTitle(R.string.dialog_cancel_title)
                .setMessage(R.string.dialog_cancel_edit_add_item_msg)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // discard the data and jump to the main page
                        walkEditText.setText("");
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
        selectPhotoAndAll(walkImageView);
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
                        Glide.with(WalkEditActivity.this).load(result.get(0).getPath()).into(imageView);
                        //将bitmap图片传入后端
                        //imageUpLoad(result.get(0).getRealPath());
                        submitPicture(result.get(0).getRealPath());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(WalkEditActivity.this, "error", Toast.LENGTH_SHORT).show();
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
    public void clickWalkSave(View view) {
        Intent intentFromPrevious = getIntent();
        //String dateDiary = intentFromPrevious.getExtras().getString("dateDiary");
        Log.d(TAG, "clickWalkSave: dateDiary " + dateDiary);
        //check update or insert data
        if (stateCode == 666) {
            // need id to update
            Intent intent = getIntent();
            int id = intent.getExtras().getInt("id");
            Glide.with(WalkEditActivity.this).load(path).into(walkImageView);
            walkStudyEventDao.update(new WorkStudyEventItem(id,walkEditText.getText().toString(), path, "walk", dateDiary,R.drawable.walk));
        } else {
            walkStudyEventDao.insertItem(new WorkStudyEventItem(walkEditText.getText().toString(), path, "walk", dateDiary,R.drawable.walk));
        }
        //back to the Home Page
        Toast.makeText(this, "save successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(WalkEditActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * when user click the button,show date picker
     *
     * @param view
     */
    public void showDatePickerWalk(View view) {
        DatePickerFragmentWalk datePickerFragment = new DatePickerFragmentWalk();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragmentWalk extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Log.d(TAG, "onDateSet: year" + year + "month: " + month + "day: " + day);
            // convert the int to the date
            dateDiary = DateConvertUtil.convertFromInt(month, day);

        }
    }
}
