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
        startActivity(intent);
    }

    /**
     * click button jump to study edit page
     * @param view
     */
    public void jumpToStudyEditPage(View view) {
        Intent intent = new Intent(AddItemsActivity.this,StudyEditActivity.class);
        startActivity(intent);
    }

    /**
     * click button jump to study edit page
     * @param view
     */
    public void jumpToEventEditPage(View view) {
        Intent intent = new Intent(AddItemsActivity.this,EventEditActivity.class);
        startActivity(intent);
    }
}
