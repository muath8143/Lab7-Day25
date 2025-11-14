package com.example.academicsystem.Service;

import com.example.academicsystem.Model.Assignment;
import com.example.academicsystem.Model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final CourseService courseService;
    ArrayList<Assignment> assignments=new ArrayList<>();
    public ArrayList<Assignment> getAll(){
        if (assignments.isEmpty()){
            return null;
        }
        return assignments;
    }


    public boolean createAssignment (Assignment assignment){
        for (Course course:courseService.getAll()){
            if (assignment.getCourseId().equals(course.getId())){
                assignments.add(assignment);
                return true;
            }
        }
        return false;
    }

    public int update(String id ,Assignment assignment){
        int counter=-1;
        for (Assignment assignment1:assignments){
            counter++;
            if (assignment1.getId().equals(id)){
                for (Course course:courseService.getAll()){
                    if (assignment.getCourseId().equals(course.getId())){
                        assignments.set(counter,assignment);
                        return 200;
                    }
                }
               return 400;

            }
        }
        return 401;
    }

    public boolean delete(String id ){
        int counter =-1;
        for (Assignment assignment:assignments){
            counter++;
            if (assignment.getId().equals(id)){
                assignments.remove(counter);
                return true;
            }
        }
        return false;
    }

    public int duo (String id, LocalDateTime duoDate){
        for (Assignment assignment:assignments){
            if (assignment.getId().equals(id)){
                for (Course course:courseService.getAll()){
                    if (assignment.getCourseId().equals(course.getId())){
                        if (!course.isActive()){
                            return 402;
                        }
                        if (duoDate.isBefore(LocalDateTime.now())){
                            return 400;
                        }
                        assignment.setActive(true);
                        assignment.setDuoDate(duoDate);
                        return 200;
                    }
                }
                return 403;
            }
        }
        return 401;
    }

    public int closeAssignment (String id){
        for (Assignment assignment:assignments){
            if (assignment.getId().equals(id)){
                if (!assignment.isActive()){
                    return 400;
                }
                assignment.setDuoDate(LocalDateTime.now());
                assignment.setActive(false);
                return 200;
            }
        }
        return 401;
    }

    public ArrayList<Assignment> getByCourse(String idCourse){
        ArrayList<Assignment> byCourse=new ArrayList<>();
        for (Assignment assignment:assignments){
            if (assignment.getCourseId().equals(idCourse)){
                byCourse.add(assignment);
            }

        }
        if (byCourse.isEmpty()){
            return null;
        }
        return byCourse;
    }
    public ArrayList<Assignment> getByDate(LocalDateTime dateTime){
        ArrayList<Assignment> byDate=new ArrayList<>();
        for (Assignment assignment:assignments){
            if (assignment.getDuoDate().isBefore(dateTime)){
                byDate.add(assignment);
            }
        }
        if (byDate.isEmpty()){
            return null;
        }
        return byDate;
    }

}
