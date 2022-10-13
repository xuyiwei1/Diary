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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import comp5216.sydney.edu.au.diarypro.adapter.DiaryItemListViewAdapter;
import comp5216.sydney.edu.au.diarypro.dao.DiaryItemDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.entity.DiaryItem;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private Button settingBtn;
    private ListView diaryItemListView;
    private List<DiaryItem> diaryItems;
    private DiaryItemListViewAdapter diaryItemListViewAdapter;
    // the database
    private AppDatabase appDatabase;
    private DiaryItemDao diaryItemDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //init the components
        settingBtn = findViewById(R.id.settingBtn);
        diaryItemListView = this.findViewById(R.id.diaryListView);

        //TODO query the database to get the diary items
        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        diaryItemDao = appDatabase.diaryItemDao();
        //diaryItemDao.insertItem(new DiaryItem("study","Otc 24"));
        diaryItems = diaryItemDao.getAll();
       /* diaryItems = new ArrayList<>();
        diaryItems.add(new DiaryItem(1,"work","Aug 23"));
        diaryItems.add(new DiaryItem(1,"work","Aug 23"));
        diaryItems.add(new DiaryItem(1,"work","Aug 23"));*/

        //create the listview adapter
        diaryItemListViewAdapter = new DiaryItemListViewAdapter(diaryItems,this);

        //connect the listview with adapter
        diaryItemListView.setAdapter(diaryItemListViewAdapter);

        //TODO invoke the long click and click action on list view
        setupListViewListener();

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
                                diaryItemDao.deleteById(diaryItems.get(position).getId());
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
        /*this.diaryItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DiaryItem shopItem = (DiaryItem) diaryItemListViewAdapter.getItem(position);
                Log.d(TAG, "onItemClick: position" + position + " getItem: " + shopItem);
                // switch to the edit add new item layout
                Intent intent = new Intent(MainActivity.this, EditAddItemActivity.class);
                if (intent != null) {
                    intent.putExtra("itemName", shopItem.getName());
                    intent.putExtra("itemNumber", shopItem.getNumber());
                    intent.putExtra("isChecked", shopItem.isChecked());
                    intent.putExtra("position", position);

                    //get the original id of the item to update in later, because of the delete action
                    ShopItemDao shopItemDao = new ShopItemDao(MainActivity.this);
                    List<ShopItem> shopItemList = shopItemDao.queryAll();
                    ShopItem originItem = null;
                    for (ShopItem item : shopItemList) {
                        if (item.getName().equals(shopItem.getName())) {
                            originItem = item;
                        }
                    }
                    intent.putExtra("id", originItem.getId());

                    mLauncher.launch(intent);
                    itemAdapter.notifyDataSetChanged();
                }
            }
        });*/
    }



}
