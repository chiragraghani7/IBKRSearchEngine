class Node {
  Node left;
  Node right;
  String data;

  Node() {
    this.left = null;
    this.right = null;
    this.data = "";
  }

  Node(String str) {
    this();
    this.data = str;
  }

  public String toString() {
    String left_data = this.left + "";
    String right_data = this.right + "";
    String op = this.data;
    if (!"null".equals(left_data)) {
      op += "L- " + left_data;
    }
    if (!"null".equals(right_data)) {
      op += "R- " + right_data;
    }
    return op;
  }
}