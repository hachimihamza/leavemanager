package com.example.hamza.lms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import lms.Employee;
import lms.Network;

public class EmployeeLoginActivity extends AppCompatActivity {

    private String employeeID;
    private String password;
    private String role = "employee";
    private String url = Network.root + "employee-signin";
    private Context context = this;
    private TextView loginFailed;
    private TextView passwordView;
    private TextView employeeIDView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_employee_login);

        loginFailed = (TextView) findViewById(R.id.login_failed);
        passwordView = (TextView) findViewById(R.id.password);
        employeeIDView = (TextView) findViewById(R.id.email);
    }

    public void employeeSigninRequest(View v) {

        RequestQueue queue = Volley.newRequestQueue(this);
        this.employeeID = employeeIDView.getText().toString();
        this.password = passwordView.getText().toString();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method
                .POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.compareTo("employee") == 0) {
                            Intent intent = new Intent(context, HomeActivity.class);
                            Employee emp = new Employee(employeeID);
                            intent.putExtra("emp", emp);
                            intent.putExtra("type", "employee");
                            startActivity(intent);
                        } else if (response.compareTo("manager") == 0) {
                            Intent intent = new Intent(context, HomeActivity.class);
                            Employee emp = new Employee(employeeID);
                            intent.putExtra("emp", emp);
                            intent.putExtra("type", "manager");
                            startActivity(intent);
                        } else if (response.compareTo("DRH") == 0) {
                            Intent intent = new Intent(context, HomeActivity.class);
                            Employee emp = new Employee(employeeID);
                            intent.putExtra("emp", emp);
                            intent.putExtra("type", "DRH");
                            startActivity(intent);
                        } else {
                            loginFailed.setText(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loginFailed.setText("Network Error. try again");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("employee_id", employeeID);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", employeeID + password);
                params.put("username", employeeID);
                params.put("password", password);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
