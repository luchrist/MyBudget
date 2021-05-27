package com.example.mybudget.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mybudget.R;
import com.example.mybudget.database.MainActivity;
import com.example.mybudget.database.Settings;

public class Logbuch extends AppCompatActivity implements View.OnClickListener {
    TextView log;
    MainActivity ma;
    ImageButton back;
    SharedPreferences sp;
    Button analyse,delete;
    String key,newdbudg,newmbudg,newaus,newein,logausgabe;
    int n,anfang,betragn;
    double betrag,dbudget,mbudget,einnahmen,ausgaben;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logbuch);
        ma = new MainActivity();
        log = (TextView) findViewById(R.id.log);
        back= (ImageButton) findViewById(R.id.back);
        analyse = (Button) findViewById(R.id.analyse);
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        analyse.setOnClickListener(this);
        back.setOnClickListener(this);
        sp = getSharedPreferences("SP",0);
        log.setText(sp.getString("Keylog", "Keine Einträge"));
        logausgabe = "";

        //log.setText(ma.logausgabe);

    }

    @Override
    public void onClick(View v) {
        logausgabe = "";
        if(v.equals(back)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }else if (v.equals(analyse)){
            Intent intent = new Intent(this, CategoryAnalysis.class);
            startActivity(intent);
            this.finish();
        }else if (v.equals(delete)){
            n = Integer.parseInt(sp.getString("N", "10"));
            anfang = n -5;
            betragn = n-2;
            key = "Key" + String.valueOf(betragn);
            SharedPreferences.Editor editor = sp.edit();
            String anf = String.valueOf(anfang);

            betrag = Double.parseDouble(sp.getString(key,"0.00"));
            dbudget = Double.parseDouble(sp.getString("Key1","0"));
            mbudget = Double.parseDouble(sp.getString("Key2","0"));

            einnahmen = Double.parseDouble(sp.getString("Key6","0"));
            ausgaben = Double.parseDouble(sp.getString("Key7","0"));


            dbudget -= betrag;
            newdbudg = String.valueOf(dbudget);
            editor.putString("Key1",newdbudg);
            mbudget -= betrag;
            newmbudg = String.valueOf(mbudget);
            editor.putString("Key2",newmbudg);
            if(betrag >=0){
                einnahmen -= betrag;
                newein = String.valueOf(einnahmen);
                editor.putString("Key6", newein);
            }else{
                ausgaben -= betrag;
                newaus = String.valueOf(ausgaben);
                editor.putString("Key7", newaus);
            }


            editor.putString("N", anf);
            for (int i = anfang; i <= n; i++) {
                key = "Key" + String.valueOf(i);
                editor.putString(key, null);


            }
            editor.commit();
            n = Integer.parseInt(sp.getString("N", "10"));
            for(int i = 10; i<=n; i++){
                key = "Key" + String.valueOf(i);
                String entry = sp.getString(key,"Keine weiteren Einträge");
                //logListe.add(entry);
                logausgabe = logausgabe +entry;


            }

            editor.putString("Keylog", logausgabe);
            editor.commit();







            log.setText(sp.getString("Keylog", "Keine Einträge"));
        }

    }




}
