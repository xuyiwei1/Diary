package comp5216.sydney.edu.au.diarypro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import comp5216.sydney.edu.au.diarypro.adapter.DiaryItemListViewAdapter;
import comp5216.sydney.edu.au.diarypro.dao.FoodDao;
import comp5216.sydney.edu.au.diarypro.dao.RunWalkDao;
import comp5216.sydney.edu.au.diarypro.dao.WorkStudyEventDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.entity.DiaryItem;
import comp5216.sydney.edu.au.diarypro.entity.FoodItem;
import comp5216.sydney.edu.au.diarypro.entity.RunWalkItem;
import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;
import comp5216.sydney.edu.au.diarypro.util.DateConvertUtil;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private Button settingBtn;
    private Button addNewItemBtn;
    private ListView diaryItemListView;
    private static List<DiaryItem> diaryItems = new ArrayList<>();

    private List<WorkStudyEventItem> workStudyEventItems;
    private List<RunWalkItem> runWalkItems;
    private List<FoodItem> foodItems;

    private static DiaryItemListViewAdapter diaryItemListViewAdapter;
    private static String dateDiary;
    // the database
    private AppDatabase appDatabase;
    private static WorkStudyEventDao workStudyEventDao;
    private static RunWalkDao runWalkDao;
    private static FoodDao foodDao;
    // cloud service
    private FirebaseStorage storage = FirebaseStorage.getInstance();

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
        // query the food run .. table to get other item and add it to the diaryItems list
        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        workStudyEventDao = appDatabase.workStudyEventItemDao();
        runWalkDao = appDatabase.runWalkItemDao();
        foodDao = appDatabase.foodItemDao();
        // remember to clear the diaryItems because it was declare as static, all the diaryItems field share the same cache
        diaryItems.clear();
        workStudyEventItems = workStudyEventDao.getAll();
        for (WorkStudyEventItem workStudyEventItem : workStudyEventItems) {
            diaryItems.add(new DiaryItem(workStudyEventItem.getId(), workStudyEventItem.getType(), workStudyEventItem.getDateDiary(), workStudyEventItem.getImageInHomePage()));
        }

        runWalkItems = runWalkDao.getAll();
        for (RunWalkItem runWalkItem : runWalkItems) {
            diaryItems.add(new DiaryItem(runWalkItem.getId(), runWalkItem.getType(), runWalkItem.getDateDiary(), runWalkItem.getImageInHomePage()));
        }

        foodItems = foodDao.getAll();
        for (FoodItem foodItem : foodItems) {
            diaryItems.add(new DiaryItem(foodItem.getId(), foodItem.getType(), foodItem.getDateDiary(), foodItem.getImageInHomePage()));
        }

        //create the listview adapter
        diaryItemListViewAdapter = new DiaryItemListViewAdapter(diaryItems, this);

        //connect the listview with adapter
        diaryItemListView.setAdapter(diaryItemListViewAdapter);

        // invoke the long click and click action on list view
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
            // when user choose a date in home page, filter the diary item based on the date
            List<WorkStudyEventItem> itemByDate = workStudyEventDao.getItemByDate(dateDiary);
            List<RunWalkItem> runWalkDaoItemByDate = runWalkDao.getItemByDate(dateDiary);
            List<FoodItem> foodDaoItemByDate = foodDao.getItemByDate(dateDiary);
            //clear the item and fill the list view again
            diaryItems.clear();
            for (WorkStudyEventItem workStudyEventItem : itemByDate) {
                diaryItems.add(new DiaryItem(workStudyEventItem.getId(), workStudyEventItem.getType(), workStudyEventItem.getDateDiary(), workStudyEventItem.getImageInHomePage()));
            }
            for (RunWalkItem runWalkItem : runWalkDaoItemByDate) {
                diaryItems.add(new DiaryItem(runWalkItem.getId(), runWalkItem.getType(), runWalkItem.getDateDiary(), runWalkItem.getImageInHomePage()));
            }
            for (FoodItem foodItem : foodDaoItemByDate) {
                diaryItems.add(new DiaryItem(foodItem.getId(), foodItem.getType(), foodItem.getDateDiary(), foodItem.getImageInHomePage()));
            }
            diaryItemListViewAdapter.notifyDataSetChanged();
        }

        /**
         * when user click the cancel button, show all diary item
         *
         * @param dialog
         */
        @Override
        public void onCancel(@NonNull DialogInterface dialog) {
            super.onCancel(dialog);
            List<WorkStudyEventItem> all = workStudyEventDao.getAll();
            List<FoodItem> foodAll = foodDao.getAll();
            List<RunWalkItem> runWalkAll = runWalkDao.getAll();
            diaryItems.clear();
            for (WorkStudyEventItem workStudyEventItem : all) {
                diaryItems.add(new DiaryItem(workStudyEventItem.getId(), workStudyEventItem.getType(), workStudyEventItem.getDateDiary(), workStudyEventItem.getImageInHomePage()));
            }
            for (RunWalkItem runWalkItem : runWalkAll) {
                diaryItems.add(new DiaryItem(runWalkItem.getId(), runWalkItem.getType(), runWalkItem.getDateDiary(), runWalkItem.getImageInHomePage()));
            }
            for (FoodItem foodItem : foodAll) {
                diaryItems.add(new DiaryItem(foodItem.getId(), foodItem.getType(), foodItem.getDateDiary(), foodItem.getImageInHomePage()));
            }
            diaryItemListViewAdapter.notifyDataSetChanged();
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
                                DiaryItem diaryItem = (DiaryItem) diaryItemListViewAdapter.getItem(position);
                                if (diaryItem.getName().equals("work") || diaryItem.getName().equals("study") || diaryItem.getName().equals("event")) {
                                    workStudyEventDao.deleteById(diaryItems.get(position).getId());
                                } else if (diaryItem.getName().equals("run") || diaryItem.getName().equals("walk")) {
                                    runWalkDao.deleteById(diaryItems.get(position).getId());
                                } else if (diaryItem.getName().equals("food")) {
                                    foodDao.deleteById(diaryItems.get(position).getId());
                                }
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

        // handle the single click event
        this.diaryItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DiaryItem diaryItem = (DiaryItem) diaryItemListViewAdapter.getItem(position);
                //query the work study or event item based on diaryItem's id
                if (diaryItem.getName().equals("work") || diaryItem.getName().equals("study") || diaryItem.getName().equals("event")) {
                    WorkStudyEventItem workStudyEventItem = workStudyEventDao.getItemById(diaryItem.getId());
                    Log.d(TAG, "onItemClick: position" + position + " getItem: " + workStudyEventItem);
                    //TODO check the type of the diary item switch to the work study or event layout
                    Intent intent = new Intent(HomeActivity.this, WorkEditActivity.class);
                    String type = workStudyEventItem.getType();
                    if (type.equals("work")) {
                        intent = new Intent(HomeActivity.this, WorkEditActivity.class);
                    } else if (type.equals("study")) {
                        intent = new Intent(HomeActivity.this, StudyEditActivity.class);
                    } else if (type.equals("event")) {
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
                } else if (diaryItem.getName().equals("run") || diaryItem.getName().equals("walk")) {
                    RunWalkItem runWalkItem = runWalkDao.getItemById(diaryItem.getId());
                    Log.d(TAG, "onItemClick: position" + position + " getItem: " + runWalkItem.getId());
                    Intent intent = new Intent(HomeActivity.this, WorkEditActivity.class);
                    String type = runWalkItem.getType();
                    if (type.equals("run")) {
                        intent = new Intent(HomeActivity.this, RunEditActivity.class);
                    } else if (type.equals("walk")) {
                        intent = new Intent(HomeActivity.this, WalkEditActivity.class);
                    }
                    if (intent != null) {
                        intent.putExtra("runTime", "" + runWalkItem.getRunTime());
                        intent.putExtra("runDistance", "" + runWalkItem.getRunDistance());
                        intent.putExtra("runCalories", "" + runWalkItem.getRunCalories());
                        intent.putExtra("id", runWalkItem.getId());
                        intent.putExtra("fromHomePage", 666);
                        intent.putExtra("dateDiary", dateDiary);

                        mLauncher.launch(intent);
                        diaryItemListViewAdapter.notifyDataSetChanged();
                    }
                } else if (diaryItem.getName().equals("food")) {
                    FoodItem foodItem = foodDao.getItemById(diaryItem.getId());
                    Log.d(TAG, "onItemClick: position" + position + " getItem: " + foodItem.getId());
                    Intent intent = new Intent(HomeActivity.this, WorkEditActivity.class);
                    String type = foodItem.getType();

                    intent = new Intent(HomeActivity.this, FoodEditActivity.class);
                    if (intent != null) {
                        intent.putExtra("breakfastName", "" + foodItem.getBreakfastName());
                        intent.putExtra("lunchName", "" + foodItem.getLunchName());
                        intent.putExtra("dinnerName", "" + foodItem.getDinnerName());
                        intent.putExtra("breakfastCal", "" + foodItem.getBreakfastCal());
                        intent.putExtra("lunchCal", "" + foodItem.getLunchCal());
                        intent.putExtra("dinnerCal", "" + foodItem.getDinnerCal());
                        intent.putExtra("totalCal", "" + foodItem.getTotalCal());
                        intent.putExtra("id", foodItem.getId());
                        intent.putExtra("fromHomePage", 666);
                        intent.putExtra("dateDiary", dateDiary);

                        mLauncher.launch(intent);
                        diaryItemListViewAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    //get the data and show the data that we get in database
    ActivityResultLauncher<Intent> mLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                    });

    /**
     * upload content of diary item to the cloud
     *
     * @param view
     */
    public void uploadToCloud(View view) {
        //TODO if you want to test sync function, please change the rules in cloud service otherwise it cannot work.

        // Create a storage reference from our app
        List<WorkStudyEventItem> all = workStudyEventDao.getAll();
        List<RunWalkItem> walkRunAll = runWalkDao.getAll();
        List<FoodItem> foodAll = foodDao.getAll();
        for (WorkStudyEventItem workStudyEventItem : all) {
            StorageReference storageRef = storage.getReference().child(workStudyEventItem.getType());
            //upload content of diary
            storageRef.child(workStudyEventItem.getType() + UUID.randomUUID().toString().substring(0, 5) + ".txt").putBytes(workStudyEventItem.getContent().getBytes());
            //upload image of diary
            String imagePath = workStudyEventItem.getImagePath();
            File file = new File(imagePath);
            //if user do not attach photo in their dairy do not upload photo
            Uri uri = null;
            if (file == null) {
                continue;
            } else {
                uri = Uri.fromFile(file);
            }
            UploadTask uploadTask = storageRef.child(workStudyEventItem.getType()).putFile(uri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: upload a photo");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: upload a photo");
                }
            });
        }
        for (RunWalkItem runWalkItem : walkRunAll) {
            StorageReference storageRef = storage.getReference().child(runWalkItem.getType());
            //upload content of diary
            String content = runWalkItem.getType() + " distance: " + runWalkItem.getRunDistance() + "\n" + runWalkItem.getType() + " time: " + runWalkItem.getRunTime() + "\n" + runWalkItem.getType() + " calories: " + runWalkItem.getRunCalories();
            storageRef.child(runWalkItem.getType() + UUID.randomUUID().toString().substring(0, 5) + ".txt").putBytes(content.getBytes());
            /*//upload image of diary
            String imagePath = runWalkItem.getImagePath();
            File file = new File(imagePath);
            //if user do not attach photo in their dairy do not upload photo
            Uri uri = null;
            if(file == null) {
                continue;
            }else {
                uri = Uri.fromFile(file);
            }
            UploadTask uploadTask = storageRef.child(runWalkItem.getType()).putFile(uri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: upload a photo");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: upload a photo");
                }
            });*/
        }
        for (FoodItem foodItem : foodAll) {
            StorageReference storageRef = storage.getReference().child(foodItem.getType());
            //upload content of diary
            String content = "breakfast Name: " + foodItem.getBreakfastName() + "\n" + "breakfast cal: " + foodItem.getBreakfastCal() + "\n" + "lunch Name: " + foodItem.getLunchName() + "\n" + "lunch cal: " + foodItem.getLunchCal() + "\n" + "dinner Name: " + foodItem.getDinnerName() + "\n" + "dinner cal: " + foodItem.getDinnerCal() + "\n" + "total cal: " + foodItem.getTotalCal();
            storageRef.child(foodItem.getType() + UUID.randomUUID().toString().substring(0, 5) + ".txt").putBytes(content.getBytes());
        }
        Toast.makeText(this, "user driven synchronization success", Toast.LENGTH_SHORT).show();
    }

}
