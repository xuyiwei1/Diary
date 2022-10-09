package comp5216.sydney.edu.au.diarypro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private Button editBtn;
    private Button agreementBtn;
    private Button privacyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //init components
        editBtn = this.findViewById(R.id.editBtn);
        agreementBtn = this.findViewById(R.id.agreementBtn);
        privacyBtn = this.findViewById(R.id.privacyBtn);

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
    }
}
