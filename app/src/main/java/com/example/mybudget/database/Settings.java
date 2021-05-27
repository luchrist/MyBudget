package com.example.mybudget.database;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mybudget.R;
import com.example.mybudget.database.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnreturn;
    TextInputEditText ineins;
    TextInputEditText inzwei;
    TextInputEditText indrei;
    double einnahmen;
    double ausgaben;
    double sparen;
    public Calendar calendar;
    public String currentDate;
    public String datum_Tag;
    public SimpleDateFormat dateFormat;
    Integer tag;
    SimpleDateFormat monthFormat;
    String datum_Monat;
    Integer month;
    int tage;
    boolean pressed;
    //MainActivity ma = new MainActivity();
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);


        btnreturn = (ImageButton) findViewById(R.id.returnbtn);
        btnreturn.setOnClickListener(this);
        ineins = (TextInputEditText) findViewById(R.id.input1);
        inzwei = (TextInputEditText) findViewById(R.id.input2);
        indrei = (TextInputEditText) findViewById(R.id.input3);
        sp = getSharedPreferences("SP",0);
        ineins.setText(sp.getString("Key3", "0"));
        inzwei.setText(sp.getString("Key4", "0"));
        indrei.setText(sp.getString("Key5", "0"));


    }

    @Override
    public void onClick(View v) {

        final SharedPreferences.Editor editor = sp.edit();
        double dbudget = Double.parseDouble(sp.getString("Key1","0"));
        double mbudget = Double.parseDouble(sp.getString("Key2","0"));
        if(v.equals(btnreturn)){
            double altein = Double.parseDouble(sp.getString("Key3","0"));
            try {
                einnahmen = Double.parseDouble(ineins.getText().toString());


                calendar = Calendar.getInstance();
                currentDate = DateFormat.getDateInstance().format(calendar.getTime());
                monthFormat = new SimpleDateFormat("mm", Locale.GERMANY);
                datum_Monat = monthFormat.format(new java.util.Date());
                month = Integer.parseInt(datum_Monat.toString());
                if(month == 1 || month == 3||month == 5||month == 7||month == 8||month==10||month==12){
                    tage = 31;
                }else if (month==2){
                    tage = 29;
                }else{
                    tage = 30;
                }
                ausgaben = Double.parseDouble(inzwei.getText().toString());
                double altaus = Double.parseDouble(sp.getString("Key4","0"));
                sparen = Double.parseDouble(indrei.getText().toString());
                double altsp = Double.parseDouble(sp.getString("Key5","0"));
                double dif = einnahmen -ausgaben-sparen;
                double altdif = altein - altaus -altsp;
                mbudget = mbudget + dif -altdif;
                mbudget = Math.round(mbudget * 100)/100.00;



                String dbudg = String.valueOf(dbudget);
                String mbudg = String.valueOf(mbudget);
                editor.putString("Key1", dbudg);
                editor.putString("Key2", mbudg);


                String strsp = String.valueOf(sparen);
                editor.putString("Key5", strsp);


                String straus = String.valueOf(ausgaben);
                editor.putString("Key4", straus);

                String strein = String.valueOf(einnahmen);
                editor.putString("Key3", strein);
                editor.commit();

                Toast.makeText(this, "Änderungen gespeichert", Toast.LENGTH_SHORT).show();
            }catch(Exception ex){
                Toast.makeText(this, "Kein gültiger Betrag", Toast.LENGTH_SHORT).show();
            }

                    if (altein == 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                        builder.setTitle("Ersteinrichtung erkannt")
                                .setMessage("Wollen Sie " + currentDate + " als Starttag festlegen <Ja> oder die bisherigen Tage des Monats nachtragen <Nein> ?")
                                .setPositiveButton("JA", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editor.putString("reset","true");
                                        editor.commit();
                                        back();

                                    }
                                });
                        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putString("reset", "false");
                                editor.commit();
                                back();

                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        back();
                    }



                //double dif = einnahmen-altein;



                /*dbudget = dbudget + dif/tage;
                mbudget = mbudget + dif;
                dbudget = Math.round(dbudget * 100)/100.00;
                mbudget = Math.round(mbudget * 100)/100.00;

                 */


                /*dif = ausgaben-altaus;
                dbudget = dbudget - dif/tage;
                mbudget = mbudget - dif;
                dbudget = Math.round(dbudget * 100)/100.00;

                mbudget = Math.round(mbudget * 100)/100.00;

                 */



                /*dif = sparen-altsp;
                dbudget = dbudget - dif/tage;
                mbudget = mbudget - dif;
                dbudget = Math.round(dbudget * 100)/100.00;

                 */





        }

    }

    private void back() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }


}
