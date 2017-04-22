package com.html5killer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SummaryActivity extends Activity {

    /* renamed from: net.androidiconpacks.findmulti.SummaryActivity.1 */
    class C06671 implements OnClickListener {
        C06671() {
        }

        public void onClick(View v) {
            SummaryActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ((Button) findViewById(R.id.btnFinish)).setOnClickListener(new C06671());
    }
}
