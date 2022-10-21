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

import androidx.appcompat.app.AppCompatActivity;

import comp5216.sydney.edu.au.diarypro.dao.UserItemDao;
import comp5216.sydney.edu.au.diarypro.dao.WorkStudyEventDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.entity.UserItem;
import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;

public class RegisterActivity  extends AppCompatActivity {
    private EditText username;
    private EditText password_1;
    private EditText password_2;
    private TextView message;
    private Button registerButton;
    private Button cancelButton;

    private AppDatabase appDatabase;
    private UserItemDao UserItemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        password_1 = findViewById(R.id.password_1);
        password_2 = findViewById(R.id.password_2);
        message = findViewById(R.id.message);
        registerButton = findViewById(R.id.register);
        cancelButton = findViewById(R.id.cancel);

        registerButton.setOnClickListener(view -> register());

        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        UserItemDao = appDatabase.userItemDao();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check whether user want to cancel to save info
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Register is not complete yet")
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
    public void register() {
        if(password_1.getText().toString().equals(password_2.getText().toString())) {
            if (UserItemDao.checkUserExist(username.getText().toString()) == 0) {
                UserItemDao.insertItem(new UserItem(username.getText().toString(), password_1.getText().toString()));
                Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "username already exist", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "not same", Toast.LENGTH_SHORT).show();
        }
    }
}