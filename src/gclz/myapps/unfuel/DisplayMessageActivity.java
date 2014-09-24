package gclz.myapps.unfuel;

import gclz.myapps.tools.Analytics;
import gclz.myapps.tools.DeleteCar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author goncalo.louzada
 *
 */
public class DisplayMessageActivity extends Activity {

	String carName;
	String dir;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Intent intent = getIntent();
		this.carName = intent.getStringExtra(MainActivity.CAR_MESSAGE);
		this.dir = intent.getStringExtra(MainActivity.DIR_MESSAGE);
		
		
		Analytics a = new Analytics();
		a.getValues(carName, dir);
		
		
		// Get the message from the intent
//	    Intent intent = getIntent();
//	    String message = intent.getStringExtra(MainActivity.KM_MESSAGE);

	    // Create the text view
		setContentView(R.layout.activity_display_message);
		
	    TextView textViewKM = (TextView)findViewById(R.id.textViewKM);
	    textViewKM.setText(String.valueOf(a.getOldTotalKm()));
	    
	    TextView textViewFUEL = (TextView)findViewById(R.id.textViewFUEL);
	    textViewFUEL.setText(String.valueOf(a.getOldTotalFuel()));
	    
	    TextView textViewPRICE = (TextView)findViewById(R.id.textViewPRICE);	    
	    textViewPRICE.setText(String.valueOf(a.getOldTotalPrice()));
	    
	    TextView textViewTR = (TextView)findViewById(R.id.textViewTR);
	    textViewTR.setText(String.valueOf(a.getTotalRefills()));
	    
	    TextView textViewAVGPRICE = (TextView)findViewById(R.id.textViewAvgP);
	    textViewAVGPRICE.setText(String.valueOf(a.getOldAvgPrice()));
	    
	    TextView textViewC = (TextView)findViewById(R.id.textViewCONSUMPTION);
	    textViewC.setText(String.valueOf(a.getOldConsumption()));
	    
	    TextView textViewTripConsumption = (TextView)findViewById(R.id.textViewTripConsumption);
	    textViewTripConsumption.setText(String.valueOf(a.getTripConsumption()));
	    
	    TextView textViewCarName = (TextView)findViewById(R.id.textViewCarName);
	    textViewCarName.setText(carName);
	    
		// Show the Up button in the action bar.
//		setupActionBar();
		
		setResult(1, intent);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
//	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
//	private void setupActionBar() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//			getActionBar().setDisplayHomeAsUpEnabled(true);
//		}
//	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.display_message, menu);
//		return true;
//	}

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
	
	/** Called when the user clicks the Submit button */
	public void deleteData(View view) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				DisplayMessageActivity.this);

		// Setting Dialog Title
		alertDialog.setTitle("Delete Confirmation");

		// Setting Dialog Message
		alertDialog.setMessage("Are you sure you want to delete this car? There's no turning back!");


		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// User pressed YES button. Write Logic Here
						boolean delete = new DeleteCar().deleteFile(carName, dir);
						Intent intent = getIntent();
						if (delete) {
							setResult(2, intent);
						}
						else {
							setResult(3, intent);
						}
						
						finish();

					}
				});

		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// User pressed No button. Write Logic Here
						Toast.makeText(getApplicationContext(),
								"Canceled.", Toast.LENGTH_SHORT).show();
					}
				});



		// Showing Alert Message
		alertDialog.show();

	}

}
