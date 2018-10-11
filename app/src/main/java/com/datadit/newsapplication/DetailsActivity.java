package com.datadit.newsapplication;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    private String nAuthor, nName, nDate, nDescription, nTitle, nUrl, nImageUrl;
    private TextView newsAuthor, newsName, newsDate, newsDescription, newsTitle, textViewLink;
    private ImageView imageViewNews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageViewNews = findViewById(R.id.imageViewNews);
        textViewLink = findViewById(R.id.textViewLink);

        nAuthor = getIntent().getStringExtra("author");
        nName = getIntent().getStringExtra("name");
        nDate = getIntent().getStringExtra("date");
        nDescription = getIntent().getStringExtra("description");
        nTitle = getIntent().getStringExtra("title");
        nUrl = getIntent().getStringExtra("url");
        nImageUrl = getIntent().getStringExtra("imageUrl");

        textViewLink.setClickable(true);
        textViewLink.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href =" + nUrl + ">Click here to read more</a>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textViewLink.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
        }


        if (nImageUrl != null && !TextUtils.isEmpty(nImageUrl)) {
            if (imageViewNews.getVisibility() == View.GONE) {
                imageViewNews.setVisibility(View.VISIBLE);
            }
            Glide.with(this).load(nImageUrl).into(imageViewNews);
        } else {
            imageViewNews.setVisibility(View.GONE);
        }

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
