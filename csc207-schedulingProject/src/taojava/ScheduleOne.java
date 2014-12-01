package taojava;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Implements the Schedule Class
 * @author Ameer Shujjah
 * @author Tommy Pitcher
 * @author Yazan Kittaneh
 */
public class ScheduleOne
    implements Schedule
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+
  /**
   * An Array of School Objects to contain information
   * about all the colleges
   */
  ArrayList<School> colleges = new ArrayList<School>();

  // +---------------+---------------------------------------------------
  // |Helper Methods |
  // +---------------+

  /**
   * Prints the added dates of all the schools, as a test
   */
  public void printDates(PrintWriter pen)
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
            pen.print(tmp.dates.get(j));
            pen.print(" Priority = " + tmp.dates.get(j).priority);
            pen.println();
          }// for all the dates
        pen.println("-----------------------");
      } // for all the colleges
  }// printDates()

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
        if (tmp == '/')
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

    dates.add(new Dates(date[0], date[1], date[2], priority));
  } //date(String, int)

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
  public void setMatch(School school1, School school2, Dates date,
                       boolean location)
  {
    History history1 = new History(school2, location, date);
    History history2 = new History(school1, !location, date);

    school1.history.add(history1);
    school2.history.add(history2);
  } //setMatch(School school1, School school2, Dates date)

  /**
   * Uses the basic schedule and randomly permutes it until all 
   * the desired restrictions are fulfilled. 
   */
  public boolean setRestrictions()
  {
    // Check all the distance restrictions
    
    return false;     
  }// setRestrictions()

  /**
   * Shuffle an ArrayList using Java's in-built Collections
   * @param list
   */
  public <T> void shuffle(ArrayList<T> list)
  {
    Collections.shuffle(list);
  }// shuffle(ArrayList<T>)
  
  // Citation : http://stackoverflow.com/questions/18441846/how-sort-a-arraylist-in-java
  /**
   * Sort an ArrayList<History>
   * @param list, the ArrayList with the history objects
   */
  public void sort(ArrayList<History> list)
  {
    Collections.sort(list, new Comparator<History>()
                       {
                         // Anonymous Function
                         @Override
                         public int compare(History hist, History other)
                         {

                           return other.played.compareTo(hist.played);
                         }// compare(History, History)
                       }); 
  }// sort(ArrayList<History>, Comparator<History>)

  // +--------+----------------------------------------------------------
  // |Methods |
  // +--------+

  @Override
  /**
   * Adds information from the given input files to the objects.
   * @param schools, A string,the name of the file with colleges data
   * @param location, A string, the name of the file with the distances
   */
  public void schoolsInput(String schools, String location)
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    //An Array of Schools
    //A temp array of Dates
    ArrayList<Dates> dateTmp = new ArrayList<Dates>();
    //Using a Scanner to read the file
    FileInputStream fis = null;
    try
      {
        fis = new FileInputStream(schools);
      }//try
    catch (FileNotFoundException e)
      {
        e.printStackTrace();
      }//catch
    Scanner scanner = new Scanner(fis);

    //Initialize the necessary temp variables
    String College = "";
    String Initials = "";
    String line;
    int iterator = 0;
    School tmpCollege = null;
    while (scanner.hasNextLine())
      {
        line = scanner.nextLine();
        Scanner linebreaker;
        //Update the College Name
        if (line.contains("College:"))
          {
            College = line.substring(9);
            tmpCollege = new School(College);
          }// if College name
        if (line.contains("Initials:"))
          {
            Initials = line.substring(9);
            tmpCollege.setInitials(Initials);
          }// if College's Initials
        //Add all the dates
        else if (line.contains("Available:"))
          {
            //Break the line into another Scanner Object.
            linebreaker = new Scanner(line.substring(10));
            while (linebreaker.hasNext())
              {
                //Split the date into day, month and year and
                //add to the temporary dates array
                date(linebreaker.next(), 0, dateTmp);
              }//while, there are more dates
          } // else if, for available dates 
        else if (line.contains("Must:"))
          {
            linebreaker = new Scanner(line.substring(5));
            //System.out.println("Printing all Priority Dates:");
            while (linebreaker.hasNext())
              {
                //Split the date into it day, month and year
                //add to the temporary dates array
                date(linebreaker.next(), 1, dateTmp);
              }//while, there are more dates
          } // else if, for must dates
        else if (line.contains("Cant:"))
          {
            linebreaker = new Scanner(line.substring(5));
            //System.out.println("Printing all UnAvailable Dates:");
            while (linebreaker.hasNext())
              {
                //Split the date into it day, month and year
                //add to the temporary dates array
                date(linebreaker.next(), 2, dateTmp);
              }//while, there are more dates
          } // else if, for all the unavailable dates 
        else if (line.compareTo("//End") == 0)
          {
            //Add the dates to the School Object
            tmpCollege.setDates((ArrayList<Dates>) dateTmp.clone());
            colleges.add(tmpCollege);
            dateTmp.clear();
          }// else if, the end of a College
      }// while, there are unseen lines in the data 
    scanner.close();

    //Now scan the file with the distances
    FileInputStream fil = null;
    try
      {
        fil = new FileInputStream(location);
      }//try
    catch (FileNotFoundException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }//catch
    scanner = new Scanner(fil);

    String[] collegeInitials = new String[10];
    //get all the initials and store them in an array
    scanner.nextLine();
    line = scanner.nextLine();
    iterator = 0;
    Scanner linebreaker = new Scanner(line);

    while (linebreaker.hasNext())
      {
        collegeInitials[iterator] = linebreaker.next();
        iterator++;
      }// while, there are more initials

    //reset the iterator and create a new one
    iterator = 0;
    int secondIterator;
    //loop over all the other lines
    while (scanner.hasNextLine())
      {
        secondIterator = 0;
        linebreaker = new Scanner(scanner.nextLine().substring(4));
        //for each college, add all the distances with the corresponding
        //college initials
        while (linebreaker.hasNext())
          {
            colleges.get(iterator).distances[secondIterator] =
                new Location(Integer.parseInt(linebreaker.next()),
                             collegeInitials[secondIterator]);
            secondIterator++;
          }// while, there are more distances in the line
        iterator++;
      }// while, there are more lines

    scanner.close();
  }// fileInput(String, String)

  @Override
  /**
   * Using the algorithm, creates the schedule
   */
  public void algorithm()
  {
    //Initialize Variables
    int teamsSize = 9;
    int halfSize = 5;
    Random rand = new Random();
    int random = rand.nextInt(10);
    School first = colleges.get(random);
    colleges.remove(random);
    boolean home = true;
    int checkDate = 0;
    int checkOtherDate = 0;
    School collegeOne;
    School collegeTwo = null;
    Dates tmpDate = null;
    boolean check = true;
    int len;
    int len2;
    shuffle(colleges);
    // For all the 18 days
    for (int day = 0; day < 18; day++)
      {
        // For the second round of double round robin
        if (day == 9)
          home = false;

        System.out.println("Day {" + (day + 1) + "}");

        int teamIndex = day % teamsSize;
        collegeTwo = colleges.get(teamIndex);
        // For the pivot team
        System.out.println(collegeTwo.Initials + " vs " + first.Initials);

        len = first.dates.size();
        for (checkDate = 0; checkDate < len && check != false; checkDate++)
          {
            tmpDate = first.dates.get(checkDate);
            len2 = collegeTwo.dates.size();
            for (checkOtherDate = 0; checkOtherDate < len2; checkOtherDate++)
              {
                if (collegeTwo.dates.get(checkOtherDate).equals(tmpDate))
                  {
                    check = false;
                    break;
                  }// if dates match;
              } // for all the dates in the other school
          }// for all the dates in school 1
        checkDate--;
        //System.out.println("CheckDate = " + checkDate + "CheckOtherDate = "
        //                 + checkOtherDate);
        if (check == false)
          {
            setMatch(first, collegeTwo, tmpDate, home);
            collegeTwo.dates.remove(checkOtherDate);
            first.dates.remove(checkDate);
            check = true;
          } //if, we found common dates
        else
          {
            //Dates do not Match
            System.out.println("Dates do not Match");
          } //else, no common dates found

        //For the rest of the teams
        for (int idx = 1; idx < halfSize; idx++)
          {
            int firstTeam = (day + idx) % teamsSize;
            int secondTeam = (day + teamsSize - idx) % teamsSize;
            // System.out.println("firstTeam Index" + firstTeam + " " + "secondTeam Index" + secondTeam);
            System.out.println(colleges.get(firstTeam).Initials + " vs "
                               + colleges.get(secondTeam).Initials);

            collegeOne = colleges.get(firstTeam);
            collegeTwo = colleges.get(secondTeam);
            len = collegeOne.dates.size();
            for (checkDate = 0; checkDate < len && check != false; checkDate++)
              {
                tmpDate = collegeOne.dates.get(checkDate);
                len2 = collegeTwo.dates.size();
                for (checkOtherDate = 0; checkOtherDate < len2; checkOtherDate++)
                  {
                    if (collegeTwo.dates.get(checkOtherDate).equals(tmpDate))
                      {
                        check = false;
                        break;
                      }// if dates match;
                  } // for all the dates in the other school  
              }// for all the dates in school 1
            checkDate--;
            if (check == false)
              {
                setMatch(collegeOne, collegeTwo, tmpDate, home);
                collegeTwo.dates.remove(checkOtherDate);
                collegeOne.dates.remove(checkDate);
                check = true;
              } //if, we found common dates
            else
              {
                System.out.println("Dates do not Match-for Second Half");
              } //else, no common dates found
          }// for each round
      }// for, double round robin
    colleges.add(first);
  }// algorithm()

  @Override
  /**
   * Outputs the schedule by writing it to a file
   * @param output, the name of the output file
   */
  public void output(String output)
  {
    // The Output Function is going to loop over all the schools stored in the field colleges.
    // It is going to loop over each schools history and print the opponent schools name, date and 
    // venue.
    // It is going to use writer to write the above objects into a file with the given input name.

    /* A general outline of the code is given below
        //Initialize the Reader
        //Create/Open the file
        int len;
        for (int i = 0; i < 10; i++)
          {
          len = colleges[i].history.size();
            for (int j = 0; j < len; j++)
              {
                //Write the stuff to the file
              }// for all the history objects
          }// for all the colleges
    */

    /*
    School             Date         Location
    ___________________________________________________
    School 3(S3)       day          place
    School 5(S5)       day          place
    School 2(S2)       day          place
    School 7(S3)       day          place
    ___________________________________________________
    ...
    */
    School tmp;
    PrintWriter pen = new PrintWriter(System.out, true);

    //File file = new File("example.txt");
    //PrintWriter pen = new PrintWriter(new FileWriter(file));

    for (int i = 0; i < colleges.size(); i++) //for every school
      {
        tmp = colleges.get(i);
        String home;
        //print header
        pen.printf("%20s%40s%15s\n", tmp.name, "Date", "Location");
        pen.println();
        pen.println("Total Matches = " + tmp.history.size());
        sort(tmp.history);
        for (History hist : tmp.history)
          {
            if (hist.home == true)
              home = "home";
            else
              home = "away";
            pen.printf("%20s%40s%15s", hist.opponent.name, hist.played, home);
            pen.println();
          }// all the items in History
        pen.println();
        pen.println();
        pen.println();
        pen.println();
      }// for every school
    pen.close();

  }// output()

  public static void main(String[] args)
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    ScheduleOne test = new ScheduleOne();
    test.schoolsInput("data.txt", "distance.txt");
    //test.printDates(pen);
    test.algorithm();
    test.output("Doesn't Matter");

  }// main

}// Class ScheduleOne
