package com.example.classinformationmanagement.model;

import java.io.Serializable;
import java.util.List;

import kotlin.jvm.Transient;

public class Table implements Serializable {
    private Long id;
    private String name;

    @Transient
    private List<String> listname;
    @Transient
    private List<Long> listid;
    @Transient
    private List<String> listrole;
    @Transient
    private  List<Boolean> listRollCall;



    public Table(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Table() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getListname() {
        return listname;
    }

    public void setListname(List<String> listname) {
        this.listname = listname;
    }

    public List<Long> getListid() {
        return listid;
    }

    public void setListid(List<Long> listid) {
        this.listid = listid;
    }

    public List<String> getListrole() {
        return listrole;
    }

    public void setListrole(List<String> listrole) {
        this.listrole = listrole;
    }

    public List<Boolean> getListRollCall() {
        return listRollCall;
    }

    public void setListRollCall(List<Boolean> listRollCall) {
        this.listRollCall = listRollCall;
    }
}
