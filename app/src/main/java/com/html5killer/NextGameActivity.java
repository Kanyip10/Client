package com.html5killer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NextGameActivity extends Activity {

    /* renamed from: net.androidiconpacks.findmulti.NextGameActivity.1 */
    class C06421 implements OnClickListener {
        C06421() {
        }

        public void onClick(View v) {
            NextGameActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_top, R.anim.fadeout);
        setContentView(R.layout.activity_next);
        ((Button) findViewById(R.id.btnNext)).setOnClickListener(new C06421());
    }
}
