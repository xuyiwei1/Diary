package comp5216.sydney.edu.au.diarypro;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserPrivacyActivity extends AppCompatActivity {

    private TextView privacyTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_privacy);
        privacyTextView = this.findViewById(R.id.privacyTextView);
        privacyTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
