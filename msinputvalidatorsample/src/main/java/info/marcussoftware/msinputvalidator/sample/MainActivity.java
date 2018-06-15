package info.marcussoftware.msinputvalidator.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import info.marcussoftware.msinputvalidator.view.MSEditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customMask = ((TextView) findViewById(R.id.inputMask)).getText().toString();
                ((TextView) findViewById(R.id.textView)).setText("Mask " + customMask);
                ((MSEditText) findViewById(R.id.inputMasked)).setCustomMask(customMask);
            }
        });
    }
}
