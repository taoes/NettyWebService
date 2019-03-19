package com.seven.web.example.to;

public class BeanTo {
  private String name;

  private int age;

  private Boolean man;

  public BeanTo(String name, int age, Boolean man) {
    this.name = name;
    this.age = age;
    this.man = man;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Boolean getMan() {
    return man;
  }

  public void setMan(Boolean man) {
    this.man = man;
  }
}
