package com.wk.magnifyingView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.wk.magnifyinglayout.R;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private MagnifierView magnifierView;
    private ConstraintLayout touchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        magnifierView = findViewById(R.id.magnifier);

        touchLayout = findViewById(R.id.layout);
        touchLayout.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        magnifierView.setTouch(v,event);
        return true;
    }
}