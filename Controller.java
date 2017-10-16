/*
* CS180 Project 4
*
* Create the server for FoilMaker
*
*@author Faisal Vora,voraf@purdue.edu,B09
*
* @version 11/25/2016.
*
**/
import org.apache.commons.lang.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller extends Thread{
    static boolean loginStatus;
    static boolean playingStatus;
    static boolean isPlaying;
    static int numberOfPlayers=0;
    PrintWriter pw;
    InputStreamReader isr;
    BufferedReader in;
    Socket socket;
   // Controller c=new Controller();
    Model model;
    User user;
    String loginToken;
   static String gameToken;
    int oppFooled;
    int oppScore;
    String username;
    boolean leader=false;
    String answer;
    static ArrayList<String> playerSuggestions= new ArrayList<String>();
    static  ArrayList<String> playerChoices= new ArrayList<String>();


    public Controller(Socket socket){
        this.socket=socket;
        playingStatus=false;
        loginStatus=false;

    }

    /*register method check if the username and password are valid and registers the user*/
    public void register(String clientMessageRegister)
    {
        String splitMessage[]=clientMessageRegister.split("--");
        boolean flagUpperCase=false;
        boolean flagDigit=false;
        char c;
        for(int i=0;i<splitMessage[2].length();i++)
        {
            if(Character.isDigit(splitMessage[2].charAt(i)))
                flagDigit=true;
            c=splitMessage[2].charAt(i);
            if(c>=65&&c<=90)
                flagUpperCase=true;
        }
        if(splitMessage.length<3)
            pw.println("RESPONSE--CREATENEWUSER--INVALIDMESSAGEFORMAT");
        else if(splitMessage[1].equals("")||splitMessage[1].length()>10||!(splitMessage[1].contains("_"))&&(!StringUtils.isAlphanumeric(splitMessage[1])))
            pw.println("RESPONSE--CREATENEWUSER--INVALIDUSERNAME");
        else if(splitMessage[2].equals("")||splitMessage[2].length()>10||!(splitMessage[2].contains("*"))&&!(splitMessage[2].contains("&"))&&!(splitMessage[2].contains("$"))&&!(splitMessage[2].contains("#"))&&(!StringUtils.isAlphanumeric(splitMessage[2])))
            pw.println("RESPONSE--CREATENEWUSER--INVALIDUSERPASSWORD");
        else if(flagDigit==false||flagUpperCase==false)
            pw.println("RESPONSE--CREATENEWUSER--INVALIDUSERPASSWORD");
        else
        {
            boolean flagUserPresent=false;
            for(String key: model.map.keySet())
            {
                if(key.equals(splitMessage[1]))
                    flagUserPresent=true;
            }
            if(flagUserPresent==true)
                pw.println("RESPONSE--CREATENEWUSER--USERALREADYEXISTS");
            else
            {
                pw.println("RESPONSE--CREATENEWUSER--SUCCESS");
                model.modelRegister(splitMessage[1],splitMessage[2]);

            }

        }
    }

    /*login method checks if the username and password are present in the userdatabase and logs in the user*/
    public void login(String clientMessageLogin)
    {
        String splitMessage[]=clientMessageLogin.split("--");

        boolean flagUserPresent=false;
        boolean flagPasswordPresent=false;
        String userDetails[];
        for(String key: model.map.keySet())
        {
            userDetails=model.map.get(key).split(":");
            if(key.equals(splitMessage[1]))
                flagUserPresent=true;
            if(userDetails[1].equals(splitMessage[2]))
                flagPasswordPresent=true;
        }
        if(splitMessage.length<3)
            pw.println("RESPONSE--LOGIN--INVALIDMESSAGEFORMAT");
        else if(flagUserPresent==false)
            pw.println("RESPONSE--LOGIN--UNKNOWNUSER");
        else if(flagPasswordPresent==false)
            pw.println("RESPONSE--LOGIN--INVALIDUSERPASSWORD");
        else if(loginStatus==true)
            pw.println("RESPONSE--LOGIN--USERALREADYLOGGEDIN");
        else
        {
            user.setUsername(splitMessage[1]);
            user.setPassword(splitMessage[2]);
            username=splitMessage[1];
            loginToken=this.generateLoginToken10();
            pw.println("RESPONSE--LOGIN--SUCCESS--"+loginToken);
        }


    }
    /*generateLoginToken10 method generates a random token which is of length 10*/
    public String generateLoginToken10()
    {
        Random rand = new Random();
        int randomNum =0;
        String token="";
        for (int i=0;i<10;i++){
            randomNum= 1 + rand.nextInt((2 - 1) + 1);
            if(randomNum==1)
                randomNum= 65 + rand.nextInt((90 - 65) + 1);
            else
                randomNum= 97 + rand.nextInt((122 - 97) + 1);

            token=token+(char)randomNum;
        }
        return token;
    }
    /*generateLoginToken3 method generates a random token which is of length 3*/
    public String generateGameToken3()
    {
        Random rand = new Random();
        int randomNum =0;
        String token="";
        for (int i=0;i<3;i++){
            randomNum= 97 + rand.nextInt((122 - 97) + 1);
            token=token+(char)randomNum;

        }
        return token;
    }
    /*getAndSendQuestion method gets the question form wordle deck and sends it to the user if present*/
    public void getAndSendQuestion()throws IOException
    {
        String questionAnswer=model.getQuestion();

        if(questionAnswer==""){
            model.modelUpdateScoreAfterRound(user.getUsername(),user.getPassword(),user.getCumulativeScore(),user.getFooled(),user.getFooledBy());
            pw.println("GAMEOVER");}
        else {
            String question = questionAnswer.substring(0, questionAnswer.indexOf(":"));
            String answer1 = questionAnswer.substring(questionAnswer.indexOf(":") + 1);
            answer=answer1;
            pw.println("NEWGAMEWORD--" + question + "--" + answer);
        }
    }
    /*startGame method checks if the user is ready to start the game and actually starts the game*/
    public void startNewGame(String clientMessageStartNewGame)
    {
        String splitMessage[]=clientMessageStartNewGame.split("--");
        if(!splitMessage[1].equals(loginToken))
            pw.println("RESPONSE--STARTNEWGAME--USERNOTLOGGEDIN");
        else if(playingStatus==true)
            pw.println("RESPONSE--STARTNEWGAME--USERNOTLOGGEDIN");
        else
        {
            gameToken=generateGameToken3();
            pw.println("RESPONSE--STARTNEWGAME--SUCCESS--"+gameToken);
            numberOfPlayers=1;
            model.leader.add(user);
            leader=true;
            while(true)
            {
                for(User user2:model.participants)
                {
                    pw.println("NEWPARTICIPANT--"+user2.getUsername()+"--"+user.getCumulativeScore());
                    return;

                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }
    /*joinGame method checks if the user is ready to join the game and informs the leader when a person joins the game*/
    public void joinGame(String clientMessageJoinGame)  {
        String splitMessage[]=clientMessageJoinGame.split("--");
        if(!splitMessage[1].equals(loginToken))
            pw.println("RESPONSE--JOINGAME--USERNOTLOGGEDIN--"+gameToken);
        else if(!splitMessage[2].equals(gameToken)){
            pw.println("RESPONSE--JOINGAME--GAMEKEYNOTFOUND--"+gameToken);
        }
        else if(playingStatus==true)
            pw.println("RESPONSE--JOINGAME--FAILURE--"+gameToken);
        else
        {
            pw.println("RESPONSE--JOINGAME--SUCCESS--"+gameToken);
            numberOfPlayers++;

            model.participants.add(user);
            try {
                while(true) {
                    if(isPlaying==true)
                    {
                        getAndSendQuestion();
                        return;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* allParticipantsHaveJoined method checks if all the participants have joined and then sends the question to each participant*/
    public void allParticipantsHaveJoined(String clientMessage)throws IOException
    {
        String splitMessage[]=clientMessage.split("--");
        if(!splitMessage[1].equals(loginToken)) {
            pw.println("RESPONSE--ALLPARTICIPANTSHAVEJOINED--USERNOTLOGGEDIN");
        }
        else if(!splitMessage[2].equals(gameToken))
            pw.println("RESPONSE--ALLPARTICIPANTSHAVEJOINED--INVALIDGAMETOKEN");
        else if(playingStatus==true)
            pw.println("RESPONSE--ALLPARTICIPANTSHAVEJOINED--USERNOTGAMELEADER");
        else
        {
            isPlaying=true;
            getAndSendQuestion();
        }

    }
    /* playerSuggestion method checks if the user is logged in and sends the round optionsto the participants */
    public void playerSuggestion(String clientMessage)
    {
        String splitMessage[]=clientMessage.split("--");
        if(splitMessage.length<4)
            pw.println("RESPONSE--PLAYERSUGGESTION--INVALIDMESSAGEFORMAT");
        else if(!splitMessage[1].equals(loginToken))
            pw.println("RESPONSE--PLAYERSUGGESTION--USERNOTLOGGEDIN");
        else if(!splitMessage[2].equals(gameToken))
            pw.println("RESPONSE--PLAYERSUGGESTION--INVALIDGAMETOKEN");
        else
        {

            user.setPlayerSuggestion(splitMessage[3]);
            playerSuggestions.add(splitMessage[3]);
            if(playerSuggestions.size()>2){
                playerSuggestions.clear();
                playerSuggestions.add(splitMessage[3]);
            }
            String s;

            while(true){
                if(playerSuggestions.size()==numberOfPlayers)
                {

                   s="ROUNDOPTIONS";
                    for(int i=0;i<numberOfPlayers;i++)
                     {
                        s=s+"--"+playerSuggestions.get(i);
                     }
                    s=s+"--"+answer;
                    pw.println(s);
                    return;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /* playerChoice method checks if the user is logged in and takes the player choices and sends the result */

    public void playerChoice(String clientMessage)throws IOException
    {
        String splitMessage[]=clientMessage.split("--");
        if(splitMessage.length<3)
            pw.println("RESPONSE--CREATENEWUSER--INVALIDMESSAGEFORMAT");
        if(!splitMessage[1].equals(loginToken))
            pw.println("RESPONSE--PLAYERCHOICE--USERNOTLOGGEDIN");
        else if(!splitMessage[2].equals(gameToken))
            pw.println("RESPONSE--PLAYERCHOICE--INVALIDGAMETOKEN");
        else {
            user.setPlayerChoice(splitMessage[3]);
            playerChoices.add(splitMessage[3]);
            if(playerChoices.size()>2){
                playerChoices.clear();
                playerChoices.add(splitMessage[3]);
            }
            while (true) {
                if (playerChoices.size() ==numberOfPlayers) {
                    String s = gameLogic(playerChoices);
                    sendsResults(s);
                    getAndSendQuestion();
                    return;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /* gameLogic method deals with the logic of the game .It deals with the cumilative score,fooled by and fooled */
    public String gameLogic(ArrayList<String> playerChoices)
    {
        user.setMessage("");
        int count=0;
        for(int i=0;i<numberOfPlayers;i++) {

            if(user.getPlayerSuggestion().equals(playerChoices.get(i))&&(!user.getPlayerChoice().equals(user.getPlayerSuggestion())))
                count++;
        }

        if((!user.getPlayerChoice().equals(answer))&&(!user.getPlayerChoice().equals(user.getPlayerSuggestion())))
        {


            if(leader==true) {
                user.setMessage(user.getMessage()+"You were fooled by " + model.participants.get(0).getUsername() + ".");
                oppFooled+=1;
                oppScore+=5;

            }
            else{
                user.setMessage(user.getMessage()+"You were fooled by "+model.leader.get(0).getUsername()+".");
                oppFooled+=1;
                oppScore+=5;


            }
            user.setFooledBy(user.getFooledBy()+1);
        }
        if(user.getPlayerChoice().equals(user.getPlayerSuggestion()))
        {
            if(leader==true)
            {
                if(model.participants.get(0).getPlayerChoice().equals(user.getPlayerSuggestion())){
                    user.setFooled(user.getFooled()+1);
                    user.setCumulativeScore(user.getCumulativeScore()+5);
                }
                //else if(model.participants.get(0).getPlayerChoice().equals(answer))
                  //  oppScore+=10;
            }
            if(leader==false)
            {
                if(model.leader.get(0).getPlayerChoice().equals(user.getPlayerSuggestion())){
                    user.setFooled(user.getFooled()+1);
                    user.setCumulativeScore(user.getCumulativeScore()+5);
                }
               // else if(model.leader.get(0).getPlayerChoice().equals(answer))
                   // oppScore+=10;
            }
        }

        if(model.leader.get(0).getPlayerChoice().equals(answer)&&leader==false)
        {
            oppScore+=10;
        }
        else if(model.participants.get(0).getPlayerChoice().equals(answer)&&leader==true)
        {
            oppScore+=10;
        }
        if(user.getPlayerChoice().equals(answer))
        {
            user.setMessage(user.getMessage()+" You got it right!.");
            user.setCumulativeScore(user.getCumulativeScore()+10);

        }
        if(count>0)
        {

          int inc=(count)*5;
            user.setCumulativeScore(user.getCumulativeScore()+inc);
            user.setFooled(user.getFooled()+1);
            if(leader==true)
                user.setMessage(user.getMessage()+"You Fooled "+model.participants.get(0).getUsername());
            else
                user.setMessage(user.getMessage()+"You Fooled "+model.leader.get(0).getUsername());
        }
        return user.getMessage();
    }
    private void sendsResults(String s) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String message="";
        if (leader == true) {
            message = "ROUNDRESULT--" + user.getUsername() + "--" + user.getMessage() + "--" + user.getCumulativeScore() + "--" + user.getFooled() + "--" + user.getFooledBy() + "--" + model.participants.get(0).getUsername() + "--" + model.participants.get(0).getMessage() + "--" + oppScore + "--" + oppFooled + "--" + model.participants.get(0).getFooledBy();
        }
        else {
            message = "ROUNDRESULT--" + model.leader.get(0).getUsername() + "--" + model.leader.get(0).getMessage() + "--" + oppScore + "--" + oppFooled + "--" + model.leader.get(0).getFooledBy() + "--" + model.participants.get(0).getUsername() + "--" + model.participants.get(0).getMessage() + "--" + model.participants.get(0).getCumulativeScore() + "--" + model.participants.get(0).getFooled() + "--" + model.participants.get(0).getFooledBy();
        }
            pw.println(message);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
    public void run() {
       // System.out.printf("connection received from %s\n", socket);
        try {
            model=new Model();
            pw = new PrintWriter(socket.getOutputStream(),true);
            isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);
            user=new User("","",0,0,0);
            while(true)
            {
                String clientMessage = in.readLine();
                if(clientMessage.equals("LOGOUT--")) {
                    if(loginStatus==true) {
                        loginStatus=false;
                    pw.println("RESPONSE--LOGOUT--SUCCESS");
                    break;
                    }
                    else if(loginStatus==false)
                    {
                        pw.println("RESPONSE--LOGOUT--USERNOTLOGGEDIN");
                    }

                }
                else if(clientMessage.substring(0,clientMessage.indexOf('-')).equals("CREATENEWUSER"))
                    register(clientMessage);
                else if(clientMessage.substring(0,clientMessage.indexOf('-')).equals("LOGIN"))
                    login(clientMessage);
                else if(clientMessage.substring(0,clientMessage.indexOf('-')).equals("STARTNEWGAME"))
                    startNewGame(clientMessage);
                else if(clientMessage.substring(0,clientMessage.indexOf('-')).equals("JOINGAME"))
                    joinGame(clientMessage);
                else if(clientMessage.substring(0,clientMessage.indexOf('-')).equals("ALLPARTICIPANTSHAVEJOINED"))
                    allParticipantsHaveJoined(clientMessage);
                else if(clientMessage.substring(0,clientMessage.indexOf('-')).equals("PLAYERSUGGESTION"))
                    playerSuggestion(clientMessage);
                else if(clientMessage.substring(0,clientMessage.indexOf('-')).equals("PLAYERCHOICE"))
                    playerChoice(clientMessage);
            }


            pw.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // allocate server socket at given port...
        ServerSocket serverSocket = new ServerSocket(4343);
        Model model=new Model();
        model.internalDataStructure();
        // System.out.printf("socket open, waiting for connections on %s\n", serverSocket);

        // infinite server loop: accept connection,
        // spawn thread to handle...
        while (true) {
            Socket socket = serverSocket.accept();
            Controller server = new Controller(socket);
            new Thread(server).start();
        }

    }
}
