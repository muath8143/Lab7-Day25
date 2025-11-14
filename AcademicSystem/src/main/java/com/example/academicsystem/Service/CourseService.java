package com.example.academicsystem.Service;

import com.example.academicsystem.Model.Course;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class CourseService {
    //Virtual database
    ArrayList<Course> courses=new ArrayList<>();
    //CRUD
    public ArrayList<Course> getAll(){
        if (courses.isEmpty()){
            return null;
        }
        return courses;
    }

    public void createCourse(Course course){
        courses.add(course);
    }


    public boolean updateCourse(String id ,Course course){
        int counter=-1;
        for (Course course1:courses){
            counter++;
            if (course1.getId().equals(id)){
                courses.set(counter,course);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCourse(String id){
        int counter=-1;
        for (Course course:courses){
            counter++;
            if (course.getId().equals(id)){
                courses.remove(counter);
                return true;
            }
        }
        return false;
    }

    public int LaunchNow(String id, LocalDate endDate){
        LocalDate startDate=LocalDate.now();
        for (Course course:courses){
            if (course.getId().equals(id)){
                if (endDate.isBefore(startDate)){
                    return 400;
                }
                if (course.isActive()){
                    return 401;
                }
                course.setActive(true);
                course.setStartDate(startDate);
                course.setEndDate(endDate);
                return 200;
            }
        }
        return 402;
    }

    public int scheduledLaunch (String id,LocalDate startDate,LocalDate endDate){
        for (Course course:courses){
            if (course.getId().equals(id)){
                if (course.isActive()){
                    return 400;
                }
                if (startDate.isBefore(LocalDate.now())){
                    return 401;
                }
                if (endDate.isBefore(startDate)){
                    return 402;
                }
                course.setStartDate(startDate);
                course.setEndDate(endDate);
                course.setActive(true);
                return 200;
            }
        }
        return 403;
    }

    public int editLaunchDate(String id,LocalDate endDate){
        for (Course course:courses){
            if (course.getId().equals(id)){
                if (!course.isActive()){
                    return 400;
                }
                if (endDate.isBefore(course.getStartDate())){
                    return 401;
                }
                course.setEndDate(endDate);
                return 200;
            }
        }
        return 402;
    }

    public int courseClose(String id){
        for (Course course:courses){
            if (course.getId().equals(id)){
                if (!course.isActive()){
                    return 400;
                }
                course.setEndDate(LocalDate.now());
                course.setActive(false);
                return 200;
            }
        }
        return 401;
    }

    public ArrayList<Course> getByActive (boolean active){
        ArrayList<Course> byActive=new ArrayList<>();
        for (Course course:courses){
            if (course.isActive()==active){
                byActive.add(course);
            }
        }
        if (byActive.isEmpty()){
            return null;
        }
        return byActive;
    }
}
