package com.example.lab.android.nuc.chat.Base.Stu_Tea;

import java.util.ArrayList;
import java.util.List;

public class GetTeacher {
    List<SetTeacher> setTeachers = new ArrayList<>();

    public void add(SetTeacher setTeacher){
        setTeachers.add(setTeacher);
    }
    public List<SetTeacher> getSetTeachers() {
        return setTeachers;
    }

    public void setSetTeachers(List<SetTeacher> setTeachers) {
        this.setTeachers = setTeachers;
    }
}
