package com.example.hamza.lms;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lms.Employee;
import lms.Network;
import lms.RequestLeave;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {
    private String url = Network.root + "request-leave";

    private boolean downloading = false;
    public static RequestLeave requestLeave = new RequestLeave();
    private Button saveInfo;
    private Button cancelInfo;
    private Button refresh;
    private Employee emp;
    private static RequestQueue queue;
    public static EditText firstName;
    public static EditText lastName;
    public static EditText email;
    public static EditText phone;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        emp = (Employee) getArguments().getSerializable("emp");
        saveInfo = (Button)getView().findViewById(R.id.save_info);
        cancelInfo = (Button)getView().findViewById(R.id.cancel_info);
        refresh = (Button)getView().findViewById(R.id.refresh);

        firstName = (EditText)getView().findViewById(R.id.first_name);
        lastName = (EditText)getView().findViewById(R.id.last_name);
        email = (EditText)getView().findViewById(R.id.email);
        phone = (EditText)getView().findViewById(R.id.phone);

        queue = Volley.newRequestQueue(getActivity());
        String getInfo = Network.root + emp.getEmployeeID()
                +"/basic-information";
        firstName.setText(emp.getEmployeeID());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject
                                    ("fields");
                            firstName.setText(jsonObject.getString
                                    ("first_name"));
                            lastName.setText(jsonObject.getString
                                    ("last_name"));
                            email.setText(jsonObject.getString
                                    ("email"));
                            phone.setText(jsonObject.getString
                                    ("phone"));
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

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName.setText("appel");
                String getInfo = Network.root + emp.getEmployeeID() +
                        "/basic-information";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, getInfo,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject
                                            ("fields");
                                    firstName.setText(jsonObject.getString
                                            ("first_name"));
                                    lastName.setText(jsonObject.getString
                                            ("last_name"));
                                    email.setText(jsonObject.getString
                                            ("email"));
                                    phone.setText(jsonObject.getString
                                            ("phone"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        firstName.setText(error.toString());
                    }
                });
                queue.add(stringRequest);
            }
        });

        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo(v);
            }
        });
        cancelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
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

    public void saveInfo(View v){
        String saveUrl = Network.root + emp.getEmployeeID() + "/save-info";
        StringRequest stringRequest = new StringRequest(Request.Method
                .POST, saveUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), "Your data has been " +
                                        "edited successfully",
                                Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //toast("Network Error. try again");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("first_name", firstName.getText().toString());
                params.put("last_name", lastName.getText().toString());
                params.put("email", email.getText().toString());
                params.put("phone", phone.getText().toString());
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

    public void cancelInfo(View v){

    }

}
