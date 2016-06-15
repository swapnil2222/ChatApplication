package om.example.swapnil.sggsataglance;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;

import android.os.AsyncTask;

import android.util.Log;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    ImageView imageView;
    EditText username;
    EditText password;
    TextView forgotPassword;
    Button signIn;
    Intent intent;
    private static String user;
SessionManager manager;
    private ProgressDialog pDialog;
    NetworkInfo wifiCheck;
    JSONArray users;
    JSONObject hay;
    String firstname;
    String usernames;
    String surname;
    String email;



    JSONParser jsonParser=new JSONParser();
//    ConnectivityManager connection=(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

//php login script location:

    //localhost :
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
    // private static final String LOGIN_URL = "http://xxx.xxx.x.x:1234/webservice/login.php";

    //testing on Emulator:

   //private static final String LOGIN_URL = "http://192.168.0.197/sggsApp/login.php";
//testing from a real server:
private static final String LOGIN_URL = "http://sggsapp.tk/sggsApp/login.php";

    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=(ImageView)findViewById(R.id.logo);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        forgotPassword=(TextView)findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

        signIn=(Button)findViewById(R.id.signin);
        signIn.setOnClickListener(this);

           //to create SessionManagers Object
        manager=new SessionManager();

        }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.signin:
                user=username.getText().toString();
                String pass=password.getText().toString();

                if(username.getText().toString().equals("")||password.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter the Credentials",Toast.LENGTH_LONG).show();
                }

                 if(isInternetOn())
                {


                    new AttemptLogin().execute(user, pass);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Check Internet Connection",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.forgotPassword:
                Intent i=new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(i);
                break;

        }

    }
    public String getMessage()
    {

        return user;
    }
    public String getFirstname(){return firstname;}
    public String getSurname(){return surname;}
    public String getEmail(){return email;}




    public final boolean isInternetOn() {

        //get connectivity manager object to check internet
        ConnectivityManager connection=(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        wifiCheck=connection.getNetworkInfo(connection.TYPE_WIFI);

        //Check for network Connection
        if(connection.getNetworkInfo(0).getState()==NetworkInfo.State.CONNECTED || connection.getNetworkInfo(0).getState()== NetworkInfo.State.CONNECTING ||
                connection.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connection.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED )
        {
            //Toast.makeText(getApplicationContext(),"Connected to internet",Toast.LENGTH_LONG).show();
            return true;
        }
        else if(connection.getNetworkInfo(0).getState()== NetworkInfo.State.DISCONNECTED ||
                connection.getNetworkInfo(1).getState()==NetworkInfo.State.DISCONNECTED
                )
        {
            //
        }



        return false;
    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Login...!");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {
            int success;
            String username = args[0];
            String password = args[1];


            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    //String status=manager.getPreference(MainActivity.this,"Status");
                    String jsons = null;
                    try {
                        List<NameValuePair> param = new ArrayList<NameValuePair>();
                        param.add(new BasicNameValuePair("username",user));

                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(LOGIN_URL);
                        httppost.setEntity(new UrlEncodedFormEntity(params));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity resEntity = response.getEntity();
                        jsons = EntityUtils.toString(resEntity);
                        hay = new JSONObject(jsons);
                        JSONArray users = hay.getJSONArray("posts");
                        JSONObject jb = users.getJSONObject(0);
                        firstname = jb.getString("firstname");
                        usernames = jb.getString("username");
                        surname = jb.getString("surname");
                        email = jb.getString("email");

                        manager.setPreference(MainActivity.this, "Status", "1");
                        manager.setPreference(MainActivity.this, "User", user);
                        manager.setPreference(MainActivity.this,"firstname",firstname);
                        manager.setPreference(MainActivity.this,"surname",surname);
                        manager.setPreference(MainActivity.this,"email",email);

                        Log.i("Profile JSON: ", json.toString());
                     //   Log.i("Firstname:",firstname);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent i = new Intent(MainActivity.this, Welcome.class);
                    i.putExtra("message", user);
                    i.putExtra("firstname",firstname);
                    i.putExtra("surname",surname);
                    i.putExtra("email",email);
                    finish();
                    startActivity(i);
                   // Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                   // Log.i("Activty ","Started");
                   // return jsons;
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    //Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
            }
      //  public String getMassage() {return usernames;}




    }



    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        //System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                // About option clicked.
                return true;

            case R.id.action_settings:
                // Settings option clicked.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
