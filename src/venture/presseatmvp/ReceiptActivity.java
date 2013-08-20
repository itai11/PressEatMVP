package venture.presseatmvp;

import java.io.FileInputStream;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReceiptActivity extends Activity {
	private int counter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receipt);
		counter = 0;
		TextView address = (TextView) findViewById(R.id.receiptAddressVal);
		TextView phone = (TextView) findViewById(R.id.receiptPhoneVal);
		try {
			// Read string from input
			FileInputStream fis = openFileInput("regInfo");
			StringBuffer fileContent = new StringBuffer("");
			byte[] buffer = new byte[1024];
			while (fis.read(buffer) != -1) {
				fileContent.append(new String(buffer));
			}
			String json = fileContent.toString();
			json = json.substring(json.indexOf('{'), json.lastIndexOf('}') + 1);
			// X Read string from input
			JSONObject js = new JSONObject(json);
			String jsonPhone = js.getString("phone");
			String jsonAddress = js.getString("address");
			address.setText(jsonAddress);
			phone.setText(jsonPhone);
		} catch (Exception e) {
			Log.e("Pizza", "Error in Receipt - reading json");
		}
		Button order = (Button) findViewById(R.id.receiptOrder);
		order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						HomeActivity.class);
				startActivity(intent);
			}
		});
		Button add = (Button) findViewById(R.id.receiptListAdd);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				counter++;
				TextView text = new TextView(getApplicationContext());
				text.setText("Testing string " + counter);
				LinearLayout list = (LinearLayout) findViewById(R.id.receiptLinear);
				list.addView(text);
			}
		});
		Button cancel = (Button) findViewById(R.id.receiptCancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						HomeActivity.class);
				startActivity(intent);
			}
		});
	}
}
