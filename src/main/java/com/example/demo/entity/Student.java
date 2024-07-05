package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "student")
@Data
public class Student {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int rollNo;
   @Column(name="student_name")
   private String name ;
   @Column(name="student_percentage")
   private float percentage;
   @Column(name="student_branch")
   private String branch  ;
   @Column(name="is_passed")
   private Boolean isPassed  ;
}

