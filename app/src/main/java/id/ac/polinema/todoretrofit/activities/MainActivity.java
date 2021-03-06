package id.ac.polinema.todoretrofit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import id.ac.polinema.todoretrofit.Application;
import id.ac.polinema.todoretrofit.Constant;
import id.ac.polinema.todoretrofit.R;
import id.ac.polinema.todoretrofit.Session;
import id.ac.polinema.todoretrofit.adapters.TodoAdapter;
import id.ac.polinema.todoretrofit.generator.ServiceGenerator;
import id.ac.polinema.todoretrofit.models.Envelope;
import id.ac.polinema.todoretrofit.models.Todo;
import id.ac.polinema.todoretrofit.services.TodoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTodoClickedListener, TodoAdapter.OnTodoClickedDeleteListener {

    private RecyclerView todosRecyclerView;
    private Session session;
    private TodoService service;
    private TodoAdapter adapter;
    private EditText search;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SaveTodoActivity.class);
                intent.putExtra(Constant.KEY_REQUEST_CODE, Constant.ADD_TODO);
                startActivityForResult(intent, Constant.ADD_TODO);
			}
		});
        session = Application.provideSession();
        if (!session.isLogin()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        todosRecyclerView = findViewById(R.id.rv_todos);
        search = findViewById(R.id.edt_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count == 0){
                    loadTodos();
                }else{
                    cariTodos(search.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        todosRecyclerView.setLayoutManager(layoutManager);
        adapter = new TodoAdapter(this, this, this);
        todosRecyclerView.setAdapter(adapter);
        service = ServiceGenerator.createService(TodoService.class);
        loadTodos();
    }

    private void loadTodos() {
        Call<Envelope<List<Todo>>> todos = service.getTodos(null, 1, 10);
        todos.enqueue(new Callback<Envelope<List<Todo>>>() {
            @Override
            public void onResponse(Call<Envelope<List<Todo>>> call, Response<Envelope<List<Todo>>> response) {
                if (response.code() == 200) {
                    Envelope<List<Todo>> okResponse = response.body();
                    List<Todo> items = okResponse.getData();
                    adapter.setItems(items);
                }
            }

            @Override
            public void onFailure(Call<Envelope<List<Todo>>> call, Throwable t) {

            }
        });
    }

    private void handleDelete(Todo todo) {
        int id = todo.getId();
        Call<Envelope<Todo>> deleteTodo = service.deleteTodo(id);
        deleteTodo.enqueue(new Callback<Envelope<Todo>>() {
            @Override
            public void onResponse(Call<Envelope<Todo>> call, Response<Envelope<Todo>> response) {
                if (response.code() == 200) {
                    loadTodos();
                } else {
                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Envelope<Todo>> call, Throwable t) {

            }
        });
    }

    private void cariTodos(String cari) {
        Call<Envelope<List<Todo>>> todos = service.getTodos(cari, 1, 10);
        todos.enqueue(new Callback<Envelope<List<Todo>>>() {
            @Override
            public void onResponse(Call<Envelope<List<Todo>>> call, Response<Envelope<List<Todo>>> response) {
                if (response.code() == 200) {
                    Envelope<List<Todo>> okResponse = response.body();
                    List<Todo> items = okResponse.getData();
                    adapter.setItems(items);
                }
            }

            @Override
            public void onFailure(Call<Envelope<List<Todo>>> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_logout) {
            session.removeSession();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Todo todo) {
        Intent intent = new Intent(this, SaveTodoActivity.class);
        intent.putExtra(Constant.KEY_TODO, todo);
        intent.putExtra(Constant.KEY_REQUEST_CODE, Constant.UPDATE_TODO);
        startActivityForResult(intent, Constant.UPDATE_TODO);
    }

    @Override
    public void onClickDelete(Todo todo) {
        handleDelete(todo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadTodos();
        }
    }
}
