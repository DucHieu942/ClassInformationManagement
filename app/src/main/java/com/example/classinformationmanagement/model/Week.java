package com.example.classinformationmanagement.model;

import java.io.Serializable;
import java.util.List;

public class Week implements Serializable {
        private  Long id;
        private  String name;
        private  String date;
        private  Integer quantity;
        private List<Long> absenceStudentIdList;

        public Long getId() {
                return id;
        }

        public Week() {
        }

        public Week(Long id, String name, String date) {
                this.id = id;
                this.name = name;
                this.date = date;
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

        public String getDate() {
                return date;
        }

        public void setDate(String date) {
                this.date = date;
        }

        public Integer getQuantity() {
                return quantity;
        }

        public void setQuantity(Integer quantity) {
                this.quantity = quantity;
        }

        public List<Long> getAbsenceStudentIdList() {
                return absenceStudentIdList;
        }

        public void setAbsenceStudentIdList(List<Long> absenceStudentIdList) {
                this.absenceStudentIdList = absenceStudentIdList;
        }
}
