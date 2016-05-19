package om.example.swapnil.sggsataglance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    ImageView imageView;
    EditText username;
    EditText password;
    TextView forgotPassword;
    Button signIn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=(ImageView)findViewById(R.id.logo);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        forgotPassword=(TextView)findViewById(R.id.forgotPassword);
        signIn=(Button)findViewById(R.id.signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if(username.getText().toString().equals("admin")&& password.getText().toString().equals("admin@123"))
                        {
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                            intent=new Intent(getApplicationContext(),Welcome.class);
                            startActivity(intent);
                        }
                        if(username.getText().toString().equals("")||password.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Enter the Credentials",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "username Or password is invalid", Toast.LENGTH_SHORT).show();
                        }
                }

        });


    }

                @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
