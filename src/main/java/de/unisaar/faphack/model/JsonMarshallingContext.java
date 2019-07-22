package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.Tile;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class JsonMarshallingContext implements MarshallingContext {

  private final File file;

  private static StorableFactory factory;

  private IdentityHashMap<Object, String> writecache;

  private Deque<JSONObject> stack;

  private Map<String, Storable> readcache;

  private int idGenerator = 1;

  public JsonMarshallingContext(File f, StorableFactory fact) {
    file = f;
    factory = fact;
    readcache = new HashMap<String, Storable>();
    writecache = new IdentityHashMap<Object, String>();
    stack = new ArrayDeque<JSONObject>();
  }

  @Override
  public void save(Storable s) {
    // TODO Auto-generated method stub
    // get ID
//    String className = factory.getClassName(s.getClass());
//    String runningID = Integer.toString(idGenerator++);
//    String id = className + "@" + runningID;
    
    // Create outer json
    JSONObject json = new JSONObject();
    this.stack.add(json);
    
    s.marshal(this);
    JSONObject fulljson = stack.pop();
    
    try (FileWriter writer = new FileWriter(file)) {
      writer.write(fulljson.toJSONString());
      System.out.println("Successfully wrote JSON object to file...");
      System.out.println("\nJSON Object: " + fulljson);
    }
    catch (Exception e) {
      System.out.println("Exception");
    }
  }

  public Storable read() {
    // TODO Auto-generated method stub
    JSONParser parser = new JSONParser();
    try
    {
      Reader reader = new FileReader(file);
      Object obj = parser.parse(reader);
      JSONObject jsonObject = (JSONObject)obj;
      
      // Get ID
      String currentID = (String)jsonObject.get("id");
      
      // Get class of stored object
      String classString = currentID.split("@")[0];  // first part of id encodes class
      factory.toString();
      Storable currentObj = factory.newInstance(classString);
      currentObj.unmarshal(this);
      
      //Storable finishedObject = null;  // TODO remove later
      // Put read object in readcache
      readcache.put(currentID, currentObj);
      System.out.println(currentObj);
      Wearable w = (Wearable)currentObj;
      System.out.println(w.weight);
      return currentObj;
    }
    catch(FileNotFoundException fe)
    {
      fe.printStackTrace();
    }
    catch(Exception e)
    {
       e.printStackTrace();
    }
    
    return null;
  }

  @Override
  public void write(String key, Storable object) {
    // TODO Auto-generated method stub
    // Create json
    JSONObject json = new JSONObject();
    if (object == null) {
      JSONObject parentJson = this.stack.getLast();
      parentJson.put(key, "null");
      return;
    };
    
    // get ID
    System.out.println(object);
    System.out.println(object.getClass());
    String className = factory.getClassName(object.getClass());
    String runningID = Integer.toString(idGenerator++);
    String id = className + "@" + runningID;
    json.put("id", id);
    
    // Put json on stack so other Marshalling methods have access to it
    this.stack.add(json);
    
    // Marshall all of the children fields
    object.marshal(this);
    
    // Get current jsonObject out of stack
    JSONObject fulljson = this.stack.pop();
    
    System.out.println(stack);

    // Write new json
    // Or put new json in parent json
    JSONObject parentJson = this.stack.getLast();
    parentJson.put(key, fulljson);
    
    
    
//    try (FileWriter writer = new FileWriter(file)) {
//      writer.write(fulljson.toJSONString());
//      System.out.println("Successfully wrote JSON object to file...");
//      System.out.println("\nJSON Object: " + fulljson);
//    }
//    catch (Exception e) {
//      System.out.println("Exception");
//    }
  
    //

  }

  @Override
  public <T extends Storable> T read(String key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void write(String key, int object) {
    // TODO Auto-generated method stub

  }

  @Override
  public int readInt(String key) {
    // TODO Auto-generated method stub
    JSONParser jsonParser = new JSONParser();
    try {
      Object object = jsonParser.parse(file.toString());
      JSONObject jsonObject = (JSONObject) object;
      int number = (int) jsonObject.get(key);
      System.out.println(number);
      return number;
    }
    catch (ParseException e) {
      System.out.println("ParseException: Cannot parse given file!");
      return 0;
    }
  }

  @Override
  public void write(String key, double object) {
    // TODO Auto-generated method stub
    if (writecache.containsKey(key)){
      
    }

  }

  @Override
  public double readDouble(String key) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void write(String key, String object) {
    // TODO Auto-generated method stub

  }

  @Override
  public String readString(String key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void write(String key, Collection<? extends Storable> coll) {
    // TODO Auto-generated method stub

  }

  @Override
  public void readAll(String key, Collection<? extends Storable> coll) {
    // TODO Auto-generated method stub

  }

  @Override
  public void write(String key, Tile[][] coll) {
    // TODO Auto-generated method stub

  }

  @Override
  public Tile[][] readBoard(String key) {
    // TODO Auto-generated method stub
    return null;
  }

}
