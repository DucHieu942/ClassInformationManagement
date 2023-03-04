package com.example.classinformationmanagement.model;

public class Student {
    private String fullname;
    private Long groupId;
    private Long id;
    private String name;
    private long absence;
    private String email;
    private String phone;
    private String role;


    public Student() {
    }

    public Student(String fullname, Long groupId,Long id, String name) {
        this.id = id;
        this.fullname = fullname;
        this.groupId = groupId;
        this.name = name;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public long getAbsence() {
        return absence;
    }

    public void setAbsence(long absence) {
        this.absence = absence;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }


    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}
}
