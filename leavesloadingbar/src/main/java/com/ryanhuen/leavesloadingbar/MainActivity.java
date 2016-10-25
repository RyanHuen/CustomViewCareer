
package com.ryanhuen.leavesloadingbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ryanhuen.leavesloadingbar.view.LeavesLoadingBar;

public class MainActivity extends AppCompatActivity {
    LeavesLoadingBar mLeavesLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLeavesLoadingBar = (LeavesLoadingBar) findViewById(R.id.leaves_loading_bar);
    }
}
