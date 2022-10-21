package comp5216.sydney.edu.au.diarypro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import comp5216.sydney.edu.au.diarypro.dao.UserItemDao;
import comp5216.sydney.edu.au.diarypro.dao.WorkStudyEventDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.entity.UserItem;
import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;
import comp5216.sydney.edu.au.diarypro.util.UserInfo;

public class ChangePasswordActivity  extends AppCompatActivity {
    private EditText password_old;
    private EditText password_1;
    private EditText password_2;
    private Button submitButton;
    private Button cancelButton;

    private AppDatabase appDatabase;
    private UserItemDao UserItemDao;

    UserInfo userInfo;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        password_old = findViewById(R.id.oldPassword);
        password_1 = findViewById(R.id.password_1);
        password_2 = findViewById(R.id.password_2);
        submitButton = findViewById(R.id.submit);
        cancelButton = findViewById(R.id.cancel);

        submitButton.setOnClickListener(view -> change());

        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        UserItemDao = appDatabase.userItemDao();

        userInfo = (UserInfo)getApplicationContext();
        id = userInfo.getId();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check whether user want to cancel to save info
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                builder.setTitle("Change password is not complete yet")
                        .setMessage("Unsaved register will discard if you click yes")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // discard the data and jump to the main page
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
    }

    /**
     * click the register button and add new user to user db
     *
     * @param
     */
    public void change() {
        if(password_old.getText().toString().equals(UserItemDao.getPasswordById(id))){
            if(password_1.getText().toString().equals(password_2.getText().toString())){
                UserItemDao.changePassword(id,password_1.getText().toString());
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                Intent  intent= new Intent(ChangePasswordActivity.this,HomeActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "not same", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "old password is not same", Toast.LENGTH_SHORT).show();
        }
    }
}