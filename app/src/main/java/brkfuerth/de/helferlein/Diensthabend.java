package brkfuerth.de.helferlein;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import brkfuerth.de.helferlein.ArrayAdapter.ArrayAdapterBesatzung;
import brkfuerth.de.helferlein.Datenstrukturen.Besatzung;
import brkfuerth.de.helferlein.xml.Dienst;
import brkfuerth.de.helferlein.xml.Dienste;
import brkfuerth.de.helferlein.xml.Dienstplan;
import brkfuerth.de.helferlein.xml.Personal;


public class Diensthabend extends Activity {
    Spinner spinner;
    Dienste dienste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diensthabend);

        if(isConnected()) {
            new RequestTask().execute(getString(R.string.diensthabend));
        }
        spinner = (Spinner) findViewById(R.id.spinner);

    }

    private void runOnPostExecute(String result) {
        Serializer serializer = new Persister();

        Reader reader = new StringReader(result);
        Dienste dienste = null;
        try {
            dienste = serializer.read(Dienste.class, reader);
            this.dienste = dienste;
        } catch (Exception e) {
e.printStackTrace();
        }
        List<String> spinnerItems = new ArrayList<String>();
        int x=0;
        for(Dienstplan dp : dienste.getDienstplan()) {
            spinnerItems.add(x++, dp.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,spinnerItems);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addDienste(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addDienste(Integer dienstplanid) {
        List<Besatzung> besatzung = new ArrayList<Besatzung>();
        Dienstplan dienstplan = dienste.getDienstplan().get(dienstplanid);
        for (Dienst dienst : dienstplan.getDienst()) {
            for(Personal personal : dienst.getPersonal()) {
                Besatzung pers = new Besatzung();
                pers.setDienstbeginn(dienst.getDienstbeginn());
                pers.setTag(dienst.getTag());
                pers.setDienstdauer(dienst.getDienstdauer());
                pers.setName(personal.getName());
                pers.setFunktion(personal.getFunktion());
                pers.setTelefon(personal.getTelefon());
                pers.setId(dienst.getId());
                besatzung.add(pers);
            }
        }
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapterBesatzung adapter = new ArrayAdapterBesatzung(this,besatzung);
        lv.setAdapter(adapter);
    }


    public String GET(String url) {
        String result = "";
        InputStream inputStream = null;

        HttpClient client = new DefaultHttpClient();
        Log.d("URL", url);

        HttpGet request = new HttpGet(url);

        HttpResponse response;
        try {
            response = client.execute(request);
            Log.d("Response of GET request", response.toString());
            inputStream = response.getEntity().getContent();
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    private class RequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(),"Daten geladen",Toast.LENGTH_SHORT).show();
            runOnPostExecute(result);
        }
    }
}



