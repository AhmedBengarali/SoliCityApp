package com.mar.solicity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;
import com.google.firebase.firestore.auth.User;
import com.mar.solicity.data.Users;
import com.mar.solicity.loginandregister.LoginActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    //    FirebaseAuth firebaseAuth;
    private AppBarConfiguration mAppBarConfiguration;
    private long backPressedTime;
    private Toast backtoast;
    String user_role;
    String userId;
    private Users retrievedUser;


    public MainActivity() {
    }

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;
    private Menu nav_Menu;
    //vars
    private Context mContext;
    private double mPhotoUploadProgress = 0;

    public MainActivity(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext = context;

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userId = mAuth.getCurrentUser().getUid();

        DatabaseReference databaseReference = mFirebaseDatabase.getReference(userId);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        nav_Menu = navigationView.getMenu();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                getCurrentUser(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_beneficiary, R.id.nav_product, R.id.nav_donation, R.id.nav_add_user,R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setItemIconTintList(null);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int menuId = destination.getId();

                switch (menuId) {

                }
            }
        });
    }

    public Users getCurrentUser(DataSnapshot dataSnapshot) {

        Log.d(TAG, "getUserSettings: retrieving user account settings from firebase.");


        Users user = new Users();
        final String uID = mAuth.getCurrentUser().getUid();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            //Users dbuser =  (Users) ds.getValue(Users.class);
            // user_account_settings node
            if (ds.getKey().equals("Users")) {

                Log.d(TAG, "userId: " + uID);
                Log.d(TAG, "role: " +
                        ds.child(uID)
                                .getValue(Users.class)
                                .getUserRole()
                );
                if (!ds.child(uID)
                        .getValue(Users.class)
                        .getUserRole().equals("Admin")) {
                    nav_Menu.findItem(R.id.nav_add_user).setVisible(false);

                }

            }
        }

        return user;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_logout:
                mAuth.signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backtoast.cancel();
            super.onBackPressed();
            return;
        } else {
            backtoast = Toast.makeText(getBaseContext(), "Appuyez à nouveau pour quitter", Toast.LENGTH_SHORT);
            backtoast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
