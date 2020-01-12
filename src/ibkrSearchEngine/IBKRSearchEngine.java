import java.util.*;

public class IBKRSearchEngine {

  public static void main(String args[]) {
    ArrayList<String> inputObjects= new ArrayList<>();
    //client code
    DataJson obj = new DataJson();
    inputObjects = obj.getObjects();
    //System.out.println(inputObjects);
    Adapter generalizeObj = new Adapter();
    generalizeObj.setList(inputObjects);
    // String query = "(user_name = 'chirag raghani' or (country = 'India' and age ='21' )) and (user_name = 'nikhil ghind' or user_email = 'nikhil@gmail.com')";

    // SQLParser sqlParser = new SQLParser();
    // System.out.println(sqlParser.getData(query));
  }

}
