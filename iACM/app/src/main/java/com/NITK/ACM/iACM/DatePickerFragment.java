package com.NITK.ACM.iACM;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {



    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet (DatePicker view, int year, int month, int day) {


        if(month<10 && day<10)
            AddEvent.datetext.setText("0"+day+"/0"+month+"/"+year);

        else if(month<10)
            AddEvent.datetext.setText(""+day+"/0"+month+"/"+year);

        else if(day<10)
            AddEvent.datetext.setText("0"+day+"/"+month+"/"+year);

        else
            AddEvent.datetext.setText(""+day+"/"+month+"/"+year);

        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(day+"/"+month+"/"+year);
            AddEvent.event.setDate(date1);
            Integer val=year*10000+month*100+day;
            AddEvent.event.setDatevalue(val);
            Log.i("date",date1.toString());
        }catch (Exception e)
        {};
    }


}

