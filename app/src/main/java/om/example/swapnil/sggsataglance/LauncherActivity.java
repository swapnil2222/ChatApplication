package om.example.swapnil.sggsataglance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class LauncherActivity extends Activity{
SessionManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_launcher);
                manager=new SessionManager();
        Thread thread=new Thread(){
        public void run()
        {
            try {
                //thread will sleep for 5 seconds
                sleep(3000);
                //After 3 seconds redirect to another activity
                String status=manager.getPreference(LauncherActivity.this,"Status");

                if(status.equals("1"))
                {
                    String user=manager.getPreference(LauncherActivity.this,"User");
                    String firstname=manager.getPreference(LauncherActivity.this,"firstname");
                    String surname=manager.getPreference(LauncherActivity.this,"surname");
                    String email=manager.getPreference(LauncherActivity.this,"email");

                    Intent i=new Intent(LauncherActivity.this,Welcome.class);
                    i.putExtra("message",user);
                    i.putExtra("firstname",firstname);
                    i.putExtra("surname",surname);
                    i.putExtra("email",email);


                    //Log.i("Activity","Started");
                    //Log.i("Username is:",user);
                    //Log.i("Firstname is:",firstname);
                    startActivity(i);
                }
                else
                {
                    Intent i=new Intent(LauncherActivity.this,MainActivity.class);
                    startActivity(i);
                }
                //remove activity
                finish();


            }catch (Exception e)
            {
//
            }
        }
        };
        thread.start();
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
