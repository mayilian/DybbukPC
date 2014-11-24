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
import android.os.AsyncTask;
import android.os.Bundle;

public class ClingActivity extends Activity {

	EditText textOut;
	TextView textIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mute);

		textOut = (EditText) findViewById(R.id.textout);
		Button buttonSend = (Button) findViewById(R.id.send);
		textIn = (TextView) findViewById(R.id.textin);
		buttonSend.setOnClickListener(buttonSendOnClickListener);
	}

	Button.OnClickListener buttonSendOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			new ClientTask(your IP, port number).execute();
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
