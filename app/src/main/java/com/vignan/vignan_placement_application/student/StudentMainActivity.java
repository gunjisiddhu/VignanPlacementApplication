package com.vignan.vignan_placement_application.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.dept_cordinator.dept_approve_list;
import com.vignan.vignan_placement_application.dept_cordinator.dept_home;
import com.vignan.vignan_placement_application.dept_cordinator.dept_profile;

public class StudentMainActivity extends AppCompatActivity {

    com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;
    RelativeLayout relativeLayout;


    final Fragment fragment11 = new Home();
    final Fragment fragment22 = new Companies();
    final Fragment fragment33 = new AppliedCompanyStatus();
    final Fragment fragment44 = new Profile();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);




        //FirebaseAuth.getInstance().signOut();
        /*if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }*/

        bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        relativeLayout = findViewById(R.id.main_body_container);


        bottomNavigationView.setOnNavigationItemReselectedListener(item -> { });


        fm.beginTransaction().add(R.id.main_body_container, fragment44, "4").hide(fragment44).commit();
        fm.beginTransaction().add(R.id.main_body_container, fragment33, "3").hide(fragment33).commit();
        fm.beginTransaction().add(R.id.main_body_container, fragment22, "2").hide(fragment22).commit();
        fm.beginTransaction().add(R.id.main_body_container,fragment11, "1").commit();

        bottomNavigationView.setOnNavigationItemReselectedListener(item -> { });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        fm.beginTransaction().hide(active).show(fragment11).commit();
                        active = fragment11;
                        break;
                    case R.id.companies:
                        fm.beginTransaction().hide(active).show(fragment22).commit();
                        active = fragment22;
                        break;
                    case R.id.company_status:
                        fm.beginTransaction().hide(active).show(fragment33).commit();
                        active = fragment33;
                    case R.id.user_profile:
                        fm.beginTransaction().hide(active).show(fragment44).commit();
                        active = fragment44;
                        break;

                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.main_body_container,fragment).commit();

                return true;
            }
        });



    }
}