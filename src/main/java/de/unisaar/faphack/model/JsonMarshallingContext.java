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
    // TODO writecache, stack
  }

  @Override
  public void save(Storable s) {
    // TODO Auto-generated method stub
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
      
      //Storable finishedObject = curr;  // TODO remove later
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
    return 0;
  }

  @Override
  public void write(String key, double object) {
    // TODO Auto-generated method stub

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
