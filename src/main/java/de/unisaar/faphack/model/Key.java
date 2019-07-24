package de.unisaar.faphack.model;

public class Key extends Item {
  private int keyId;

  @Override
  public String getTrait() { return TraitOwner.KEY; }

  /**
   * Checks if a given keyId matches this current item
   * @param keyId
   * @return true if this key matches the given keyId, false otherwise
   */
  public boolean matchedKeyId(int keyId) {
    //TODO
    return false;
  }
  
  public void marshal(MarshallingContext c) {
    super.marshal(c);
    c.write("keyId", keyId);
  };
  
  public void unmarshal(MarshallingContext c) {
    super.unmarshal(c);
    keyId = c.readInt("keyId");
  };
}
