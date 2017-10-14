package com.example.timotiusek.musikonek;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.timotiusek.musikonek.CustomClass.MagicBox;
import com.example.timotiusek.musikonek.CustomClass.PlanAppointmentController;
import com.example.timotiusek.musikonek.Helper.DateFormatter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PlanAppointmentActivity extends AppCompatActivity {

    private static int DATE_PICKER_CODE = 101;

    @BindView(R.id.meeting_amount__plan_appointment_act) EditText meetingAmount;
    @BindView(R.id.appointments_listview__plan_appointment_act) ListView appointmentList;

    private int[] schedule = null;
    private class CustomArrayAdapter extends ArrayAdapter<String> {

        private ArrayList< ArrayList<String> > timeData;
        private int[] selectedDays;
        private String[] selectedTime;
        private int targetDay = 0;

        private class ViewHolder {
            public Spinner day;
            public Spinner time;
        }

        public int[] getSelectedDays() {
            return selectedDays;
        }

        public CustomArrayAdapter(Context context, String[] dummy, ArrayList< ArrayList<String> > real) {
            super(context, R.layout.row_layout_appointment_form, dummy);
            this.timeData = real;
            selectedDays = new int[dummy.length];
            selectedTime = new String[dummy.length];
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String data = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            final ViewHolder viewHolder; // view lookup cache stored in tag

            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.row_layout_appointment_form, parent, false);
                viewHolder.day = (Spinner) convertView.findViewById(R.id.day__appointmnet_form_fl);
                viewHolder.time = (Spinner) convertView.findViewById(R.id.time__appointmnet_form_fl);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                    selectedDays[position] = index;
                    targetDay = index;
                    setTimeSpinner(targetDay, viewHolder, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            setTimeSpinner(targetDay, viewHolder, position);

            // Return the completed view to render on screen
            return convertView;
        }

        private void setTimeSpinner(final int day, ViewHolder viewHolder, final int position) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                    PlanAppointmentActivity.this,
                    android.R.layout.simple_spinner_item,
                    timeData.get(day));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder.time.setAdapter(spinnerArrayAdapter);
            viewHolder.time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                    selectedTime[position] = timeData.get(day).get(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private ArrayList< ArrayList<String> > getTeacherAvailableTime(int duration) {
        final int INT_LENGTH = 32;
        int courseLength = (int) (Math.pow(2, Math.ceil((double) duration/30.0)) - 1);
        ArrayList< ArrayList<String> > output = new ArrayList<>(schedule.length);
        for(int i = 0; i < schedule.length; i++) {
            ArrayList<String> a = new ArrayList<>();
            for(int j = 0; j < INT_LENGTH; j++) {
                if((schedule[i] & (courseLength << j)) == (courseLength << j)) {
                    Date s_time = new Time(7, j*30, 0);
                    Date e_time = new Time(7, j*30 + duration, 0);
                    a.add(MagicBox.dateStartEndFormatter(s_time, e_time));
                }
            }
            output.add(a);
        }
        return output;
        //return new String[][]{new String[]{"lol"}};
    }

    @OnTextChanged(R.id.meeting_amount__plan_appointment_act)
    public void populateAdapter() {
        if(meetingAmount.getText().toString().equals("")) {
            return;
        }
        if(schedule == null) {
            Toast.makeText(this, "Data is still loading, please wait", Toast.LENGTH_LONG).show();
            meetingAmount.setText("");
            return;
        }
        if(schedule.length != 7) {
            Toast.makeText(this, "No active schedule for this teacher", Toast.LENGTH_LONG).show();
            return;
        }

        String[] listViewTricker = new String[Integer.parseInt(meetingAmount.getText().toString())];
        if(listViewTricker.length == 0) {
            return;
        }
        for(String i : listViewTricker) {
            i = "string ga guna";
        }

        ArrayList< ArrayList<String> > actualData = getTeacherAvailableTime(getIntent().getExtras().getInt("duration_minute"));

        appointmentList.setAdapter(new CustomArrayAdapter(this, listViewTricker, actualData));
        appointmentList.setFooterDividersEnabled(true);
        appointmentList.setDividerHeight(1);
    }

    private boolean[] appointmentDays() {
        boolean[] output = new boolean[] {false, false, false, false, false, false, false};
        int[] selectedDays = ((CustomArrayAdapter) appointmentList.getAdapter()).getSelectedDays();
        for (int selectedDay : selectedDays) {
            output[selectedDay] = true;
        }
        return output;
    }

    private boolean dataNotValid() {
        int[] days = ((CustomArrayAdapter) appointmentList.getAdapter()).selectedDays;
        String[] time = ((CustomArrayAdapter) appointmentList.getAdapter()).selectedTime;
        for(int i = 0; i < days.length - 1; i++) {
            if(days[i] == days[i+1]) {
                Pattern p = Pattern.compile("(\\d{1,2})\\.(\\d{1,2})\\s\\-\\s(\\d{1,2})\\.(\\d{1,2})");
                Matcher m_1 = p.matcher(time[i]);
                Matcher m_2 = p.matcher(time[i+1]);
                if (m_1.find() && m_2.find()) {
                    int h1 = Integer.parseInt(m_1.group(1));
                    int m1 = Integer.parseInt(m_1.group(2));
                    int h2 = Integer.parseInt(m_2.group(1));
                    int m2 = Integer.parseInt(m_2.group(2));

                    if(h1*60 + m1 >= h2*60 + m2) {
                        return true;
                    }
                }
            } else if(days[i] > days[i+1]) {
                return true;
            }
        }
        return false;
    }

    @OnClick(R.id.submit__plan_appointment_act)
    public void submit() {
        if(dataNotValid()) {
            Toast.makeText(this, "Data must be sequentially incremented", Toast.LENGTH_SHORT).show();
            return;
        }
        android.app.DatePickerDialog dialog = new android.app.DatePickerDialog(
                PlanAppointmentActivity.this,
                new android.app.DatePickerDialog.OnDateSetListener(){

                    private boolean isDateNotValid(int year, int monthOfYear, int dayOfMonth) {
                        //Log.d("DEBUG", year+"/"+monthOfYear+"/"+dayOfMonth);
                        String dayOfWeek = DateFormatter.dayNameOf(year, monthOfYear, dayOfMonth);
                        for(int day : ((CustomArrayAdapter) appointmentList.getAdapter()).selectedDays) {
                            //Log.d("DEBUG", dayOfWeek + " " + PlanAppointmentController.days[day]);
                            if(PlanAppointmentController.days[day].equalsIgnoreCase(dayOfWeek)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        if(isDateNotValid(year, monthOfYear, dayOfMonth)) {
                            String message = "";
                            String mockMessage = "";
                            int counter = 0;

                            for(int day : ((CustomArrayAdapter) appointmentList.getAdapter()).selectedDays) {
                                if(!mockMessage.contains(PlanAppointmentController.days[day])){
                                    mockMessage += " " + PlanAppointmentController.days[day] + ",";
                                    counter++;
                                }
                            }

                            if(counter == 1){
                                message += "Date must be a";
                            }else{
                                message += "Date must be either";
                            }

                            int at = 0;

                            for(int day : ((CustomArrayAdapter) appointmentList.getAdapter()).selectedDays) {
                                if(!message.contains(PlanAppointmentController.days[day])){
                                    if(at == counter - 1 ) {
                                        message += " " + PlanAppointmentController.days[day];
                                    }else if(at == counter -2){
                                        message += " " + PlanAppointmentController.days[day] + " or";
                                    }else{
                                        message += " " + PlanAppointmentController.days[day] + ",";
                                    }
                                    //Log.d("asdf",PlanAppointmentController.days[day] + " at " + at + "/"+counter);
                                    at++;
                                }
                            }
                            Toast.makeText(PlanAppointmentActivity.this, message, Toast.LENGTH_LONG).show();
                            return;
                        }

                        Intent intent = new Intent(PlanAppointmentActivity.this, SetScheduleActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putIntArray("calendar", new int[]{year, monthOfYear, dayOfMonth});
                        bundle.putIntArray("day", ((CustomArrayAdapter) appointmentList.getAdapter()).selectedDays);
                        bundle.putStringArray("time", ((CustomArrayAdapter) appointmentList.getAdapter()).selectedTime);
                        bundle.putAll(getIntent().getExtras());

                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        dialog.setTitle("Choose Starting Date");
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_appointment);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Arrange Schedule");

        new PlanAppointmentController().getDataAsync(PlanAppointmentActivity.this);
    }

    public void onDataReady(int[] schedule) {
        this.schedule = schedule;
    }
}
