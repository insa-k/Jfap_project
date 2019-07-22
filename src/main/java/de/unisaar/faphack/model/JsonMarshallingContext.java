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
  }

  @Override
  public void save(Storable s) {
    // TODO Auto-generated method stub
  }

  public Storable read() {
    // TODO Auto-generated method stub
    JSONParser jsonParser = new JSONParser();
    try {
      Object storableObject = jsonParser.parse(file.toString());
      JSONObject storableJSON = (JSONObject) storableObject;
      // TODO: return Storable from StorableJSON
      return null;
    }
    catch (ParseException e) {
      System.out.println("ParseException: Cannot parse given file!");
      return null;
    }
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
    JSONParser jsonParser = new JSONParser();
    try {
      Object object = jsonParser.parse(file.toString());
      JSONObject jsonObject = (JSONObject) object;
      int number = (int) jsonObject.get(key);
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

  }

  @Override
  public double readDouble(String key) {
    // TODO Auto-generated method stub
    JSONParser jsonParser = new JSONParser();
    try {
      Object object = jsonParser.parse(file.toString());
      JSONObject jsonObject = (JSONObject) object;
      double number = (double) jsonObject.get(key);
      return number;
    }
    catch (ParseException e) {
      System.out.println("ParseException: Cannot parse given file!");
      return 0;
    }
  }

  @Override
  public void write(String key, String object) {
    // TODO Auto-generated method stub

  }

  @Override
  public String readString(String key) {
    // TODO Auto-generated method stub
    JSONParser jsonParser = new JSONParser();
    try {
      Object object = jsonParser.parse(file.toString());
      JSONObject jsonObject = (JSONObject) object;
      String string = (String) jsonObject.get(key);
      return string;
    }
    catch (ParseException e) {
      System.out.println("ParseException: Cannot parse given file!");
      return null;
    }
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
