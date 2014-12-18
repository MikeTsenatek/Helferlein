package brkfuerth.de.helferlein.ArrayAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.List;

import brkfuerth.de.helferlein.Datenstrukturen.AlarmNummer;
import brkfuerth.de.helferlein.Datenstrukturen.Alarmierung;
import brkfuerth.de.helferlein.Datenstrukturen.Besatzung;
import brkfuerth.de.helferlein.HelferleinDatabase;
import brkfuerth.de.helferlein.R;

/**
 * Created by homer on 23.10.14.
 */
public class ArrayAdapterAlarmierung extends ArrayAdapter<Alarmierung> {
    private Context context;
    private List<Alarmierung> alarmierung;
    private Hashtable<Integer, String> alarmNummern = new Hashtable<Integer, String>();

    public ArrayAdapterAlarmierung(Context context, List<Alarmierung> alarmierung) {
        super(context, R.layout.list_item_personal, alarmierung);
        this.context = context;
        this.alarmierung = alarmierung;
        HelferleinDatabase helferleinDatabase = new HelferleinDatabase(context);
        for(AlarmNummer alarmNummer : helferleinDatabase.getAllAlarmNummer()) {
            alarmNummern.put(alarmNummer.getNummer(),alarmNummer.getName());
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_item_alarmierung, parent, false);

        TextView name = (TextView) row.findViewById(R.id.name);
        TextView uhrzeit = (TextView) row.findViewById(R.id.uhrzeit);
        TextView anzahl = (TextView) row.findViewById(R.id.anzahl);
        TextView sofort = (TextView) row.findViewById(R.id.sofort);
        TextView spaeter = (TextView) row.findViewById(R.id.spaeter);
        TextView nicht = (TextView) row.findViewById(R.id.nicht);
        TextView unbekannt = (TextView) row.findViewById(R.id.unbekannt);
        TextView nichtErreicht = (TextView) row.findViewById(R.id.nichtErreicht);

        name.setText(alarmNummern.get(alarmierung.get(position).getNummer()));
        uhrzeit.setText(alarmierung.get(position).getUhrzeit());
        anzahl.setText("Gesamt: "+alarmierung.get(position).getAnzahl());
        sofort.setText("Sofort: "+alarmierung.get(position).getSofort());
        spaeter.setText("Später: "+alarmierung.get(position).getSpaeter());
        nicht.setText("Nicht: "+alarmierung.get(position).getNicht());
        unbekannt.setText("Nicht: "+alarmierung.get(position).getUnbekannt());
        nichtErreicht.setText("Nicht erreicht: "+alarmierung.get(position).getNichtErreicht());

        return row;
    }
}
