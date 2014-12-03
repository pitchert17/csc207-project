package taojava;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Implements all the helper functions for the algorithm
 * @author Ameer Shujjah
 * @author Tommy Pitcher
 * @author Yazan Kittaneh
 */
public class Helper
{

  // +-----------------+---------------------------------------------------
  // |Printing Methods |
  // +-----------------+

  /**
   * Prints the Location Array of all the Colleges
   * @param pen, a PrintWriter
   */
  public static void printInitials(PrintWriter pen, ArrayList<School> colleges)
  {

    for (School college : colleges)
      {
        pen.println("College Name = " + college.name + "| Initials "
                    + college.Initials);
        for (int i = 0; i < 10; i++)
          {
            pen.print(college.distances[i].schoolInitials + " ");
          }// all the Location objects initials
        pen.println();
        for (int i = 0; i < 10; i++)
          {
            pen.print(college.distances[i].distance + " ");
          }// all the Location objects, distances
        pen.println();
      }// for all the colleges
  }// printInitials(PrintWriter)

  /**
   * Prints the added dates of all the schools, as a test
   */
  public static void printDates(PrintWriter pen, ArrayList<School> colleges)
  {
    School tmp;
    int size;
    for (int i = 0; i < colleges.size(); i++)
      {
        pen.println("-----------------------");
        tmp = colleges.get(i);
        pen.println(tmp.name);
        size = tmp.dates.size();
        pen.println("Total Dates =" + size);
        for (int j = 0; j < size; j++)
          {
            if (!tmp.dates.get(j).used)
              {
                pen.print(tmp.dates.get(j));
                pen.print(" Priority = " + tmp.dates.get(j).priority);
                pen.print(" Day " + tmp.dates.get(j).day);
                pen.println();
              }// if, date not used
          }// for all the dates
        pen.println("-----------------------");
      } // for all the colleges
  }// printDates()

  // +-----------------+---------------------------------------------------
  // |Algorithm Methods|
  // +-----------------+

  //Citation : http://stackoverflow.com/questions/26471421/round-robin-algorithm-implementation-java
  /**
   * Using an algorithm based on cyclic permutation to create a schedule
   */
  public static void algorithm(ArrayList<School> colleges)
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    //Initialize Variables
    sortDates(colleges);
    int teamsSize = colleges.size() - 1;
    int halfSize = colleges.size() / 2;
    Random rand = new Random();
    int random = rand.nextInt(colleges.size());
    School first = colleges.get(random);
    colleges.remove(random);
    boolean home = true;
    School collegeOne;
    School collegeTwo = null;

    shuffle(colleges);
    // For all the 18 days
    for (int day = 0; day < teamsSize * 2; day++)
      {
        // For the second round of double round robin
        if (day == teamsSize)
          home = false;

        // System.out.println("Day {" + (day + 1) + "}");

        int teamIndex = day % teamsSize;
        collegeTwo = colleges.get(teamIndex);
        // For the pivot team
        setMatches(first, collegeTwo, home);

        //For the rest of the teams
        for (int idx = 1; idx < halfSize; idx++)
          {
            int firstTeam = (day + idx) % teamsSize;
            int secondTeam = (day + teamsSize - idx) % teamsSize;
            collegeOne = colleges.get(firstTeam);
            collegeTwo = colleges.get(secondTeam);
            setMatches(collegeOne, collegeTwo, home);
          }// for each round
      }// for, double round robin
    colleges.add(first);
    // For Knox and Lawrence, change 22nd Nov to 10th Dec if possible
    changeDates(colleges);
  }// algorithm()

  /**
  * Checks whether the two dates are equal
  * @param dateOne, the first Date
  * @param dateTwo, the second Date
  * @return true, if the dates are equal, false otherwise
  */
  public static boolean checkDatesEqual(Dates dateOne, Dates dateTwo)
  {
    if (dateOne.equals(dateTwo) && !dateOne.used)
      return true;
    return false;
  }// checkDatesEqual(Dates, Dates)

  /**
   * Check whether the Dates ArrayList has any Weekday dates 
   * @param array, the ArrayList<Dates>
   * @return true, if there are Tue/Wed dates, else false
   */
  public static boolean hasTueWed(ArrayList<Dates> array,
                                  ArrayList<Dates> arrayTwo)
  {
    for (Dates date : array)
      {
        for (Dates dateTwo : arrayTwo)
          {
            if (date.equals(dateTwo) && (date.day == 2 || date.day == 3))
              return true;
          }// for, all the dates in the second array
      }// for, all the dates
    return false;
  }// hasTueWed()

  /**
   * Check whether the Dates ArrayList has any Weekend dates 
   * @param array, the ArrayList<Dates>
   * @return true, if there are Tue/Wed dates, else false
   */
  public static boolean hasFriSat(ArrayList<Dates> array,
                                  ArrayList<Dates> arrayTwo)
  {
    for (Dates date : array)
      {
        for (Dates dateTwo : arrayTwo)
          {
            if (date.equals(dateTwo) && (date.day == 5 || date.day == 6))
              return true;
          }// for, all the dates in the second array
      }// for, all the dates
    return false;
  }// hasFriSat()

  /**
   * Set up a match on a specified date for the two schools
   * 
   * @param school1
   *    First school
   * @param school2
   *    Second school
   * @param date
   *    Date we want to set the match for
   */
  public static void setMatch(School school1, School school2, Dates date,
                              boolean location)
  {
    History history1 = new History(school2, location, date);
    History history2 = new History(school1, !location, date);

    school1.history.add(history1);
    school2.history.add(history2);
  } //setMatch(School school1, School school2, Dates date)

  /**
   * Given a matchup between collegeOne and collegeTwo, it searches
   * through the dates to find a common date and sets the match
   * @param collegeOne, first college
   * @param collegeTwo, second college
   */
  public static void setMatches(School collegeOne, School collegeTwo,
                                boolean home)
  {
    // Declarations
    PrintWriter pen = new PrintWriter(System.out, true);
    int checkDate = 0;
    int checkOtherDate = 0;
    Dates tmpDate = null;
    boolean check = true;
    //pen.println(collegeOne.Initials + " vs " + collegeTwo.Initials);

    // Get the Distance between the two colleges
    int distance = getDistance(collegeOne, collegeTwo);
    //pen.println("Distance =" + distance);

    // Get the lengths of the two Dates arrays
    int len = collegeOne.dates.size();
    int len2 = collegeTwo.dates.size();
    for (checkDate = 0; checkDate < len && check != false; checkDate++)
      {
        tmpDate = collegeOne.dates.get(checkDate);
        for (checkOtherDate = 0; checkOtherDate < len2; checkOtherDate++)
          {
            if (checkDatesEqual(collegeTwo.dates.get(checkOtherDate), tmpDate))
              {
                check = false;
                break;
              }// if, dates match;
          } // for all the dates in the other school  
      }// for all the dates in school 1
    checkDate--;
    if (check == false)
      {
        setMatch(collegeOne, collegeTwo, tmpDate, home);
        collegeTwo.dates.get(checkOtherDate).isUsed();
        collegeOne.dates.get(checkDate).isUsed();
        check = true;
      } //if, we found common dates, set the match and change the status of the date
    else
      {
        //pen.println("No Common Dates Found");
        // No match-up found
      } //else, no common dates found
  }// setMatches(School, School)

  /**
  * Reset all the dates used field to false, and empty
  * the history ArrayList so we can run the algorithm again;
  */
  public static void resetDates(ArrayList<School> colleges)
  {
    for (School college : colleges)
      {
        for (Dates date : college.dates)
          {
            date.resetUsed();
          }// for all the dates
        college.history.clear();
      }// for all the colleges
  }// resetDates

  /**
   * Get the distance between the two schools.
   * @param college, school 1
   * @param opponent, school 2
   * @return an int, the distance between the two schools
   */
  public static int getDistance(School college, School opponent)
  {
    int len = college.distances.length;
    int i;
    for (i = 0; i < len; i++)
      {
        if (college.distances[i].schoolInitials.equals(opponent.Initials))
          break;
      }// for all the Location Objects
    return college.distances[i].distance;
  }// getDistance(School, School)

  /**
   * For Knox and Lawrence, change 22nd Nov if possible to 10th Dec
   * @param colleges, ArrayList of all the schools
   */
  public static void changeDates(ArrayList<School> colleges)
  {
    Dates tmp = new Dates(22, 11, 2016, 1);
    for (School college : colleges)
      {
        if (college.Initials.equals("KC") || college.Initials.equals("LU"))
          {
            for (History hist : college.history)
              {
                if (hist.played.date == 22
                    && hist.played.month == 11
                    && (!hist.opponent.Initials.equals("LFC")
                        || !hist.opponent.Initials.equals("MC") || !hist.opponent.Initials.equals("BC")))
                  {
                    hist.played.date = 10;
                    hist.played.month = 12;
                  }// if, it is playing some team on the 22nd other than Lake Forest
                // Monmouth or Beloit
              }//for, all the history objects
          }// if Knox or Lawrence
      }//for, all the colleges 
  }// changeDates(ArrayList<School> colleges)

  // +-------------------+-------------------------------------------------
  // |Restriction Methods|
  // +-------------------+

  /**
   * Check if no schools are playing on restricted dates 
   * @param colleges, for all the schools
   * @return true, if no school is playing on the restricted dates, false otherwise
   */
  public static boolean checkRestrictedDates(ArrayList<School> colleges)
  {
    boolean works = true;
    for (School college : colleges)
      {
        if (college.Initials.equals("LFC") || college.Initials.equals("MC")
            || college.Initials.equals("BC"))
          {
            for (History hist : college.history)
              {
                // If playing on 10th Dec, finals week for them
                if (hist.played.month == 12 && hist.played.day == 10)
                  {
                    System.out.println("WTFFF10thDec");
                    hist.works = false;
                    works = false;
                  }// if,
              }// for all the history objects
          }// if, Lake Forest, Monmouth or Beloit
        else if (college.Initials.equals("KC") || college.Initials.equals("LU"))
          {
            for (History hist : college.history)
              {
                // If playing on 22nd Nov, finals week for them
                if (hist.played.month == 11 && hist.played.date == 22)
                  {
                    System.out.println("WTFFF22ndNov");
                    System.out.println(hist.played);
                    hist.works = false;
                    works = false;
                  }// if,
              }// for all the history objects
          }// if, Knox or Lawrence
      }// for all the colleges
    return works;
  }// checkMustPlay(ArrayList<School)

  /**
   * Check for each school if the distance restrictions are fulfilled.
   * @param college, the input School
   * @return true, if all fulfilled, false if not.
   */
  public static boolean checkDistance(School college)
  {
    boolean works = true;
    for (History hist : college.history)
      {
        Dates date = hist.played;
        School opponent = hist.opponent;
        int day = date.day;
        if (day == 2 || day == 3)
          {
            if (getDistance(college, opponent) > 270)
              {
                hist.works = false;
                works = false;
              }// if, traveling more than 270 miles
          }// if it is a weekday, check the distance
      }// for all the history objects
    return works;
  }// checkDistance

  /**
   * Checks if a team plays a home back-to-back
   * and a away back-to-back
   * 
   * @param school
   * @return
   *    true: Team has played both home and away back-to-backs
   *    false: Team has not played one or both home and away back-to-backs
   */
  public static boolean checkBacktoBack(School school)
  {
    //Declarations
    boolean works = true;
    int iter = 0;
    boolean location;
    //Declarations
    sort(school.history);
    History tmp;
    int len = school.history.size();
    sort(school.history);
    for (iter = 0; iter < len; iter++)
      {
        tmp = school.history.get(iter);
        if (tmp.played.day == 5)
          {
            location = tmp.home;
            if (school.history.get(iter + 1).home != location
                || (getDistance(school.history.get(iter).opponent,
                                school.history.get(iter + 1).opponent) > 180))
              {
                school.history.get(iter).works = false;
                school.history.get(iter + 1).works = false;
                works = false;
              }// if location and distance are not compatible
          }// if it is a friday
      }// for all the history objects

    return works;
  } // checkBacktoBack(School school)

  /**
   * Uses the basic schedule and checks whether all the
   * restrictions are fulfilled.
   */
  public static boolean checkRestrictions(ArrayList<School> colleges)
  {
    boolean works = true;
    if (!checkRestrictedDates(colleges))
      works = false;
    for (School college : colleges)
      {
        if (!checkBacktoBack(college) || !checkDistance(college))
          {
            works = false;
          }// if,
      }// For all the colleges, check the restrictions
    return works;
  }// setRestrictions()

  // +--------------+------------------------------------------------------
  // |Other Methods |
  // +--------------+

  /**
   * Converts a date string into its components and adds it to the ArrayList
   * @param str, a String, the date
   * @param priority, the priority of the date
   * @param dates, An ArrayList<Dates> to add the date to
   */
  public static void date(String str, int priority, ArrayList<Dates> dates)
  {
    // Initialize variables to split the string into day,month and year
    int[] date = new int[3];
    char tmp;
    String buffer = "";
    int iterator = 0;
    for (int i = 0; i < str.length(); i++)
      {
        tmp = str.charAt(i);
        if (tmp == '-')
          {
            date[iterator] = Integer.parseInt(buffer);
            buffer = "";
            iterator++;
          } // if, a '/' then empty the buffer
        else if (iterator == 2)
          {
            date[iterator] = Integer.parseInt(str.substring(i));
            break;
          }// else if, add the year
        else
          {
            buffer += tmp;
          } // else, add to the buffer
      }// for, all the characters in the string

    dates.add(new Dates(date[2], date[1], date[0], priority));
  } //date(String, int)

  /**
   * Shuffle an ArrayList using Java's in-built Collections
   * @param list
   */
  public static <T> void shuffle(ArrayList<T> list)
  {
    Collections.shuffle(list);
  }// shuffle(ArrayList<T>)

  // Citation : http://stackoverflow.com/questions/18441846/how-sort-a-arraylist-in-java
  /**
   * Sort an ArrayList<History>
   * @param list, the ArrayList with the history objects
   */
  public static void sort(ArrayList<History> list)
  {
    Collections.sort(list, new Comparator<History>()
                       {
                         @Override
                         public int compare(History hist, History other)
                         {

                           return other.played.compareTo(hist.played);
                         }// compare(History, History)
                       });
  }// sort(ArrayList<History>, Comparator<History>)

  /**
   * Sorts all the dates in all the schools
   */
  public static void sortDates(ArrayList<School> colleges)
  {
    for (School college : colleges)
      {
        Collections.sort(college.dates, new Comparator<Dates>()
                           {
                             @Override
                             public int compare(Dates o1, Dates o2)
                             {
                               return o1.compareTo(o2);
                             }
                           });
      }// for all the colleges
  }// sortDates()
}// Class Helper
