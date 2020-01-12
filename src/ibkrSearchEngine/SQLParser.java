import java.util.*;

class SQLParser {
  Database db;
  {
    this.db = new Database();
    this.db.initializeDatabase("Users");
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

  public LinkedList<Attribute> getData(String tableName, String query) {
    Node root = this.createParseTree(query);
    return parseTreeUtil(tableName, root);
  }

  public LinkedList<Attribute> parseTreeUtil(String tableName, Node p) {
    //
    LinkedList<Attribute> result = new LinkedList<>();
    if (p == null) {
      return null;
    }
    if (p.left == null && p.right == null) {
      return this.db.getAttribute(tableName, p.data);
    } else {
      LinkedList<Attribute> left_ll = this.parseTreeUtil(tableName, p.left);
      LinkedList<Attribute> right_ll = this.parseTreeUtil(tableName, p.right);
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
