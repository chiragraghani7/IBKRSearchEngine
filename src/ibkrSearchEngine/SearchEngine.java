import java.util.*;

public class SearchEngine {

  public static void main(String args[]) {
    String query = "(user_name = 'chirag raghani' or (country = 'India' and age >= '21' )) and (user_name = 'nikhil ghind' or user_email ='jatin@gmail.com')";

    SQLParser sqlParser = new SQLParser();
    System.out.println(sqlParser.getData("Users", query));
  }

}
