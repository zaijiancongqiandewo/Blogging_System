package com.lsr.blogging.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BloggingAdmin {
    private String adminName;
    private String adminPass;
}
