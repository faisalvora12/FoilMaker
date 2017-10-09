/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test4 {
    static JLabel topLeader = new JLabel();
    static JLabel messageLeader = new JLabel();
    static JFrame frame = new JFrame("FoilMaker");
    static JPanel panel = new JPanel(new GridBagLayout()); //master panel
    static JPanel login = new JPanel(new GridBagLayout()); //user/pass fields
    static JPanel button = new JPanel(new GridBagLayout()); //Login/Register buttons
    static JPanel parts = new JPanel(new GridBagLayout());
    static JPanel loginButton = new JPanel(new GridBagLayout());
    static JPanel joinPanel = new JPanel(new GridBagLayout());//panel for the join components
    static JPanel textfPanel = new JPanel(new GridBagLayout()); //panel for the key
    static JPanel keyPanel = new JPanel(new GridBagLayout()); //panel for the key
    static JPanel textPanel = new JPanel(new GridBagLayout()); //panel for the participants
    static JPanel buttonPanel = new JPanel(new GridBagLayout()); //panel for the button
    static JTextField displayKey = new JTextField();
    static String overallResults = ""; //this will be updated every time a round is over

    public JPanel getLogin() {
        JLabel user = new JLabel("Username:");
        JLabel pass = new JLabel("Password:");
        FoilMaker.topLeader = new JLabel("FoilMaker!");
       */
/* JPanel pfoil = new JPanel();
        pfoil.setBorder(BorderFactory.createLineBorder(Color.gray));
        pfoil.add(foil);*//*

        FoilMaker.messageLeader = new JLabel("New user created");
        */
/*JPanel pcreated = new JPanel();
        pcreated.setBorder(BorderFactory.createLineBorder(Color.gray));
        pcreated.add(created);*//*

        login.setLayout(new GridLayout(2, 2, 50, 10));


        JTextField userName = new JTextField();
        userName.setPreferredSize(new Dimension(150, 25));


        JTextField passWord = new JTextField();
        passWord.setMinimumSize(new Dimension(150, 25));


        JButton LOGIN = new JButton("Login");
        JButton REGISTER = new JButton("Register");


        login.add(user);
        login.add(userName);
        login.add(pass);
        login.add(passWord);


        button.add(LOGIN);
        button.add(REGISTER);
        JPanel pane = new JPanel(new GridBagLayout());
        pane.setSize(new Dimension(100, 100));
        pane.setBorder(BorderFactory.createLineBorder(Color.gray));


        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        c.gridx = 0;
        c.gridy = 0;
        pane.add(login, c);
        c.gridy = 1;
        pane.add(button, c);
        panel.add(pane);
       */
/* c.gridy = 2; c.anchor = GridBagConstraints.LINE_START;
        panel.add(pcreated);*//*

        LOGIN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                FoilMaker.t.name = userName.getText();
                FoilMaker.t.passwordName = passWord.getText();
                FoilMaker.t.flag = 2;

            }
        });
        REGISTER.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                FoilMaker.t.name = userName.getText();
                FoilMaker.t.passwordName = passWord.getText();
                FoilMaker.t.flag = 1;

            }
        });
        return pane;
    }

    public static void main(String args[])
    {
        test4 t=new test4();
        JPanel card = new JPanel(new CardLayout());
        JPanel p = t.getLogin();
        card.add(p);
        topLeader.setText("Hey");
        messageLeader.setText("Welcome");
        frame.add(topLeader, BorderLayout.NORTH);
        frame.add(messageLeader, BorderLayout.SOUTH);
        frame.add(card);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
*/
