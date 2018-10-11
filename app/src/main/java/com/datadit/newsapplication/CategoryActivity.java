package com.datadit.newsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonTechnology, buttonSports, buttonBusiness, buttonEntertainment;
    private String newsCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        buttonTechnology = findViewById(R.id.buttonTechnology);
        buttonSports = findViewById(R.id.buttonSports);
        buttonBusiness = findViewById(R.id.buttonBusiness);
        buttonEntertainment = findViewById(R.id.buttonEntertainment);

        buttonTechnology.setOnClickListener(this);
        buttonSports.setOnClickListener(this);
        buttonBusiness.setOnClickListener(this);
        buttonEntertainment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonTechnology) {
            newsCategory = "Technology";
        } else if (view == buttonEntertainment) {
            newsCategory = "Entertainment";
        } else if (view == buttonSports) {
            newsCategory = "Sports";
        } else if (view == buttonBusiness) {
            newsCategory = "Business";
        }
        Intent intent = new Intent(CategoryActivity.this, HomeActivity.class);
        intent.putExtra("Category", newsCategory);
        startActivity(intent);
    }
}
