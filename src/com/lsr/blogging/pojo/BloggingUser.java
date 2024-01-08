package com.lsr.blogging.pojo;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BloggingUser {
    private String userName;
    private String adminName;
    private String password;
    private String identify;
    private int age;
    private String headImageUrl;
}
