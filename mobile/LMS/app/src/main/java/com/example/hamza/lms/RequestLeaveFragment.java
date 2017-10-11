package com.example.hamza.lms;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lms.Employee;
import lms.Network;
import lms.RequestLeave;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestLeaveFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private String url = Network.root + "request-leave";

    private boolean downloading = false;
    public static RequestLeave requestLeave = new RequestLeave();
    public static Button startDateBtn;
    public static Button endDateBtn;
    public static Button submitBtn;
    public static Button cancelBtn;
    public static TextView responseView;
    private static TextView duration;
    private Employee emp;
    RequestQueue queue;

    public RequestLeaveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_leave, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        emp = (Employee) getArguments().getSerializable("emp");
        requestLeave.setEmployeeID(emp.getEmployeeID());
        startDateBtn = (Button) getView().findViewById(R.id.startDateBtn);
        endDateBtn = (Button) getView().findViewById(R.id.endDateBtn);
        submitBtn = (Button) getView().findViewById(R.id.submit);
        //cancelBtn = (Button) getView().findViewById(R.id.cancel);
        duration = (TextView) getView().findViewById(R.id.duration);
        responseView =(TextView) getView().findViewById(R
                .id.response);
        Spinner spinner = (Spinner) getView().findViewById(R.id.spinner);
        List<CharSequence> charSequences = new ArrayList<CharSequence>();
        charSequences.add("Annual");
        charSequences.add("Exceptional");
        charSequences.add("Sick");

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>
                (getActivity(), R.layout.support_simple_spinner_dropdown_item,
                        charSequences);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        queue = Volley.newRequestQueue(getActivity());
        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new StartDatePickerFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "Start" +
                        " Date");
            }
        });

        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new RequestLeaveFragment
                        .EndDatePickerFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "End " +
                        "Date");
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d = Integer.parseInt(duration.getText().toString());
                if(d <= 0){
                    Toast.makeText(getActivity(), "Error",
                            Toast.LENGTH_LONG).show();
                } else {
                    submitRequest(v);
                }
            }
        });

        /*
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String leaveType = "Annual";
        switch (position) {
            case 0:
                leaveType = "Annual"; break;
            case 1:
                leaveType = "Exceptional"; break;
            case 2:
                leaveType = "Sick"; break;
        }
        requestLeave.setLeaveType(leaveType);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void cancelRequest(View v) {

    }
    public void submitRequest(View v){

        StringRequest stringRequest = new StringRequest(Request.Method
                .POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //responseView.setText(response);
                        Toast.makeText(getActivity(), response,
                                Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //toast("Network Error. try again");
                duration.setText(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("employee_id", requestLeave.getEmployeeID());
                params.put("leave_type", requestLeave.getLeaveType());
                params.put("request_date", requestLeave.getRequestDate());
                params.put("start_date", requestLeave.getStartDate());
                params.put("end_date", requestLeave.getEndDate());
                params.put("status", requestLeave.getStatus());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Authorization", requestLeave.getEmployeeID());
                params.put("username" , requestLeave.getEmployeeID() );
                params.put("password" , "password" );
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public static class StartDatePickerFragment extends DialogFragment
            implements
            DatePickerDialog.OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int day =  calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar. YEAR);
            return new DatePickerDialog(getActivity(), this,
                    year, month,
                    day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Date date = new Date();
            date.setYear(year);
            date.setMonth(month);
            date.setDate(dayOfMonth);
            requestLeave.setStartDate(date);
            startDateBtn.setText(requestLeave.getStartDate());

        }
    }


    public static class EndDatePickerFragment extends DialogFragment
            implements
            DatePickerDialog.OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int day =  calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar. YEAR);
            return new DatePickerDialog(getActivity(), this,
                    year, month,
                    day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Date date = new Date();
            date.setYear(year);
            date.setMonth(month);
            date.setDate(dayOfMonth);
            requestLeave.setEndDate(date);
            requestLeave.computeDuration();
            endDateBtn.setText(requestLeave.getEndDate());
            duration.setText(String.valueOf(requestLeave.getDuration()));

        }
    }

}
