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


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaveHistoryFragment extends Fragment {

    private RequestQueue queue;
    private Employee emp;
    private String url;
    private ListView listView;
    private GridView gridView;
    private Context context;
    private static List<String> list;
    private ArrayAdapter<String> adapter;

    public LeaveHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leave_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        emp = (Employee) getArguments().getSerializable("emp");
        url = Network.root + emp.getEmployeeID() + "/leave-history";
        context = getActivity();
        queue = Volley.newRequestQueue(context);

        list = new ArrayList<String>();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            list = readJSON(response);
                            adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_list_item_1, list);
                            gridView = (GridView) getView().findViewById(R.id
                                    .history);
                            gridView.setAdapter(adapter);
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

    public List<String> readJSON(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        list.add("leave_type");
        list.add("request_date");
        list.add("start_date");
        list.add("end_date");
        list.add("status");
        list.add("------------");
        list.add("------------");
        list.add("------------");
        list.add("------------");
        list.add("------------");

        for (int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject
                    ("fields");
            list.add(jsonObject.getString("leave_type"));
            list.add(jsonObject.getString("request_date"));
            list.add(jsonObject.getString("start_date"));
            list.add(jsonObject.getString("end_date"));
            list.add(jsonObject.getString("status"));
        }
        return list;
    }

}
