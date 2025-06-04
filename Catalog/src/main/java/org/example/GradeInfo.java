package org.example;

import java.util.Date;

public class GradeInfo {
    private final double grade;
    private final Date date;
    private final String subject;
    private final String professor;

    public GradeInfo(double grade, Date date, String subject, String professor) {
        this.grade = grade;
        this.date = date;
        this.subject = subject;
        this.professor = professor;
    }

    public double getGrade() {
        return grade;
    }

    public Date getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getProfessor() {
        return professor;
    }
} 