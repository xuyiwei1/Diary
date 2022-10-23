package comp5216.sydney.edu.au.diarypro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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


import comp5216.sydney.edu.au.diarypro.dao.RunWalkDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.engine.GlideEngine;
import comp5216.sydney.edu.au.diarypro.entity.RunWalkItem;
import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;
import comp5216.sydney.edu.au.diarypro.util.DateConvertUtil;
import comp5216.sydney.edu.au.diarypro.util.UserInfo;

public class WalkEditActivity extends AppCompatActivity {

    private EditText walkDistance;
    private EditText walkTime;
    private TextView walkCalories;

    private AppDatabase appDatabase;
    private RunWalkDao walkWalkItemDao;
    private String path;
    private static String dateDiary;
    // judge which page the data come from using to judge insert or update data
    private int stateCode;

    UserInfo userInfo;
    int weight;

    // for log
    private static final String TAG = "WalkEditActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_edit);
        // init the layout
        walkCalories = this.findViewById(R.id.walkCalories);
        walkTime = this.findViewById(R.id.walkTime);
        walkDistance = this.findViewById(R.id.walkDistance);

        //init the database
        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        walkWalkItemDao = appDatabase.runWalkItemDao();
        userInfo = (UserInfo)getApplicationContext();
        weight = userInfo.getUserItem().getWeight();



        //show the information when user click the item in Home page
        Intent intent = getIntent();
        String walkTimeStr = intent.getStringExtra("walkTime");
        String walkDistanceStr = intent.getStringExtra("walkDistance");
        String walkCaloriesStr = intent.getStringExtra("walkCalories");

        stateCode = intent.getIntExtra("fromHomePage", -1);
        // load the photo and text
        walkDistance.setText(walkDistanceStr);
        walkCalories.setText(walkCaloriesStr);
        walkTime.setText(walkTimeStr);

        walkTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0){
                    walkCalories.setText((String.format("%.2f", 0.164*weight*Integer.parseInt(walkTime.getText().toString()))));
                }else{
                    walkCalories.setText("0");
                }
            }
        });
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
                        walkDistance.setText("");
                        walkCalories.setText("");
                        walkTime.setText("0");
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
            walkWalkItemDao.update(new RunWalkItem(id,walkTime.getText().toString(),walkDistance.getText().toString(),walkCalories.getText().toString() , "walk", dateDiary,R.drawable.walk));
        } else {
            walkWalkItemDao.insertItem(new RunWalkItem(walkTime.getText().toString(),walkDistance.getText().toString(),walkCalories.getText().toString(), "walk", dateDiary,R.drawable.walk));
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
