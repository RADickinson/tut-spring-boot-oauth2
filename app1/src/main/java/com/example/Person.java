package com.example;

public class Person {

  private String identifier;
  private String name;
  private int age;
  private int id;
  
  
  public Person(String identifier, String name, int age, int id) {
    super();
    this.identifier = identifier;
    this.name = name;
    this.age = age;
    this.id = id;
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
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  
  @Override
  public String toString() {
    return "Person(id="+id+",identifier="+identifier+",name="+name+",age="+age+")";
  }
}
