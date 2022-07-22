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
import com.app.coursebooking.model.Student;
import com.app.coursebooking.model.Validator;

import java.util.ArrayList;
import java.util.List;

public class StudentCourseListActivity extends AppCompatActivity {
    RecyclerView recycler;
    StudentCourseListActivity.CourseListAdapter adapter;

    EditText etKeyword;
    Button btnSearch;

    String studentUsername;

    private List<Course> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_list);

        courseList.addAll(Database.getInstance().getCourses());

        Student student = (Student) getIntent().getSerializableExtra("user");
        studentUsername = student.getUsername();

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new StudentCourseListActivity.CourseListAdapter();
        recycler.setAdapter(adapter);

        etKeyword = findViewById(R.id.etKeyword);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseList = Database.getInstance().searchCourseWithDays(etKeyword.getText().toString().trim());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    class CourseListAdapter extends RecyclerView.Adapter<StudentCourseListActivity.CourseListAdapter.ViewHolder> {

        @NonNull
        @Override
        public StudentCourseListActivity.CourseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new StudentCourseListActivity.CourseListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_course_student, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull StudentCourseListActivity.CourseListAdapter.ViewHolder holder, int position) {
            Course Course = courseList.get(position);
            holder.bind(Course);
        }

        @Override
        public int getItemCount() {
            return courseList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View view;

            TextView tvStatus, tvCodeName, tvInstructor, tvTime, tvCapacity, tvDescription;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.view = itemView;
                tvCodeName = view.findViewById(R.id.tvCodeName);
                tvInstructor = view.findViewById(R.id.tvInstructor);
                tvStatus = view.findViewById(R.id.tvStatus);
                tvTime = view.findViewById(R.id.tvTime);
                tvCapacity = view.findViewById(R.id.tvCapacity);
                tvDescription = view.findViewById(R.id.tvDescription);
            }

            public void bind(Course course) {
                tvCodeName.setText("");
                tvInstructor.setText("");
                tvStatus.setText("");
                tvTime.setText("");
                tvCapacity.setText("");
                tvDescription.setText("");

                tvCodeName.setVisibility(View.VISIBLE);
                tvInstructor.setVisibility(View.VISIBLE);
                tvStatus.setVisibility(View.VISIBLE);
                tvTime.setVisibility(View.VISIBLE);
                tvCapacity.setVisibility(View.VISIBLE);
                tvDescription.setVisibility(View.VISIBLE);

                if(course.getInstructorUsername() == null){
                    tvStatus.setText("No Instructor");
                    tvCodeName.setText("Course ID: " + course.getCourseCode() + "  " + "Course Name: " + course.getCourseName());
                    tvInstructor.setVisibility(View.GONE);
                    tvTime.setVisibility(View.GONE);
                    tvCapacity.setVisibility(View.GONE);
                    tvDescription.setVisibility(View.GONE);

                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Toast.makeText(StudentCourseListActivity.this, "This course has no instructor", Toast.LENGTH_LONG).show();
                            return false;
                        }
                    });
                }else if(course.getDays().isEmpty()) {
                    tvStatus.setText("Days and hours not set");
                    tvCodeName.setText("Course ID: " + course.getCourseCode() + "  " + "Course Name: " + course.getCourseName());
                    tvInstructor.setText("Instructor: " + course.getInstructorUsername());

                    tvTime.setVisibility(View.GONE);
                    tvCapacity.setVisibility(View.GONE);
                    tvDescription.setVisibility(View.GONE);

                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Toast.makeText(StudentCourseListActivity.this, "This course has no days and hours", Toast.LENGTH_LONG).show();
                            return false;
                        }
                    });
                }else{
                    if(course.getStudents().contains(studentUsername)){
                        tvStatus.setTextColor(Color.GREEN);
                        tvStatus.setText("Enrolled: Yes");
                    }else{
                        tvStatus.setTextColor(Color.BLACK);
                        tvStatus.setText("Enrolled: No");
                    }

                    tvCodeName.setText("Course ID: " + course.getCourseCode() + "  " + "Course Name: " + course.getCourseName());
                    tvInstructor.setText("Instructor: " + course.getInstructorUsername());
                    tvTime.setText("Time: " + course.getDays() + "  " + course.getHours());
                    tvCapacity.setText("Capacity: " + course.getCapacity());
                    tvDescription.setText("Description: " + course.getDescription());

                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            if (!course.getStudents().contains(studentUsername)) {
                                new AlertDialog.Builder(view.getContext())
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                boolean conflict = false;
                                                Course conflictCourse = null;

                                                for(Course c:Database.getInstance().getCourses()){
                                                    if(c.getStudents().contains(studentUsername) && c.getDays().equals(course.getDays())
                                                            && Validator.isConflict(c.getHours(), course.getHours())){
                                                        conflict = true;
                                                        conflictCourse = c;
                                                    }
                                                }

                                                if(conflict){
                                                    Toast.makeText(v.getContext(), "This course conflicts with " + conflictCourse.getCourseCode(), Toast.LENGTH_LONG).show();
                                                }else if( Database.getInstance().getCourse(course.getCourseCode()).getCapacity() <= 0) {
                                                    Toast.makeText(v.getContext(), "This course is full", Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                    Database.getInstance().getCourse(course.getCourseCode()).getStudents().add(studentUsername);
                                                    Database.getInstance().getCourse(course.getCourseCode()).reduceCapacity();
                                                    Database.getInstance().save(StudentCourseListActivity.this);
                                                    notifyDataSetChanged();
                                                }
                                            }
                                        })
                                        .setNegativeButton("Cancel", null)
                                        .setMessage("Enroll this course?")
                                        .show();
                            } else {
                                if (course.getStudents().contains(studentUsername)) {
                                    new AlertDialog.Builder(view.getContext())
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Database.getInstance().getCourse(course.getCourseCode()).getStudents().remove(studentUsername);
                                                    Database.getInstance().getCourse(course.getCourseCode()).addCapacity();
                                                    Database.getInstance().save(StudentCourseListActivity.this);
                                                    notifyDataSetChanged();
                                                }
                                            })
                                            .setNegativeButton("Cancel", null)
                                            .setMessage("Un-enroll this course?")
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
}
