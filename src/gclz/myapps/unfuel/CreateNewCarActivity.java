package gclz.myapps.unfuel;

import java.io.FileWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author goncalo.louzada
 *
 */
public class CreateNewCarActivity extends Activity {

	private String dir = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Intent intent = getIntent();
		this.dir = intent.getStringExtra(MainActivity.DIR_MESSAGE);
		
		System.out.println("CREATE_NEW_CAR " + dir);

		
		setContentView(R.layout.activity_create_new_car);
		// Show the Up button in the action bar.
		// setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	// @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	// private void setupActionBar() {
	// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	// getActionBar().setDisplayHomeAsUpEnabled(true);
	// }
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_new_car, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean createCar(View view) {
		EditText carName = (EditText) findViewById(R.id.carName);
		String carNameString = carName.getText().toString();
		if (carNameString.equals("")) {
			Toast.makeText(getApplicationContext(), "Insert valid car name",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		else {
			
			if (carNameString.endsWith(" ")) {
				carNameString = carNameString.substring(0, carNameString.length()-1);

			}

			try {
				CSVWriter writer = new CSVWriter(new FileWriter(dir	+ carNameString + ".csv"));

				writer.writeNext(new String[] { "CarName", carNameString });
				writer.writeNext(new String[] { "OriginalKm", "0" });
				writer.writeNext(new String[] { "TotalRefills", "0" });
				writer.writeNext(new String[] { "TotalKm", "0" });
				writer.writeNext(new String[] { "TotalPrice", "0" });
				writer.writeNext(new String[] { "AvgPrice", "0", "0" });
				writer.writeNext(new String[] { "TotalFuel", "0" });
				writer.writeNext(new String[] { "Consumption", "0", "0" });
				writer.writeNext(new String[] { "TripConsumption", "0" });
				writer.writeNext(new String[] { "TripFuel", "0" });
				writer.close();
				

				Toast.makeText(getApplicationContext(),
						"Car " + carNameString + " created!",
						Toast.LENGTH_SHORT).show();

				finish();

				return true;
			}

			catch (Exception e) {
				// e.printStackTrace();
				System.out.println("Exception creating car " + e);
				Toast.makeText(getApplicationContext(), "Car creation FAILED",
						Toast.LENGTH_SHORT).show();

				return false;

			}
		}

	}

}
