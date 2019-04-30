package id.ac.polinema.todoretrofit.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.ac.polinema.todoretrofit.R;
import id.ac.polinema.todoretrofit.models.Todo;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private Context context;
    private List<Todo> items;
    private OnTodoClickedListener listener;

    public TodoAdapter(Context context, OnTodoClickedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setItems(List<Todo> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    public void setListener(OnTodoClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_todo, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Todo todo = items.get(i);
        viewHolder.bind(todo, listener);
    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todoText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.text_todo);
        }

        public void bind(final Todo todo, final OnTodoClickedListener listener) {
            todoText.setText(todo.getTodo());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(todo);
                }
            });
        }
    }

    public interface OnTodoClickedListener {
        void onClick(Todo todo);
    }
}
