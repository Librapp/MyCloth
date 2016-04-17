package com.luke.mycloth.bean;

import java.io.Serializable;

/**
 * Created by Luke on 2016/2/21 0021.
 */
public class Cloth implements Serializable {
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
    public int state = 0;
    public int category = 0;
    public int style = 0;
    public int material = 0;
    public int spring = 0;
    public int summer = 0;
    public int autumn = 0;
    public int winter = 0;
    public String color;
    public String price;
    public String produce_time;
    public String manifest_time;
    public String create_time;

}
