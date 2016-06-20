package trinkgeldrechner.ninja.hein;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
	// initialisieren der variablen
	Button btn_schlecht;
	Button btn_normal;
	Button btn_excellent;
	Button btn_berechnen;
	TextView txt_trinkgeld;
	EditText txt_eingabe;
	TextView txt_prozentsatz;
	TextView txt_gesamt;

	public static int prozentsatz = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Textfelder finden
		txt_trinkgeld = (TextView) findViewById(R.id.txt_trinkgeld);
		txt_prozentsatz = (TextView) findViewById(R.id.txt_prozentsatz);
		txt_gesamt = (TextView) findViewById(R.id.txt_gesamt);

		txt_eingabe = (EditText) findViewById(R.id.txt_eingabe);

		// Buttons finden
		btn_berechnen = (Button) findViewById(R.id.buttonBerechnen);
		btn_schlecht = (Button) findViewById(R.id.buttonSchlecht);
		btn_normal = (Button) findViewById(R.id.buttonNormal);
		btn_excellent = (Button) findViewById(R.id.buttonExcellent);

		// OnClicklistener für die Buttons festlegen
		btn_berechnen.setOnClickListener(this);
		btn_schlecht.setOnClickListener(this);
		btn_normal.setOnClickListener(this);
		btn_excellent.setOnClickListener(this);

		// Optionales Handling ungültiger Eingaben
		// txt_eingabe.setKeyListener(DigitsKeyListener.getInstance("0123456789.,"));
		txt_prozentsatz.setText(getProzentsatzText(	));
		txt_trinkgeld.setText(getString(R.string.trinkgeld) + "0.0 €");
		txt_gesamt.setText("Gesamtbetrag: 0.0");
	}

	/*
	 * Liefert einen String, der den passenden Text zum Prozentsatz ausgibt
	 * 
	 * @return prozentsatztext String mit dem passendem Prozentsatz sowie
	 * zusatzinfos
	 */
	private String getProzentsatzText() {
		String prozentsatztext = "";
		prozentsatztext = "Prozentsatz: " + getProzentsatz() + "%";
		return prozentsatztext;
	}

	/*
	 * Get Methode um den Prozentsatz zu bekommen
	 * 
	 * @return prozentsatz Der aktuelle Prozentsatz
	 */
	public int getProzentsatz() {
		return prozentsatz;
	}

	/*
	 * Berechnet das Trinkgeld, sowie den Gesamtbetrag jeweils gerundet auf 2
	 * Stellen nach dem Komma
	 */
	private void trinkgeldBerechnen() {
		// 3 Variablen für das Trinkgeld, die Eingabe und den Gesamtwert
		float floatTrinkg = 0;
		float floatEingabe = 0;
		float floatGesamt = 0;

		// Falls kein Text drin steht, soll nix geschehen
		if (TextUtils.isEmpty(txt_eingabe.getText().toString())) {
			return;
		}
		// Catch wegen Eingabe und Verarbeitung von Werten vom User
		try {
			// Kommas werden hier zum Punkt als Trennzeichen zwischen euro und
			// cent. Dies verallgemeinert beides
			floatTrinkg = Float.parseFloat(txt_eingabe.getText().toString()
					.replace(',', '.'));
			floatEingabe = floatTrinkg;

			// Trinkgeld 10 %
			floatTrinkg = floatTrinkg / 100 * prozentsatz;

			// Zahl auf 2 Nachkommastellen Runden
			floatTrinkg = (float) Math.round(floatTrinkg * 100) / 100;
			floatEingabe = (float) Math.round(floatEingabe * 100) / 100;
			// Gesamtwert berechnen
			floatGesamt = floatEingabe + floatTrinkg;

		} catch (NumberFormatException e) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Ungültige Zahl eingegeben", Toast.LENGTH_SHORT);
			toast.show();
		}
		
		// Textausgabe anpassen für Trinkgeld und Gesamtbetrag
		txt_trinkgeld.setText("Trinkgeld entspricht: "
				+ String.valueOf(floatTrinkg) + " €");
		txt_gesamt.setText("Gesamtbetrag: " + String.valueOf(floatGesamt)
				+ " €");
	}

	/*
	 * Das Optionsmenü,
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// Original version von der Virtualbox
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		// Die Version, die bei mir in Arch generiert wurde.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * Verhalten des OnClickListener für die Buttons ist hier festgelegt
	 * 
	 */
	public void onClick(View v) {
		
		// Simple Switch case Anweisung abhängig von der übergebenen Id View v
		// man muss vom Objekt die id nehmen, da switch case über nummern geht
		// nicht über String.(zumindest in älteren Java versionen)
		switch (v.getId()) {
		
		case R.id.buttonBerechnen:

			trinkgeldBerechnen();
			break;

		case R.id.buttonSchlecht:
			// Farbe des Buttons verändern, falls er geklickt wurde
			// Die anderen beiden Buttons verlieren ihre veränderte Farbe
			btn_schlecht.getBackground().setColorFilter(Color.GREEN,
					PorterDuff.Mode.MULTIPLY);
			btn_normal.getBackground().clearColorFilter();
			btn_excellent.getBackground().clearColorFilter();

			break;
		case R.id.buttonNormal:
			// Farbe des Buttons verändern, falls er geklickt wurde
			// Die anderen beiden Buttons verlieren ihre veränderte Farbe
			btn_schlecht.getBackground().clearColorFilter();
			btn_normal.getBackground().setColorFilter(Color.GREEN,
					PorterDuff.Mode.MULTIPLY);
			btn_excellent.getBackground().clearColorFilter();

			break;
		case R.id.buttonExcellent:
			// Farbe des Buttons verändern, falls er geklickt wurde
			// Die anderen beiden Buttons verlieren ihre veränderte Farbe
			btn_schlecht.getBackground().clearColorFilter();
			btn_normal.getBackground().clearColorFilter();
			btn_excellent.getBackground().setColorFilter(Color.GREEN,
					PorterDuff.Mode.MULTIPLY);

			break;
		}

	}

	/*
	 * Sobald Settings geklick wird öffnet er den Settingsmenü eintrag,
	 * und das passende Dialogmenü, falls diese geklickt wird.
	 *  
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.geiz:
			// create Dialog
			dialogmenu();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * 
	 * Gibt die Passende Nummer zurück von 0 bis 2, passend zum Dialogmenü 0:
	 * prozentsatz = 15 1: prozentsatz = 10 2: prozentsatz = 5
	 * 
	 * @param int returnValue
	 * 
	 * @return int prozentsatz
	 */
	public int getGenerosityReference() {
		int returnValue = 1;
		switch (prozentsatz) {

		case 15:
			returnValue = 0;
			break;
		case 10:
			returnValue = 1;
			break;
		case 5:
			returnValue = 2;
			break;
		default:
			break;
		}
		return returnValue;
	}

	/*
	 * Das Dialogmenü, welches nach klicken von Geiz bestimmen in den Settings
	 * erzeugt wird
	 * 
	 * SingleCoiceItems werden hierbei genutzt, die nur bestätigt werden können
	 */
	public void dialogmenu() {
		// Text Array, welches die Auswahlmöglichkeiten bestimmt
		CharSequence generosity[] = new CharSequence[] { "Generös", "Normal",
				"Knauserig" };
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// Specify the list array, the items to be selected by default (null for
		// none),
		// and the listener through which to receive callbacks when items are
		// selected
		builder.setTitle(R.string.generosityDialog).setSingleChoiceItems(
				generosity, getGenerosityReference(),
				new DialogInterface.OnClickListener() {

					// Passender OnClickListener
					public void onClick(DialogInterface dialog, int which) {
						// Der Nutzer klick auf die unterschiedlichen optionen.
						// Danach wird der Prozentsatz dementsprechend angepasst
						switch (which) {

						case 0:
							prozentsatz = 15;
							break;
						case 1:
							prozentsatz = 10;
							break;
						case 2:
							prozentsatz = 5;
							break;

						default:
							break;
						}

						txt_prozentsatz.setText(getProzentsatzText());
					}

				});
		// Ok Button, cancel wird hier nicht gebraucht
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
					
					}
				});
		// anzeigen des Dialog-Interfaces
		builder.show();
	}
}
