package taojava;

/**
 * Implements the Location Class
 * @author Ameer Shujjah
 * @author Tommy Pitcher
 * @author Yazan Kittaneh
 */
public class Location
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+
  
  /**
   * The distance to the school
   */
  int distance;
  
  /**
   * The name of the school
   */
  String schoolInitials;
  
  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+
  
  /**
   * Create a Location Object
   * @param _distance, the distance between the school
   * @param initials, the initials of the school
   */
  public Location(int _distance, String initials)
  {
    this.distance = _distance;
    this.schoolInitials = initials;
  }//Location(int, String)
  
  
}// Class Location
