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


import comp5216.sydney.edu.au.diarypro.dao.FoodDao;
import comp5216.sydney.edu.au.diarypro.dao.RunWalkDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.engine.GlideEngine;
import comp5216.sydney.edu.au.diarypro.entity.FoodItem;
import comp5216.sydney.edu.au.diarypro.entity.RunWalkItem;
import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;
import comp5216.sydney.edu.au.diarypro.util.DateConvertUtil;
import comp5216.sydney.edu.au.diarypro.util.UserInfo;

public class FoodEditActivity extends AppCompatActivity {

    private EditText breakfastCal;
    private EditText lunchCal;
    private EditText dinnerCal;
    private TextView totalCal;

    private EditText breakfastName;
    private EditText lunchName;
    private EditText dinnerName;

    private AppDatabase appDatabase;
    private FoodDao foodItemDao;
    private String path;
    private static String dateDiary;
    // judge which page the data come from using to judge insert or update data
    private int stateCode;


    // for log
    private static final String TAG = "FoodEditActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);
        // init the layout
        breakfastCal = this.findViewById(R.id.breakfastCal);
        lunchCal = this.findViewById(R.id.lunchCal);
        dinnerCal = this.findViewById(R.id.dinnerCal);
        totalCal = this.findViewById(R.id.totalCal);

        breakfastName = this.findViewById(R.id.breakfastName);
        lunchName = this.findViewById(R.id.lunchName);
        dinnerName = this.findViewById(R.id.dinnerName);

        //init the database
        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        foodItemDao = appDatabase.foodItemDao();

        //show the information when user click the item in Home page
        Intent intent = getIntent();
        String breakfastNameStr = intent.getStringExtra("breakfastName");
        String lunchNameStr = intent.getStringExtra("lunchName");
        String dinnerNameStr = intent.getStringExtra("dinnerName");

        String breakfastCalStr = intent.getStringExtra("breakfastCal");
        String lunchCalStr = intent.getStringExtra("lunchCal");
        String dinnerCalStr = intent.getStringExtra("dinnerCal");

        String totalCalStr = intent.getStringExtra("totalCal");

        stateCode = intent.getIntExtra("fromHomePage", -1);
        // load the photo and text
        breakfastName.setText(breakfastNameStr);
        lunchName.setText(lunchNameStr);
        dinnerName.setText(dinnerNameStr);

        breakfastCal.setText(breakfastCalStr);
        lunchCal.setText(lunchCalStr);
        dinnerCal.setText(dinnerCalStr);

        totalCal.setText(totalCalStr);

        breakfastCal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0){
                    totalCal.setText(""+(
                            (breakfastCal.getText().toString().equals("")?0:Integer.parseInt(breakfastCal.getText().toString()))+
                            (lunchCal.getText().toString().equals("")?0:Integer.parseInt(lunchCal.getText().toString()))+
                            (dinnerCal.getText().toString().equals("")?0:Integer.parseInt(dinnerCal.getText().toString()))
                   ));
                }else{

                }
            }
        });
        lunchCal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0){
                    totalCal.setText(""+(
                            (breakfastCal.getText().toString().equals("")?0:Integer.parseInt(breakfastCal.getText().toString()))+
                                    (lunchCal.getText().toString().equals("")?0:Integer.parseInt(lunchCal.getText().toString()))+
                                    (dinnerCal.getText().toString().equals("")?0:Integer.parseInt(dinnerCal.getText().toString()))
                    ));
                }else{

                }
            }
        });
        dinnerCal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0){
                    totalCal.setText(""+(
                            (breakfastCal.getText().toString().equals("")?0:Integer.parseInt(breakfastCal.getText().toString()))+
                                    (lunchCal.getText().toString().equals("")?0:Integer.parseInt(lunchCal.getText().toString()))+
                                    (dinnerCal.getText().toString().equals("")?0:Integer.parseInt(dinnerCal.getText().toString()))
                    ));
                }else{

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
        AlertDialog.Builder builder = new AlertDialog.Builder(FoodEditActivity.this);
        builder.setTitle(R.string.dialog_cancel_title)
                .setMessage(R.string.dialog_cancel_edit_add_item_msg)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // discard the data and jump to the main page
                        breakfastName.setText("");
                        lunchName.setText("");
                        dinnerName.setText("");

                        breakfastCal.setText("0");
                        lunchCal.setText("0");
                        dinnerCal.setText("0");
                        totalCal.setText("0");
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
    public void clickFoodSave(View view) {
        Intent intentFromPrevious = getIntent();
        //String dateDiary = intentFromPrevious.getExtras().getString("dateDiary");
        Log.d(TAG, "clickFoodSave: dateDiary " + dateDiary);
        //check update or insert data
        if (stateCode == 666) {
            // need id to update
            Intent intent = getIntent();
            int id = intent.getExtras().getInt("id");
            foodItemDao.update(new FoodItem(id,breakfastName.getText().toString(),lunchName.getText().toString(),dinnerName.getText().toString(),breakfastCal.getText().toString(),lunchCal.getText().toString(),dinnerCal.getText().toString(),totalCal.getText().toString(),"food", dateDiary,R.drawable.food));
        } else {
            foodItemDao.insertItem(new FoodItem(breakfastName.getText().toString(),lunchName.getText().toString(),dinnerName.getText().toString(),breakfastCal.getText().toString(),lunchCal.getText().toString(),dinnerCal.getText().toString(),totalCal.getText().toString(),"food", dateDiary,R.drawable.food));
        }
        //back to the Home Page
        Toast.makeText(this, "save successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(FoodEditActivity.this, HomeActivity.class);
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
