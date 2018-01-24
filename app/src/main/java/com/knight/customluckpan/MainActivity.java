package com.knight.customluckpan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.knight.customluckpan.weight.CustomRotateLuckPan;
import com.knight.customluckpan.weight.LuckPanLayout;

public class MainActivity extends AppCompatActivity implements LuckPanLayout.AnimationEndListener {
    private CustomRotateLuckPan customRotateLuckPan;
    private LuckPanLayout luckPanLayout;
    private ImageView go;
    private ImageView backgroud;
    private String[] strs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strs = getResources().getStringArray(R.array.names);
        luckPanLayout = (LuckPanLayout) findViewById(R.id.luckpan_layout);
        luckPanLayout.setAnimationEndListener(this);
        go = (ImageView) findViewById(R.id.go);
        backgroud = (ImageView) findViewById(R.id.backgroud);
    }

    public void rotation(View view) {
        luckPanLayout.rotate(0, 100);
    }

    @Override
    public void endAnimation(int position) {
        Toast.makeText(this, "Position = " + position + "," + strs[position], Toast.LENGTH_SHORT).show();

    }
}
