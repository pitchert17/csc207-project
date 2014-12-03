package taojava;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
  static ArrayList<School> colleges = new ArrayList<School>();

  /**
   * The total number of matches played
   */
  int totalMatches;

  // +--------+----------------------------------------------------------
  // |Methods |
  // +--------+

  @SuppressWarnings("unchecked")
  @Override
  /**
   * Adds information from the given input files to the objects.
   * @param schools, A string,the name of the file with colleges data
   * @param location, A string, the name of the file with the distances
   */
  public void schoolsInput(String schools, String location)
  {
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

    //Initialize the necessary temporary variables
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
            Initials = line.substring(10);
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
                Helper.date(linebreaker.next(), 0, dateTmp);
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
                Helper.date(linebreaker.next(), 1, dateTmp);
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
                Helper.date(linebreaker.next(), 2, dateTmp);
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
    @SuppressWarnings("resource")
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
   * Generate the schedule
   */
  public void generateSchedule()
  {
    // Run it once
    boolean work = false;
    Helper.resetDates(colleges);
    Helper.algorithm(colleges);

    for (int i = 0; i < 1000; i++)
      {
        if (Helper.checkRestrictions(colleges))
          {
            work = true;
            break;
          }// the algorithm fulfills the restrictions
        else
          {
            Helper.resetDates(colleges);
            Helper.algorithm(colleges);
          }// the algorithm fails the restrictions
      }
    if (Helper.checkRestrictions(colleges))
      {
        work = true;
      }// the algorithm fulfills the restrictions
    if (!work)
      {
        System.out.println("Doesn't Work");
      }// if, it does not work
    else
      {
        System.out.println("It works!");
      }// it works
  }// generateSchedule

  @Override
  /**
   * Outputs the schedule by writing it to a file
   * @param output, the name of the output file
   */
  public void output(String output)
  {
    // Declarations
    School tmp;
    PrintWriter writer = null;
    try
      {
        writer = new PrintWriter(output, "UTF-8");
      }// try
    catch (FileNotFoundException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }// catch
    catch (UnsupportedEncodingException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }// catch

    for (int i = 0; i < colleges.size(); i++) //for every school
      {
        tmp = colleges.get(i);
        String home;
        //write header
        writer.printf("%20s%40s%15s%15s\n", tmp.name, "Date", "Location",
                      "works");
        writer.println();
        writer.println("Total Matches = " + tmp.history.size());
        Helper.sort(tmp.history);
        String spacer = "";
        for (History hist : tmp.history)
          {
            // Using boolean to write home or away
            if (hist.home == true)
              home = "home";
            else
              home = "away";
            if (!hist.works)
              spacer = "InCompatible";
            else
              spacer = "Compatible";
            writer.printf("%20s%40s%15s%15s", hist.opponent.name, hist.played,
                          home, spacer);
            writer.println();
          }// all the items in History
        writer.println();
        writer.println();
        writer.println();
        writer.println();
      }// for every school
    writer.close();
  }// output()

  public static void main(String[] args)
  {
    ScheduleOne test = new ScheduleOne();
    test.schoolsInput("data.txt", "distance.txt");
    test.generateSchedule();
    //Helper.printDates(pen, colleges);
    test.output("Output.txt");
  }// main
}// Class ScheduleOne
