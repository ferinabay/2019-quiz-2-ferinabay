package id.ac.polinema.todoretrofit.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.ac.polinema.todoretrofit.R;
import id.ac.polinema.todoretrofit.Settings;
import id.ac.polinema.todoretrofit.models.Todo;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private Context context;
    private List<Todo> items;
    private OnTodoClickedListener listener;
    private OnTodoClickedDeleteListener listener1;
    Settings settings;

    public TodoAdapter(Context context, OnTodoClickedListener listener, OnTodoClickedDeleteListener listener1) {
        this.context = context;
        this.listener = listener;
        this.listener1 = listener1;
        this.settings = new Settings(context);
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
        viewHolder.bind(todo, listener, listener1);

    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView todoText;

        public ViewHolder( View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.text_todo);

            float textSize = settings.getTextSize();
            todoText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }

        public void bind(final Todo todo, final OnTodoClickedListener listener, final OnTodoClickedDeleteListener listener1) {
            todoText.setText(todo.getTodo());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    listener.onClick(todo);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Option");
                    builder.setMessage("Apa yang ingin anda lakukan?");

                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.onClick(todo);
                        }
                    });

                    builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener1.onClickDelete(todo);
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    builder.create().show();
                }
            });
        }
    }

    public interface OnTodoClickedListener {
        void onClick(Todo todo);
    }

    public interface OnTodoClickedDeleteListener {
        void onClickDelete(Todo todo);
    }
}
