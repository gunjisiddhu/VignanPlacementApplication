package com.vignan.vignan_placement_application.super_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.student.AppliedCompanyStatus;
import com.vignan.vignan_placement_application.student.Companies;
import com.vignan.vignan_placement_application.student.Home;
import com.vignan.vignan_placement_application.student.Profile;

public class SuperAdminMainActivity extends AppCompatActivity {

    com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_main);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);




        //FirebaseAuth.getInstance().signOut();
        /*if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }*/

        bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        relativeLayout = findViewById(R.id.main_body_container);


        getSupportFragmentManager().beginTransaction().replace(R.id.main_body_container,new Super_Admin_Home()).commit();
        bottomNavigationView.setSelectedItemId(R.id.home);

        /*getSupportFragmentManager().beginTransaction().replace(R.id.main_body_container,new Super_Admin_Profiles()).commit();
        bottomNavigationView.setSelectedItemId(R.id.user_profile);*/


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new Super_Admin_Home();
                        break;
                    case R.id.companies:
                        fragment = new Super_Admin_Companies();
                        break;
                    case R.id.company_status:
                        fragment = new Super_Admin_Status();
                        break;
                    case R.id.user_profile:
                        fragment = new Super_Admin_Profiles();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body_container,fragment).commit();

                return true;
            }
        });

    }
}