package com.lsr.blogging.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article {
    private int articleID;
    private String articleName;
    private String userName;
    private String categoryName;
    private String articleContent;
    private String createTime;
    private String updateTime;
    //之后可以将这里设置成为原子的，加以或减一
    private  int articleViews;
    private String pageImageUrl;
}
