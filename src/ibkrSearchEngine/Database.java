import java.util.*;

class Database {
  private Map<String, Attribute> adhar_hashMap;
  private Map<String, LinkedList<Attribute>> user_name_hashMap;
  private Map<String, LinkedList<Attribute>> user_email_hashMap;
  private TreeMap<String, LinkedList<Attribute>> age_treeMap;
  private Map<String, LinkedList<Attribute>> country_hashMap;

  {
    this.adhar_hashMap = new HashMap<>();
    this.country_hashMap = new HashMap<>();
    this.user_email_hashMap = new HashMap<>();
    this.age_treeMap = new TreeMap<>();
    this.user_name_hashMap = new HashMap<>();
  }

  Database() {
    DataJson data = new DataJson();
    this.initialiseDatabase(data.getData());
  }

  private void initialiseDatabase(String input) {

    String[] users = input.trim().split("\\r?\\n");
    // System.out.println(users);

    LinkedList<Attribute> userListByName;
    LinkedList<Attribute> userListByAge;
    LinkedList<Attribute> userListByCountry;

    String[] temp = new String[2];

    for (String u : users) {
      // System.out.println(u);

      String[] input_split = u.trim().split(",");
      ArrayList<String> arr = new ArrayList<>();
      for (String str : input_split) {
        // System.out.println(str);
        temp = str.split(":");

        arr.add(temp[1].replaceAll("\'", ""));
      }
      // object creation
      Attribute obj = new Attribute(arr.get(0), arr.get(1), arr.get(2), arr.get(3), arr.get(4));
      arr.clear();

      adhar_hashMap.put(obj.getadhar_id(), obj);
      LinkedList<Attribute> email_ll = new LinkedList<Attribute>();
      email_ll.add(obj);
      user_email_hashMap.put(obj.getuser_email(), email_ll);

      // user name hashmap

      if (user_name_hashMap.containsKey(obj.getuser_name())) {
        LinkedList<Attribute> tempList_user_name = user_name_hashMap.get(obj.getuser_name());
        tempList_user_name.add(obj);
        user_name_hashMap.put(obj.getuser_name().trim(), tempList_user_name);
      } else {
        LinkedList<Attribute> getUserNameLinkedList = new LinkedList<Attribute>();
        getUserNameLinkedList.add(obj);
        user_name_hashMap.put(obj.getuser_name().trim(), getUserNameLinkedList);
      }

      // user age hashmap

      if (age_treeMap.containsKey(obj.getage())) {
        LinkedList<Attribute> tempList_user_age = age_treeMap.get(obj.getage());
        tempList_user_age.add(obj);
        age_treeMap.put(obj.getage().trim(), tempList_user_age);
      } else {
        LinkedList<Attribute> getUserageLinkedList = new LinkedList<Attribute>();
        getUserageLinkedList.add(obj);
        age_treeMap.put(obj.getage().trim(), getUserageLinkedList);
      }

      // user country hashmap

      if (country_hashMap.containsKey(obj.getcountry())) {
        LinkedList<Attribute> tempList_user_country = country_hashMap.get(obj.getcountry());
        tempList_user_country.add(obj);
        country_hashMap.put(obj.getcountry().trim(), tempList_user_country);
      } else {

        LinkedList<Attribute> getUserNameLinkedList = new LinkedList<Attribute>();
        getUserNameLinkedList.add(obj);
        country_hashMap.put(obj.getcountry().trim(), getUserNameLinkedList);
      }
    }
  }

  public Map<String, Attribute> getAdharMap() {
    return new HashMap<>(this.adhar_hashMap);
  }

  public Map<String, LinkedList<Attribute>> getUserEmailMap() {
    return new HashMap<>(this.user_email_hashMap);
  }

  public Map<String, LinkedList<Attribute>> getCountryMap() {
    return new HashMap<>(this.country_hashMap);
  }

  public Map<String, LinkedList<Attribute>> getUserNameMap() {
    return new HashMap<>(this.user_name_hashMap);
  }

  public TreeMap<String, LinkedList<Attribute>> getAgeMap() {
    return new TreeMap<>(this.age_treeMap);
  }
}