package com.lsr.blogging.pojo;

public class MYtest {
    private String username;
    //   private String adminName;
    private String pass;
    private String checkPass;
    //   private String identify;
    private int age;

    public MYtest() {
    }

    public MYtest(String username, String pass, String checkPass, int age) {
        this.username = username;
        this.pass = pass;
        this.checkPass = checkPass;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCheckPass() {
        return checkPass;
    }

    public void setCheckPass(String checkPass) {
        this.checkPass = checkPass;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "test{" +
                "username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", checkPass='" + checkPass + '\'' +
                ", age=" + age +
                '}';
    }
}
