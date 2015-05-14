package trinkgeldrechner.ninja.hein;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	Button btn_schlecht;
	Button btn_normal;
	Button btn_excellent;
	TextView txt_trinkgeld;
	EditText txt_eingabe;
	
	public static int prozentsatz = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// EditText geldEingabe = (EditText) findViewById(R.id.Geldeingabe);
		// OnKeyListener eingabe = View.setOnKeyListener(geldEingabe);

		txt_trinkgeld = (TextView) findViewById(R.id.txt_trinkgeld);

		btn_schlecht = (Button) findViewById(R.id.buttonSchlecht);
		btn_normal = (Button) findViewById(R.id.buttonNormal);
		btn_excellent = (Button) findViewById(R.id.buttonExcellent);

		btn_schlecht.setOnClickListener(this);
		btn_normal.setOnClickListener(this);
		btn_excellent.setOnClickListener(this);

		txt_eingabe = (EditText) findViewById(R.id.txt_eingabe);
		//		Optionales Handling ungültiger Eingaben
		//		txt_eingabe.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"));
	}

	private void trinkgeldBerechnen() {
		float floatValue = 0;
		if (TextUtils.isEmpty(txt_eingabe.getText().toString())) {
			return;
		}
		try {
			floatValue = Float.parseFloat(txt_eingabe.getText().toString().replace(',', '.'));

			//	Trinkgeld 10 %
			floatValue = floatValue /100 * prozentsatz;
			
			//	Zahl auf 2 Nachkommastellen Runden
			floatValue = (float)Math.round(floatValue*100)/100;
			
			//	Wert ausgeben
			txt_trinkgeld.setText(String.valueOf(floatValue));
			
		} catch (NumberFormatException e) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Ungültige Zahl eingegeben", Toast.LENGTH_SHORT);
			toast.show();
		}

		// floatValue = Float.parseFloat(txt_eingabe.getText().toString());

		txt_trinkgeld.setText(String.valueOf(floatValue));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// Original version von der Virtualbox
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) { 

		case R.id.buttonSchlecht:
			Toast toast1 = Toast.makeText(getApplicationContext(),
					"Schlechter Service hier.", Toast.LENGTH_SHORT);
//			toast1.show();
			trinkgeldBerechnen();
			break;
		case R.id.buttonNormal:
			Toast toast2 = Toast.makeText(getApplicationContext(),
					"Normaler Service hier.", Toast.LENGTH_SHORT);
//			toast2.show();
			trinkgeldBerechnen();
			break;
		case R.id.buttonExcellent:
			Toast toast3 = Toast.makeText(getApplicationContext(),
					"Excellenter Service hier.", Toast.LENGTH_SHORT);
//			toast3.show();
			trinkgeldBerechnen();
			break;
		}

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.geiz:
	        	// create Dialog bla
	        	
	            return true;
	    
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	

}