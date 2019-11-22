package com.NITK.ACM.iACM;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(hourOfDay<10 && minute<10)
            AddEvent.timetext.setText("0"+hourOfDay+":0"+minute);

        else if(hourOfDay<10)
            AddEvent.timetext.setText("0"+hourOfDay+":"+minute);

        else if(minute<10)
            AddEvent.timetext.setText(""+hourOfDay+":0"+minute);

        else
            AddEvent.timetext.setText(""+hourOfDay+":"+minute);
        SimpleDateFormat formatter1=new SimpleDateFormat("hh:mm:ss");
        try
        {
            Date date1=new SimpleDateFormat("hh:mm:ss").parse(hourOfDay+":"+minute+":"+00);
            AddEvent.event.setTime(date1);
        }catch (Exception e)
        {};
    }
}
