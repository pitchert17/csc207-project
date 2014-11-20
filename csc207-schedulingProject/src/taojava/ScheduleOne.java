package taojava;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
  School[] colleges = new School[10];

  // +---------------+---------------------------------------------------
  // |Helper Methods |
  // +---------------+

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
    while (scanner.hasNextLine())
      {
        line = scanner.nextLine();
        Scanner linebreaker;
        //Update the College Name
        if (line.contains("College:"))
          {
            College = line.substring(9);
            colleges[iterator] = new School(College);
          }// if College name
        if (line.contains("Initials:"))
          {
            Initials = line.substring(9);
            colleges[iterator].setInitials(Initials);
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
            colleges[iterator].setDates((ArrayList<Dates>) dateTmp.clone());
            dateTmp.clear();
            iterator++;
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
            colleges[iterator].distances[secondIterator] =
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
    // stub
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
  }// output()
}// Class ScheduleOne
