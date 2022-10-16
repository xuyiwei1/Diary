package comp5216.sydney.edu.au.diarypro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import comp5216.sydney.edu.au.diarypro.adapter.DiaryItemListViewAdapter;
import comp5216.sydney.edu.au.diarypro.dao.DiaryItemDao;
import comp5216.sydney.edu.au.diarypro.dao.WorkStudyEventDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.entity.DiaryItem;
import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;
import comp5216.sydney.edu.au.diarypro.util.DateConvertUtil;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private Button settingBtn;
    private Button addNewItemBtn;
    private ListView diaryItemListView;
    private List<DiaryItem> diaryItems = new ArrayList<>();
    private List<WorkStudyEventItem> workStudyEventItems;
    private DiaryItemListViewAdapter diaryItemListViewAdapter;
    private static String dateDiary;
    // the database
    private AppDatabase appDatabase;
    private WorkStudyEventDao workStudyEventDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //init the components
        settingBtn = findViewById(R.id.settingBtn);
        diaryItemListView = this.findViewById(R.id.diaryListView);
        addNewItemBtn = this.findViewById(R.id.addNewItemBtn);

        //init the date of diary is now by default
        dateDiary = DateConvertUtil.getCurrentDate();

        //TODO query the database to get the diary items
        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        workStudyEventDao = appDatabase.workStudyEventItemDao();
        //diaryItemDao.insertItem(new DiaryItem("study","Otc 24"));
        workStudyEventItems = workStudyEventDao.getAll();
        for (WorkStudyEventItem workStudyEventItem : workStudyEventItems) {
            diaryItems.add(new DiaryItem(workStudyEventItem.getId(), workStudyEventItem.getType(), workStudyEventItem.getDateDiary()));
        }
       /* diaryItems = new ArrayList<>();
        diaryItems.add(new DiaryItem(1,"work","Aug 23"));
        diaryItems.add(new DiaryItem(1,"work","Aug 23"));
        diaryItems.add(new DiaryItem(1,"work","Aug 23"));*/

        //create the listview adapter
        diaryItemListViewAdapter = new DiaryItemListViewAdapter(diaryItems, this);

        //connect the listview with adapter
        diaryItemListView.setAdapter(diaryItemListViewAdapter);

        //TODO invoke the long click and click action on list view
        setupListViewListener();

        //set the setting button click listener
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // jump to the setting page
                Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * when user click the button,show date picker
     *
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
            Log.d(TAG, "onDateSet: year" + year + "month: " + month + "day: " + day);
            // convert the int to the date
            dateDiary = DateConvertUtil.convertFromInt(month, day);
        }
    }

    /**
     * click the plus icon and jump to add new diary item layout
     *
     * @param view
     */
    public void clickAddNewDiaryItem(View view) {
        Intent intent = new Intent(HomeActivity.this, AddItemsActivity.class);
        // pass the date to the diary edit page to store to the database
        intent.putExtra("dateDiary", dateDiary);
        startActivity(intent);
    }

    /**
     * handle the long click and click events
     */
    public void setupListViewListener() {
        this.diaryItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemLongClick: position:" + position + "row:" + id);
                //create a alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle(R.string.dialog_delete_title)
                        .setMessage(R.string.dialog_delete_shop_item_msg)
                        .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //remove the data from local database
                                workStudyEventDao.deleteById(diaryItems.get(position).getId());
                                //when click the delete button delete the item in the listview
                                diaryItems.remove(position);
                                diaryItemListViewAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // users click the cancel button, nothing happened
                            }
                        });
                //show the alertDialog
                builder.create().show();
                return true;
            }
        });

        //TODO handle the single click event
        this.diaryItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DiaryItem diaryItem = (DiaryItem) diaryItemListViewAdapter.getItem(position);
                //query the work study or event item based on diaryItem's id
                WorkStudyEventItem workStudyEventItem = workStudyEventDao.getItemById(diaryItem.getId());
                Log.d(TAG, "onItemClick: position" + position + " getItem: " + workStudyEventItem);
                //TODO check the type of the diary item switch to the work study or event layout
                Intent intent = new Intent(HomeActivity.this, WorkEditActivity.class);
                String type = workStudyEventItem.getType();
                if (type.equals("work")) {
                    intent = new Intent(HomeActivity.this, WorkEditActivity.class);
                }else if(type.equals("study")) {
                    intent = new Intent(HomeActivity.this, StudyEditActivity.class);
                }else if(type.equals("event")) {
                    intent = new Intent(HomeActivity.this, EventEditActivity.class);
                }
                if (intent != null) {
                    intent.putExtra("content", workStudyEventItem.getContent());
                    intent.putExtra("imagePath", workStudyEventItem.getImagePath());
                    intent.putExtra("id", workStudyEventItem.getId());
                    intent.putExtra("fromHomePage", 666);
                    intent.putExtra("dateDiary", dateDiary);

                    //get the original id of the item to update in later, because of the delete action
                    /*List<WorkStudyEventItem> shopItemList = workStudyEventDao.getAll();
                    WorkStudyEventItem originItem = null;
                    for (WorkStudyEventItem item : shopItemList) {
                        if (item.getName().equals(shopItem.getName())) {
                            originItem = item;
                        }
                    }
                    intent.putExtra("id", originItem.getId());*/

                    mLauncher.launch(intent);
                    diaryItemListViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    //get the data and show the data that we get in database
    ActivityResultLauncher<Intent> mLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                    });

}
