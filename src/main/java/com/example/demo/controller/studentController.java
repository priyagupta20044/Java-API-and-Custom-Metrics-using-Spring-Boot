package com.example.demo.controller;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class studentController {

   @Autowired
   StudentRepository repo;
   private Counter myStudentCounter;
   private MeterRegistry meterRegistry;


   @ExceptionHandler(Exception.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public String handleTheException(Exception ex) {
       return "Invalid Id :(";
   }


   public studentController(MeterRegistry meterRegistry){
       this.meterRegistry = meterRegistry;
       this.myStudentCounter = Counter.builder("custom.metric.one")
               .tags("status","created")
               .description("total number of students created - function one")
               .register(meterRegistry);
   }


   public void giveStudent() {
       long val = repo.count();
       Tags tags = Tags.of("status", "pending");
       meterRegistry.gauge("custom.metric.two", tags, val);
       System.out.println("successful" + val);

   }


   // Fetch the data about all the students : Get all the students // localhost:8080/students
   @GetMapping("/students")
   public List<Student> getAllStudents(){
       List<Student> students = repo.findAll();
       return students;
   }


   // localhost::8080/students/1
   @GetMapping("/students/{myID}")
   public Student getStudentByID(@PathVariable int myID){
       Student myStudent = repo.findById(myID).get();
       return myStudent;
   }


   @PostMapping("/students/add")
   @ResponseStatus(code= HttpStatus.CREATED)
   public boolean addStudent(@RequestBody Student myNewStudent){
       repo.save(myNewStudent);
       this.myStudentCounter.increment();
       return true;
   }


   @PutMapping("/students/update/{MyID}")
   public Student updateStudent(@PathVariable int MyID){
       Student tempStudent = repo.findById(MyID).get();
       tempStudent.setName("RISHI");
       tempStudent.setPercentage(79);
       repo.save(tempStudent);
       return tempStudent;
   }


   @DeleteMapping("/students/delete/{MyID}")
   public void deleteStudent(@PathVariable int MyID){
       repo.deleteById(MyID);
   }
}

