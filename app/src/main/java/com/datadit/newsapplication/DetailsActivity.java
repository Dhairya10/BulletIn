package com.datadit.newsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    String nAuthor, nName, nDate, nDescription, nTitle;
    TextView newsAuthor, newsName, newsDate, newsDescription, newsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nAuthor = getIntent().getStringExtra("author");
        nName = getIntent().getStringExtra("name");
        nDate = getIntent().getStringExtra("date");
        nDescription = getIntent().getStringExtra("description");
        nTitle = getIntent().getStringExtra("title");

        if (nAuthor.equals("null"))
            nAuthor = "Author Unknown";

        if (nName.equals("null"))
            nName = "Source Unknown";

        if (nDate.equals("null"))
            nDate = "Date Unknown";

        if (nTitle.equals("null"))
            nTitle = "Title";

        if (nDescription.equals("null"))
            nDescription = "Description";

        newsTitle = findViewById(R.id.textviewTitle);
        newsAuthor = findViewById(R.id.textViewAuthor);
        newsName = findViewById(R.id.textViewName);
        newsDate = findViewById(R.id.textViewDate);
        newsDescription = findViewById(R.id.textViewDescription);

        newsTitle.setText(nTitle);
        newsAuthor.setText(nAuthor);
        newsName.setText(nName);
        newsDate.setText(nDate);
        newsDescription.setText(nDescription);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_up, R.anim.move_down);
    }
}
