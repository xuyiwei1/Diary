package comp5216.sydney.edu.au.diarypro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import comp5216.sydney.edu.au.diarypro.util.UserInfo;

public class SettingActivity extends AppCompatActivity {

    private Button editBtn;
    private Button agreementBtn;
    private Button privacyBtn;
    private Button settingHomeBtn;
    private Button logoutBtn;

    private TextView nicknameLabel;

    UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //init components
        editBtn = this.findViewById(R.id.editBtn);
        agreementBtn = this.findViewById(R.id.agreementBtn);
        privacyBtn = this.findViewById(R.id.privacyBtn);
        settingHomeBtn = this.findViewById(R.id.settingHomeBtn);
        logoutBtn = this.findViewById(R.id.logoutBtn);
        nicknameLabel = this.findViewById(R.id.nickname);

        userInfo = (UserInfo)getApplicationContext();

        nicknameLabel.setText(userInfo.getUserItem().getNickname());

        // set listener
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jump to EditUserInfo page
                Intent intent = new Intent(SettingActivity.this,EditUserInfoActivity.class);
                startActivity(intent);
            }
        });
        agreementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jump to the agreement page
                Intent intent = new Intent(SettingActivity.this,UserAgreementActivity.class);
                startActivity(intent);
            }
        });
        privacyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jump to the privacy page
                Intent intent = new Intent(SettingActivity.this, UserPrivacyActivity.class);
                startActivity(intent);
            }
        });
        settingHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jump to the home page
                Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * click the add icon, jump to the add items page
     * @param view
     */
    public void jumpToTheHomePage(View view) {
        Intent intent = new Intent(SettingActivity.this,AddItemsActivity.class);
        startActivity(intent);
    }
}
