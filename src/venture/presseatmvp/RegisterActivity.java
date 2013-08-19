package venture.presseatmvp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		if (fileList().length != 0) {
			// Log.d("Pizza", "Files in dir");
			EditText eName = (EditText) findViewById(R.id.registerName);
			EditText ePhone = (EditText) findViewById(R.id.registerPhone);
			EditText eAddress = (EditText) findViewById(R.id.registerAddress);

			FileInputStream fis;
			try {
				// Read string from input
				fis = openFileInput("regInfo");
				StringBuffer fileContent = new StringBuffer("");
				byte[] buffer = new byte[1024];
				while (fis.read(buffer) != -1) {
					fileContent.append(new String(buffer));
				}
				String json = fileContent.toString();
				json = json.substring(json.indexOf('{'),
						json.lastIndexOf('}') + 1);
				// X Read string from input

				Log.d("Pizza", json);
				JSONObject js = new JSONObject(json);
				String jsonName = js.getString("name");
				String jsonPhone = js.getString("phone");
				String jsonAddress = js.getString("address");
				eName.setText(jsonName);
				ePhone.setText(jsonPhone);
				eAddress.setText(jsonAddress);
			} catch (FileNotFoundException e) {
				Log.e("Pizza", "Read - Error File not found");
				e.printStackTrace();
			} catch (JSONException e) {
				Log.e("Pizza", "Read - Json exception");
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("Pizza", "Read - IOException");
				e.printStackTrace();
			}
			// Intent intent = new Intent(this.getApplicationContext(),
			// HomeActivity.class);
			// startActivity(intent);
		}
		Button b = (Button) findViewById(R.id.registerButton);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText eName = (EditText) findViewById(R.id.registerName);
				EditText ePhone = (EditText) findViewById(R.id.registerPhone);
				EditText eAddress = (EditText) findViewById(R.id.registerAddress);
				String name = eName.getText().toString();
				String phone = ePhone.getText().toString();
				String address = eAddress.getText().toString();
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("name", name);
					jsonObject.put("phone", phone);
					jsonObject.put("address", address);
					Log.d("Pizza", jsonObject.toString());
				} catch (Exception e) {
					Log.e("Pizza", "Error in json");
					e.printStackTrace();
				}
				try {
					FileOutputStream fos = openFileOutput("regInfo",
							Context.MODE_PRIVATE);
					fos.write(jsonObject.toString().getBytes());
					fos.close();
				} catch (FileNotFoundException e1) {
					Log.e("Pizza", "Write - Error File not found");
					e1.printStackTrace();
				} catch (IOException e) {
					Log.e("Pizza", "Error in Write or Close, IO exception");
					e.printStackTrace();
				}
				Intent intent = new Intent(getApplicationContext(),
						HomeActivity.class);
				startActivity(intent);
			}
		});
		// Log.d("Pizza", "No Files in Dir");

	}
	/*
	 * public String readToString(FileInputStream fis) { String s = ""; int cur;
	 * byte[] bytes = new byte[0]; try { cur = fis.read(readString.getBytes());
	 * while (cur != -1) { s += cur + " "; cur = fis.read(); } bytes = new
	 * byte[s.length()]; for (int i = 0; i < s.length(); i++) { byte b = (byte)
	 * (s.charAt(i) - '0'); bytes[i] = b; Log.d("pizza", b + ""); } } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * } return new String(bytes); }
	 */
}
