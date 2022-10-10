package comp5216.sydney.edu.au.diarypro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditUserInfoActivity extends AppCompatActivity {

    private Button cancelBtn;
    private Button saveBtn;
    private EditText userNickNameEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        //init the components
        cancelBtn = this.findViewById(R.id.cancelBtn);
        saveBtn = this.findViewById(R.id.saveBtn);
        userNickNameEdit = this.findViewById(R.id.userNickNmaeEdit);

        //set listener
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check whether user want to cancel to save info
                AlertDialog.Builder builder = new AlertDialog.Builder(EditUserInfoActivity.this);
                builder.setTitle(R.string.dialog_cancel_title)
                        .setMessage(R.string.dialog_cancel_edit_add_item_msg)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // discard the data and jump to the main page
                                userNickNameEdit.setText("");
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
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO save the user info to the database

                //jump to the main page
                finish();
            }
        });
    }


}
