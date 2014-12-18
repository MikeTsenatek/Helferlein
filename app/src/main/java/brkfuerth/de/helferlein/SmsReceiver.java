package brkfuerth.de.helferlein;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import brkfuerth.de.helferlein.Datenstrukturen.Alarmierung;

public class SmsReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        Object[] messages = (Object[]) bundle.get("pdus");
        SmsMessage[] sms = new SmsMessage[messages.length];

        for (int n = 0; n < messages.length; n++) {
            sms[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
        }
        for (SmsMessage msg : sms) {
            Pattern pattern = Pattern.compile("AlarmRuf Status\\nAusgeloest: (\\w{2}\\.\\w{2}\\.\\w{2} \\w{2}:\\w{2}),\\nZiel: (\\w{6}),\\nAnz: (\\w{1,3}) ,\\nSofort: (\\w{1,3}),\\nSpaeter: (\\w{1,3}),\\nNicht: (\\w{1,3}),\\nUnbek\\.: (\\w{1,3}),\\nNicht erreicht: (\\w{1,3}).");
            Matcher matcher = pattern.matcher(msg.getMessageBody());
            if (matcher.find()) {
                Toast.makeText(context,
                        "Alarmiert " + matcher.group(2) + "\n" +
                                "um " + matcher.group(1) + "\n" +
                                "Anz " + matcher.group(3) + "\n" +
                                "Sofort " + matcher.group(4) + "\n" +
                                "Später " + matcher.group(5) + "\n" +
                                "Nicht " + matcher.group(6) + "\n" +
                                "Unbek " + matcher.group(7) + "\n" +
                                "NichtErreicht " + matcher.group(8)
                        , Toast.LENGTH_LONG).show();
                SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yy HH:mm");
                SimpleDateFormat utc = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                try {
                    HelferleinDatabase helferleinDatabase = new HelferleinDatabase(context);

                    try {
                        helferleinDatabase.createDataBase();
                    } catch (IOException ioe) {
                        throw new Error("Unable to create database");
                    }

                    Alarmierung alarmierung = new Alarmierung();
                    alarmierung.setNummer(Integer.parseInt(matcher.group(2)));
                    alarmierung.setUhrzeit(utc.format(parser.parse(matcher.group(1))));
                    alarmierung.setAnzahl(Integer.parseInt(matcher.group(3)));
                    alarmierung.setSofort(Integer.parseInt(matcher.group(4)));
                    alarmierung.setSpaeter(Integer.parseInt(matcher.group(5)));
                    alarmierung.setNicht(Integer.parseInt(matcher.group(6)));
                    alarmierung.setUnbekannt(Integer.parseInt(matcher.group(7)));
                    alarmierung.setNichtErreicht(Integer.parseInt(matcher.group(8)));
                    helferleinDatabase.addAlarmierung(alarmierung);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(context,"Nicht gefunden...\n"+msg.getMessageBody(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}