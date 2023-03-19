package com.example.deliverynest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AboutUs extends BaseActivity {
    ImageButton GithubNauman,GithubTejas,GithubKshitij,
    LinkedinNauman,LinkedinTejas,LinkedinKshitij,
    TelegramNauman,TelegramTejas,TelegramKshitij;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        GithubNauman=findViewById(R.id.GithubNauman);
        GithubTejas=findViewById(R.id.GithubTejas);
        GithubKshitij=findViewById(R.id.GithubKshitij);
        LinkedinNauman=findViewById(R.id.LinkedinNauman);
        LinkedinTejas=findViewById(R.id.LinkedinTejas);
        LinkedinKshitij=findViewById(R.id.LinkedinKshitij);
        TelegramNauman=findViewById(R.id.TelegramNauman);
        TelegramTejas=findViewById(R.id.TelegramTejas);
        TelegramKshitij=findViewById(R.id.TelegramKshitij);

        GithubNauman.setOnClickListener(v -> {
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/nauman8939"));
            startActivity(urlIntent);
        });
        GithubTejas.setOnClickListener(v -> {
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Tejas810"));
            startActivity(urlIntent);
        });
        GithubKshitij.setOnClickListener(v -> {
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/GhodakeKshitij"));
            startActivity(urlIntent);
        });
        LinkedinNauman.setOnClickListener(v -> {
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/nauman-pathan-5aa2b6231/"));
            startActivity(urlIntent);
        });
        LinkedinTejas.setOnClickListener(v -> {
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/tejas-kulkarni-611a1b1b7/"));
            startActivity(urlIntent);
        });
        LinkedinKshitij.setOnClickListener(v -> {
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/kshitij-ghodake-28212916b/"));
            startActivity(urlIntent);
        });
        TelegramNauman.setOnClickListener(v->{
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/kshitij-ghodake-28212916b/"));
            startActivity(urlIntent);
        });
        TelegramTejas.setOnClickListener(v->{
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/kshitij-ghodake-28212916b/"));
            startActivity(urlIntent);
        });
        TelegramKshitij.setOnClickListener(v->{
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/kshitij-ghodake-28212916b/"));
            startActivity(urlIntent);
        });
    }
}