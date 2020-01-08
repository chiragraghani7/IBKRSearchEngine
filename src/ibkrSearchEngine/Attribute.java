import java.util.*;

class Attribute {
  private String adhar_id;
  private String user_name;
  private String user_email;
  private String age;
  private String country;

  Attribute(String adhar_id, String user_name, String user_email, String age, String country) {
    this.adhar_id = adhar_id;
    this.user_name = user_name;
    this.user_email = user_email;
    this.age = age;
    this.country = country;
  }

  @Override
  public String toString() {
    return this.adhar_id + " " + this.user_name + " " + this.user_email + " " + this.age + " " + this.country;
  }

  public String getadhar_id() {
    return this.adhar_id;
  }

  public String getuser_name() {
    return this.user_name;
  }

  public String getuser_email() {
    return this.user_email;
  }

  public String getage() {
    return this.age;
  }

  public String getcountry() {
    return this.country;
  }
}