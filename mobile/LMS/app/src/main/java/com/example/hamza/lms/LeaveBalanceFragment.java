package com.example.hamza.lms;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lms.Employee;
import lms.Network;

import static java.lang.Integer.parseInt;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaveBalanceFragment extends Fragment {

    private String result;
    private TextView textView;
    private String url;
    private ListView listView;
    private GridView gridView;
    private Employee emp;
    private Context context;
    RequestQueue queue;
    public static TextView annual_total;
    private static TextView annual_taken;
    private static TextView annual_remaining;
    private static TextView excep_total;
    private static TextView excep_taken;
    private static TextView excep_remaining;
    private static TextView sick_taken;


    public LeaveBalanceFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave_balance, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        emp = (Employee) getArguments().getSerializable("emp");
        url = Network.root + emp.getEmployeeID() + "/leave-balance";
        context = getActivity();
        queue = Volley.newRequestQueue(getActivity());


        annual_total = (TextView) getView().findViewById(R.id.annual_total);

        annual_taken = (TextView) getView().findViewById(R.id
                .annual_taken);
        annual_remaining = (TextView) getView().findViewById(R.id
                .annual_remaining);

        excep_total = (TextView) getView().findViewById(R.id
                .excep_total);
        excep_taken = (TextView) getView().findViewById(R.id
                .excep_taken);
        excep_remaining = (TextView) getView().findViewById(R.id
                .excep_remaining);

        sick_taken = (TextView) getView().findViewById(R.id
                .sick_taken);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            readJSON(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

    public void readJSON(String json) throws JSONException {
        /*
        JSONArray jsonArray = new JSONArray(json);
        List<String> list = new ArrayList<String>();

        for (int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject
                    ("fields");
            String totalstr = jsonObject.getString("total_days");
            int taken = jsonObject.getInt("taken_days");
            int remaining = 0;

            if(totalstr.compareTo("null")!=0) {
                remaining = taken + parseInt(totalstr);
            }

            list.add(jsonObject.getString("leave_type"));
            list.add(totalstr);
            list.add(String.valueOf(taken));
            list.add(String.valueOf(remaining));
        }
        return list;
        */

        JSONArray jsonArray = new JSONArray(json);


            JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject
                    ("fields");
            String totalstr = jsonObject.getString("total_days");
            int taken = jsonObject.getInt("taken_days");
            int remaining = 0;

            if(totalstr.compareTo("null")!=0) {
                remaining = parseInt(totalstr) - taken;
            }

            annual_total.setText(totalstr);
            annual_taken.setText(String.valueOf(taken));
            annual_remaining.setText("   "+String.valueOf(remaining));

        jsonObject = jsonArray.getJSONObject(1).getJSONObject
                ("fields");
        totalstr = jsonObject.getString("total_days");
        taken = jsonObject.getInt("taken_days");
        remaining = 0;

        if(totalstr.compareTo("null")!=0) {
            remaining = parseInt(totalstr) - taken;
        }

        excep_total.setText(totalstr);
        excep_taken.setText(String.valueOf(taken));
        excep_remaining.setText("   "+String.valueOf(remaining));

        jsonObject = jsonArray.getJSONObject(2).getJSONObject
                ("fields");
        totalstr = jsonObject.getString("total_days");
        taken = jsonObject.getInt("taken_days");
        remaining = 0;
        /*
        if(totalstr.compareTo("null")!=0) {
            remaining = parseInt(totalstr) - taken;
        }*/

        sick_taken.setText(String.valueOf(taken));

    }

}
