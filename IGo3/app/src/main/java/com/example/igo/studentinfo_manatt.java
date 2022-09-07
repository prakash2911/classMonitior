package com.example.igo;

import android.widget.CheckBox;

public class studentinfo_manatt {
    private String Name;
    private String RegNo;
    private boolean cb;
    //private boolean ispres;

    public studentinfo_manatt(String Nam,String Reg,boolean cbx){
        this.Name=Nam;
        this.RegNo=Reg;
        this.cb=cbx;
        //this.ispres=true;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }

    public boolean getCb() {
        return cb;
    }

    public void setCb(boolean cb) {
        this.cb = cb;
    }

    /*public boolean isIspres() {
        return ispres;
    }

    public void setIspres(boolean ispres) {
        this.ispres = ispres;
    }*/
}
