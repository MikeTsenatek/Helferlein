package brkfuerth.de.helferlein;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import brkfuerth.de.helferlein.ArrayAdapter.ArrayAdapterAlarmierung;
import brkfuerth.de.helferlein.ArrayAdapter.ArrayAdapterBesatzung;
import brkfuerth.de.helferlein.Datenstrukturen.Alarmierung;


public class Alarmierungen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmierungen);
        HelferleinDatabase helferleinDatabase = new HelferleinDatabase(this);

        try {
            helferleinDatabase.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        ListView lv = (ListView) findViewById(R.id.listView2);

        List<Alarmierung> alamierungen = helferleinDatabase.getAllAlarmierung();
        Collections.reverse(alamierungen);


        ArrayAdapterAlarmierung adapter = new ArrayAdapterAlarmierung(this,alamierungen);
        lv.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.f, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
