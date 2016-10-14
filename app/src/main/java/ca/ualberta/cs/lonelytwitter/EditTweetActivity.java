package ca.ualberta.cs.lonelytwitter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EditTweetActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tweet);

        TextView tweettext = (TextView) findViewById(R.id.TweetStr);
        tweettext.setText(((Tweet) getIntent().getSerializableExtra("tweet")).getMessage());
    }
}
