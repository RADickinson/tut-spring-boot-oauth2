package com.example;

import java.util.List;

public class Permission {
  private String domain;
  private List<String> permissions;
  
  public Permission(String domain, List<String> permissions) {
    this.domain = domain;
    this.permissions = permissions;
  }
  
  public String getDomain() {
    return domain;
  }
  
  public List<String> getPermissions() {
    return permissions;
  }

  @Override
  public String toString() {
    return "Permission(domain="+domain+",permissions="+permissions+")";
  }

}

