import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class FoilMaker {
    static int clicked=0;
    static Socket socket ;
    static PrintWriter out;
    static InputStreamReader isr;
    static BufferedReader in;
    static FoilMaker t;
    static int flag=0;
    int count1=0;
    int count2=0;
    static String name = "";
    static String passwordName = "";
    static String serverIP = "localhost";
    static int serverPort = 5282;
    static JFrame frame = new JFrame("FoilMaker");
    static JLabel top = new JLabel();
    static JLabel message = new JLabel();
    static String suggest1;
    static String suggest2;
    static String suggest3;
    static String answer;
    static String gameToken ;
    static String userToken;
    static String serverMessage;
    static JPanel card;
    static JPanel p;
    static String nextRound;
    static String serverOutput2="";

    public void Login()
    {
        GUI.t1.LOGIN.addActionListener(new ActionListener() {//Login button
            public void actionPerformed(ActionEvent ae) {
                name = GUI.t1.userName.getText();
                passwordName = GUI.t1.passWord.getText();
                try{
                    out.println("LOGIN--" + name + "--" + passwordName);
                    String serverOutput=t.server();
                    String output = serverOutput.substring(17, serverOutput.indexOf('-',18));
                    if (!(output.equalsIgnoreCase("SUCCESS"))) {
                        if (output.equalsIgnoreCase("INVALIDMESSAGEFORMAT"))
                            message.setText("Request does not comply with the format given above");
                        else if (output.equalsIgnoreCase("UNKNOWNUSER"))
                            message.setText("Invalid username");
                        else if (output.equalsIgnoreCase("INVALIDUSERPASSWORD"))
                            message.setText("Invalid password (User not authenticated)");
                        else if (output.equalsIgnoreCase("USERALREADYLOGGEDIN"))
                            message.setText("User already logged in");

                    }
                    else {
                        if (output.equalsIgnoreCase("SUCCESS")) {
                            userToken = serverMessage.substring(26);
                            card.remove(p);
                             p=GUI.t1.getStartNewGame();
                            card.add(p);
                            top.setText(name);
                            card.updateUI();
                            t.StartNewGame();
                            t.JoinGame();

                        }
                    }

                }
                catch(IOException e)
                {System.out.print("IOException");}


            }
        });
    }



    public void StartNewGame() {
        GUI.NEW.addActionListener(new ActionListener()  {//start new game button
            public void actionPerformed(ActionEvent ae) {
                try {
                    out.println("STARTNEWGAME--" + userToken);
                    String serverOutput = t.server();
                    String output = serverOutput.substring(24, serverOutput.indexOf('-', 25));
                    if (!(output.equalsIgnoreCase("SUCCESS"))) {
                        if (output.equalsIgnoreCase("USERNOTLOGGEDIN"))
                            message.setText("User is not logged in");
                        else if (output.equalsIgnoreCase("FAILURE"))
                            message.setText("User already playing the game");

                    } else if (output.equalsIgnoreCase("SUCCESS")) {
                        card.remove(p);
                        p=GUI.t1.getNewGame();
                        card.add(p);
                        top.setText(name);
                        message.setText("Game started you are the leader");
                        card.updateUI();
                        gameToken = serverMessage.substring(33);
                        GUI.t1.displayKey.setText(gameToken);



                        new Thread() {
                            public void run() {

                                    try {
                                        flag++;
                                        String output=t.server();//new participant full string
                                        String output2=output.substring(0,14);//new participant
                                        String output4=output.substring(output.indexOf('-')+2,output.lastIndexOf('-')-1);//alice name of new participant
                                        GUI.participants.setText(output4);//displaying participants name
                                        GUI.START.setEnabled(true);
                                        if(output2.equalsIgnoreCase("NEWPARTICIPANT")) {
                                            t.StartGame();
                                        }
                                    }
                                    catch(Exception e)
                                    {System.out.println("IOException");
                                    }
                            }
                        }.start();



                    }
                }
                catch (IOException e)
                {
                    System.out.println("IOException");
                }


            }
        });
    }



    public void JoinGame(){
        GUI.JOIN.addActionListener(new ActionListener() {//Join Game
            public void actionPerformed(ActionEvent ae) {
                card.remove(p);
                p=GUI.t1.getJoinGame();
                card.add(p);

                top.setText(name);
                message.setText("Welcome");
                card.updateUI();
                GUI.JOIN2.addActionListener(new ActionListener() {//Inner join game button
                    public void actionPerformed(ActionEvent ae) {
                        out.println("JOINGAME--" + userToken + "--" + GUI.key.getText());

                        try {
                            serverOutput2=t.server();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String output = serverOutput2.substring(20, serverOutput2.indexOf('-', 21));
                                if (output.equalsIgnoreCase("USERNOTLOGGEDIN"))
                                    message.setText("User is not logged in");
                                else if (output.equalsIgnoreCase("FAILURE"))
                                    message.setText("User already playing the game");
                                else if (output.equalsIgnoreCase("GAMEKEYNOTFOUND"))
                                    message.setText("Invalid Game key");
                                else if(output.equalsIgnoreCase("SUCCESS")){
                                    gameToken=GUI.key.getText();
                                    new Thread() {
                                        public void run() {
                                            card.remove(p);
                                            p=GUI.t1.getWaiting();
                                            card.add(p);
                                            top.setText(name);
                                            message.setText("Joined game waiting for leader");
                                            card.updateUI();
                                            try {
                                                GUI.START.setEnabled(true);
                                                String serverOutput3=t.server();//question
                                                String question = serverOutput3.substring(13, serverOutput3.indexOf('-',14));
                                                answer = serverOutput3.substring(serverOutput3.indexOf('-',14)+2,serverOutput3.length());//the answer
                                                card.remove(p);
                                                p=GUI.t1.getSuggestion();
                                                card.add(p);
                                                top.setText(name);
                                                message.setText("Enter your suggestion");
                                                GUI.zebra.setText(question);
                                                card.updateUI();
                                                t.Suggestion();

                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }.start();




                    }





                    }
                });

            }
        });
    }




    public void StartGame(){
        GUI.START.addActionListener(new ActionListener() {//start game Button
            public void actionPerformed(ActionEvent ae) {

                out.println("ALLPARTICIPANTSHAVEJOINED--"+userToken+"--"+gameToken);
                try{
                    String serverOutput = t.server();
                    String output="";
                    output=serverOutput.substring(0,8);//checking if response was sent or new game word
                    if(output.equalsIgnoreCase("RESPONSE"))
                    {
                    if (output.equalsIgnoreCase("USERNOTLOGGEDIN"))
                        message.setText("User is not logged in");
                    else if (output.equalsIgnoreCase("INVALIDGAMETOKEN"))
                        message.setText("User already playing the game");
                    else if (output.equalsIgnoreCase("USERNOTGAMELEADER"))
                        message.setText("Invalid game key");}
                    else {

                        new Thread() {
                            public void run() {
                                flag++;
                               String question = serverOutput.substring(13, serverOutput.indexOf('-',14));//the question
                                answer = serverOutput.substring(serverOutput.indexOf('-',14)+2,serverOutput.length());//the answer

                        card.remove(p);
                        p=GUI.t1.getSuggestion();
                        GUI.zebra.setText(question);
                        card.add(p);
                        top.setText(name);
                        message.setText("Enter your suggestion");
                        card.updateUI();
                        t.Suggestion();
                            }
                        }.start();

                    }
                }
                catch (IOException e)
                {
                    System.out.println("IOException");
                }

            }
        });
    }



    public void Suggestion(){
        GUI.SUGGESTION.addActionListener(new ActionListener() {//Receive the suggestion of the user
            public void actionPerformed(ActionEvent ae) {
                clicked++;
                /*if(clicked!=3){*/
                if(GUI.suggest.getText().equals(answer)|| GUI.suggest==null||GUI.suggest.getText().equals("")) {
                        message.setText("Try a different suggestion");
                        GUI.suggest.setText("");
                        card.updateUI();
                    }
                    else {

                         out.println("PLAYERSUGGESTION--" + userToken + "--" + gameToken + "--" + GUI.suggest.getText());

                        try {

                             String serverOutput = t.server();

                             String output=serverOutput.substring(0,8);//checking if response was sent or options
                             if(output.equalsIgnoreCase("RESPONSE")) {
                              output = serverOutput.substring(28, serverOutput.indexOf('-', 30));

                                 if (output.equalsIgnoreCase("USERNOTLOGGEDIN"))
                                   message.setText("User is not logged in");
                                else if (output.equalsIgnoreCase("INVALIDGAMETOKEN"))
                                    message.setText("User already playing the game");
                                 else if (output.equalsIgnoreCase("UNEXPECTEDMESSAGETYPE"))
                                     message.setText("The type of message was in correct");
                                 else if (output.equalsIgnoreCase("INVALIDMESSAGEFORMAT"))
                                    message.setText("The format of the message is wrong");
                             }
                            else {
                                 suggest1 = serverOutput.substring(14, serverOutput.indexOf('-', 14));//option1
                                 String hold = serverOutput.substring(serverOutput.indexOf('-', 14) + 2, serverOutput.length());//part two
                                 suggest2 = hold.substring(0,hold.indexOf('-'));//option2
                                 suggest3=hold.substring(hold.indexOf('-')+2);//option3

                                 GUI.butt1 = new JRadioButton();
                                 GUI.butt2 = new JRadioButton();
                                 GUI.butt3 = new JRadioButton();
                                 GUI.butt1.setText(suggest1);
                                 GUI.butt2.setText(suggest2);
                                 GUI.butt3.setText(suggest3);
                                 card.remove(p);
                                 p = GUI.t1.getOptions();
                                 card.add(p);
                                 top.setText(name);
                                 message.setText("Pick your choice");
                                 card.updateUI();
                                 t.Options();
                            }

                        } catch (IOException e) {
                        System.out.println("IOException");
                         }

                    }

            }
           /* else
                {

                }}*/
        });
    }
    public void Options()
    {
        GUI.OPTION.addActionListener(new ActionListener() {//Gives the users options
            public void actionPerformed(ActionEvent ae) {
                clicked++;
                if(GUI.butt1.isSelected())
                out.println("PLAYERCHOICE--"+userToken+"--"+gameToken+"--"+GUI.butt1.getText());
                else if(GUI.butt2.isSelected())
                    out.println("PLAYERCHOICE--"+userToken+"--"+gameToken+"--"+GUI.butt2.getText());
                else if(GUI.butt3.isSelected())
                    out.println("PLAYERCHOICE--"+userToken+"--"+gameToken+"--"+GUI.butt3.getText());
                try{
                    String serverOutput = t.server();
                    String output = serverOutput.substring(24, serverOutput.indexOf('-',25));
                        if (output.equalsIgnoreCase("USERNOTLOGGEDIN"))
                            message.setText("User is not logged in");
                        else if (output.equalsIgnoreCase("INVALIDGAMETOKEN"))
                            message.setText("User already playing the game");
                        else if (output.equalsIgnoreCase("UNEXPECTEDMESSAGETYPE"))
                            message.setText("The type of message was in correct");
                        else if (output.equalsIgnoreCase("INVALIDMESSAGEFORMAT"))
                            message.setText("The format of the message is wrong");
                        else {
                            makeResults(serverOutput);
                            try{
                                String question;
                                nextRound=t.server();
                                if((nextRound.equals("GAMEOVER--")) == false) {
                                    question = nextRound.substring(13, nextRound.indexOf('-', 14));//the question
                                    answer = nextRound.substring(nextRound.indexOf('-', 14) + 2, nextRound.length());//the answer
                                    GUI.zebra.setText(question);
                                }

                            }
                            catch(IOException e) {
                                System.out.println("IOException");
                            }
                            card.remove(p);
                            p=GUI.t1.getResults();
                            card.add(p);
                            top.setText(name);
                            message.setText("Click Next Round to go to next round!");
                            card.updateUI();
                            t.results();
                        }

                }
                catch (IOException e)
                {
                    System.out.println("IOException");
                }

            }
        });
    }

    public static int nthIndexOf(String s, char c, int n) {
        int i = -1;
        while (n-- > 0) {
            i = s.indexOf(c, i + 1);
            if (i == -1)
                break;
        }
        return i;
    }

    public void makeResults(String longResult)
    {
        int []index=new int[20];
        for(int i=0;i<20;i++)
        {
           index[i]=nthIndexOf(longResult,'-',i+1);
        }

        String nameOfPlayer1=longResult.substring(index[1]+1,index[2]);
        String messageForPlayer1=longResult.substring(index[3]+1,index[4]);
        String scoreOfPlayer1=longResult.substring(index[5]+1,index[6]);
        String fooledByPlayer1=longResult.substring(index[7]+1,index[8]);
        String gotFooledByOthers1=longResult.substring(index[9]+1,index[10]);

        String nameOfPlayer2=longResult.substring(index[11]+1,index[12]);
        String messageForPlayer2=longResult.substring(index[13]+1,index[14]);
        String scoreOfPlayer2=longResult.substring(index[15]+1,index[16]);
        String fooledByPlayer2=longResult.substring(index[17]+1,index[18]);
        String gotFooledByOthers2=longResult.substring(index[19]+1);

        if(name.equals(nameOfPlayer1))
            GUI.round.setText(messageForPlayer1);
        else if(name.equals(nameOfPlayer2))
            GUI.round.setText(messageForPlayer2);

        GUI.overall.setText(nameOfPlayer1+"=> Score: "+scoreOfPlayer1+" | Fooled:"+fooledByPlayer1+" player(s) | Fooled by: "+gotFooledByOthers1+" player(s)"
        +"\n"+nameOfPlayer2+"=> Score: "+scoreOfPlayer2+" | Fooled:"+fooledByPlayer2+" player(s) | Fooled by: "+gotFooledByOthers2+" player(s)");

    }

    public void results()
    {
        GUI.t1.next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(nextRound.equals("GAMEOVER--")) {
                    out.println("LOGOUT--");
                    try{
                        String serverOutput = t.server();
                        String output = serverOutput.substring(18,serverOutput.indexOf('-',18));
                        if (output.equalsIgnoreCase("USERNOTLOGGEDIN"))
                            message.setText("User is not logged in");
                        else if (output.equalsIgnoreCase("SUCCESS")){
                            message.setText("User logged out sucessfully");
                            message.setText("Game Over");
                            GUI.next.setEnabled(false);
                            card.updateUI();
                        }

                    }
                    catch (IOException e)
                    {
                        System.out.println("IOException");
                    }

                }
                else
                {


                    card.remove(p);
                    p=GUI.t1.getSuggestion();
                    card.add(p);
                    top.setText(name);
                    message.setText("Enter your suggestion");
                    suggest1="";
                    suggest2="";
                    suggest3="";

                    GUI.round.setText("");
                    GUI.overall.setText("");
                    GUI.suggest.setText("");
                    card.updateUI();
                    t.Suggestion();


                }

            }
        });
    }

    public void Register() {
        GUI.t1.REGISTER.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                name = GUI.t1.userName.getText();
                passwordName = GUI.t1.passWord.getText();
                out.println("CREATENEWUSER--" + name + "--" + passwordName);
                try{
                    String serverOutput=t.server();
                    String output = serverOutput.substring(25, serverOutput.indexOf('-',26));
                    if (output.equalsIgnoreCase("INVALIDMESSAGEFORMAT"))
                        message.setText("Request does not comply with the format given above");
                    else if (output.equalsIgnoreCase("INVALIDUSERNAME"))
                        message.setText("username empty");
                    else if (output.equalsIgnoreCase("INVALIDUSERPASSWORD"))
                        message.setText("Password empty");
                    else if (output.equalsIgnoreCase("USERALREADYEXISTS"))
                        message.setText("User already exists in the user store");
                    else if (output.equalsIgnoreCase("SUCCESS"))
                        message.setText("New User Created.Please Login");
                }
                catch(IOException e)
                {System.out.print("IOException");}
            }
        });
    }
    public String server()throws IOException
    {
        serverMessage = in.readLine();
        return serverMessage;
    }
    public String server2()throws IOException
    {
        serverMessage = in.readLine();

        return serverMessage;
    }
    public static void main(String args[]) throws IOException {

        //Connect to server
        socket = new Socket(serverIP, serverPort);
        //create data writer
         out = new PrintWriter(socket.getOutputStream(), true);
        //create data reader
        isr = new InputStreamReader(socket.getInputStream());
         in = new BufferedReader(isr);
         t = new FoilMaker();
        GUI.setSize();
         card = new JPanel(new CardLayout());
        top.setText("FoilMaker");
        message.setText("Welcome");
         p = GUI.t1.getLogin();
        card.add(p);
        frame.add(top, BorderLayout.NORTH);
        frame.add(message, BorderLayout.SOUTH);
        frame.add(card);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,700));
        frame.pack();
        frame.setVisible(true);

            t.Register();
            t.Login();
    }
}