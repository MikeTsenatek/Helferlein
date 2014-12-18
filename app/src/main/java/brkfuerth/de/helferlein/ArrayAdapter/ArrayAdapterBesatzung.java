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

import java.util.List;

import brkfuerth.de.helferlein.Datenstrukturen.Besatzung;
import brkfuerth.de.helferlein.R;

/**
 * Created by homer on 23.10.14.
 */
public class ArrayAdapterBesatzung extends ArrayAdapter<Besatzung> {
    private Context context;
    private List<Besatzung> besatzung;

    public ArrayAdapterBesatzung(Context context, List<Besatzung> besatzung) {
        super(context, R.layout.list_item_personal, besatzung);
        this.context = context;
        this.besatzung = besatzung;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_item_personal, parent, false);

        LinearLayout linLayout = (LinearLayout) row.findViewById(R.id.lin_layout_text);
        TextView name = (TextView) row.findViewById(R.id.name);
        TextView funktion = (TextView) row.findViewById(R.id.funktion);
        TextView telefon = (TextView) row.findViewById(R.id.telefon);
        ImageButton telefonIcon = (ImageButton) row.findViewById(R.id.telefonIcon);

        TextView separator = (TextView) row.findViewById(R.id.seperator);

        name.setText(besatzung.get(position).getName());
        funktion.setText(besatzung.get(position).getFunktion());
        if(besatzung.get(position).getTelefon() != null) {
            telefon.setText(besatzung.get(position).getTelefon());
            telefonIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "tel:" + besatzung.get(position).getTelefon().trim();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                    context.startActivity(callIntent);
                }
            });
        }
        else {
            telefonIcon.setVisibility(View.GONE);
        }

        if (position == 0) {
            separator.setVisibility(View.VISIBLE);
            separator.setText(besatzung.get(position).getTag() + " " + besatzung.get(position).getDienstbeginn());
        } else if (!besatzung.get(position).getId().
                equals(besatzung.get(position - 1).getId())) {
            separator.setVisibility(View.VISIBLE);
            separator.setText(besatzung.get(position).getTag() + " " + besatzung.get(position).getDienstbeginn());
        } else {
            separator.setVisibility(View.GONE);
            separator.setText("");
        }
        return row;
    }
}
