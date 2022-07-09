package com.app.coursebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.coursebooking.model.Database;
import com.app.coursebooking.model.Student;

public class StudentListActivity extends AppCompatActivity {
    RecyclerView recycler;
    StudentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new StudentListAdapter();
        recycler.setAdapter(adapter);
    }

    class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_student, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Student student = Database.getInstance().getStudents().get(position);
            holder.bind(student);
        }

        @Override
        public int getItemCount() {
            return Database.getInstance().getStudents().size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View view;

            TextView tvId, tvUsername;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.view = itemView;
                tvId = view.findViewById(R.id.tvId);
                tvUsername = view.findViewById(R.id.tvUsername);


            }

            public void bind(Student student) {
                tvId.setText("Student ID: " + student.getId());
                tvUsername.setText("Username: " + student.getUsername());

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog.Builder(view.getContext())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Database.getInstance().removeStudent(student);
                                        Database.getInstance().save(StudentListActivity.this);
                                        notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .setMessage("Delete this student?")
                                .show();
                        return false;
                    }
                });
            }
        }
    }
}