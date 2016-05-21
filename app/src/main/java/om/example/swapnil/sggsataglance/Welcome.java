package om.example.swapnil.sggsataglance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Welcome extends ActionBarActivity implements View.OnClickListener {
SessionManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        manager=new SessionManager();
        Button button=(Button)findViewById(R.id.btn_logout);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
    switch (view.getId())
    {
        case  R.id.btn_logout:
            manager.setPreference(Welcome.this,"Status","0");
            Toast.makeText(getApplicationContext(),"You have Suucessfully Logout",Toast.LENGTH_LONG).show();
            finish();
    }
    }
//to stop user from going welcome page to login page on back button press
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
