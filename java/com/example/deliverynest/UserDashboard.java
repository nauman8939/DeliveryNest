package com.example.deliverynest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.animation.content.Content;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDashboard extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    SessionManager sessionManager;
    static final float END_SCALE = 0.7f;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LottieAnimationView menuIcon;
    LinearLayout contentView;
    ChipNavigationBar chipNavigationBar;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<RecentOrders> list;
    ImageSlider imageSlider;
    String fullname,username;
    TextView userfullname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        // find by id
         userfullname = findViewById(R.id.hello);
         imageSlider = findViewById(R.id.imageslider);
        //getting name from session and displaying on textview
        sessionManager = new SessionManager(this);
        HashMap<String, String> usersDetails = sessionManager.getUsersDetailsFromSession();
        fullname = usersDetails.get(SessionManager.KEY_FULLNAME);
         username = usersDetails.get(SessionManager.KEY_USERNAME);


        //offer section
       offerSection();
        //menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.navigationdrawer);
        contentView = findViewById(R.id.content);
        //logout process
        LottieAnimationView logoutButton = findViewById(R.id.usericon);
        // set the default checked item in the sidebar menu
        navigationView.setCheckedItem(R.id.nav_home);

        //bottom nav
        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        bottomNavigationView.setBackground(null);
        //Recent Orders
        recyclerView = findViewById(R.id.Recent_Orders);

        database = FirebaseDatabase.getInstance().getReference("orders");
        recyclerView(username);
        getRecords();
        animateNavigationDrawer();




    }
    public void getRecords(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child(username);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot: snapshot.getChildren()) {
                    if(datasnapshot.getKey().equals("name")){
                        userfullname.setText("Hey! , "+datasnapshot.getValue().toString()+".");

                    }


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void offerSection() {
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.discount_five, ScaleTypes.CENTER_INSIDE));
        slideModels.add(new SlideModel(R.drawable.discount_five, ScaleTypes.CENTER_INSIDE));
        slideModels.add(new SlideModel(R.drawable.discount_five, ScaleTypes.CENTER_INSIDE));
        slideModels.add(new SlideModel(R.drawable.discount_five, ScaleTypes.CENTER_INSIDE));
        slideModels.add(new SlideModel(R.drawable.discount_five, ScaleTypes.CENTER_INSIDE));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }

    private void recyclerView(String userName) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        Query query = database.orderByChild("orders").equalTo(userName).limitToLast(5);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String orderId = dataSnapshot.child("order_id").getValue(String.class);
                    String status = dataSnapshot.child("status").getValue(String.class);
                    RecentOrders recentOrders = new RecentOrders();
                    recentOrders.order_id = orderId;
                    recentOrders.status = status;
                    list.add(recentOrders);

                    // Show orders in RecyclerView
                    myAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    View noOrdersLayout = findViewById(R.id.Recent_Orders);
                    noOrdersLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        return true;
    }

    public void naviagtionDrawer(View view) {
        //Naviagtion Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        animateNavigationDrawer();

    }

    public void animateNavigationDrawer() {

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);
                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void logout(MenuItem item) {
        sessionManager.logout();
        Intent intent = new Intent(UserDashboard.this, LoginScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public  void UserProfile(View view){
        Intent i = new Intent(getApplicationContext(),User_Profile.class);
        startActivity(i);
    }

    public void UserProfile(MenuItem item) {
        Intent i = new Intent(getApplicationContext(),OrderSummary.class);
        startActivity(i);

    }
    public void CreateOrder(MenuItem item) {
        Intent i = new Intent(getApplicationContext(),CreateOrder.class);
        startActivity(i);

    }
    public void AboutUs(MenuItem item) {

        Intent intent = new Intent(UserDashboard.this, AboutUs.class);

        startActivity(intent);
    }


    public void naviagtionDrawerM(MenuItem item) {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        animateNavigationDrawer();

    }
}
