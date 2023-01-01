package com.example.menuvesql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VeriTabani extends SQLiteOpenHelper {

    //Veritabanı Tanımları
    private static final String DATABASE_NAME = "notlar.db";
    private static final int DATABASE_VERSION = 1;

    //Tablo Tanımları
    private static final String TABLE_NAME = "t_notlar";
    private static final String ROW_ID = "ID";
    private static final String ROW_ADLAR = "Adlar";
    private static final String ROW_VIZE = "Vize";
    private static final String ROW_FINAL = "Final";
    private static final String ROW_BNOTU = "BasariNotlari";

    public VeriTabani(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROW_ADLAR + " TEXT NOT NULL,"
                + ROW_VIZE + " INTEGER NOT NULL,"
                + ROW_FINAL + " INTEGER NOT NULL,"
                + ROW_BNOTU + " REAL NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void veriEkle(String ad, int vizeNot, int finalNot, double bNotu) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_ADLAR, ad);
            cv.put(ROW_VIZE, vizeNot);
            cv.put(ROW_FINAL, finalNot);
            cv.put(ROW_BNOTU, bNotu);
            db.insert(TABLE_NAME, null, cv);
        }
        catch (Exception e) {}
        finally {
            db.close();
        }
    }

    public List<String> veriListele() {
        SQLiteDatabase db = getReadableDatabase();

        String sutunlar[] = {ROW_ID, ROW_ADLAR, ROW_VIZE, ROW_FINAL, ROW_BNOTU};
        Cursor cursor = db.query(TABLE_NAME, sutunlar, null, null, null, null, null);

        List<String> veriler = new ArrayList<>();
        while (cursor.moveToNext()) {
            veriler.add(
                    cursor.getInt(0) + " - "
                    + cursor.getString(1) + " - "
                    + cursor.getInt(2) + " - "
                    + cursor.getInt(3) + " - "
                    +cursor.getInt(4)
            );
        }
        db.close();
        return veriler;
    }

    public void veriGuncelle(int kisiId, String ad, int vizeNot, int finalNot, double bNotu) {
        SQLiteDatabase db = getWritableDatabase();
        String where = ROW_ID + " = " + kisiId;

        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_ADLAR, ad);
            cv.put(ROW_VIZE, vizeNot);
            cv.put(ROW_FINAL, finalNot);
            cv.put(ROW_BNOTU, bNotu);
            db.update(TABLE_NAME, cv, where, null);
        }
        catch (Exception e) {}
        finally {
            db.close();
        }
    }

    public void veriSil(int kisiId) {
        SQLiteDatabase db = getWritableDatabase();
        String where = ROW_ID + " = " + kisiId;

        db.delete(TABLE_NAME, where, null);
        db.close();
    }
}
