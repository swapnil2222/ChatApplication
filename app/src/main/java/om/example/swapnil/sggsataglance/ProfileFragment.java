package om.example.swapnil.sggsataglance;

import android.app.ProgressDialog;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swapnil on 6/11/2016.
 */
public class ProfileFragment extends Fragment {
    TextView textViewUname;
    TextView textViewSname;
    TextView textViewFname;
    TextView textViewEmail;
    JSONArray user;
    JSONObject hay;
    static String message;
    static String firstname;
    static String surname;
    static String email;

    private static final String LOGIN_URL = "http://sggsapp.tk/sggsApp/profile.php";

    //    EditText editText;
    private ProgressDialog pDialog;
    NetworkInfo wifiCheck;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        //to get username from welcome activity
      Welcome welcome=(Welcome)getActivity();
        message=welcome.getMassage();
        firstname=welcome.getFirstname();
        surname=welcome.getSurname();
        email=welcome.getEmail();

        textViewFname=(TextView)view.findViewById(R.id.firstnames);
        textViewUname=(TextView)view.findViewById(R.id.username);
        textViewSname=(TextView)view.findViewById(R.id.surname);
        textViewEmail=(TextView)view.findViewById(R.id.email);

        textViewFname.setText( firstname);
        textViewUname.setText( message);
        textViewSname.setText( surname);
        textViewEmail.setText( email);

        return view;
    }

    }


