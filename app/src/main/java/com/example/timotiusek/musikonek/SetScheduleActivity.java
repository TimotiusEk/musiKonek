package com.example.timotiusek.musikonek;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timotiusek.musikonek.CustomClass.PlanAppointmentController;
import com.example.timotiusek.musikonek.CustomClass.Schedule;
import com.example.timotiusek.musikonek.CustomClass.SetScheduleController;
import com.example.timotiusek.musikonek.Helper.DateFormatter;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetScheduleActivity extends AppCompatActivity {

    @BindView(R.id.schedule_lv__set_schedule_act) ListView scheduleLv;
    @BindView(R.id.set_schedule_btn__set_schedule_act) Button setScheduleBtn;

    private Date[] appointmentDates;

    private class ScheduleAdapters extends ArrayAdapter<String> {

        private class ViewHolder {
            TextView scheduleName;
            TextView scheduleDateAndTime;
            TextView scheduleStatus;
            Button dateButton;
            Button timeButton;
        }

        private ArrayList<Schedule> data;

        public ScheduleAdapters(Context context, ArrayList< Schedule > data) {
            super(context, R.layout.row_layout_schedule, new String[data.size()]);
            this.data = data;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final ViewHolder viewHolder;

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_layout_schedule, parent, false);
            viewHolder.scheduleName = (TextView) convertView.findViewById(R.id.schedule_name__schedule_rl);
            viewHolder.scheduleDateAndTime = (TextView) convertView.findViewById(R.id.schedule_date_and_time__schedule_rl);
            viewHolder.scheduleStatus = (TextView) convertView.findViewById(R.id.schedule_status__schedule_rl);
//            viewHolder.dateButton = (Button) convertView.findViewById(R.id.change_date__schedule_rl);
//            viewHolder.timeButton = (Button) convertView.findViewById(R.id.change_time__schedule_rl);

            viewHolder.scheduleName.setText(this.data.get(position).getName());
            viewHolder.scheduleDateAndTime.setText(this.data.get(position).getDateAndTime());
            viewHolder.scheduleStatus.setText(this.data.get(position).getStatus());

            convertView.setTag(viewHolder);

            return convertView;
        }
    }

//    private class ScheduleAdapter extends BaseAdapter {
//
//        private class ViewHolder {
//            public TextView scheduleName;
//            public TextView scheduleDateAndTime;
//            public TextView scheduleStatus;
//        }
//
//        private ArrayList<Schedule> schedules;
//        private Context mContext;
//        private LayoutInflater inflater;
//
//        ScheduleAdapter(ArrayList<Schedule> schedules, Context mContext){
//            this.schedules = schedules;
//            this.mContext = mContext;
//        }
//
//        @Override
//        public int getCount() {
//            return schedules.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return schedules.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            final Schedule schedule = (Schedule) getItem(position);
//            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            ViewHolder holder = new ViewHolder();
//            if(convertView == null){
//                convertView = inflater.inflate(R.layout.row_layout_schedule, parent, false);
//
//                holder.scheduleName = convertView.findViewById(R.id.schedule_name__schedule_rl);
//                holder.scheduleDateAndTime = convertView.findViewById(R.id.schedule_date_and_time__schedule_rl);
//                holder.scheduleStatus = convertView.findViewById(R.id.schedule_status__schedule_rl);
//
//                holder.scheduleName.setText(schedule.getName());
//                holder.scheduleDateAndTime.setText(schedule.getDateAndTime());
//                holder.scheduleStatus.setText(schedule.getStatus());
//            }
//            return convertView;
//        }
//    }

    public Date[] getAppointmentDates() {
        return appointmentDates;
    }

    private ArrayList<Schedule> getAppointmentList() {
        ArrayList<Schedule> schedules = new ArrayList<>();
        //Log.d("DEBUG", getIntent().getExtras().getInt("appointments") + "");
        for(int i = 0; i < getIntent().getExtras().getInt("appointments"); i++) {
            //Log.d("DEBUG", (i+1) + "");
//            schedules.add(new Schedule("Pertemuan " + (i+1), appointmentDates[i].getDate() + "/" + appointmentDates[i].getMonth() +
//            "/" + appointmentDates[i].getYear() + "  " + appointmentDates[i].getHours() + ":" + appointmentDates[i].getMinutes()));
            schedules.add(new Schedule("Pertemuan " + (i+1), DateFormatter.setScheduleFormatter(appointmentDates[i])));

        }

        return schedules;
    }

    private void decodeBundle() {
        /*
         * Initializing all necessary variables for the calculations
         */
        int[] calendar = getIntent().getExtras().getIntArray("calendar");
        int[] day = getIntent().getExtras().getIntArray("day");
        Date[] time = new Date[day.length];
        for(int i = 0; i < time.length; i++) {
            Pattern p = Pattern.compile("(\\d{1,2})\\.(\\d{1,2})\\s\\-\\s(\\d{1,2})\\.(\\d{1,2})");
            Matcher m = p.matcher(getIntent().getExtras().getStringArray("time")[i]);
            if (m.find()) {
                int h1 = Integer.parseInt(m.group(1));
                int m1 = Integer.parseInt(m.group(2));

                time[i] = new Time(h1, m1, 0);
            }
        }
        int appointments = getIntent().getExtras().getInt("appointments");
        appointmentDates = new Date[appointments];

        String dayOfWeek = DateFormatter.dayNameOf(calendar[0], calendar[1], calendar[2]);
        //Log.d("DEBUG", "ANS: " + dayOfWeek + " " + calendar[2]);

        int startingPoint = 0;

        for(int i = 0; i < day.length; i++) {
            if(PlanAppointmentController.days[day[i]].equalsIgnoreCase(dayOfWeek)) {
                startingPoint = i;
                break;
            }
        }

        for(int i = 0; i < appointments; i++) {
            appointmentDates[i] = new Date(
                    calendar[0],
                    calendar[1],
                    calendar[2] + (day[(startingPoint+i) % day.length] - day[startingPoint]) + (7 * ((startingPoint+i) / day.length)),
                    time[(startingPoint+i) % time.length].getHours(),
                    time[(startingPoint+i) % time.length].getMinutes());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_schedule);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Atur Jadwal");

        decodeBundle();
        scheduleLv.setAdapter(new SetScheduleActivity.ScheduleAdapters(this, getAppointmentList()));
    }

    @OnClick(R.id.set_schedule_btn__set_schedule_act)
    void submitAppointment(){
        new SetScheduleController().postDataAsync(SetScheduleActivity.this);
    }

    public void onDataSubmitted(int CODE) {
        if(CODE == -1) {
            Toast.makeText(this, "Submission fail, please try again", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "Request Sent", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SetScheduleActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
