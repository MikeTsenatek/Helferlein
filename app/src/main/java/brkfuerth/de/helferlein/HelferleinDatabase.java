package brkfuerth.de.helferlein;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import brkfuerth.de.helferlein.Datenstrukturen.AlarmNummer;
import brkfuerth.de.helferlein.Datenstrukturen.Alarmierung;
import brkfuerth.de.helferlein.Datenstrukturen.Meldung;

/**
 * Created by homer on 12.11.14.
 */
public class HelferleinDatabase extends SQLiteOpenHelper {


    private static String DB_PATH = "/data/data/de.brkfuerth.helferlein/databases/";

    private Context context;

    // All Static variables
// Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "alarm";


    // Contacts table name
    private static final String TABLE_NUMMER = "nummer";
    private static final String TABLE_ALARMIERNUNG = "alarmierung";
    private static final String TABLE_MELDUNG = "meldung";


    // Contacts Table Columns names
    private static final String KEY_NUMMER = "nummer";
    private static final String KEY_NAME = "name";

    private static final String KEY_TEXT = "text";

    private static final String KEY_ID = "ID";
    private static final String KEY_UHRZEIT = "uhrzeit";
    private static final String KEY_ANZAHL = "anzahl";
    private static final String KEY_SOFORT = "sofort";
    private static final String KEY_SPAETER = "spaeter";
    private static final String KEY_NICHT = "nicht";
    private static final String KEY_UNBEKANNT = "unbekannt";
    private static final String KEY_NICHTERREICHT = "nichtErreicht";


    public HelferleinDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
//do nothing - database already exist
        }
        else
        {
//By calling this method and empty database will be created into the default system path
//of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            copyDataBase();

        } catch (IOException e) {
            throw new Error("Error copying database");
        }
    }

    public void addAlarmierung(Alarmierung alarmierung) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NUMMER, alarmierung.getNummer());
        values.put(KEY_ANZAHL, alarmierung.getAnzahl());
        values.put(KEY_UHRZEIT, alarmierung.getUhrzeit());
        values.put(KEY_SOFORT, alarmierung.getSofort());
        values.put(KEY_SPAETER, alarmierung.getSpaeter());
        values.put(KEY_NICHT, alarmierung.getNicht());
        values.put(KEY_UNBEKANNT, alarmierung.getUnbekannt());
        values.put(KEY_NICHTERREICHT, alarmierung.getNichtErreicht());


        // Inserting Row
        db.insert(TABLE_ALARMIERNUNG, null, values);
        db.close(); // Closing database connection
    }

    public Alarmierung getAlarmierung(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALARMIERNUNG, new String[]{KEY_NUMMER,
                        KEY_UHRZEIT, KEY_ANZAHL, KEY_SOFORT, KEY_SPAETER, KEY_NICHT, KEY_UNBEKANNT,
                        KEY_NICHTERREICHT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount() <= 0) return null;
        Alarmierung alarmierung = new Alarmierung();
        alarmierung.setId(id);
        alarmierung.setNummer(Integer.parseInt(cursor.getString(0)));
        alarmierung.setUhrzeit(cursor.getString(1));
        alarmierung.setAnzahl(Integer.parseInt(cursor.getString(2)));
        alarmierung.setSofort(Integer.parseInt(cursor.getString(3)));
        alarmierung.setSpaeter(Integer.parseInt(cursor.getString(4)));
        alarmierung.setNicht(Integer.parseInt(cursor.getString(5)));
        alarmierung.setUnbekannt(Integer.parseInt(cursor.getString(6)));
        alarmierung.setNichtErreicht(Integer.parseInt(cursor.getString(7)));

        return alarmierung;
    }

    // Getting All Contacts
    public List<Alarmierung> getAllAlarmierung() {
        List<Alarmierung> alarmierungsListe = new ArrayList<Alarmierung>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ALARMIERNUNG;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Alarmierung alarmierung = new Alarmierung();
                alarmierung.setId(Integer.parseInt(cursor.getString(0)));
                alarmierung.setNummer(Integer.parseInt(cursor.getString(1)));
                alarmierung.setUhrzeit(cursor.getString(2));
                alarmierung.setAnzahl(Integer.parseInt(cursor.getString(3)));
                alarmierung.setSofort(Integer.parseInt(cursor.getString(4)));
                alarmierung.setSpaeter(Integer.parseInt(cursor.getString(5)));
                alarmierung.setNicht(Integer.parseInt(cursor.getString(6)));
                alarmierung.setUnbekannt(Integer.parseInt(cursor.getString(7)));
                alarmierung.setNichtErreicht(Integer.parseInt(cursor.getString(8)));
                alarmierungsListe.add(alarmierung);
            } while (cursor.moveToNext());
        }

        // return contact list
        return alarmierungsListe;
    }

    public int getAlarmierungsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ALARMIERNUNG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateAlarmierung(Alarmierung alarmierung) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NUMMER, alarmierung.getNummer());
        values.put(KEY_ANZAHL, alarmierung.getAnzahl());
        values.put(KEY_UHRZEIT, alarmierung.getUhrzeit());
        values.put(KEY_SOFORT, alarmierung.getSofort());
        values.put(KEY_SPAETER, alarmierung.getSpaeter());
        values.put(KEY_NICHT, alarmierung.getNicht());
        values.put(KEY_UNBEKANNT, alarmierung.getUnbekannt());
        values.put(KEY_NICHTERREICHT, alarmierung.getNichtErreicht());

        // updating row
        return db.update(TABLE_ALARMIERNUNG, values, KEY_ID + " = ?",
                new String[]{String.valueOf(alarmierung.getId())});
    }


    public void deleteAlarmierung(Alarmierung implement) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARMIERNUNG, KEY_ID + " = ?",
                new String[]{String.valueOf(implement.getId())});
        db.close();
    }


    public void addAlarmNummer(AlarmNummer alarmNummer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NUMMER, alarmNummer.getNummer());
        values.put(KEY_NAME, alarmNummer.getName());

        // Inserting Row
        db.insert(TABLE_NUMMER, null, values);
        db.close(); // Closing database connection
    }

    public AlarmNummer getAlarmNummer(int nummer) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NUMMER, new String[]{KEY_NAME}, KEY_NUMMER + "=?",
                new String[]{String.valueOf(nummer)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount() <= 0) return null;
        AlarmNummer alarmNummer = new AlarmNummer();
        alarmNummer.setName(cursor.getString(0));
        alarmNummer.setNummer(nummer);

        return alarmNummer;
    }

    public List<AlarmNummer> getAllAlarmNummer() {
        List<AlarmNummer> alarmNummerList = new ArrayList<AlarmNummer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NUMMER;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AlarmNummer alarmNummer = new AlarmNummer();
                alarmNummer.setName(cursor.getString(1));
                alarmNummer.setNummer(Integer.parseInt(cursor.getString(0)));
                alarmNummerList.add(alarmNummer);
            } while (cursor.moveToNext());
        }

        // return contact list
        return alarmNummerList;
    }

    public int getAlarmNummerCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NUMMER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateAlarmNummer(AlarmNummer alarmNummer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NUMMER, alarmNummer.getNummer());
        values.put(KEY_NAME, alarmNummer.getName());

        // updating row
        return db.update(TABLE_NUMMER, values, KEY_NUMMER + " = ?",
                new String[]{String.valueOf(alarmNummer.getNummer())});
    }


    public void deleteAlarmNummer(AlarmNummer alarmNummer) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NUMMER, KEY_NUMMER + " = ?",
                new String[]{String.valueOf(alarmNummer.getNummer())});
        db.close();
    }

    private boolean checkDataBase() {

        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }


    private void copyDataBase() throws IOException {
//Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
// Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
//Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

//transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

//Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
Log.d("Database", "Kopiert...");
    }


    public void addMeldung(Meldung meldung) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, meldung.getId());
        values.put(KEY_TEXT, meldung.getMeldung());

        // Inserting Row
        db.insert(TABLE_MELDUNG, null, values);
        db.close(); // Closing database connection
    }

    public Meldung getMeldung(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MELDUNG, new String[]{KEY_TEXT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount() <= 0) return null;
        Meldung meldung= new Meldung();
        meldung.setMeldung(cursor.getString(0));
        meldung.setId(id);

        return meldung;
    }

    // Getting All Contacts
    public List<Meldung> getAllMeldung() {
        List<Meldung> meldungList = new ArrayList<Meldung>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MELDUNG;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Meldung meldung= new Meldung();
                meldung.setMeldung(cursor.getString(1));
                meldung.setId(Integer.valueOf(cursor.getString(0)));
                meldungList.add(meldung);
            } while (cursor.moveToNext());
        }

        // return contact list
        return meldungList;
    }

    public int getMeldungCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MELDUNG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateMeldung(Meldung meldung) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, meldung.getMeldung());
        values.put(KEY_ID, meldung.getId());

        // updating row
        return db.update(TABLE_MELDUNG, values, KEY_ID + " = ?",
                new String[]{String.valueOf(meldung.getId())});
    }


    public void deleteMeldung(Meldung meldung) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MELDUNG, KEY_ID + " = ?",
                new String[]{String.valueOf(meldung.getId())});
        db.close();
    }



}