package com.example.classinformationmanagement.model;

public class StudentManage extends Student{
    private Long id;
    private Long studentId;
    private Long groupId;
    private Long weekId;
    private Boolean isRollCall;
    private String fullname;
    private String email;
    private String phone;
    private String role;
    private String name;


    public StudentManage(Long id, Long studentId, Long groupId, Long weekId, Boolean isRollCall) {
        this.id = id;
        this.studentId = studentId;
        this.groupId = groupId;
        this.weekId = weekId;
        this.isRollCall = isRollCall;
    }

    public StudentManage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getWeekId() {
        return weekId;
    }

    public void setWeekId(Long weekId) {
        this.weekId = weekId;
    }

    public Boolean getRollCall() {
        return isRollCall;
    }

    public void setRollCall(Boolean rollCall) {
        isRollCall = rollCall;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
