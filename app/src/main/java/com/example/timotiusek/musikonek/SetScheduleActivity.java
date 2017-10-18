package com.example.timotiusek.musikonek;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timotiusek.musikonek.CustomClass.PlanAppointmentController;
import com.example.timotiusek.musikonek.CustomClass.Schedule;
import com.example.timotiusek.musikonek.CustomClass.SetScheduleController;
import com.example.timotiusek.musikonek.Helper.DateFormatter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetScheduleActivity extends AppCompatActivity {

    @BindView(R.id.schedule_lv__set_schedule_act) ListView scheduleLv;
    @BindView(R.id.set_schedule_btn__set_schedule_act) Button setScheduleBtn;

    private GregorianCalendar[] appointmentDates;

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

    public GregorianCalendar[] getAppointmentDates() {
        return appointmentDates;
    }

    private ArrayList<Schedule> getAppointmentList() {
        ArrayList<Schedule> schedules = new ArrayList<>();
        Log.d("DEBUG", getIntent().getExtras().getInt("appointments") + "");
        for(int i = 0; i < getIntent().getExtras().getInt("appointments"); i++) {
            Log.d("DEBUG", (i+1) + "");
            schedules.add(new Schedule("Pertemuan " + (i+1), appointmentDates[i].get(Calendar.DATE) +
                    "/" + (appointmentDates[i].get(Calendar.MONTH)+1) + "/" + appointmentDates[i].get(Calendar.YEAR) +
                    "  " + appointmentDates[i].get(Calendar.HOUR) + ":" + appointmentDates[i].get(Calendar.MINUTE)));
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
        appointmentDates = new GregorianCalendar[appointments];

        String dayOfWeek = DateFormatter.dayNameOf(calendar[0], calendar[1], calendar[2]);
        Log.d("DEBUG", "ANS: " + dayOfWeek + " " + calendar[2]);

        int startingPoint = 0;

        for(int i = 0; i < day.length; i++) {
            if(PlanAppointmentController.days[day[i]].equalsIgnoreCase(dayOfWeek)) {
                startingPoint = i;
                break;
            }
        }

        for(int i = 0; i < appointments; i++) {
            appointmentDates[i] = new GregorianCalendar(
                    calendar[0],
                    calendar[1],
                    calendar[2] + (day[(startingPoint+i) % day.length] - day[startingPoint]) + (7 * ((startingPoint+i) / day.length)),
                    time[(startingPoint+i) % time.length].getHours(),
                    time[(startingPoint+i) % time.length].getMinutes(),
                    0);
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
