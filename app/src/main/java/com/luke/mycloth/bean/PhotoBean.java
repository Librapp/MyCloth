package com.luke.mycloth.bean;

import java.io.Serializable;

/**
 * Created by Luke on 2016/2/21 0021.
 */
public class PhotoBean implements Serializable{
    public String name;
    public String id;
    /**
     * 图片地址
     */
    public String url;
    /**
     * 本地地址
     */
    public String filepath;
    public String description;
    public int category;
    public int spring = 0;
    public int summer = 0;
    public int autumn = 0;
    public int winter = 0;
}
