package taojava;

import java.util.Date;
/**
 * Implements the Dates class
 * @author pitchert17
 * @author 
 *
 */
public class Dates
{

  int priority;

  int date;

  int month;

  int year;

  int day;

  int[] months = new int[] { 0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5 };

  public Dates(int _date, int _month, int _year, int _priority)
  {
    this.priority = _priority;
    this.date = _date;
    this.month = _month;
    this.year = _year;
    this.day =
        ((((((((this.year / 4) + (this.year % 7)) + 6) % 7) + months[this.month - 1]) % 7) + this.date) % 7);
  }// Dates(int, int, int ,int)

  public int getDay()
  {
    System.out.println("Day = " + this.day);
    return this.day;
  }// getDay()

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

}// Class Dates
