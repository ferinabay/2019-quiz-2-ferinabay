package id.ac.polinema.todoretrofit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);

        service = ServiceGenerator.createService(AuthService.class);
        session = Application.provideSession();
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
}
