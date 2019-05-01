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

import id.ac.polinema.todoretrofit.Application;
import id.ac.polinema.todoretrofit.R;
import id.ac.polinema.todoretrofit.Session;
import id.ac.polinema.todoretrofit.generator.ServiceGenerator;
import id.ac.polinema.todoretrofit.models.Envelope;
import id.ac.polinema.todoretrofit.models.User;
import id.ac.polinema.todoretrofit.services.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;

    private AuthService service;
    private Session session;

    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);

        service = ServiceGenerator.createService(AuthService.class);
        session = Application.provideSession();

        // init constraintLayout
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        // initializing animation drawable by getting background from constraint layout
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

        // setting enter fade animation duration to 3 seconds
        animationDrawable.setEnterFadeDuration(3000);

        // setting exit fade animation duration to 2 seconds
        animationDrawable.setExitFadeDuration(2000);
    }

    public void handleLogin(final View view) {
        User user = new User();
        user.setUsername(usernameInput.getText().toString());
        user.setPassword(passwordInput.getText().toString());
        Call<Envelope<String>> auth = service.getToken(user);
        auth.enqueue(new Callback<Envelope<String>>() {
            @Override
            public void onResponse(Call<Envelope<String>> call, Response<Envelope<String>> response) {
                if (response.code() == 200) {
                    Envelope<String> okResponse =  response.body();
                    String token = okResponse.getData();
                    session.setSession(token);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("Error", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Envelope<String>> call, Throwable t) {
                String message = "Error has occurred";
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void handleRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
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
