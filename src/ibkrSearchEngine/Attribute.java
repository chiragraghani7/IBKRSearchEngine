import java.util.*;

class Attribute {
  private HashMap<String, String> map;

  Attribute() {
    this.map = new HashMap<>();
  }

  public void setMap(HashMap<String, String> map) {
    this.map = map;
  }

  @Override
  public String toString() {
    return this.map.toString() + "\n";
  }

  public String get(String attr) {
    return this.map.get((String) attr);
  }

  public void add(String[] data) {
    this.map.put((String) data[0], (String) data[1]);
  }
}