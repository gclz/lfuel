package gclz.myapps.unfuel;

import gclz.myapps.tools.Analytics;
import gclz.myapps.tools.GetCarArray;
import gclz.myapps.tools.RefillOperations;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author goncalo.louzada
 *
 */
public class MainActivity extends Activity {

	public final static String KM_MESSAGE = "gclz.myapps.unfuel.Km";
	public final static String FUEL_MESSAGE = "gclz.myapps.unfuel.Fuel";
	public final static String PRICE_MESSAGE = "gclz.myapps.unfuel.Price";
	public final static String CAR_MESSAGE = "gclz.myapps.unfuel.Car";
	public final static String DIR_MESSAGE = "gclz.myapps.unfuel.Dir";

	private String selectedCar = "";
	private String[] cars;
	private int oldKm = 0;
	private String internal_path;
	private String external_path = Environment.getExternalStorageDirectory()
			.getPath() + "/unfuel/";
	private String final_path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		internal_path = getApplicationContext().getFilesDir().toString();
		
		//Check if memory card is mounted, and which memory should be used
		if (Environment.getExternalStorageState().equals("mounted")) {
			cars = new GetCarArray().getCarNames(external_path);
			final_path = external_path;
		}

		else {
			cars = new GetCarArray().getCarNames(internal_path);
			final_path = internal_path + "/";
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		
		//If cars exist, select one
		if (cars.length > 0) {
			AlertDialog.Builder selectCarBuilder = new Builder(this);
			selectCarBuilder.setTitle("Select Car");

			selectCarBuilder.setItems(cars, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					selectedCar = cars[which];
					Button selectCar = (Button) findViewById(R.id.buttonSelect);
					selectCar.setText(selectedCar);
					EditText km = (EditText) findViewById(R.id.Km);
					oldKm = new Analytics().getOnlyKm(selectedCar, final_path);
					km.setHint("Enter Km (" + oldKm + ")");

				}

			});
			selectCarBuilder.show();

			//if no car exists, create one
		} else {
			final Intent intent = new Intent(this, CreateNewCarActivity.class);
			intent.putExtra(DIR_MESSAGE, final_path);
			startActivity(intent);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the user clicks the Submit button */
	public void submitData(View view) {
		if (selectedCar.equals("")) {
			Toast.makeText(getApplicationContext(), "Select car first!",
					Toast.LENGTH_SHORT).show();

		}

		else {
			int kmcheck = 0;
			int fuelcheck = 0;
			int pricecheck = 0;

			final Intent intent = new Intent(this, DisplayMessageActivity.class);
			intent.putExtra(CAR_MESSAGE, selectedCar);
			intent.putExtra(DIR_MESSAGE, final_path);

			// KM CHECK
			EditText km = (EditText) findViewById(R.id.Km);
			final String kmString = km.getText().toString();
			if (kmString.equals("") || kmString.equals("0")) {
				Toast.makeText(getApplicationContext(), "Insert valid Km!",
						Toast.LENGTH_SHORT).show();
			} else if (Integer.valueOf(kmString) <= oldKm) {
				Toast.makeText(getApplicationContext(),
						"Invalid Km: new Km must be bigger than " + oldKm,
						Toast.LENGTH_SHORT).show();
			}

			else {
				intent.putExtra(KM_MESSAGE, kmString);
				kmcheck = 1;
			}

			// FUEL CHECK
			EditText fuel = (EditText) findViewById(R.id.Fuel);
			final String fuelString = fuel.getText().toString();
			if (kmString.equals("") || kmString.equals("0")) {
				Toast.makeText(getApplicationContext(), "Insert valid Fuel!",
						Toast.LENGTH_SHORT).show();
			} else {
				intent.putExtra(FUEL_MESSAGE, fuelString);
				fuelcheck = 1;
			}

			// PRICE CHECK
			EditText price = (EditText) findViewById(R.id.Price);
			final String priceString = price.getText().toString();
			if (priceString.equals("") || priceString.equals("0")) {
				Toast.makeText(getApplicationContext(), "Insert valid Price!",
						Toast.LENGTH_SHORT).show();
			} else {

				intent.putExtra(PRICE_MESSAGE, priceString);
				pricecheck = 1;
			}

			// FINAL CHECK
			if (kmcheck == 0 || fuelcheck == 0 || pricecheck == 0) {

			} else {

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						MainActivity.this);

				// Setting Dialog Title
				alertDialog.setTitle("Confirmation");

				// Setting Dialog Message
				alertDialog.setMessage("Submit refill data?");

				// Setting Positive "Yes" Button
				alertDialog.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Do refill
								RefillOperations r = new RefillOperations();
								r.calculation(selectedCar, final_path,
										Double.valueOf(fuelString),
										Integer.valueOf(kmString),
										Double.valueOf(priceString));
								startActivityForResult(intent, 1);

							}
						});

				// Setting Negative "NO" Button
				alertDialog.setNegativeButton("NO",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								//Do nothing
							}
						});

				// Showing Alert Message
				alertDialog.show();
			}
		}
	}

	/** Called when the user clicks the Check Data button */
	public void checkData(View view) {

		if (selectedCar.equals("")) {
			Toast.makeText(getApplicationContext(), "Select car first!",
					Toast.LENGTH_SHORT).show();

		}

		else {
			Intent intent = new Intent(this, DisplayMessageActivity.class);
			intent.putExtra(CAR_MESSAGE, selectedCar);
			intent.putExtra(DIR_MESSAGE, final_path);
			startActivityForResult(intent, 1);
		}
	}

	public void selectCar(View view) {
		
		cars = new GetCarArray().getCarNames(final_path);
		
		for (String s:cars) {
			System.out.println(s);
		}

		AlertDialog.Builder selectCarBuilder = new Builder(this);
		selectCarBuilder.setTitle("Select Car");

		selectCarBuilder.setItems(cars, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				selectedCar = cars[which];
				Button selectCar = (Button) findViewById(R.id.buttonSelect);
				selectCar.setText(selectedCar);
				EditText km = (EditText) findViewById(R.id.Km);
				oldKm = new Analytics().getOnlyKm(selectedCar, final_path);
				km.setHint("Enter Km (" + oldKm + ")");
				km.setText(null);
			}

		});
		selectCarBuilder.show();

	}

	public void addCar(View view) {
		final Intent intent = new Intent(this, CreateNewCarActivity.class);
		intent.putExtra(DIR_MESSAGE, final_path);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		
		if (resultCode == 2) {
			//A CAR WAS DELETED
			Button selectCar = (Button) findViewById(R.id.buttonSelect);
			selectCar.setText("Select Car");
			Toast.makeText(getApplicationContext(), "Car " + selectedCar + " deleted!",
					 Toast.LENGTH_SHORT).show();
			selectedCar = "";
			EditText km = (EditText) findViewById(R.id.Km);
			km.setHint("Enter Km");

		}
		
		if (resultCode == 3) {
			//FAILED TO DELETE A CAR
			Toast.makeText(getApplicationContext(), "Error, car " + selectedCar + " couldn't be deleted!",
					 Toast.LENGTH_SHORT).show();
		}

	}

}
