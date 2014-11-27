package com.example.dybbukpc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.remotemute.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class ClingActivity extends Activity {

	EditText textOut;
	TextView textIn;
	SharedPreferences prefs;
	static final String IP_PREF = "IP_PREF";
    static final int PORT = 4445; //just

			
;	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mute);
		prefs =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		textOut = (EditText) findViewById(R.id.textout);
		Button buttonCling = (Button) findViewById(R.id.cling);
		Button buttonIP = (Button) findViewById(R.id.ip);

		textIn = (TextView) findViewById(R.id.textin);
		buttonCling.setOnClickListener(buttonClingOnClickListener);
		buttonIP.setOnClickListener(buttonIPOnClickListener);

	}
	
	Button.OnClickListener buttonIPOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			AlertDialog.Builder alert = new AlertDialog.Builder(ClingActivity.this);

			alert.setTitle("Set IP");

			// Set an EditText view to get user input
			final EditText input = new EditText(ClingActivity.this);
			alert.setView(input);

			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String value = input.getText().toString();
							Editor editor = prefs.edit();
							editor.putString(IP_PREF, value);
							editor.commit();
						}
					});

			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Canceled.
						}
					});

			alert.show();
		}
	};
	
	Button.OnClickListener buttonClingOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			String ip = prefs.getString(IP_PREF, "");
			 new ClientTask(ip, PORT).execute();
		}
	};

	public class ClientTask extends AsyncTask<Void, Void, Void> {

		String IP;
		int port;

		ClientTask(String ip, int port) {
			this.IP = ip;
			this.port = port;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			Socket socket = null;
			DataOutputStream dataOutputStream = null;
			OutputStream out = null;
			try {
				socket = new Socket(IP, port);
				out = socket.getOutputStream();
				out.write(textOut.getText().toString().getBytes());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != socket) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (null != dataOutputStream) {
					try {
						dataOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}

	}
}
