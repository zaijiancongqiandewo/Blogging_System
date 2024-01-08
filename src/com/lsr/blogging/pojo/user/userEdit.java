package com.lsr.blogging.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class userEdit {
    private String oldPassword;
    private String newPassword;
    private String userName;
}
