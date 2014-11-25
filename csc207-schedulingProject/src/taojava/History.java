package taojava;

import java.util.Date;

/**
 * Implements the History class.
 * @author Ameer Shujjah
 * @author Tommy Pitcher
 * @author Yazan Kittaneh
 */
public class History
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * School name of the opponent
   */
  School opponent;

  /**
   * Boolean corresponding to the location of the match
   * true for home, false for away
   */
  boolean home;

  /**
   * A dates object with the date of the match
   */
  Dates played;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Constructs a History Object
   * @param college, the school object
   * @param location, the location, whether home or away
   * @param time, the date of the match-up
   */
  public History(School college, boolean location, Dates time)
  {
    this.opponent = college;
    this.home = location;
    this.played = time;
  }// History(School, boolean, Dates)

  // +--------+----------------------------------------------------------
  // |Methods |
  // +--------+

  
  
}// Class History
