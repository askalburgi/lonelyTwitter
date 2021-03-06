/*
Copyright (c) 2016 Team 20, CMPUT 301, University of Alberta - All Rights Reserved.
You may use, copy or distribute this code under terms and conditions of University of Alberta
and Code of Student Behavior.

Please contact www.askalburg.com for more details or queestions.
*/

package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * The type Lonely twitter activity.
 */
public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private ListView oldTweetsList;
	private List<Tweet> tweetList = new ArrayList<Tweet>();
	private ArrayAdapter<Tweet> tweetAdapter;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	public ListView getOldTweetsList() {
		return oldTweetsList;
	}

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();


//				Date theDate = new Date();
				Tweet newTweet = new NormalTweet(text);
//				try {
//					newTweet.setMessage("test");
//				} catch (TweetTooLongException e) {
//					e.printStackTrace();
//				}
//				newTweet.getMessage();

//				ImportantTweet newImportantTweet = new ImportantTweet(text);
//				newImportantTweet.getMessage();

//				ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
				tweetList.add(newTweet);
//				tweetList.add(newImportantTweet);
				tweetAdapter.notifyDataSetChanged();

				saveInFile();
//				saveInFile(text, new Date(System.currentTimeMillis()));
//				finish();

			}
		});

		Button clearButton = (Button) findViewById(R.id.clear);
		clearButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tweetList.clear();
				tweetAdapter.notifyDataSetChanged();
				saveInFile();
			}
		});
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

		oldTweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(LonelyTwitterActivity.this, EditTweetActivity.class);
				startActivity(intent);
				intent.putExtra("tweet", (Tweet) oldTweetsList.getItemAtPosition(position));
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
//		String[] tweets =
 		loadFromFile();
		this.tweetAdapter = new ArrayAdapter<Tweet>(this,
				R.layout.list_item, tweetList);
		oldTweetsList.setAdapter(this.tweetAdapter);
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"LonelyTwitter Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://ca.ualberta.cs.lonelytwitter/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	/**
	 * loads from file which is global variable FILENAME, depends on gson
	 */
	private void loadFromFile() {
		ArrayList<String> tweets = new ArrayList<String>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//			String line = in.readLine();
//			while (line != null) {
//				tweets.add(line);
//				line = in.readLine();
//			}
			Gson gson = new Gson();
			// code from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt TODAY
			Type lisType = new TypeToken<ArrayList<NormalTweet>>(){}.getType();
			tweetList = gson.fromJson(in, lisType);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//throw new RuntimeException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		return tweets.toArray(new String[tweets.size()]);
	}

	/**
	 * saves tweetlist to FILENAME using gson
	 */
//	private void saveInFile(String text, Date date) {
	private void saveInFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
//					Context.MODE_APPEND);
					0);
			OutputStreamWriter writer = new OutputStreamWriter(fos);
			Gson gson = new Gson();
			gson.toJson(tweetList, writer);
			writer.flush();
//			fos.write(new String(date.toString() + " | " + text)
//					.getBytes());
//			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			throw new RuntimeException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"LonelyTwitter Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://ca.ualberta.cs.lonelytwitter/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}
}