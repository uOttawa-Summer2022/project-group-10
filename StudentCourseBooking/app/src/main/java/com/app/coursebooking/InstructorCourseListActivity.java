package com.app.coursebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.coursebooking.model.Course;
import com.app.coursebooking.model.Database;
import com.app.coursebooking.model.Instructor;

import java.util.ArrayList;
import java.util.List;

public class InstructorCourseListActivity extends AppCompatActivity {

    RecyclerView recycler;
    CourseListAdapter adapter;

    EditText etKeyword;
    Button btnSearch;

    String instructorUsername;

    private List<Course> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_course_list);

        courseList.addAll(Database.getInstance().getCourses());

        Instructor instructor = (Instructor) getIntent().getSerializableExtra("user");
        instructorUsername = instructor.getUsername();

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new CourseListAdapter();
        recycler.setAdapter(adapter);

        etKeyword = findViewById(R.id.etKeyword);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseList = Database.getInstance().searchCourse(etKeyword.getText().toString().trim());
                adapter.notifyDataSetChanged();
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
            Course Course = courseList.get(position);
            holder.bind(Course);
        }

        @Override
        public int getItemCount() {
            return courseList.size();
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

                if (course.getInstructorUsername() == null) {
                    tvStatus.setText("No Instructor");
                } else {
                    tvStatus.setText("Instructor: " + course.getInstructorUsername());
                }

                if (instructorUsername.equals(course.getInstructorUsername())) {
                    tvStatus.setTextColor(Color.GREEN);
                } else {
                    tvStatus.setTextColor(Color.BLACK);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (instructorUsername.equals(course.getInstructorUsername())) {
                            Intent intent = new Intent(view.getContext(), InstructorEditCourseActivity.class);
                            intent.putExtra("code", course.getCourseCode());
                            startActivity(intent);
                        }
                    }
                });

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        if (course.getInstructorUsername() == null) {
                            new AlertDialog.Builder(view.getContext())
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Database.getInstance().getCourse(course.getCourseCode()).setInstructorUsername(instructorUsername);
                                            Database.getInstance().save(InstructorCourseListActivity.this);
                                            notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .setMessage("Assign this course?")
                                    .show();
                        } else {
                            if (instructorUsername.equals(course.getInstructorUsername())) {
                                new AlertDialog.Builder(view.getContext())
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Database.getInstance().getCourse(course.getCourseCode()).setInstructorUsername(null);
                                                Database.getInstance().save(InstructorCourseListActivity.this);
                                                notifyDataSetChanged();
                                            }
                                        })
                                        .setNegativeButton("Cancel", null)
                                        .setMessage("Un-assign this course?")
                                        .show();
                            } else {
                                Toast.makeText(v.getContext(), "This course already has an instructor", Toast.LENGTH_LONG).show();
                            }
                        }
                        return false;
                    }
                });
            }
        }
    }
}
