package com.example.menuvesql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DuzenleSayfasi extends AppCompatActivity {

    ListView listViewNotlar;
    Button buttonSil, buttonDuzenle;
    TextView textViewAd;
    EditText etVize2, etFinal2;

    private void init() {
        listViewNotlar = findViewById(R.id.listViewNotlar);
        buttonSil = findViewById(R.id.buttonSil);
        buttonDuzenle = findViewById(R.id.buttonDuzenle);
        textViewAd = findViewById(R.id.textViewAd);
        etVize2 = findViewById(R.id.etVize2);
        etFinal2 = findViewById(R.id.etFinal2);
    }

    //Veritabanı Değişkenleri
    VeriTabani veriTabani;
    int kisiId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duzenle_sayfasi);
        init();
        setTitle("Not Düzenle");
        veriTabani = new VeriTabani(this);

        Listele();

        buttonDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etVize2.getText().toString().isEmpty() || etFinal2.getText().toString().isEmpty()) {
                    Toast.makeText(DuzenleSayfasi.this, "Öğrenci Seçiniz", Toast.LENGTH_SHORT).show();
                }
                else {
                    int vizeNot = Integer.parseInt(etVize2.getText().toString());
                    int finalNot = Integer.parseInt(etFinal2.getText().toString());
                    double basariNot = (vizeNot * 0.4) + (finalNot * 0.6);

                    veriTabani.veriGuncelle(kisiId, textViewAd.getText().toString(), vizeNot, finalNot, basariNot);
                    Toast.makeText(DuzenleSayfasi.this, "Not güncellendi", Toast.LENGTH_SHORT).show();
                    Listele();
                    Temizle();
                }
            }
        });

        buttonSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veriTabani.veriSil(kisiId);
                Toast.makeText(DuzenleSayfasi.this, "Öğrenci silindi", Toast.LENGTH_SHORT).show();
                Listele();
                Temizle();
            }
        });
    }

    private void Listele() {
        List<String> veriler = veriTabani.veriListele();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, veriler);
        listViewNotlar.setAdapter(adapter);

        listViewNotlar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String secilen = listViewNotlar.getItemAtPosition(i).toString();
                String bolunenler[] = secilen.split(" - ");

                kisiId = Integer.parseInt(bolunenler[0]);
                textViewAd.setText(bolunenler[1]);
                etVize2.setText(bolunenler[2]);
                etFinal2.setText(bolunenler[3]);
            }
        });
    }

    private void Temizle() {
        kisiId = 0;
        textViewAd.setText("Öğrenci Adı");
        etVize2.setText("");
        etFinal2.setText("");
    }

    //Options Menu İşlemleri
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.girisSayfa)
            navToGiris();

        return super.onOptionsItemSelected(item);
    }

    //Not Giriş Sayfasına Geçiş
    private void navToGiris() {
        Intent intent = new Intent(DuzenleSayfasi.this, MainActivity.class);
        startActivity(intent);
    }
}