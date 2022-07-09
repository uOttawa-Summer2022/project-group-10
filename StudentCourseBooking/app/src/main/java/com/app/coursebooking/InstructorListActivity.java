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
import com.app.coursebooking.model.Instructor;

public class InstructorListActivity extends AppCompatActivity {
    RecyclerView recycler;
    InstructorListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_list);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new InstructorListAdapter();
        recycler.setAdapter(adapter);
    }

    class InstructorListAdapter extends RecyclerView.Adapter<InstructorListAdapter.ViewHolder> {

        @NonNull
        @Override
        public InstructorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new InstructorListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_instructor, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull InstructorListAdapter.ViewHolder holder, int position) {
            Instructor Instructor = Database.getInstance().getInstructors().get(position);
            holder.bind(Instructor);
        }

        @Override
        public int getItemCount() {
            return Database.getInstance().getInstructors().size();
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

            public void bind(Instructor instructor) {
                tvId.setText("Instructor ID: " + instructor.getId());
                tvUsername.setText("Username: " + instructor.getUsername());

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog.Builder(view.getContext())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Database.getInstance().removeInstructor(instructor);
                                        Database.getInstance().save(InstructorListActivity.this);
                                        notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .setMessage("Delete this Instructor?")
                                .show();
                        return false;
                    }
                });
            }
        }
    }
}