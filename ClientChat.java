import javax.swing.*;
import java.lang.Exception;
import java.awt.event.*;
import java.awt.*;
import java.util.Arrays;

public class ClientChat {
    static String userName;
    static String roomName;
    static String ip;
    static String port;

    public static void main(String[] args) {
        UserInterface gui = new UserInterface();
        while (userName==null) {
            System.out.printf("waiting");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class UserInterface extends JFrame implements ActionListener{
    Container c;
    JLabel title;
    JLabel userNameL;
    JTextField userNameI;
    JLabel roomNameL;
    JTextField roomNameI;
    JLabel portL;
    JTextField portI;
    JLabel ipL;
    JTextField ipI;
    JButton submit;
    String userName;

    public UserInterface() {
        setTitle("Chat de SAD");
        setSize(600, 400);
        setLocation(500, 250);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Formulari d'accés");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(230, 10);
        c.add(title);

        userNameL = new JLabel("Nom d'usuari:");
        userNameL.setFont(new Font("Arial", Font.PLAIN, 15)); 
        userNameL.setSize(280, 20); 
        userNameL.setLocation(150, 100); 
        c.add(userNameL);

        userNameI = new JTextField(); 
        userNameI.setFont(new Font("Arial", Font.PLAIN, 15)); 
        userNameI.setSize(190, 20); 
        userNameI.setLocation(250, 100); 
        c.add(userNameI);

        roomNameL = new JLabel("Nom de la sala:");
        roomNameL.setFont(new Font("Arial", Font.PLAIN, 15)); 
        roomNameL.setSize(280, 20); 
        roomNameL.setLocation(150, 150); 
        c.add(roomNameL);

        roomNameI = new JTextField(); 
        roomNameI.setFont(new Font("Arial", Font.PLAIN, 15)); 
        roomNameI.setSize(190, 20); 
        roomNameI.setLocation(250, 150); 
        c.add(roomNameI);

        portL = new JLabel("Nom de la sala:");
        portL.setFont(new Font("Arial", Font.PLAIN, 15)); 
        portL.setSize(280, 20); 
        portL.setLocation(150, 200); 
        c.add(portL);

        portI = new JTextField(); 
        portI.setFont(new Font("Arial", Font.PLAIN, 15)); 
        portI.setSize(100, 20);
        portI.setLocation(250, 200); 
        c.add(portI);

        ipL = new JLabel("Direcció ip:");
        ipL.setFont(new Font("Arial", Font.PLAIN, 15)); 
        ipL.setSize(280, 20); 
        ipL.setLocation(150, 250); 
        c.add(ipL);

        ipI = new JTextField(); 
        ipI.setFont(new Font("Arial", Font.PLAIN, 15)); 
        ipI.setSize(100, 20); 
        ipI.setLocation(250, 250); 
        c.add(ipI);

        submit = new JButton("Submit"); 
        submit.setFont(new Font("Arial", Font.PLAIN, 15)); 
        submit.setSize(120, 33); 
        submit.setLocation(240, 300); 
        submit.addActionListener(this); 
        c.add(submit); 

        setVisible(true);

        // b = new JButton("click");// creating instance of JButton
        // b.setBounds(130, 100, 100, 40);// x axis, y axis, width, height

        // f.add(b);// adding button in JFrame

        // f.setSize(400, 500);// 400 width and 500 height
        // f.setLayout(null);// using no layout managers
        // f.setVisible(true);// making the frame visible
    }

    public void actionPerformed(ActionEvent e) {
        ClientChat.userName = userNameI.getText();
        ClientChat.roomName = userNameI.getText();
        ClientChat.ip = userNameI.getText();
        ClientChat.port = userNameI.getText();
        System.out.printf("%s",userName);
        dispose();
    }
}
// f = new JFrame("SAD ChatRoom");
// f.setSize(600, 450);
// f.setLocation(600, 450);
// f.setContentPane(new JPanel());
// f.getContentPane().setLayout(null);
// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);