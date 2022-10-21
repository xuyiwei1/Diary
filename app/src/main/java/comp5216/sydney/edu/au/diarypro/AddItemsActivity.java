package comp5216.sydney.edu.au.diarypro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
    }

    /**
     * click button jump to work edit page
     * @param view
     */
    public void jumpToWorkEditPage(View view) {
        Intent intent = new Intent(AddItemsActivity.this,WorkEditActivity.class);
        // pass the date to the diary edit page
        Intent intentFromPrevious = getIntent();
        String dateDiary = intentFromPrevious.getStringExtra("dateDiary");
        intent.putExtra("dateDiary",dateDiary);
        startActivity(intent);
    }

    /**
     * click button jump to study edit page
     * @param view
     */
    public void jumpToStudyEditPage(View view) {
        Intent intent = new Intent(AddItemsActivity.this,StudyEditActivity.class);
        // pass the date to the diary edit page
        Intent intentFromPrevious = getIntent();
        String dateDiary = intentFromPrevious.getStringExtra("dateDiary");
        intent.putExtra("dateDiary",dateDiary);
        startActivity(intent);
    }

    /**
     * click button jump to study edit page
     * @param view
     */
    public void jumpToEventEditPage(View view) {
        Intent intent = new Intent(AddItemsActivity.this,EventEditActivity.class);
        // pass the date to the diary edit page
        Intent intentFromPrevious = getIntent();
        String dateDiary = intentFromPrevious.getStringExtra("dateDiary");
        intent.putExtra("dateDiary",dateDiary);
        startActivity(intent);
    }

    /**
     * click button jump to food edit page
     * @param view
     */
    public void jumpToFoodEditPage(View view) {
        Intent intent = new Intent(AddItemsActivity.this,FoodEditActivity.class);
        // pass the date to the diary edit page
        Intent intentFromPrevious = getIntent();
        String dateDiary = intentFromPrevious.getStringExtra("dateDiary");
        intent.putExtra("dateDiary",dateDiary);
        startActivity(intent);
    }
    /**
     * click button jump to run edit page
     * @param view
     */
    public void jumpToRunEditPage(View view) {
        Intent intent = new Intent(AddItemsActivity.this,RunEditActivity.class);
        // pass the date to the diary edit page
        Intent intentFromPrevious = getIntent();
        String dateDiary = intentFromPrevious.getStringExtra("dateDiary");
        intent.putExtra("dateDiary",dateDiary);
        startActivity(intent);
    }

    /**
     * click button jump to walk edit page
     * @param view
     */
    public void jumpToWalkEditPage(View view) {
        Intent intent = new Intent(AddItemsActivity.this,WalkEditActivity.class);
        // pass the date to the diary edit page
        Intent intentFromPrevious = getIntent();
        String dateDiary = intentFromPrevious.getStringExtra("dateDiary");
        intent.putExtra("dateDiary",dateDiary);
        startActivity(intent);
    }
}
