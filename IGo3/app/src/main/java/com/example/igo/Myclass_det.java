package com.example.igo;

public class Myclass_det
{
    private String ClassName;
    private String CourseName;
    private String Strength;

    public Myclass_det(String cln,String csn,String stre){
        this.ClassName=new String(cln);
        this.CourseName=new String(csn);
        this.Strength=new String(stre);
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        this.ClassName = className;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        this.CourseName = courseName;
    }

    public String getStrength() {
        return Strength;
    }

    public void setStrength(String strength) {
        this.Strength = strength;
    }
}
