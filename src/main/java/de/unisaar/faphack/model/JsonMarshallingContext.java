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

  private IdentityHashMap<Storable, String> writecache;

  private Deque<JSONObject> stack;

  private Map<String, Storable> readcache;

  private int idGenerator = 1;

  public JsonMarshallingContext(File f, StorableFactory fact) {
    file = f;
    factory = fact;
    readcache = new HashMap<String, Storable>();
    writecache = new IdentityHashMap<Storable, String>();
    stack = new ArrayDeque<JSONObject>();
  }

  @Override
  public void save(Storable s) {
    //TODO Clear stack in the beginning?
    // Create outer json
    JSONObject json = new JSONObject();
    this.stack.addFirst(json);

    this.write("Outer", s);  // Delegate saving to method write(key, Storable)
    JSONObject wrappedjson = stack.pop();
    JSONObject fulljson = (JSONObject)wrappedjson.get("Outer");

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
  //TODO Clear stack in the beginning?
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
      Storable currentObj = factory.newInstance(classString);
      // Put first in readcache, then marshal
      readcache.put(currentID, currentObj);
      stack.addFirst(jsonObject);
      currentObj.unmarshal(this);
      stack.pop();
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
    // Check if object is already in writecache
    if (this.writecache.containsKey(object)) {
      // TODO: is this the right treatment of already marshalled objects?
      String id = this.writecache.get(object);
      JSONObject parentJson = this.stack.getFirst();
      parentJson.put(key, id);
      return;
    }
    
    // Create json
    JSONObject json = new JSONObject();
    // Save Storables that are null
    if (object == null) {
      JSONObject parentJson = this.stack.getFirst();
      parentJson.put(key, null);
      return;
<<<<<<< Updated upstream
    };

    // Storable that is not null
=======
    }
    
>>>>>>> Stashed changes
    // get ID
    String className = factory.getClassName(object.getClass());
    String runningID = Integer.toString(idGenerator++);
    String id = className + "@" + runningID;
    json.put("id", id);
    
    // Put object and id in writecache, this should be before descending into recursion
    this.writecache.put(object, id);
    
    // Put json on stack so other Marshalling methods have access to it
    this.stack.addFirst(json);
    
    // Marshall all of the children fields
    object.marshal(this);
    
    // Get current jsonObject out of stack
    JSONObject fulljson = this.stack.pop();

    // Put new json in parent json
    JSONObject parentJson = this.stack.getFirst();
    parentJson.put(key, fulljson);
  }

  @Override
  public <T extends Storable> T read(String key) {
    JSONObject currentjson = stack.getFirst();
    Object rawValue = currentjson.get(key);
    // Check if Storable is null
    if (rawValue == null) {
      return null;
    }
    // Check if Storable is already in cache
    if (readcache.containsKey(key)) {
      // TODO: check whether cast is ok
      T currentObj = (T)readcache.get(key);
      return currentObj;
    }
    if (rawValue instanceof String) {
      if (readcache.containsKey(rawValue)) {
        T currentObj = (T)readcache.get(rawValue);
        return currentObj;
      }
    }
    // Storable is not in readcache and has to be read now
    JSONObject jsonObject = (JSONObject)rawValue;

    // get id
    String currentID = (String)jsonObject.get("id");

    // Get class of stored object
    String classString = currentID.split("@")[0];  // first part of id encodes class
    T currentObj = (T)factory.newInstance(classString);
    // Put first in readcache, then marshal
    readcache.put(currentID, currentObj);
    stack.addFirst(jsonObject);
    currentObj.unmarshal(this);

    // remove json from stack after object is finished
    stack.pop();
    return currentObj;
  }

  @Override
  public void write(String key, int object) {
    JSONObject parentJson = this.stack.getFirst();
    parentJson.put(key, object);
  }

  @Override
  public int readInt(String key) {
    JSONObject currentjson = stack.getFirst();
    // Following recommendation in 
    // https://st,ackoverflow.com/questions/17164014/java-lang-classcastexception-java-lang-long-cannot-be-cast-to-java-lang-integer
    // to cast JSON Output to int
    Object rawValue = currentjson.get(key);
    int value = ((Long)rawValue).intValue();
    // TODO: catch if the object cannot be cast to int
    return value;
  }

  @Override
  public void write(String key, double object) {
    // get current collecting json
    JSONObject parentJson = this.stack.getFirst();
    parentJson.put(key, object);
  }

  @Override
  public double readDouble(String key) {
    JSONObject currentjson = stack.getFirst();
    Object rawValue = currentjson.get(key);
    float value = (Float)rawValue;
    return value;
  }

  @Override
  public void write(String key, String object) {
    JSONObject parentJson = this.stack.getFirst();
    parentJson.put(key, object);
  }

  @Override
  public String readString(String key) {
    JSONObject currentjson = stack.getFirst();
    Object rawValue = currentjson.get(key);
    String value = (String)rawValue;
    return value;
  }

  @Override
  public void write(String key, Collection<? extends Storable> coll) {
    // Create List
    JSONArray array = new JSONArray();

    // Iterate over collection and get json for every Storable in it
    for (Storable storable : coll) {
      this.stack.addFirst(new JSONObject());
      this.write("Outerloop", storable);
      JSONObject wrappedjson = stack.pop();
      Object newElement = wrappedjson.get("Outerloop");
      // Check whether Object was read right now or whether it is in the cache
      if (newElement instanceof String) {
        String fulljson = (String)newElement;
        array.add(fulljson);
      }
      else {
        JSONObject fulljson = (JSONObject)newElement;
        array.add(fulljson);
      }
    }
    // Put JSONArray into parent json
    JSONObject parentJson = this.stack.getFirst();
    parentJson.put(key, array);
  }

  @Override
  public void readAll(String key, Collection<? extends Storable> coll) {
    // Get collection of jsons
    JSONObject currentjson = stack.getFirst();
    Object rawValue = currentjson.get(key);
    Collection<JSONObject> value = (Collection<JSONObject>)rawValue;
    // Loop over jsons in collection and decode them
    for (Object element: value) {
      coll.add(helperReadJSONArray(element));
    }
    return;
  }

  /* Helper method that returns a storable of type T 
   * because only elements of type T can be added to the collection in the 
   * parent method readAll but inside readAll it is not possible to cast to T
   */
  public <T extends Storable> T helperReadJSONArray(Object element){
    if (element instanceof String) {
      // was already read and should be in cache
      T storable = (T)readcache.get(element);
      return storable;
    }
    if (element instanceof JSONObject){
      JSONObject innerjson = (JSONObject)element;
      JSONObject wrapper = new JSONObject();
      wrapper.put("outer_key", innerjson);
      stack.addFirst(wrapper);
      T storable = (T)read("outer_key");
      stack.pop();
      return storable;
    }
    System.out.println("element has wrong type");
    System.out.println(element);
    return null;
  }

  @Override
  public void write(String key, Tile[][] coll) {
    // Saves Board as a 2-dimensional JSONArray
    JSONObject parentJson = this.stack.getFirst();
    JSONArray json_board = new JSONArray();
    // Loop over Board
    for (Tile[] row: coll) {
      JSONArray json_row = new JSONArray();
      for (Tile tile: row) {
        stack.addFirst(new JSONObject());
        write("dummy_key", tile);
        JSONObject tile_json = stack.pop();
        // Get tiles out of tile json
        json_row.add(tile_json.get("dummy_key"));
      }
      json_board.add(json_row);
    }
    parentJson.put(key, json_board);

  }

  @Override
  public Tile[][] readBoard(String key) {
    // Get JSONArray encoding the board
    JSONObject currentjson = stack.getFirst();
    Object rawValue = currentjson.get(key);
    JSONArray json_board = (JSONArray)rawValue;

    // Initialize Board as Tile[][]
    int x = json_board.size();
    int y = ((JSONArray)json_board.get(0)).size();
    Tile[][] board = new Tile[x][y];

    // Loop over the Array, read individual Tiles and put them in the board
    for (int i = 0; i < x; i++) {
      JSONArray json_row = (JSONArray)json_board.get(i);
      for (int j = 0; j < y; j++) {
        Object field = json_row.get(j);
        if (field instanceof String) {
          // this tile is already in cache
          Tile tile = (Tile)readcache.get((String)field);
          board[i][j] = tile;
        }
        if (field instanceof JSONObject) {
          JSONObject json_field = (JSONObject)field;
          JSONObject wrapper = new JSONObject();
          wrapper.put("outer_key", json_field);
          stack.addFirst(wrapper);
          Tile tile = read("outer_key");
          stack.pop();
          board[i][j] = tile;
        }
      }
    }
    return board;
  }

}
