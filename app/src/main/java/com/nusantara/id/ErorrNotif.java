package com.nusantara.id;


import com.nusantara.id.R;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.os.Bundle;
import android.graphics.Color;

public class ErorrNotif extends AppCompatActivity {

    private TextView error;
    @Override
    protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.activity_errornotif);
		this.error = (TextView) findViewById(R.id.crashwindow);
        this.error.setText(getIntent().getStringExtra("error"));
		Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
		setSupportActionBar(toolbar);
		toolbar.setTitleTextColor(Color.WHITE);
    }
}

