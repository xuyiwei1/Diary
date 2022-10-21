package comp5216.sydney.edu.au.diarypro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp5216.sydney.edu.au.diarypro.dao.UserItemDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.entity.UserItem;

public class LoginActivity  extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private TextView message;
    private Button loginButton;
    private Button registerButton;

    private AppDatabase appDatabase;
    private comp5216.sydney.edu.au.diarypro.dao.UserItemDao UserItemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        message = findViewById(R.id.message);
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);

        loginButton.setOnClickListener(view -> login());
        registerButton.setOnClickListener(view -> register());

        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        UserItemDao = appDatabase.userItemDao();
    }

    /**
     * click the plus icon and jump to add new diary item layout
     *
     * @param
     */
    public void login() {
        if (UserItemDao.checkUserExist(username.getText().toString()) == 0) {
            Toast.makeText(this, "username do not exist", Toast.LENGTH_SHORT).show();
        }
        else{
            if(UserItemDao.getPassword(username.getText().toString()).equals(password.getText().toString())){
                Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
                Intent  intent= new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "wrong password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * click the plus icon and jump to add new diary item layout
     *
     * @param
     */
    public void register() {
        Intent  intent= new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}