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
import android.os.Bundle;

public class ClingActivity extends Activity {

	EditText textOut;
	TextView textIn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mute);

		textOut = (EditText)findViewById(R.id.textout);
	    Button buttonSend = (Button)findViewById(R.id.send);
	    textIn = (TextView)findViewById(R.id.textin);
	    buttonSend.setOnClickListener(buttonSendOnClickListener);
	}
	

	Button.OnClickListener buttonSendOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Socket socket = null;
					DataOutputStream dataOutputStream = null;
					OutputStream out = null;
					try {
						socket = new Socket(your IP, 4445);
						out = socket.getOutputStream();
						//dataOutputStream = new DataOutputStream(socket.getOutputStream());
						out.write(textOut.getText().toString().getBytes());
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (socket != null) {
							try {
								socket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						if (dataOutputStream != null) {
							try {
								dataOutputStream.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			});
			thread.start();
		}
	};
}
