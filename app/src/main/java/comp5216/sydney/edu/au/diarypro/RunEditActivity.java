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

public class RunEditActivity extends AppCompatActivity {

    private EditText runDistance;
    private EditText runTime;
    private TextView runCalories;

    private AppDatabase appDatabase;
    private RunWalkDao runWalkItemDao;
    private String path;
    private static String dateDiary;
    // judge which page the data come from using to judge insert or update data
    private int stateCode;

    UserInfo userInfo;
    int weight;

    // for log
    private static final String TAG = "RunEditActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_edit);
        // init the layout
        runCalories = this.findViewById(R.id.runCalories);
        runTime = this.findViewById(R.id.runTime);
        runDistance = this.findViewById(R.id.runDistance);

        //init the database
        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        runWalkItemDao = appDatabase.runWalkItemDao();
        userInfo = (UserInfo)getApplicationContext();
        weight = userInfo.getUserItem().getWeight();



        //show the information when user click the item in Home page
        Intent intent = getIntent();
        String runTimeStr = intent.getStringExtra("runTime");
        String runDistanceStr = intent.getStringExtra("runDistance");
        String runCaloriesStr = intent.getStringExtra("runCalories");

        stateCode = intent.getIntExtra("fromHomePage", -1);
        // load the photo and text
        runDistance.setText(runDistanceStr);
        runCalories.setText(runCaloriesStr);
        runTime.setText(runTimeStr);

        runTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0){
                    runCalories.setText(String.format("%.2f", 0.164*weight*Integer.parseInt(runTime.getText().toString())));
                }else{
                    runCalories.setText("0");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(RunEditActivity.this);
        builder.setTitle(R.string.dialog_cancel_title)
                .setMessage(R.string.dialog_cancel_edit_add_item_msg)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // discard the data and jump to the main page
                        runDistance.setText("");
                        runCalories.setText("");
                        runTime.setText("0");
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
    public void clickRunSave(View view) {
        Intent intentFromPrevious = getIntent();
        //String dateDiary = intentFromPrevious.getExtras().getString("dateDiary");
        Log.d(TAG, "clickRunSave: dateDiary " + dateDiary);
        //check update or insert data
        if (stateCode == 666) {
            // need id to update
            Intent intent = getIntent();
            int id = intent.getExtras().getInt("id");
            runWalkItemDao.update(new RunWalkItem(id,runTime.getText().toString(),runDistance.getText().toString(),runCalories.getText().toString() , "run", dateDiary,R.drawable.run));
        } else {
            runWalkItemDao.insertItem(new RunWalkItem(runTime.getText().toString(),runDistance.getText().toString(),runCalories.getText().toString(), "run", dateDiary,R.drawable.run));
        }
        //back to the Home Page
        Toast.makeText(this, "save successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RunEditActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * when user click the button,show date picker
     *
     * @param view
     */
    public void showDatePickerRun(View view) {
        DatePickerFragmentRun datePickerFragment = new DatePickerFragmentRun();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragmentRun extends DialogFragment
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
