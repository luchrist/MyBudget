
package com.example.mybudget.database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mybudget.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PreferenceManager.OnActivityStopListener {
    ImageButton plusbtn;
    ImageButton minusbtn;
    ImageButton settingbtn;
    ImageButton reset;
    TextInputEditText input;
    TextInputEditText note;
    private Button logbuch;
    private Spinner cat;
    double zahl;
    public EditText editd;
    public EditText editm;
    public SharedPreferences sp;
    public double dbudget;
    double ndbudg;
    public double mbudget;
    double nmbudg;
    String newdb;
    String newmb;
    ProgressBar pb;
    public double ein;
    public double aus;
    public double bud;
    public double dif;
    public double calc;
    public int prog;
    int n;
    public Calendar calendar;
    public String currentDate;
    public String datum_Tag;
    public SimpleDateFormat dateFormat;
    Integer tag;
    SimpleDateFormat monthFormat;
    String datum_Monat;
    int month;
    int tage;
    String category;
    String notiz;
    //public ArrayList<String> logListe = new ArrayList<String>();
    double mbudgallg;
    double dbudgallg;
    double ausgaben;
    double einnahmen;
    double sparen;
    String newein;
    String newaus;
    String key;
    String betrag;
    public String logausgabe = "";
    String nstr;
    public boolean commit;
    private boolean safe;
    private double ausg;
    private String ausge;
    private boolean noteok = true;
    Toolbar toolbar;
    ImageButton info;
    private double car,food,job,free,lent,order,present,clothes,party,others;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("SP",0);
        //SharedPreferences.Editor editor = sp.edit();


        Spinner category = (Spinner) findViewById(R.id.category);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(myAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        pb = (ProgressBar) findViewById(R.id.pb);
        editd = (EditText) findViewById(R.id.editd);
        editm = (EditText) findViewById(R.id.editm);

        /*editd.setText(sp.getString("Key1", "0"));
        editm.setText(sp.getString("Key2","0"));

         */

        plusbtn = (ImageButton) findViewById(R.id.plusbtn);
        plusbtn.setOnClickListener(this);
        minusbtn = (ImageButton) findViewById(R.id.minusbtn);
        minusbtn.setOnClickListener(this);
        info = (ImageButton) findViewById(R.id.infobtn);
        info.setOnClickListener(this);
        settingbtn = (ImageButton) findViewById(R.id.setup);
        settingbtn.setOnClickListener(this);
        input = (TextInputEditText) findViewById(R.id.inputm);
        note = (TextInputEditText) findViewById(R.id.notetxt);
        cat = (Spinner) findViewById(R.id.category);
        logbuch = (Button)findViewById(R.id.logbuch);
        logbuch.setOnClickListener(this);
        reset = (ImageButton) findViewById(R.id.reset);
        reset.setOnClickListener(this);

        ein = Double.parseDouble(sp.getString("Key3","0"));
        aus = Double.parseDouble(sp.getString("Key4", "0"));
        bud = Double.parseDouble(sp.getString("Key2", "0"));
        sparen = Double.parseDouble(sp.getString("Key5","0"));
        einnahmen = Double.parseDouble(sp.getString("Key6","0"));
        ausgaben = Double.parseDouble(sp.getString("Key7","0"));

        n = Integer.parseInt(sp.getString("N", "10"));

        dif = ein -aus;
        /*calc = dif/100;
        bud = bud/calc;
        prog = (int) bud;
        pb.setProgress(prog);

         */

        calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        currentDate = currentDate + " | ";

        dateFormat = new SimpleDateFormat("dd", Locale.GERMANY);
        datum_Tag = dateFormat.format(new java.util.Date());
        tag = Integer.parseInt(datum_Tag.toString());

        mbudgallg= ein - aus -sparen;


        //monthFormat = new SimpleDateFormat("mm", Locale.GERMANY);
        //datum_Monat = monthFormat.format(new java.util.Date());
        //month = Integer.parseInt(datum_Monat.toString());
        month = calendar.get(Calendar.MONTH);

        if(month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11){
            tage = 31;
        }else if (month==1){
            tage = 29;
        }else{
            tage = 30;
        }
        dbudgallg = mbudgallg/tage;
        dbudget = dbudgallg * tag + einnahmen + ausgaben;
        nmbudg = Double.parseDouble(sp.getString("Key2","0"));
        ndbudg = dbudget;
        sp = getSharedPreferences("SP",0);
        SharedPreferences.Editor editor = sp.edit();
        if (sp.getInt("safe",0)== 0){
            safe = false;
        }else{
            safe = true;
        }

        if(tag == 1&& safe == false){


            mbudget = Double.parseDouble(sp.getString("Key2","0"));
            ein = Double.parseDouble(sp.getString("Key3","0"));
            aus = Double.parseDouble(sp.getString("Key4", "0"));
            sparen = Double.parseDouble(sp.getString("Key5", "0"));

            nmbudg = mbudgallg + mbudget;
            ndbudg = nmbudg/tage;

            safe = true;
            editor.putInt("safe", 1);
            editor.commit();

        }
        if(tag != 1){
            safe = false;
            editor.putInt("safe", 0);
            editor.commit();
        }
        nmbudg = Math.round(nmbudg * 100)/100.00;
        ndbudg = Math.round(ndbudg * 100)/100.00;
        newdb = String.valueOf(ndbudg);
        newmb = String.valueOf(nmbudg);

        editd.setText(newdb);
        editm.setText(newmb);

        editor.putString("Key1", newdb );
        editor.putString("Key2", newmb);
        editor.commit();

        bud = Double.parseDouble(sp.getString("Key2", "0"));
        calc = dif/100;
        bud = bud/calc;
        prog = (int) bud;
        pb.setProgress(prog);


        if(sp.getString("reset", "false").equals("true")){
            reset();
        }
    }

    private void reset() {
        SharedPreferences.Editor editor = sp.edit();
        ein = Double.parseDouble(sp.getString("Key3", "0"));
        aus = Double.parseDouble(sp.getString("Key4", "0"));
        sparen = Double.parseDouble(sp.getString("Key5", "0"));
        mbudgallg = ein - aus - sparen;
        dbudgallg = mbudgallg / tage;
        mbudget = mbudgallg - dbudgallg * (tag - 1);
        dbudget = mbudgallg / tage;
        ausg = -dbudgallg * (tag - 1);
        ausge = String.valueOf(ausg);
        dbudget = Math.round(dbudget * 100) / 100.00;
        mbudget = Math.round(mbudget * 100) / 100.00;

        newdb = String.valueOf(dbudget);
        newmb = String.valueOf(mbudget);


        editor.putString("Key1", newdb);
        editor.putString("Key2", newmb);
        editor.putString("Key6", "0");
        editor.putString("Key7", ausge);
        for (int i = 10; i <= n; i++) {
            key = "Key" + String.valueOf(i);
            editor.putString(key, null);


        }
        editor.putString("N", "10");
        //editor.putString("zeile", "1");


        analysereset();


        editor.commit();


        editd.setText(newdb);
        editm.setText(newmb);



    }


    @Override
    public void onClick(View v) {

        noteok = true;

        sp = getSharedPreferences("SP",0);
        SharedPreferences.Editor editor = sp.edit();
        n = Integer.parseInt(sp.getString("N", "10"));



        if (v.equals(plusbtn)){
            try {
                form();
                if (noteok == true){
                    notiz = notiz + " | ";
                    zahl = Double.parseDouble(input.getText().toString());
                    dbudget = Double.parseDouble(sp.getString("Key1","0"));
                    mbudget = Double.parseDouble(sp.getString("Key2","0"));

                    einnahmen = Double.parseDouble(sp.getString("Key6","0"));

                    einnahmen = zahl + einnahmen;
                    nmbudg = zahl + mbudget;
                    ndbudg = zahl + dbudget;
                    nmbudg = Math.round(nmbudg * 100)/100.00;
                    ndbudg = Math.round(ndbudg * 100)/100.00;
                    newdb = String.valueOf(ndbudg);
                    newmb = String.valueOf(nmbudg);
                    newein = String.valueOf(einnahmen);

                    editd.setText(newdb);
                    editm.setText(newmb);

                    editor.putString("Key1", newdb );
                    editor.putString("Key2", newmb);
                    editor.putString("Key6", newein);
                    editor.commit();
                    ein = Double.parseDouble(sp.getString("Key3","0"));
                    aus = Double.parseDouble(sp.getString("Key4", "0"));
                    bud = Double.parseDouble(sp.getString("Key2", "0"));

                    dif = ein -aus;
                    calc = dif/100;
                    bud = bud/calc;
                    prog = (int) bud;
                    pb.setProgress(prog);

                    catform();

                    betrag = String.valueOf(zahl);
                    betrag = "+"+betrag;
                    datainput();

                }

            }catch(Exception ex){
                Toast.makeText(this, "Kein gültiger Betrag", Toast.LENGTH_SHORT).show();
            }


            /*
            try {
                dataSource.open();
                dataSource.createEntry(currentDate,category,notiz,zahl);
                dataSource.close();
            }catch (Exception ex){
                Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG).show();
            }


             */





        }else if(v.equals(minusbtn)){

            try {
                form();
                if(noteok == true){
                    notiz = notiz + " | ";
                    zahl = Double.parseDouble(input.getText().toString());

                    dbudget = Double.parseDouble(sp.getString("Key1","0"));
                    mbudget = Double.parseDouble(sp.getString("Key2","0"));

                    ausgaben = Double.parseDouble(sp.getString("Key7","0"));

                    ausgaben = ausgaben - zahl;

                    nmbudg = mbudget-zahl;
                    ndbudg = dbudget -zahl;
                    nmbudg = Math.round(nmbudg * 100)/100.00;
                    ndbudg = Math.round(ndbudg * 100)/100.00;
                    newdb = String.valueOf(ndbudg);
                    newmb = String.valueOf(nmbudg);
                    newaus = String.valueOf(ausgaben);


                    editd.setText(newdb);
                    editm.setText(newmb);

                    editor.putString("Key1", newdb );
                    editor.putString("Key2", newmb);
                    editor.putString("Key7", newaus);
                    editor.commit();
                    ein = Double.parseDouble(sp.getString("Key3","0"));
                    aus = Double.parseDouble(sp.getString("Key4", "0"));
                    bud = Double.parseDouble(sp.getString("Key2", "0"));
                    dif = ein -aus;
                    calc = dif/100;
                    bud = bud/calc;
                    prog = (int) bud;
                    pb.setProgress(prog);

                    catform();

                    betrag = String.valueOf(zahl);
                    betrag = "-"+betrag;
                    categorydata();
                    datainput();
                }

            }catch(Exception ex){
                Toast.makeText(this, "Kein gültiger Betrag", Toast.LENGTH_SHORT).show();
            }



            /*try {
                dataSource.open();
                dataSource.createEntry(currentDate,category,notiz,zahl);
                dataSource.close();
            }catch (Exception ex){
                Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG).show();
            }

             */
        }else if(v.equals(logbuch)){
            for(int i = 10; i<= n; i++){
                key = "Key" + String.valueOf(i);
                String entry = sp.getString(key,"Keine weiteren Einträge");
                //logListe.add(entry);
                logausgabe = logausgabe +entry;


            }
            String nstr = String.valueOf(n);
            editor.putString("Keylog", logausgabe);
            editor.putString("N", nstr);
            editor.commit();
            Intent intent = new Intent(this, Logbuch.class);
            startActivity(intent);
            this.finish();


            //dataSource.open();
            //LogListe = dataSource.getAllEntries();
            //dataSource.close();
           /* ArrayAdapter<String> adapterLog = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, logListe);
            ListView logView = (ListView) findViewById(R.id.logView);
            logView.setAdapter(adapterLog);

            */


        }else if(v.equals(reset)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Warning")
                    .setMessage("Sind Sie sicher, dass Sie alles zurücksetzen wollen?")
                    .setPositiveButton("JA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = sp.edit();
                            ein = Double.parseDouble(sp.getString("Key3", "0"));
                            aus = Double.parseDouble(sp.getString("Key4", "0"));
                            sparen = Double.parseDouble(sp.getString("Key5", "0"));
                            mbudgallg = ein - aus - sparen;
                            dbudgallg = mbudgallg / tage;
                            mbudget = mbudgallg - dbudgallg * (tag - 1);
                            dbudget = mbudgallg / tage;
                            ausg = -dbudgallg * (tag - 1);
                            ausge = String.valueOf(ausg);
                            dbudget = Math.round(dbudget * 100) / 100.00;
                            mbudget = Math.round(mbudget * 100) / 100.00;

                            newdb = String.valueOf(dbudget);
                            newmb = String.valueOf(mbudget);


                            editor.putString("Key1", newdb);
                            editor.putString("Key2", newmb);
                            editor.putString("Key6", "0");
                            editor.putString("Key7", ausge);
                            for (int i = 10; i <= n; i++) {
                                key = "Key" + String.valueOf(i);
                                editor.putString(key, null);


                            }
                            editor.putString("N", "10");
                            //editor.putString("zeile", "1");


                            analysereset();


                            editor.commit();


                            editd.setText(newdb);
                            editm.setText(newmb);


                        }
                    });
            builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alert = builder.create();
            alert.show();


        }else if (v.equals(info)){
            String nstr = String.valueOf(n);

            editor.putString("N", nstr);
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
            this.finish();


        }

        else{ //Settings

            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            this.finish();

        }




    }

    private void analysereset() {
        sp = getSharedPreferences("SP",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Auto","0.00");
        editor.putString("Essen","0.00");
        editor.putString("Beruf","0.00");
        editor.putString("Freizeit","0.00");
        editor.putString("Leihen","0.00");
        editor.putString("Bestellung","0.00");
        editor.putString("Geschenke","0.00");
        editor.putString("Kleidung","0.00");
        editor.putString("Party","0.00");
        editor.putString("Sonstiges","0.00");
        editor.commit();
    }

    private void categorydata() {
        sp = getSharedPreferences("SP",0);
        SharedPreferences.Editor editor = sp.edit();
        zahl = Double.parseDouble(input.getText().toString());
        String cars,foods,jobs,frees,lents,orders,presents,clothess,partys,otherss;
        switch (cat.getSelectedItem().toString()){
            case "Auto":
                car = Double.parseDouble(sp.getString("Auto","0.00"));
                car = car + zahl;
                car = Math.round(car * 100)/100.00;

                cars = String.valueOf(car);
                editor.putString("Auto",cars);
                break;
            case "Verpflegung":
                food = Double.parseDouble(sp.getString("Essen","0.00"));
                food += zahl;
                food = Math.round(food * 100)/100.00;
                foods = String.valueOf(food);
                editor.putString("Essen",foods);
                break;
            case "Beruf":
                job = Double.parseDouble(sp.getString("Beruf","0.00"));
                job += zahl;
                job = Math.round(job * 100)/100.00;
                jobs = String.valueOf(job);
                editor.putString("Beruf",jobs);
                break;
            case "Freizeit":
                free = Double.parseDouble(sp.getString("Freizeit","0.00"));
                free +=  zahl;
                free = Math.round(free * 100)/100.00;
                frees = String.valueOf(free);
                editor.putString("Freizeit",frees);
                break;
            case "Schulden":
                lent = Double.parseDouble(sp.getString("Leihen","0.00"));
                lent += zahl;
                lent = Math.round(lent * 100)/100.00;
                lents = String.valueOf(lent);
                editor.putString("Leihen",lents);
                break;
            case "Reisen":
                order= Double.parseDouble(sp.getString("Bestellung","0.00"));
                order += zahl;
                order = Math.round(order * 100)/100.00;
                orders = String.valueOf(order);
                editor.putString("Bestellung",orders);
                break;
            case "Geschenke":
                present = Double.parseDouble(sp.getString("Geschenke","0.00"));
                present+= zahl;
                present = Math.round(present * 100)/100.00;
                presents = String.valueOf(present);
                editor.putString("Geschenke",presents);
                break;
            case "Kleidung":
                clothes = Double.parseDouble(sp.getString("Kleidung","0.00"));
                clothes += zahl;
                clothes = Math.round(clothes * 100)/100.00;
                clothess = String.valueOf(clothes);
                editor.putString("Kleidung",clothess);
                break;
            case "Event/Party":
                party = Double.parseDouble(sp.getString("Party","0.00"));
                party+=zahl;
                party = Math.round(party * 100)/100.00;
                partys = String.valueOf(party);
                editor.putString("Party",partys);
                break;
            case "Sonstiges":
                others = Double.parseDouble(sp.getString("Sonstiges","0.00"));
                others+=zahl;
                others = Math.round(others * 100)/100.00;
                otherss = String.valueOf(others);
                editor.putString("Sonstiges",otherss);
                break;
            default:
                System.out.println("Fail");
        }
        editor.commit();
    }

    private void datainput() {
        sp = getSharedPreferences("SP",0);
        SharedPreferences.Editor editor = sp.edit();
        /*while (betrag.length() < 8){
            betrag = betrag + " ";
        }

         */
        if(n == 10){

            editor.putString("Key10","\n");
            n++;
        }
        /*int zeile = Integer.parseInt(sp.getString("zeile","1"));


        String zeil = String.valueOf(zeile);

        String zeiltxt = zeil + " |";
        zeile += 1;
        zeil = String.valueOf(zeile);
        editor.putString("zeile", zeil);

        key = "Key" + String.valueOf(n);
        editor.putString(key,zeiltxt);
        n++;

         */


        key = "Key" + String.valueOf(n);
        editor.putString(key,currentDate);
        n++;
        key = "Key" + String.valueOf(n);
        editor.putString(key,category);
        n++;
        key = "Key" + String.valueOf(n);
        editor.putString(key,notiz);
        n++;
        key = "Key" + String.valueOf(n);
        editor.putString(key,betrag);
        n++;
        key = "Key" + String.valueOf(n);
        editor.putString(key,"\n");
        n++;


        nstr = String.valueOf(n);
        editor.putString("N",nstr);


        editor.commit();
        Toast.makeText(this,"Erfolgreich hinzugefügt", Toast.LENGTH_SHORT).show();
        note.setText("");
        input.setText("");
    }


    public void form() {
        notiz = note.getText().toString();
        zahl = Double.parseDouble(input.getText().toString());

        if(notiz.length() <= 16){
            while(notiz.length() < 16){
                notiz = notiz + " ";
            }

        }else{
            Toast.makeText(this, "Notiz darf maximal 16 Zeichen haben", Toast.LENGTH_SHORT).show();
            noteok = false;
        }

        if(zahl > 99999.99){
            Toast.makeText(this, "Der Betrag darf maximal 99999.99 € sein", Toast.LENGTH_SHORT).show();
            noteok = false;
        }
    }
    private void catform() {

        category = cat.getSelectedItem().toString();
        while(category.length() < 11){
            category = category + " ";
        }
        category = category + " | ";
    }


    @Override
    public void onActivityStop() {
        this.finish();
    }


}
