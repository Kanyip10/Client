package com.html5killer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LostActivity extends Activity {

    /* renamed from: net.androidiconpacks.findmulti.LostActivity.1 */
    class C06401 implements OnClickListener {
        C06401() {
        }

        public void onClick(View v) {
            LostActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        ((Button) findViewById(R.id.btnPlayAgain)).setOnClickListener(new C06401());
    }
}
