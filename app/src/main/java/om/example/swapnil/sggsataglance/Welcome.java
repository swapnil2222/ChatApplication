package om.example.swapnil.sggsataglance;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Welcome extends FragmentActivity implements ActionBar.TabListener{
SessionManager manager;
    //This is our tablayout
    ActionBar actionBar;
    ViewPager viewPager;
    FragmentPageAdapter ft;
    static String message;
    static String firstname;
    static String surname;
    static String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewPager=(ViewPager)findViewById(R.id.pager);
        ft=new FragmentPageAdapter(getSupportFragmentManager());
        actionBar=getActionBar();
        viewPager.setAdapter(ft);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Profile").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Search Staff").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Messages").setTabListener(this));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
        actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        manager=new SessionManager();
//        Button button=(Button)findViewById(R.id.btn_logout);

        //Adding onTabSelectedListener to swipe views


     //   button.setOnClickListener(this);

//to get data from main activity
       Bundle bundle=getIntent().getExtras();
        message=bundle.getString("message");
        firstname=bundle.getString("firstname");
        surname=bundle.getString("surname");
        email=bundle.getString("email");

       /* MainActivity object=new MainActivity();
        message=object.getMessage();
*/
    }
    public String getMassage() {return message;}
    public String getFirstname(){return firstname;}
    public String getSurname(){return surname;}
    public String getEmail(){return email;}



    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                // About option clicked.
                return true;
            case R.id.action_exit:
                // Exit option clicked.

                manager.setPreference(Welcome.this, "Status", "0");
                    Toast.makeText(getApplicationContext(),"You have Suucessfully Logout",Toast.LENGTH_LONG).show();
                finish();

                return true;
            case R.id.action_settings:
                // Settings option clicked.
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
