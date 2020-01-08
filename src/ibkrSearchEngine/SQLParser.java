import java.util.*;

// class SQLParser {
// public void main(String args[]) {
// String str = "(user_name = 'chirag raghani' or (country = 'India' and age =
// '21' )) and (user_name = 'nikhil ghind' or user_email = 'nikhil@gmail.com')";
// CreateParseTree cpt = new CreateParseTree();
// Node root = cpt.createParseTree(str);
// 
// }

// }

class SQLParser {
  Database db;
  {
    this.db = new Database();
  }

  public LinkedList<Attribute> getUsersByName(String name) {
    Map<String, LinkedList<Attribute>> user_name_hashMap = this.db.getUserNameMap();
    if (user_name_hashMap.containsKey(name)) {
      return user_name_hashMap.get(name);
    } else {

      return new LinkedList<Attribute>();
    }
  }

  public LinkedList<Attribute> getUsersByAge(String age) {
    TreeMap<String, LinkedList<Attribute>> age_treeMap = this.db.getAgeMap();
    if (age_treeMap.containsKey(age)) {
      return age_treeMap.get(age);
    } else {

      return new LinkedList<Attribute>();
    }
  }

  public LinkedList<Attribute> getUsersByCountry(String country) {
    Map<String, LinkedList<Attribute>> country_hashMap = this.db.getCountryMap();
    if (country_hashMap.containsKey(country)) {
      return country_hashMap.get(country);
    } else {

      return new LinkedList<Attribute>();
    }
  }

  public LinkedList<Attribute> getUsersByEmail(String user_email) {
    Map<String, LinkedList<Attribute>> user_email_hashMap = this.db.getUserEmailMap();

    if (user_email_hashMap.containsKey(user_email)) {
      return user_email_hashMap.get(user_email);
    } else {

      return new LinkedList<Attribute>();
    }
  }

  public LinkedList<Attribute> getDataOfSingleAttribute(String query) {
    String input[] = query.trim().split("=");
    LinkedList<Attribute> result = new LinkedList<>();
    input[1] = input[1].trim().replaceAll("\'", "");

    switch (input[0].trim()) {
    case "age":
      result = getUsersByAge(input[1]);
      break;
    case "user_name":
      result = getUsersByName(input[1]);
      break;
    case "country":
      result = getUsersByCountry(input[1]);
      break;
    case "user_email":
      result = getUsersByEmail(input[1]);
      break;
    }
    return result;
  }

  public Node createParseTree(String str) {
    Stack<Node> nodes = new Stack<>();
    Stack<String> ops = new Stack<>();
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '(') {
        ops.push(str.charAt(i) + "");
      } else if (str.charAt(i) == ')') {
        while (!ops.peek().equals("("))
          nodes.push(this.createNode(ops.pop(), nodes.pop(), nodes.pop()));
        ops.pop();
      } else {
        String temp = "";
        while (str.charAt(i) != ')' && !str.substring(i, i + 3).equals(" or")
            && !str.substring(i, i + 4).equals(" and")) {
          if (str.charAt(i) == '\'') {
            while (str.charAt(i + 1) != '\'') {
              temp += str.charAt(i++) + "";
            }
            temp += str.charAt(i++) + "";
            temp += str.charAt(i) + "";
            i++;
            continue;
          }
          temp += str.charAt(i++) + "";
        }
        if (!"".equals(temp))
          nodes.push(new Node(temp));
        temp = "";
        if (str.charAt(i) == ')') {
          i--;
        } else if (str.substring(i, i + 3).equals(" or")) {
          ops.push("or");
          i += 3;
        } else if (str.substring(i, i + 4).equals(" and")) {
          ops.push("and");
          i += 4;
        }
      }
      //
      //
    }
    while (!ops.isEmpty()) {
      nodes.push(this.createNode(ops.pop(), nodes.pop(), nodes.pop()));
    }
    return nodes.pop();
  }

  public Node createNode(String data, Node right, Node left) {
    Node n = new Node(data);
    n.left = left;
    n.right = right;
    return n;
  }

  public LinkedList<Attribute> getData(String query) {
    Node root = this.createParseTree(query);
    return parseTreeUtil(root);
  }

  public LinkedList<Attribute> parseTreeUtil(Node p) {
    //
    LinkedList<Attribute> result = new LinkedList<>();
    if (p == null) {
      return null;
    }
    if (p.left == null && p.right == null) {
      return this.getDataOfSingleAttribute(p.data);
    } else {
      LinkedList<Attribute> left_ll = this.parseTreeUtil(p.left);
      LinkedList<Attribute> right_ll = this.parseTreeUtil(p.right);
      Set<Attribute> initialSet = new HashSet<Attribute>(left_ll);
      if (p.data.equals("and")) {
        //
        initialSet.retainAll(right_ll);
        result = new LinkedList<Attribute>(initialSet);

      } else if (p.data.equals("or")) {
        initialSet.addAll(right_ll);
        result = new LinkedList<Attribute>(initialSet);
      }

      return result;
    }
  }
}

// String query = "country = 'India' and name = 'chirag raghani'";
// //
// =======================================================================================
// String[] queryParts = query.split("and");
// String query1 = queryParts[0].replaceAll("\'", "");
// String query2 = queryParts[1].replaceAll("\'", "");
// //
// //
// String[] query1SubParts = query1.split("=");
// String[] query2SubParts = query2.split("=");
// // if query contains name in it
// if ("name".equals(query1SubParts[0].trim()) ||
// "name".equals(query2SubParts[0].trim())) {
// if ("name".equals(query1SubParts[0].trim())) {
// userListByName = getUsersByName(user_name_hashMap, query1SubParts[1].trim());
// for (Attribute a : userListByName) {
// resultSet1.add(a);
// }
// } else {
// userListByName = getUsersByName(user_name_hashMap, query2SubParts[1].trim());
// for (Attribute a : userListByName) {
// resultSet2.add(a);
// }
// }
// //
// }

// // if query contains age in it
// if ("age".equals(query1SubParts[0].trim()) ||
// "age".equals(query2SubParts[0].trim())) {
// if ("age".equals(query1SubParts[0].trim())) {
// userListByAge = getUsersByAge(age_treeMap, query1SubParts[1].trim());
// for (Attribute a : userListByAge) {
// resultSet1.add(a);
// }
// } else {
// userListByAge = getUsersByAge(age_treeMap, query2SubParts[1].trim());
// for (Attribute a : userListByAge) {
// resultSet2.add(a);
// }

// }
// //
// }

// // if query contain country in it
// if ("country".equals(query1SubParts[0].trim()) ||
// "country".equals(query2SubParts[0].trim())) {
// if ("country".equals(query1SubParts[0].trim())) {
// userListByCountry = getUsersByCountry(country_hashMap,
// query1SubParts[1].trim());
// for (Attribute a : userListByCountry) {
// resultSet1.add(a);
// }
// } else {
// userListByCountry = getUsersByCountry(country_hashMap,
// query2SubParts[1].trim());
// for (Attribute a : userListByCountry) {
// resultSet2.add(a);
// }
// }
// //
// }

// //
// //

// Set<Attribute> intersection = new HashSet<Attribute>(resultSet1); // use the
// copy constructor
// intersection.retainAll(resultSet2);

// //

// // ======================================= query string
// // ===========================
// String queryComparator = "age >= '18'";
// //
// =======================================================================================
// String[] compareQueryParts1 = new String[2];
// if (queryComparator.contains(">") || queryComparator.contains("<")) {

// int index1 = queryComparator.indexOf('>');
// int index2 = queryComparator.indexOf('<');
// int index3 = queryComparator.indexOf('=');
// boolean flagEqualSign = false;
//
// if (index1 != -1) {
// if (index3 == index1 + 1) {
// flagEqualSign = true;
// compareQueryParts1 = queryComparator.split(">=");
// } else {
// compareQueryParts1 = queryComparator.split(">");
// }
// } else if (index2 != -1) {
// if (index3 == index2 + 1) {
// flagEqualSign = true;
// compareQueryParts1 = queryComparator.split("<=");
// } else {
// compareQueryParts1 = queryComparator.split("<");
// }
// }

// }

//

// LinkedList<Attribute> result = new LinkedList<>();
// if ("age".equals(compareQueryParts1[0].trim())) {
// //
// for (int i = Integer.valueOf(compareQueryParts1[1].trim().replaceAll("\'",
// "")); i <= Integer
// .valueOf(age_treeMap.lastKey()); i++) {
// if (age_treeMap.get(String.valueOf(i)) != null) {
// result.addAll(age_treeMap.get(String.valueOf(i)));
// }
// }
// }
//