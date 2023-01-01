package com.example.menuvesql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etAd, etVize, etFinal;
    Button buttonKaydet;

    private void init() {
        etAd = findViewById(R.id.etAd);
        etVize = findViewById(R.id.etVize);
        etFinal = findViewById(R.id.etFinal);
        buttonKaydet = findViewById(R.id.buttonKaydet);
    }

    VeriTabani veriTabani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setTitle("Not Giriş");

        veriTabani = new VeriTabani(this);

        buttonKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ad = etAd.getText().toString();
                int vizeNot = Integer.parseInt(etVize.getText().toString());
                int finalNot = Integer.parseInt(etFinal.getText().toString());
                double basariNotu = (vizeNot * 0.4) + (finalNot * 0.6);

                veriTabani.veriEkle(ad, vizeNot, finalNot, basariNotu);
                Toast.makeText(MainActivity.this, "Not Kaydedildi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //OptionsMenu İşlemleri
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.duzenleSayfa)
            navToDuzenle();

        return super.onOptionsItemSelected(item);
    }

    //Not Düzenleme Sayfasına Geçiş
    private void navToDuzenle() {
        Intent intent = new Intent(MainActivity.this, DuzenleSayfasi.class);
        startActivity(intent);
    }
}