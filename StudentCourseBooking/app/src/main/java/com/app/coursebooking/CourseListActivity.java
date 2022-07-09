package com.app.coursebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.coursebooking.model.Database;
import com.app.coursebooking.model.Course;

public class CourseListActivity extends AppCompatActivity {
    RecyclerView recycler;
    CourseListAdapter adapter;

    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new CourseListAdapter();
        recycler.setAdapter(adapter);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseListActivity.this, AddCourseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

        @NonNull
        @Override
        public CourseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CourseListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_course, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CourseListAdapter.ViewHolder holder, int position) {
            Course Course = Database.getInstance().getCourses().get(position);
            holder.bind(Course);
        }

        @Override
        public int getItemCount() {
            return Database.getInstance().getCourses().size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View view;

            TextView tvCode, tvName, tvStatus;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.view = itemView;
                tvCode = view.findViewById(R.id.tvCode);
                tvName = view.findViewById(R.id.tvName);
                tvStatus = view.findViewById(R.id.tvStatus);
            }

            public void bind(Course course) {
                tvCode.setText("Course ID: " + course.getCourseCode());
                tvName.setText("Username: " + course.getCourseName());

                if(course.getInstructorUsername() == null){
                    tvStatus.setText("No Instructor");
                }else{
                    tvStatus.setText("Instructor: " + course.getInstructorUsername());
                }

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog.Builder(view.getContext())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Database.getInstance().removeCourse(course);
                                        Database.getInstance().save(CourseListActivity.this);
                                        notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .setMessage("Delete this Course?")
                                .show();
                        return false;
                    }
                });

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CourseListActivity.this, EditCourseActivity.class);
                        intent.putExtra("code",course.getCourseCode());
                        startActivity(intent);
                    }
                });
            }
        }
    }
}