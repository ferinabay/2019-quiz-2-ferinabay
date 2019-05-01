package id.ac.polinema.todoretrofit.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import id.ac.polinema.todoretrofit.R;
import id.ac.polinema.todoretrofit.generator.ServiceGenerator;
import id.ac.polinema.todoretrofit.models.Envelope;
import id.ac.polinema.todoretrofit.models.User;
import id.ac.polinema.todoretrofit.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText nameText;
    private EditText passwordText;
    private UserService service;

    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // init constraintLayout
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        // initializing animation drawable by getting background from constraint layout
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

        // setting enter fade animation duration to 3 seconds
        animationDrawable.setEnterFadeDuration(3000);

        // setting exit fade animation duration to 2 seconds
        animationDrawable.setExitFadeDuration(2000);

        usernameText = findViewById(R.id.input_username);
        nameText = findViewById(R.id.input_name);
        passwordText = findViewById(R.id.input_password);

        service = ServiceGenerator.createService(UserService.class);
    }

    public void handleRegister(final View view) {
        User user = new User();
        user.setUsername(usernameText.getText().toString());
        user.setName(nameText.getText().toString());
        user.setPassword(passwordText.getText().toString());
        Call<Envelope<User>> register = service.createUser(user);
        register.enqueue(new Callback<Envelope<User>>() {
            @Override
            public void onResponse(Call<Envelope<User>> call, Response<Envelope<User>> response) {
                if (response.code() == 200) {
                    Envelope<User> okResponse = response.body();
                    User registeredUser = okResponse.getData();
                    String message = "Registration successfull, please login as " + registeredUser.getUsername();
                    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("Error", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Envelope<User>> call, Throwable t) {
                String message = "Error has occurred";
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void handleLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            // stop the animation
            animationDrawable.stop();
        }
    }
}
