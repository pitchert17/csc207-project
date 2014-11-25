package taojava;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Implements the School class.
 * @author Ameer Shujjah
 * @author Tommy Pitcher
 * @author Yazan Kittaneh
 */
public class School
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The name of the School
   */
  String name;

  /**
   * The initials of the School
   */
  String Initials;

  /**
   * An Array of Location objects with the distances
   */
  Location[] distances = new Location[10];;

  /**
   * An Array list with the dates the school has considered
   */
  ArrayList<Dates> dates = new ArrayList<Dates>();;

  /**
   * An Array List with the history of the schools matches
   */
  ArrayList<History> history = new ArrayList<History>();

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+
  /**
   * Constructs a new School Object
   * @param college, a String which is the name of the college
   */
  public School(String college)
  {
    this.name = college;
  }// School(String)

  /**
   * Constructs a new School Object
   * @param college, a String the name of the college
   * @param times, An ArrayList of Dates
   */
  public School(String college, ArrayList<Dates> times)
  {
    this.name = college;
    this.dates = times;
  }// School(String, ArrayList<Dates>)

  // +--------+----------------------------------------------------------
  // |Methods |
  // +--------+

  /**
   * Sets the initials of the college
   * @param _Initials, a String, the initials of the college
   */
  public void setInitials(String _Initials)
  {
    this.Initials = _Initials;
  }// setInitials(String)

  /**
   * Sets the dates field of the Object with the input times
   * @param times, an ArrayList<Dates> object
   */
  public void setDates(ArrayList<Dates> times)
  {
    this.dates = times;
  }// setDates(ArrayList<Dates)

  /**
   * Prints the School
   * @param pen, a PrintWriter
   */
  public void print(PrintWriter pen)
  {
    pen.println("College Name : " + this.name + " " + this.Initials);
    for (int i = 0; i < this.dates.size(); i++)
      {
        pen.println("Date = " + this.dates.get(i).toString());
      }// for, all the dates
    for (int i = 0; i < 10; i++)
      {
        pen.print(" " + distances[i].schoolInitials);
        pen.println();
        pen.print(" " + distances[i].distance);
      }
    pen.println();
  }// print(PrintWriter)
}// Class School
