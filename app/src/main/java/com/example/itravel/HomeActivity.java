package com.example.itravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itravel.Adapter.PostAdapter;
import com.example.itravel.Model.Post;
import com.example.itravel.Model.User;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private RecyclerView rv_posts;
    private List<Post> posts;
    private PostAdapter postAdapter;
    Button weather, map, addPost, logout;

    FloatingActionButton profile, update_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // init components
        rv_posts = findViewById(R.id.rv_posts);
        rv_posts.setLayoutManager(new LinearLayoutManager(this));
        rv_posts.setHasFixedSize(true);

        // recycler fetch and send data.
        FirebaseDatabase pdb = FirebaseDatabase.getInstance();
        DatabaseReference pref = pdb.getReference().child("Posts");

        pref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                posts = new ArrayList<>();
                for (DataSnapshot postsSnap: snapshot.getChildren()){
                    Post post = postsSnap.getValue(Post.class);
                    posts.add(post);
                }
                //add data to our postAdapter
                postAdapter = new PostAdapter(posts);
                rv_posts.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        addPost = findViewById(R.id.btn_post);

        map = findViewById(R.id.btn_map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
            }
        });

        weather = findViewById(R.id.weather);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WeatherActivity.class);
                startActivity(intent);

            }
        });

        FloatingActionsMenu floatingMenu = findViewById(R.id.fab_menu);
        ((FloatingActionsMenu)floatingMenu).setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                findViewById(R.id.main_home_layout).getBackground().setAlpha(128); //change opacity here
            }

            @Override
            public void onMenuCollapsed() {
                findViewById(R.id.main_home_layout).getBackground().setAlpha(64); //change opacity here
            }
        });

        profile = findViewById(R.id.view_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PostActivity.class));
            }
        });

        update_profile = findViewById(R.id.profile_setting);
        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        User userProfile = snapshot.getValue(User.class);

                        if (userProfile != null) {

                            String username = userProfile.username;
                            String email = userProfile.email;
                            String phone = userProfile.phone;

                            Intent i = new Intent(v.getContext(), EditProfileActivity.class);

                            i.putExtra("username", username);
                            i.putExtra("email", email);
                            i.putExtra("phone", phone);

                            startActivity(i);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HomeActivity.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
                    }
                });


            }

        });

    }

}
