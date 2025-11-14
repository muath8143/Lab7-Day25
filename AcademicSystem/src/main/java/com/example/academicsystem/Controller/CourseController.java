package com.example.academicsystem.Controller;

import com.example.academicsystem.Api.ApiResponse;
import com.example.academicsystem.Model.Course;
import com.example.academicsystem.Service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/get")
    public ResponseEntity<?> gatAll(){
        ArrayList<Course> allCourse=courseService.getAll();
        if (allCourse==null){
            return ResponseEntity.status(400).body(new ApiResponse("No courses in system"));
        }
        return ResponseEntity.status(200).body(allCourse);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestBody @Valid Course course, Errors error){
        if (error.hasErrors()){
            String message=error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        courseService.createCourse(course);
        return ResponseEntity.status(200).body(new ApiResponse("The course added successfully"));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateCourse (@PathVariable String id,@RequestBody @Valid Course course,Errors error){
        if (error.hasErrors()){
            String message=error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        boolean flagUpdate=courseService.updateCourse(id,course);

        if (flagUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("The course has id: "+id+" updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The id: "+id+" is not exits"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id){
        boolean flagDelete = courseService.deleteCourse(id);
        if (flagDelete){
            return ResponseEntity.status(200).body(new ApiResponse("The course has id: "+id+" deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The id: "+id+" is not exits"));
    }

    @PutMapping("/launch-now/{id}/{endDate}")
    public ResponseEntity<?> launchCourseNow(@PathVariable String id, @PathVariable LocalDate endDate){
        int flagLaunch=courseService.LaunchNow(id, endDate);
        switch (flagLaunch){
            case 400:
                return ResponseEntity.status(400).body(new ApiResponse("The end date cannot be before the start date"));
            case 401:
                return ResponseEntity.status(400).body(new ApiResponse("The course is already launched"));
            case 402:
                return ResponseEntity.status(400).body(new ApiResponse("The id: "+id+" is not exits"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The course was successfully launched and its start time: "+LocalDate.now()+" and end time: "+endDate));
    }

    @PutMapping("/scheduled/{id}/{startDate}/{endDate}")
    public ResponseEntity<?> scheduledLaunch (@PathVariable String id,@PathVariable LocalDate startDate,@PathVariable LocalDate endDate){
        int flag=courseService.scheduledLaunch(id, startDate, endDate);
        switch (flag){
            case 400:
                return ResponseEntity.status(400).body(new ApiResponse("The course is already launched"));
            case 401:
                return ResponseEntity.status(400).body(new ApiResponse("The start date must be in present or future"));
            case 402:
                return ResponseEntity.status(400).body(new ApiResponse("The end date cannot be before the start date"));
            case 403:
                return ResponseEntity.status(400).body(new ApiResponse("The id: "+id+" is not exits"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The course was successfully launched and its start time: "+startDate+" and end time: "+endDate));
    }

    @PutMapping("/edit-end-date/{id}/{endDate}")
    public ResponseEntity<?> editLaunchEndDate(@PathVariable String id,@PathVariable LocalDate endDate){
        int flag=courseService.editLaunchDate(id, endDate);
        switch (flag){
            case 400:
                return ResponseEntity.status(400).body(new ApiResponse("The course is not active you cannot edit end date"));
            case 401:
                return ResponseEntity.status(400).body(new ApiResponse("The end date cannot be before the start date"));
            case 402:
                return ResponseEntity.status(400).body(new ApiResponse("The id: "+id+" is not exits"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The end date has been successfully modified"));
    }

    @PutMapping("/close-course/{id}")
    public ResponseEntity<?> courseClose (@PathVariable String id){
        int flag=courseService.courseClose(id);
        switch (flag){
            case 400:
                return ResponseEntity.status(400).body(new ApiResponse("The course is not active you cannot edit end date"));
            case 401:
                return ResponseEntity.status(400).body(new ApiResponse("The id: "+id+" is not exits"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The course has been successfully closed"));
    }

    @GetMapping("get-by-active/{active}")
    //extra end point:
    public ResponseEntity<?> getByActive(@PathVariable boolean active){
        ArrayList<Course> byActive=courseService.getByActive(active);
        if (byActive==null){
            return ResponseEntity.status(400).body(new ApiResponse("No courses in this state"));
        }
        return ResponseEntity.status(200).body(byActive);
    }
}
