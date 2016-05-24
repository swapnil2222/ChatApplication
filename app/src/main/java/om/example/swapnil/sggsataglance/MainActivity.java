package om.example.swapnil.sggsataglance;

import android.app.Activity;
import android.content.Intent;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;

import android.os.AsyncTask;

import android.util.Log;

public class MainActivity extends Activity implements View.OnClickListener {
    ImageView imageView;
    EditText username;
    EditText password;
    TextView forgotPassword;
    Button signIn;
    Intent intent;
SessionManager manager;
    private ProgressDialog pDialog;

JSONParser jsonParser=new JSONParser();
//php login script location:

    //localhost :
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
    // private static final String LOGIN_URL = "http://xxx.xxx.x.x:1234/webservice/login.php";

    //testing on Emulator:

    private static final String LOGIN_URL = "http://192.168.0.197/sggsApp/login.php";
//testing from a real server:
    //private static final String LOGIN_URL = "http://www.yourdomain.com/webservice/login.php";

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
                String user=username.getText().toString();
                String pass=password.getText().toString();
                new AttemptLogin().execute(user,pass);

                if(username.getText().toString().equals("")||password.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter the Credentials",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.forgotPassword:
                Intent i=new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(i);
                break;

        }

    }
    class AttemptLogin extends AsyncTask<String, String, String> {

        boolean failure = false;

        @Override
        protected String doInBackground(String... args) {
            int success;
            String username=args[0];
            String password=args[1];


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
                    manager.setPreference(MainActivity.this, "Status", "1");
                    String status=manager.getPreference(MainActivity.this,"Status");

                    Intent i = new Intent(MainActivity.this, Welcome.class);
                    finish();
                    startActivity(i);

                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();
            }

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Login...!");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        }




    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

}
