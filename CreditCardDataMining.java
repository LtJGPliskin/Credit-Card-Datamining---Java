/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcarddatamining;
import java.io.*;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author boucher
 */
public class CreditCardDataMining {

    /**
     * @param args the command line arguments
     */
    static String[][] finalArray;
    static String[] track1;
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
    Scanner sc = new Scanner(System.in);
    System.out.println("Input File Directory like so: /Users/pulsargreymane/Desktop/memorydump.dmp");
    String i = sc.next();
    CreditCardDataMining a = new CreditCardDataMining();
    BufferedReader br = new BufferedReader(new FileReader(i));
    a.readingData(br);
    br.close();
    a.parsedData(track1);
    a.printData(finalArray);
    }
    
    
    public void readingData(BufferedReader br) throws FileNotFoundException, IOException
    {
        int num = -1; //holds the number of records
        int k = 0;    //holds the number of lines  
        int t = 0;    //used as a security check for two ^
        String[] accounts; //holds the accounts
        boolean record = false; //boolean to only record data that fits parameters
        String everything = ""; //holds everything read
        StringBuilder sb = new StringBuilder(); //used to build strings
    String line = br.readLine(); //used to read the string

    while (line != null) {      //appends the buffered file as string to a stringbuilder
        sb.append(line);
        sb.append(System.lineSeparator());
        line = br.readLine();
    }
    everything = sb.toString(); //moves the stringbuilder to a string
    track1 = everything.split("");//fills the array track1 with every single character
    
    
    for(int i = 0; i < track1.length; i++)//checks how many records will needed to be recorded
    {
        if("%".equals(track1[i])|| (";").equals(track1[i]))
        {
            if("B".equals(track1[i + 1]))
            {
                        k++;
            }
        }
    }
    
    accounts = new String[k];
    for(int i = 0; i < track1.length; i++)//records strings that start with ;B or %B
    {
        if("%".equals(track1[i]) || (";").equals(track1[i]))
        {
            if("B".equals(track1[i + 1]))
            {
                num++;
                record = true;
                accounts[num] = "";
            }
        }
        if(record == true)
        {
            accounts[num] +=  track1[i];
        }
        if("?".equals(track1[i]))
        {
            record = false;
        }
    }
    k = 0;
    for(int i = 0; i < accounts.length; i++)//nulls out selected files that don't meet the parameters "contains B and ^
    {
        t = 0;
        if(!accounts[i].contains("B"))
        {
           accounts[i] = "";
        }
        if(!accounts[i].contains("^"))
        {
            accounts[i] = "";
        }
        everything = accounts[i];
        track1 = everything.split("");
        for(int p = 0; p < track1.length; p++)//nulls out strings without exactly two ^
        {
            if("^".equals(track1[p]))
            {
                t++;
            }
        }
            if(t != 2)
            {
                accounts[i] = "";
            }
            else
            {
                k++;
            }
    }
        track1 = new String[k];             //resizes the array
    for(int i = 0; i < accounts.length; i++)//refills the array with approved strings
    {
        if(accounts[i].contains("B"))
        {
            track1[k-1] = accounts[i];
            k--;
        }
    }
   
        
    }
    public void printData(String[][] a) //prints the data
    {
        for(int i = 0; i < a.length; i++)
        {
            System.out.println("There is/are " + (a.length) + " credit card/s on record in the memory data");
            System.out.println("<Information of record " + (i + 1) + ">");
            System.out.println("Cardholder’s Name: " +  a[i][1]);
            System.out.println("Card Number:" + a[i][0]);
            System.out.println("Expiration Date: " + a[i][2]);
            System.out.println("CVC Number: " + a[i][3]);
            System.out.println();
        }
    }
    public void parsedData(String[] b)//determines the desired data out
    {
        finalArray = new String[b.length][4];
        String c;
        String d[];
        int t;
        boolean read = false;
        for(int i = 0; i < b.length; i++)
        {
        t = 0;
        read = false;
        c = b[i];
        if(c != null)
            {
        d = c.split("");
        for(int q = 0; q < d.length; q++)   //reads the account number
        {
           if(d[q].equals("%") || d[q].equals(";"))
           {
               read = true;
               finalArray[i][0] = "";
               q = q + 2;
           }
           if(d[q].equals("^"))
           {
               read = false;
           }
           if(read == true)
           {
               if((q - 2) % 4 == 0)
               {
                   finalArray[i][0] += " ";
               }
               finalArray[i][0] += d[q];
           }
        }
        
        for(int e = 0; e < d.length; e++)//reads the user name
        {
           if( t == 0)
           {
               finalArray[i][1] = "";
           }
           if(d[e].equals("^"))
           {
               t++;
               e++;
               read = true;
           }
           if(t == 2)
           {
               read = false;
           }
           if(d[e].equals("/"))
           {
               finalArray[i][1] += " ";
           }
           else if(read == true)
           {
               finalArray[i][1] += d[e];
           }
        }
        t = 0;
        for(int e = 0; e < d.length; e++)//reads the user pin and the expiration date
        {
            if(d[e].equals("^"))
           {
               t++;
               finalArray[i][2] = "";
               finalArray[i][3] = "";
               e++;
           }
            if(t == 2)
            {
            t++;
            for(int l = 0; l < 4; l++)
            {
               finalArray[i][2] += d[e];
               e++;
            }
            for(int l = 0; l < 3; l++)
            {
                finalArray[i][3] += d[e];
                e++;
            }
            }
        }
    }}
    for(int i = 0; i < b.length; i++)
    {
        c = finalArray[i][2];
        if(c != null)
        {
        d = c.split("");
        finalArray[i][2] = "";
        for(int y = 2; y < 4; y++)
        {
            finalArray[i][2] += d[y] + "";
        }
            finalArray[i][2] += "/20";
            for(int y = 0; y < 2; y++)
        {
            finalArray[i][2] += d[y] + "";
        }
    }}}
}
    