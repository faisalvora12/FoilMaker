/**
 * Created by faisa on 11/25/2016.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Model {
     BufferedReader in = null;
    Scanner inn=null;
    BufferedWriter out = null;
    int count=1;
    static ArrayList<User> participants = new ArrayList<User>();
    static ArrayList<User> leader = new ArrayList<User>();
    static HashMap<String, String> map = new HashMap<String, String>();
    public void modelUpdateScoreAfterRound(String username,String password,int cumulativeScore,int fooled,int fooledBy)  {
        try {
            String userDetails="";
            for(String key: map.keySet())
            {
                map.put(username,username+":"+password+":"+cumulativeScore+":"+fooled+":"+fooledBy);
                userDetails+=((String)map.get(key))+"\n";
            }
            out = new BufferedWriter(new FileWriter(new File("UserDatabase")));

            out.write(userDetails);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void modelRegister(String username,String password)  {
        try {

            out = new BufferedWriter(new FileWriter(new File("UserDatabase"),true));
            out.write(username+":"+password+":"+0+":"+0+":"+0+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put(username, username+":"+password+":"+0+":"+0+":"+0);
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getQuestion() throws IOException
    {
        try {
            // File reader
            inn=new Scanner(new FileInputStream(new File("WordleDeck")));
            in = new BufferedReader(new FileReader(new File("WordleDeck")));
            String line="" ;
            for(int i=0;i<count;i++) {
                if((line=in.readLine())==null)
                    break;
            }
            count++;
            if(line==null)
                return "";
            else
            return line;
        }
        catch (IOException e)
        {
            System.out.println("IOException");
            return "";
        }
        finally {
            // Finalize
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

    }
    public void internalDataStructure() throws IOException
    {

        try {
            // File reader
            in = new BufferedReader(new FileReader(new File("UserDatabase")));
            String line;
            String split[];
            while((line=in.readLine())!=null)
            {
                split=line.split(":");
                map.put(split[0], line);
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException");
        }
        finally {
            // Finalize
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
