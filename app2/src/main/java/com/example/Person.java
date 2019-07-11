package com.example;

public class Person {

  private String identifier;
  private String name;
  private int age;
  
  
  public Person(String identifier, String name, int age) {
    super();
    this.identifier = identifier;
    this.name = name;
    this.age = age;
  }
  
  public String getIdentifier() {
    return identifier;
  }
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
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
  
  @Override
  public String toString() {
    return "Person(identifier="+identifier+",name="+name+",age="+age+")";
  }
}
