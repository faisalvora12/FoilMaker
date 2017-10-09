import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI{
    static GUI t1=new GUI();;
    static JPanel login = new JPanel(new GridBagLayout()); //user/pass fields
    static JPanel button = new JPanel(new GridBagLayout()); //Login/Register buttons
    static JPanel parts = new JPanel(new GridBagLayout());
    static JTextField displayKey = new JTextField();
    static JTextField key = new JTextField();
    static JButton LOGIN = new JButton("Login");
    static JButton REGISTER = new JButton("Register");
    static JButton NEW = new JButton("Start New Game");
    static JButton JOIN = new JButton("Join a Game");
    static JButton START = new JButton("Start Game");
    static JButton JOIN2 = new JButton("Join Game");
    static JButton SUGGESTION = new JButton("Submit Suggestion");
    static JButton OPTION = new JButton("Submit Option");
    static JTextField suggest = new JTextField();
    static JTextArea round = new JTextArea();
    static JTextArea overall = new JTextArea();
    static JButton next = new JButton("Next Round");
    static JTextArea zebra = new JTextArea();
    static JTextArea participants = new JTextArea();

    static JTextField userName = new JTextField();
    static JPasswordField passWord = new JPasswordField();
    static JRadioButton butt1;
    static JRadioButton butt2;
    static JRadioButton butt3;

    static void setSize(){
        UIManager.put("Label.font",new FontUIResource(new Font("sans",Font.PLAIN,17)));
        UIManager.put("TextField.font",new FontUIResource(new Font("sans",Font.PLAIN,17)));
        UIManager.put("TextArea.font",new FontUIResource(new Font("sans",Font.PLAIN,17)));
        UIManager.put("RadioButton.font",new FontUIResource(new Font("sans",Font.PLAIN,17)));
    }
    public JPanel getLogin(){
        JLabel user = new JLabel("Username:");
        JLabel pass = new JLabel("Password:");
        FoilMaker.top = new JLabel("FoilMaker!");
        FoilMaker.message = new JLabel("New user created");
                login.setLayout(new GridLayout(2, 2, 50, 10));



        userName.setPreferredSize(new Dimension(150, 25));

        passWord.setMinimumSize(new Dimension(150, 25));


        login.add(user); login.add(userName);
        login.add(pass); login.add(passWord);




        button.add(LOGIN);
        button.add(REGISTER);
        JPanel pane = new JPanel(new GridBagLayout());
        pane.setSize(new Dimension(100, 100));
        pane.setBorder(BorderFactory.createLineBorder(Color.gray));


        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        c.gridx = 0; c.gridy = 0;
        pane.add(login, c);
        c.gridy = 1;c.insets=new Insets(20,5,5,5);
        pane.add(button, c);
        panel.add(pane);


        LOGIN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                FoilMaker.t.name = userName.getText();
                FoilMaker.t.passwordName = passWord.getText();

            }
        });
        REGISTER.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                FoilMaker.t.name = userName.getText();
                FoilMaker.t.passwordName = passWord.getText();

            }
        });
        return pane;
    }



    public JPanel getStartNewGame(){

        JPanel pane = new JPanel(new GridBagLayout());
        pane.setSize(new Dimension(100, 100));
        pane.setBorder(BorderFactory.createLineBorder(Color.gray));


        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.insets = new Insets(0,10,0,10);
        pane.add(NEW, c);
        c.gridx = 1;
        pane.add(JOIN, c);
        return pane;
    }

    public JPanel getNewGame(){
        JLabel description = new JLabel("Others should use this key to join your game");
        GUI.START.setEnabled(false);
        displayKey = new JTextField();
        displayKey.setPreferredSize(new Dimension(35,30));

        participants.setPreferredSize(new Dimension(300, 150));
        parts.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Participants"));
        participants.setBackground(new Color(150, 255, 240));
        participants.setEditable(false);
        parts.setMinimumSize(new Dimension(200, 200));
        parts.add(participants);
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        pane.add(description, c);
        c.gridy = 1;
        pane.add(displayKey, c);
        c.gridy = 2;
        pane.add(parts, c);
        c.gridy = 3;
        pane.add(START, c);


        return pane;

    }
    public JPanel getJoinGame(){
        JLabel description = new JLabel("Enter the game key to join a game");


        key.setPreferredSize(new Dimension(70, 50));
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.insets = new Insets(50, 10, 50, 10);
        pane.add(description, c);
        c.gridy = 1;
        pane.add(key, c);
        c.gridy = 2;
        pane.add(JOIN2, c);

        return pane;
    }


    public JPanel getWaiting(){
        JLabel description = new JLabel("Waiting for leader...");
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        pane.add(description, c);
        return pane;

    }
    public JPanel getSuggestion(){
        JLabel description = new JLabel("What is the word for", SwingConstants.LEFT);
        zebra.setEditable(false);
        zebra.setPreferredSize(new Dimension(290, 150));
        zebra.setBorder(BorderFactory.createLineBorder(Color.gray));
        zebra.setBackground(new Color(150, 250, 152));
        suggest.setPreferredSize(new Dimension(250, 30));
        JPanel suggestion = new JPanel();
        suggestion.setPreferredSize(new Dimension(300, 200));
        suggestion.add(suggest);
        suggestion.setBorder(BorderFactory.createTitledBorder("Your Suggestion"));


        JPanel pane = new JPanel(new GridBagLayout());
        pane.setBorder(BorderFactory.createLineBorder(Color.gray));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.insets = new Insets(5,5,5,5);
        pane.add(description, c);
        c.gridy = 1;
        pane.add(zebra, c);
        c.gridy = 2;
        pane.add(suggestion, c);
        c.gridy = 3;
        pane.add(SUGGESTION, c);
        return pane;
    }


    public JPanel getOptions(){
        JLabel description = new JLabel("Pick your option below");


        ButtonGroup butts = new ButtonGroup();
        butts.add(butt1);
        butts.add(butt2);

        butts.add(butt3);


        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));


        buttons.add(butt1);
        buttons.add(butt2);
        buttons.add(butt3);





        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(50,10,50,10);
        c.gridx = 0;
        c.gridy = 0;
        pane.add(description, c);
        c.gridy = 1;
        pane.add(buttons, c);
        c.gridy = 2;
        pane.add(OPTION, c);
        pane.setBorder(BorderFactory.createLineBorder(Color.gray));
        return pane;
    }


    public JPanel getResults(){

        round.setEditable(false);
        round.setPreferredSize(new Dimension(300, 120));
        round.setBackground(new Color(220, 170, 220));

        round.setWrapStyleWord(true);
        overall.setWrapStyleWord(true);


        JPanel roundResult = new JPanel();
        roundResult.setBorder(BorderFactory.createTitledBorder("Round Result"));
        roundResult.add(round);



        overall.setEditable(false);
        overall.setPreferredSize(new Dimension(300, 170));
        overall.setBackground(new Color(250, 150, 150));


        JPanel overallResult = new JPanel();
        overallResult.setBorder(BorderFactory.createTitledBorder("Overall Results"));
        overallResult.add(overall);





        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
        pane.add(roundResult, c);
        c.gridy = 1;
        pane.add(overallResult, c);
        c.gridy = 2;
        pane.add(next, c);
        return pane;
    }

}
