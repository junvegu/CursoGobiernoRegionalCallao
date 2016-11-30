package com.bixspace.ciclodevida.data;

import java.io.Serializable;

/**
 * Created by junior on 30/11/16.
 */

public class UserModel implements Serializable {

    private String id;
    private String first_name;
    private String last_name;
    private String gender;
    private String email;
    private String birth_date;


    public UserModel(String id, String first_name, String last_name, String gender, String email, String birth_date) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.email = email;
        this.birth_date = birth_date;
    }


    public UserModel() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }
}
