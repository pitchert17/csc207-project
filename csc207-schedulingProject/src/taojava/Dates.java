package taojava;

import java.util.Date;

//We found the formula to calculate the day from date at 
//Citation : https://uk.answers.yahoo.com/question/index?qid=20080930151026AAgSsXC
/**
 * Implements the Dates class
 * @author Ameer Shujjah
 * @author Tommy Pitcher
 * @author Yazan Kittaneh
 */
public class Dates
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+
  /**
   * The priority of the date, 0 is neutral, 1 is must, 2 is unavailable
   */
  int priority;

  /**
   * The date of the month
   */
  int date;

  /**
   * The month
   */
  int month;

  /**
   * The year
   */
  int year;

  /**
   * The day corresponding to that day,month and year
   */
  int day;

  /**
   * Names of all the months
   */
  String[] monthNames = new String[] { "January", "February", "March", "April",
                                      "May", "June", "July", "August",
                                      "September", "October", "November",
                                      "Decemeber" };
  /**
   * Weights of months to be used to calculate the day from the date
   */
  int[] months = new int[] { 0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5 };

  /**
   * The names of the days in a week
   */
  String[] dayNames = new String[] { "Sunday", "Monday", "Tuesday",
                                    "Wednesday", "Thursday", "Friday",
                                    "Saturday" };
  /**
   * A boolean which tells us if the date has been used.
   */
  boolean used = false;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+
  /**
   * Constructs a new Dates Objects
   * @param _date, the date of the month
   * @param _month, the month
   * @param _year, the last two digits of the month
   * @param _priority, the priority
   */
  public Dates(int _date, int _month, int _year, int _priority)
  {
    this.priority = _priority;
    this.date = _date;
    this.month = _month;
    this.year = _year;
    this.day =
        ((((((((this.year / 4) + (this.year % 7)) + 6) % 7) + months[this.month - 1]) % 7) + this.date) % 7);
  }// Dates(int, int, int ,int)

  /**
   * Constructs a new Dates Objects
   * @param _date, the date of the month
   * @param _month, the month
   * @param _year, the last two digits of the month
   */
  public Dates(int _date, int _month, int _year)
  {
    this.priority = 0;
    this.date = _date;
    this.month = _month;
    this.year = _year;
    this.day =
        ((((((((this.year / 4) + (this.year % 7)) + 6) % 7) + months[this.month - 1]) % 7) + this.date) % 7);
  }// Dates(int, int, int ,int)

  // +--------+----------------------------------------------------------
  // |Methods |
  // +--------+

  /**
   * Get the day corresponding to the date
   * @return a String, the name of the day
   */
  public String getDay()
  {
    System.out.println("Day = " + this.day);
    return dayNames[this.day];
  }// getDay()

  /**
   * Compares two dates
   * @param other, a dates object
   * @return -1 if other is less than the compared date,
   *  0 if it is equal and 1 if it is bigger
   */
  public int compareTo(Dates other)
  {
    if (other.year > this.year)
      {
        return 1;
      }// if, other year is bigger
    else if (other.year == this.year)
      {
        if (other.month > this.month)
          {
            return 1;
          } // if, other month is bigger
        else if (other.month == this.month)
          {
            if (other.date > this.date)
              {
                return 1;
              }// if, other date is bigger
            else if (other.date == this.date)
              {
                return 0;
              }// else if, other date equals date
            else
              {
                return -1;
              }// else, other date is smaller
          }// else if
        else
          {
            return -1;
          }// else, other month is smaller
      }// else if, other year is the same as year
    else
      {
        return -1;
      }// else, if other year is smaller
  }// compareTo(Dates)

  /**
   * Change the used field to true.
   */
  public void isUsed()
  {
    this.used = true;
  }// 

  /**
   * Change the used field to false;
   */
  public void resetUsed()
  {
    this.used = false;
  }// resetUsed()

  /**
   * Converts the dates object to a String
   * @return a String,
   */
  public String toString()
  {
    return dayNames[this.day] + " " + this.date + " "
           + this.monthNames[this.month - 1] + " " + (this.year + 2000);
  }// toString()

  public boolean equals(Dates other)
  {
    if (this.date == other.date && this.month == other.month
        && this.year == other.year && this.priority == other.priority
        && this.used == other.used)
      return true;
    return false;
  }// equals(Dates)

}// Class Dates
