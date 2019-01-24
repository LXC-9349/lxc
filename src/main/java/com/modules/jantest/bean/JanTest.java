package com.modules.jantest.bean;

import java.io.Serializable;
import java.util.Date;
/**
* Author DoubleLi
* Date  2019-01-24
*/
public class JanTest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Date craeteTime;
    private Integer isUse;


    public JanTest(){
    }

    public void setId (Integer id) {this.id = id;} 
    public Integer getId(){ return id;} 
    public void setName (String name) {this.name = name;} 
    public String getName(){ return name;} 
    public void setCraeteTime (Date craeteTime) {this.craeteTime = craeteTime;} 
    public Date getCraeteTime(){ return craeteTime;} 
    public void setIsUse (Integer isUse) {this.isUse = isUse;} 
    public Integer isIsUse(){ return isUse;} 

}