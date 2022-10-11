package comp5216.sydney.edu.au.diarypro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import comp5216.sydney.edu.au.diarypro.adapter.DiaryItemListViewAdapter;
import comp5216.sydney.edu.au.diarypro.entity.DiaryItem;

public class HomeActivity extends AppCompatActivity {

    private Button settingBtn;
    private ListView diaryItemListView;
    private List<DiaryItem> diaryItems;
    private DiaryItemListViewAdapter diaryItemListViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //init the components
        settingBtn = findViewById(R.id.settingBtn);
        diaryItemListView = this.findViewById(R.id.diaryListView);

        //TODO query the database to get the diary items
        diaryItems = new ArrayList<>();
        diaryItems.add(new DiaryItem(1,"work","Aug 23"));
        diaryItems.add(new DiaryItem(1,"work","Aug 23"));
        diaryItems.add(new DiaryItem(1,"work","Aug 23"));

        //create the listview adapter
        diaryItemListViewAdapter = new DiaryItemListViewAdapter(diaryItems,this);

        //connect the listview with adapter
        diaryItemListView.setAdapter(diaryItemListViewAdapter);

        //TODO invoke the long click and click action on list view
        //setupListViewListener();

        //set the setting button click listener
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // jump to the setting page
                Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * when user click the button,show date picker
     * @param view
     */
    public void showDatePicker(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
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
        }
    }

}
