package comp5216.sydney.edu.au.diarypro;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp5216.sydney.edu.au.diarypro.dao.UserItemDao;
import comp5216.sydney.edu.au.diarypro.database.AppDatabase;
import comp5216.sydney.edu.au.diarypro.entity.UserItem;
import comp5216.sydney.edu.au.diarypro.util.UserInfo;

public class LoginActivity  extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;

    private Button loginButton;
    private Button registerButton;

    UserInfo userInfo;

    private AppDatabase appDatabase;
    private UserItemDao UserItemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);

        loginButton.setOnClickListener(view -> login());
        registerButton.setOnClickListener(view -> register());

        appDatabase = AppDatabase.getDatabase(this.getApplication().getApplicationContext());
        UserItemDao = appDatabase.userItemDao();

        userInfo = (UserInfo)getApplicationContext();
    }

    /**
     * click the plus icon and jump to add new diary item layout
     *
     * @param
     */
    public void login() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (UserItemDao.checkUserExist(username) == 0) {
            Toast.makeText(this, "username do not exist", Toast.LENGTH_SHORT).show();
        }
        else{
            if(UserItemDao.getPassword(username).equals(password)){
                int id = UserItemDao.getId(username);
                UserItem useritem = UserItemDao.getById(id);
                userInfo.setId(id);
                userInfo.setUserItem(useritem);

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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}