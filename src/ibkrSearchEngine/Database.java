import java.util.*;

class Database {

  private Map<String, Map<String, List<AbstractMap<Object, LinkedList<Attribute>>>>> table;

  {
    this.table = new HashMap<>();
  }
  DataParser dataParser;

  Database() {
    this.dataParser = new DataParser();

  }

  public void createTable(String tableName) {
    this.table.put(tableName, new HashMap<>());
  }

  public void initializeDatabase(String tableName) {
    if (!this.table.containsKey(tableName)) {
      this.createTable(tableName);
    }
    String input = this.dataParser.getData();
    String dataStore[] = input.trim().split("\n");
    for (String data : dataStore) {
      String columns[] = data.split(",");
      HashMap<String, String> attr = new HashMap<>();
      Attribute myAttr = new Attribute();
      for (String column : columns) {
        String attributes[] = column.trim().split(":");
        attributes[1] = attributes[1].trim().replaceAll("'", "");
        attr.put(attributes[0].trim(), attributes[1]);
        if (checkIfNumber(attributes[1])) {
          insertIntoTreeHelper(this.table.get(tableName), attributes[0], Integer.parseInt(attributes[1]), myAttr);
        } else {
          insertIntoTreeHelper(this.table.get(tableName), attributes[0], attributes[1], myAttr);
        }
      }
      myAttr.setMap(attr);
    }

  }

  private int getLastInsertedId(HashMap<Integer, Attribute> map) {
    if (map.size() == 0) {
      return 0;
    }
    Integer[] keys = map.keySet().toArray(new Integer[0]);
    return keys[keys.length - 1];
  }

  private boolean checkIfNumber(String value) {
    try {
      int val = Integer.parseInt(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private void insertIntoTreeHelper(Map<String, List<AbstractMap<Object, LinkedList<Attribute>>>> table, String key,
      Object innerKey, Attribute attr) {
    if (!table.containsKey(key)) {
      TreeMap<Object, LinkedList<Attribute>> treeMap = new TreeMap<Object, LinkedList<Attribute>>() {
        {
          put(innerKey, new LinkedList<Attribute>() {
            {
              add(attr);
            }
          });
        }
      };
      HashMap<Object, LinkedList<Attribute>> hashMap = new HashMap<Object, LinkedList<Attribute>>() {
        {
          put(innerKey, new LinkedList<Attribute>() {
            {
              add(attr);
            }
          });
        }
      };
      LinkedList<AbstractMap<Object, LinkedList<Attribute>>> list = new LinkedList<AbstractMap<Object, LinkedList<Attribute>>>() {
        {
          add(treeMap);
          add(hashMap);
        }
      };
      table.put(key, list);
      return;
    } else if (!table.get(key).get(0).containsKey(innerKey)) {
      for (AbstractMap<Object, LinkedList<Attribute>> map : table.get(key)) {
        map.put(innerKey, new LinkedList<Attribute>() {
          {
            add(attr);
          }
        });
      }
      return;
    } else {
      for (AbstractMap<Object, LinkedList<Attribute>> map : table.get(key)) {
        map.get(innerKey).add(attr);
      }

    }
    // else if (!table.get(key).get(0).get(innerKey).contains(attr)) {
    // table.get(key).get(0).get(innerKey).add(attr);
    // }
  }

  public LinkedList<Attribute> getAttribute(String tableName, String condition) {
    LinkedList<Attribute> result = new LinkedList<>();
    String splitingCond = "";
    if (condition.contains(">=")) {
      splitingCond = ">=";
    } else if (condition.contains("<=")) {
      splitingCond = "<=";
    } else if (condition.contains(">")) {
      splitingCond = ">";
    } else if (condition.contains("<")) {
      splitingCond = "<";
    } else if (condition.contains("=")) {
      splitingCond = "=";
    }
    String condAttr[] = condition.split("[>=<]");
    if (condAttr.length > 2) {
      condAttr[1] = condAttr[condAttr.length - 1].replaceAll("[']", "").trim();
    } else {
      condAttr[1] = condAttr[1].replaceAll("[']", "").trim();
    }
    condAttr[0] = condAttr[0].trim();
    Object key = condAttr[1];
    if (checkIfNumber(condAttr[1])) {
      key = Integer.parseInt(condAttr[1]);
    }
    Collection<LinkedList<Attribute>> temps;
    switch (splitingCond) {

    case ">=":
      temps = ((TreeMap<Object, LinkedList<Attribute>>) this.table.get(tableName).get(condAttr[0].trim()).get(0))
          .tailMap(key, true).values();
      for (LinkedList<Attribute> temp : temps) {
        result.addAll(temp);
      }

      break;
    case "<=":
      temps = ((TreeMap<Object, LinkedList<Attribute>>) this.table.get(tableName).get(condAttr[0].trim()).get(0))
          .headMap(key, true).values();
      for (LinkedList<Attribute> temp : temps) {
        result.addAll(temp);
      }
      break;
    case ">":
      temps = ((TreeMap<Object, LinkedList<Attribute>>) this.table.get(tableName).get(condAttr[0].trim()).get(0))
          .tailMap(key, false).values();
      for (LinkedList<Attribute> temp : temps) {
        result.addAll(temp);
      }
      break;
    case "<":
      temps = ((TreeMap<Object, LinkedList<Attribute>>) this.table.get(tableName).get(condAttr[0].trim()).get(0))
          .headMap(key, false).values();
      for (LinkedList<Attribute> temp : temps) {
        result.addAll(temp);
      }
      break;
    case "=":
      return this.table.get(tableName).get(condAttr[0].trim()).get(1).get(key);

    }
    return result;
  }

  public void printDatabase() {
    System.out.println(this.table);
  }

  public void printTable(String tableName) {
    System.out.println(this.table.containsKey(tableName) ? this.table.get(tableName) : "Table Not Defined");
  }
}