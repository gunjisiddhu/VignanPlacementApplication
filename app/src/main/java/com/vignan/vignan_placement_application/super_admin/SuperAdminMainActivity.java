package com.vignan.vignan_placement_application.super_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vignan.vignan_placement_application.R;
import com.vignan.vignan_placement_application.student.AppliedCompanyStatus;
import com.vignan.vignan_placement_application.student.Companies;
import com.vignan.vignan_placement_application.student.Home;
import com.vignan.vignan_placement_application.student.Profile;

public class SuperAdminMainActivity extends AppCompatActivity {

    com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;
    ConstraintLayout relativeLayout;
    com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton addNewCoordinator,addNewCompany;
    FloatingActionButton showMenu;
    static boolean visibility;

    private Animation fab_open,fab_close;

    final Fragment fragment11 = new Super_Admin_Home();
    final Fragment fragment22 = new Super_Admin_Companies();
    final Fragment fragment33 = new Super_Admin_Status();
    final Fragment fragment44 = new Super_Admin_Profiles();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_main);

       //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        visibility = false;

        showMenu = findViewById(R.id.fab_show_options);
        addNewCoordinator = findViewById(R.id.fab_add_new_coordinator);
        addNewCompany = findViewById(R.id.fab_add_new_company);
        fab_close = AnimationUtils.loadAnimation(SuperAdminMainActivity.this,R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(SuperAdminMainActivity.this,R.anim.fab_open);

        addNewCompany.setVisibility(View.GONE);
        addNewCoordinator.setVisibility(View.GONE);

        showMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(visibility){
                    addNewCompany.startAnimation(fab_close);
                    addNewCoordinator.startAnimation(fab_close);
                    addNewCompany.setVisibility(View.GONE);
                    addNewCoordinator.setVisibility(View.GONE);

                    showMenu.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.uparrow));

                    visibility = false;
                }else{
                    addNewCompany.startAnimation(fab_open);
                    addNewCoordinator.startAnimation(fab_open);
                    addNewCompany.setVisibility(View.VISIBLE);
                    addNewCoordinator.setVisibility(View.VISIBLE);
                    showMenu.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.downarrow));
                    visibility = true;
                }
            }
        });

        addNewCoordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuperAdminMainActivity.this,signupDeptCoordinator.class));
            }
        });


        addNewCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuperAdminMainActivity.this,CompanyInsertion.class));
            }
        });
        //FAB










        //FirebaseAuth.getInstance().signOut();
        /*if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }*/

        bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        relativeLayout = findViewById(R.id.main_body_container);




        /*getSupportFragmentManager().beginTransaction().replace(R.id.main_body_container,new Super_Admin_Profiles()).commit();
        bottomNavigationView.setSelectedItemId(R.id.user_profile);*/
        bottomNavigationView.setOnNavigationItemReselectedListener(item -> { });


        fm.beginTransaction().add(R.id.main_body_container, fragment44, "4").hide(fragment44).commit();
        fm.beginTransaction().add(R.id.main_body_container, fragment33, "3").hide(fragment33).commit();
        fm.beginTransaction().add(R.id.main_body_container, fragment22, "2").hide(fragment22).commit();
        fm.beginTransaction().add(R.id.main_body_container,fragment11, "1").commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        showFabMenu();
                        fm.beginTransaction().hide(active).show(fragment11).commit();
                        active = fragment11;
                        break;
                    case R.id.companies:
                        showFabMenu();
                        fm.beginTransaction().hide(active).show(fragment22).commit();
                        active = fragment22;
                        break;
                    case R.id.company_status:
                        showFabMenu();
                        fm.beginTransaction().hide(active).show(fragment33).commit();
                        active = fragment33;
                        break;
                    case R.id.user_profile:
                        closeFABIfOpen();
                        fm.beginTransaction().hide(active).show(fragment44).commit();
                        active = fragment44;
                        break;

                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.main_body_container,fragment).commit();

                return true;
            }
        });

    }

    private void showFabMenu() {
        showMenu.setVisibility(View.VISIBLE);

        if(visibility){
            addNewCompany.startAnimation(fab_close);
            addNewCoordinator.startAnimation(fab_close);
            addNewCompany.setVisibility(View.GONE);
            addNewCoordinator.setVisibility(View.GONE);
            showMenu.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.uparrow));
            visibility = false;
        }
    }

    private void closeFABIfOpen() {
        addNewCompany.startAnimation(fab_close);
        addNewCoordinator.startAnimation(fab_close);
        addNewCompany.setVisibility(View.GONE);
        addNewCoordinator.setVisibility(View.GONE);
        showMenu.setVisibility(View.GONE);
        showMenu.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.uparrow));
        visibility = false;


    }
}