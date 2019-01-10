package com.example.a13877.themovieapplication.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.a13877.themovieapplication.R;


public class AboutApplication extends AppCompatActivity {
    Toolbar toolbar;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_application);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("About Application");
        webView=findViewById(R.id.webview);
       // Typeface font = Typeface.createFromAsset(, "");
        String data = "<html><body><p1><i>The MovieApplication is is one of the best informative application for upcoming TV episodes and movies . This application delivers the best experience anywhere, anytime to its user<i></p1></body></html>";
        webView.loadData(data, "text/html", "UTF-8");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
