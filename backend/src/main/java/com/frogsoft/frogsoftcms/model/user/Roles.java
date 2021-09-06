
package com.frogsoft.frogsoftcms.model.user;

public enum Roles {
  ROLE_USER("ROLE_USER"),
  ROLE_ADMIN("ROLE_ADMIN");
  String role;

  Roles(String role) {
    this.role = role;
  }

  public String getRole() {
    return this.role;
  }
}