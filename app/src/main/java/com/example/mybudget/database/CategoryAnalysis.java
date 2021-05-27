package com.example.mybudget.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mybudget.R;

public class CategoryAnalysis extends AppCompatActivity implements View.OnClickListener {

    TextView car,food,job,free,lent,trip,present,clothes,party,others;
    ImageButton back;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_analysis);
        car = (TextView) findViewById(R.id.car);
        food = (TextView) findViewById(R.id.food);
        job = (TextView) findViewById(R.id.job);
        free = (TextView) findViewById(R.id.free);
        lent = (TextView) findViewById(R.id.lent);
        present = (TextView) findViewById(R.id.present);
        clothes = (TextView) findViewById(R.id.clothes);
        party = (TextView) findViewById(R.id.party);
        trip = (TextView) findViewById(R.id.order);
        others = (TextView) findViewById(R.id.others);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        sp = getSharedPreferences("SP",0);

        car.setText("Auto:        "+ sp.getString("Auto","0.00"));
        food.setText("Verpflegung: "+ sp.getString("Essen","0.00") );
        job.setText("Beruf:       "+ sp.getString("Beruf","0.00"));
        free.setText("Freizeit:    "+ sp.getString("Freizeit","0.00"));
        lent.setText("Schulden:    "+ sp.getString("Leihen","0.00"));
        trip.setText("Reisen:      "+ sp.getString("Bestellung","0.00"));
        present.setText("Geschenke:   "+ sp.getString("Geschenke","0.00"));
        clothes.setText("Kleidung:    "+ sp.getString("Kleidung","0.00"));
        party.setText("Event/Party: "+ sp.getString("Party","0.00"));
        others.setText("Sonstiges:   "+ sp.getString("Sonstiges","0.00"));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Logbuch.class);
        startActivity(intent);
        this.finish();

    }
}
