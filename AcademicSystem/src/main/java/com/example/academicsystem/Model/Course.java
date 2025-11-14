package com.example.academicsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Course {

    @NotEmpty(message = "The id is required")
    @Size(min = 2,max = 6,message = "The id must has at least 2 digit and no more 6")
    private String id;

    @NotEmpty(message = "The title is required")
    @Size(min = 5,max = 15,message = "The title must has at least 5 characters and no more 15")
    private String title;

    @NotEmpty(message = "The description is required")
    @Size(min = 50,max = 240,message = "The description must be has at least 50 characters and no more than 240")
    private String description;

    @NotNull(message = "The capacity is required")
    @Min(value = 15,message = "The capacity must be has at least 15 student")
    @Max(value = 30,message = "The capacity must be no more than 30 student")
    private Integer capacity;


    @AssertFalse(message = "The state of active must be false")
    private boolean isActive;

    @Null(message = "you can't enter start date now please leave it empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;


    @Null(message = "You can't enter end date now please leave it empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
