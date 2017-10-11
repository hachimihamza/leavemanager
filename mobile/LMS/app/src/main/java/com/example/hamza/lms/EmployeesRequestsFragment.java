package com.example.hamza.lms;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lms.Employee;
import lms.Network;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeesRequestsFragment extends Fragment {
    private RequestQueue queue;
    private Employee emp;
    private String url;
    private ListView listView;
    private GridView gridView;
    private Context context;
    private static List<String> list;
    private ArrayAdapter<String> adapter;
    String type;
    private String myEmployeeID;
    OnRequestIDSelectedListener mCallback;

    public EmployeesRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employees_requests, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        emp = (Employee) getArguments().getSerializable("emp");
        type = (String) getArguments().getString("type");
        //type = getIntent().getStringExtra("type");
        if(type.compareTo("manager")==0) {
            url = Network.root + emp.getEmployeeID() + "/manager-requests";
        } else if (type.compareTo("DRH")==0 ) {
            url = Network.root + emp.getEmployeeID() + "/drh-requests";
        }
        context = getActivity();
        queue = Volley.newRequestQueue(context);
        list = new ArrayList<String>();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            try {
                                list = readJSON(response);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_list_item_1, list);
                            gridView = (GridView) getView().findViewById(R.id
                                    .employeesRequests);
                            gridView.setAdapter(adapter);

                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View v,
                                                        int position, long id) {
                                    TextView textView = (TextView) v;
                                    String myRequestID = textView.getText()
                                            .toString();
                                    Toast.makeText(getActivity(), myRequestID,
                                            Toast.LENGTH_SHORT).show();
                                    mCallback.onRequestIDSelectedListener
                                            (myRequestID, myEmployeeID, emp
                                                    .getEmployeeID());
                                }
                            });

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

    public List<String> readJSON(String json) throws JSONException, ParseException {
        JSONArray jsonArray = new JSONArray(json);
        list.add("Request ID");
        list.add("leave_type");
        list.add("Duration");
        list.add("start_date");
        list.add("end_date");
        list.add("---------------");
        list.add("---------------");
        list.add("---------------");
        list.add("---------------");
        list.add("---------------");

        for (int i=0; i< jsonArray.length(); i++){
            list.add(jsonArray.getJSONObject(i).getString("pk"));

            JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject
                    ("fields");
            myEmployeeID = jsonObject.getString("employee");
            list.add(jsonObject.getString("leave_type"));
            //list.add(jsonObject.getString("request_date"));
            /*Date startDate = new Date(jsonObject.getString("start_date"));
            Date endDate = new Date(jsonObject.getString("end_date"));
            long n = endDate.getTime() - startDate.getTime();
            long duration = TimeUnit.DAYS.convert(n, TimeUnit.MILLISECONDS);
            */


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date sd = dateFormat.parse(jsonObject.getString("start_date"));
            Date ed = dateFormat.parse(jsonObject.getString("end_date"));
            long n = ed.getTime() - sd.getTime();
            long duration = TimeUnit.DAYS.convert(n, TimeUnit.MILLISECONDS);

            list.add(String.valueOf(duration));
            list.add(jsonObject.getString("start_date"));
            list.add(jsonObject.getString("end_date"));


        }
        return list;
    }

    public interface  OnRequestIDSelectedListener {
        public void onRequestIDSelectedListener(String requestID, String
                employeeID, String managerID);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnRequestIDSelectedListener) activity;
        }catch(ClassCastException e){

        }
    }
}
