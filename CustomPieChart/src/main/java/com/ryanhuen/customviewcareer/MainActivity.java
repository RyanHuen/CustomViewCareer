
package com.ryanhuen.customviewcareer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ryanhuen.customviewcareer.model.PieModel;
import com.ryanhuen.customviewcareer.view.PieView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PieView mPieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPieView = (PieView) findViewById(R.id.pieView);
        List<PieModel> models = new ArrayList<>();
        models.add(new PieModel("test1", 23.5f));
        models.add(new PieModel("test2", 34.5f));
        models.add(new PieModel("test3", 56.5f));
        models.add(new PieModel("test4", 78.5f));
        models.add(new PieModel("test5", 76.5f));
        mPieView.setStartAngle(100);
        mPieView.setModels(models);

    }
}
