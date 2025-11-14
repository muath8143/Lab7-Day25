package com.example.academicsystem.Controller;

import com.example.academicsystem.Api.ApiResponse;
import com.example.academicsystem.Model.Assignment;
import com.example.academicsystem.Service.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/assignment")
@RequiredArgsConstructor
public class assignmentController {
    private final AssignmentService assignmentService;

    @PostMapping("/create")
    public ResponseEntity<?> createAssignment(@RequestBody @Valid Assignment assignment , Errors error){
        if (error.hasErrors()){
            String message=error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        boolean flag=assignmentService.createAssignment(assignment);
        if (flag){
            return ResponseEntity.status(200).body(new ApiResponse("The assignment added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("There is no course that have this id: "+assignment.getCourseId()));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAll(){
        ArrayList<Assignment> assignments=assignmentService.getAll();
        if (assignments==null){
            return ResponseEntity.status(400).body(new ApiResponse("No assignments in system"));
        }
        return ResponseEntity.status(200).body(assignments);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id,@RequestBody @Valid Assignment assignment,Errors error){
        if (error.hasErrors()){
            String message=error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        int flag=assignmentService.update(id, assignment);
        switch (flag){
            case 400:
                return ResponseEntity.status(400).body(new ApiResponse("There is no course that have this id: "+assignment.getCourseId()));
            case 401:
                return ResponseEntity.status(400).body(new ApiResponse("The id: "+id+" is not exits"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The assignment updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        boolean flag=assignmentService.delete(id);
        if (flag){
            return ResponseEntity.status(200).body(new ApiResponse("The assignment has id:"+id+" deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("The id: "+id+" is not exits"));
    }

    @PutMapping("/dou/{id}/{dateTime}")
    public ResponseEntity<?> douDateAssignment(@PathVariable String id, @PathVariable LocalDateTime dateTime){
        int flag=assignmentService.duo(id,dateTime);
        switch (flag){
            case 400:
                return ResponseEntity.status(400).body(new ApiResponse("The dou date cannot in the past"));
            case 401:
                return ResponseEntity.status(400).body(new ApiResponse("The id: "+id+" in not exits"));
            case 402:
                return ResponseEntity.status(400).body(new ApiResponse("The course associated with this assignment is not activated therefore you cannot activate the assignment"));
            case 403:
                return ResponseEntity.status(400).body(new ApiResponse("There is no course associated with this assignment"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The deadline has been set: "+dateTime));
    }

    @PutMapping("/close-assignment/{id}")
    public ResponseEntity<?> closeAssignment(@PathVariable String id){
        int flag=assignmentService.closeAssignment(id);
        switch (flag){
            case 400:
                return ResponseEntity.status(400).body(new ApiResponse("The assignment is already closed"));
            case 401:
                return ResponseEntity.status(400).body(new ApiResponse("The id: "+id+" is not exits"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The assignment closed successfully"));
    }

    @GetMapping("/get-by-course-id/{courseId}")
    public ResponseEntity<?> getAssignmentsByCourseId(@PathVariable String courseId){
        ArrayList<Assignment> byCourseId=assignmentService.getByCourse(courseId);
        if (byCourseId==null){
            return ResponseEntity.status(400).body(new ApiResponse("No assignment for this course id:"+courseId));
        }
        return ResponseEntity.status(200).body(byCourseId);
    }

    @GetMapping("/get-before/{dateTime}")
    public ResponseEntity<?> getByBeforeDate(@PathVariable LocalDateTime dateTime){
        ArrayList<Assignment> byBeforeDate=assignmentService.getByDate(dateTime);
        if (byBeforeDate==null){
            return ResponseEntity.status(400).body(new ApiResponse("No assignment before this date: "+dateTime));
        }
        return ResponseEntity.status(200).body(byBeforeDate);
    }
}
