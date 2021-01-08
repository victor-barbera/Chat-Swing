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
    static Monitor mon;
    static ParametersGUI paramGui;
    static GUI chatGui;
    static MySocket s;
    static SendData send;
    static GetData receive;

    public static void main(String[] args) {
        paramGui = new ParametersGUI();
        while (userName == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("port: %s", port);
        System.out.printf("ip: %s", ip);

        mon = new Monitor();
        chatGui = new GUI(mon, userName);
        s = new MySocket(ip, Integer.parseInt(port));
        s.println(userName);
        send = new SendData(s, chatGui);
        receive = new GetData(s, chatGui);
        send.start();
        receive.start();
        try {
            send.join();
            receive.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class ParametersGUI extends JFrame implements ActionListener {
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

    public ParametersGUI() {
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

        portL = new JLabel("Port:");
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
    }

    public void actionPerformed(ActionEvent e) {
        ClientChat.userName = userNameI.getText();
        ClientChat.roomName = roomNameI.getText();
        ClientChat.port = portI.getText();
        ClientChat.ip = ipI.getText();
        System.out.printf("%s", userName);
        dispose();
    }
}

class GUI extends JFrame implements ActionListener {
    JPanel panel;
    JButton sendbutton;
    JTextField text;
    JTextArea messages;
    JList<String> list;
    JScrollPane scrollist;
    JScrollPane scrollmes;
    GridBagConstraints gbc;
    public String linetosend;
    Monitor mon;
    String username;
    String[] users = { "" };
    Timeout timeout;

    public GUI(Monitor mon, String username) {
        this.mon = mon;
        setTitle("Chat de SAD");
        panel = new JPanel();
        messages = new JTextArea();
        list = new JList<String>();
        list.setListData(this.users);
        gbc = new GridBagConstraints();
        linetosend = null;
        this.username = username;
        users[0] = username;
        timeout = new Timeout(this);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        panel.setLayout(new GridBagLayout());
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocation(500, 250);

        scrollmes = new JScrollPane(messages);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 10;
        gbc.weightx = 10.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollmes, gbc);

        scrollist = new JScrollPane(list);
        gbc.gridx = 10;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollist, gbc);

        text = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 10;
        gbc.weightx = 10.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(text, gbc);

        sendbutton = new JButton("send");
        gbc.gridx = 10;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(sendbutton, gbc);

        sendbutton.addActionListener(this);
        getRootPane().setDefaultButton(sendbutton);

        add(panel);
        setVisible(true);
        timeout.start();
    }

    public void actionPerformed(ActionEvent event) {
        mon.lock.lock();
        mon.setMessage(text.getText() + "\n");
        printMessage(mon.message);
        mon.messageState = true;
        mon.waitingToSend.signal();
        text.setText("");
        mon.lock.unlock();
    }

    public void printMessage(String mes) {
        messages.append(mes);
    }

    public void addUser(String user) {
        int index = -1;
        int N = this.users.length;
        for (int i = 0; i < N; i++) {
            if (this.users[i].equals(user)) {
                index = i;
            }
        }
        if (index == -1) {
            this.users = Arrays.copyOf(this.users, N + 1);
            this.users[N] = user;
            list.setListData(this.users);
        }
        timeout.addTime(index);
        System.out.println(Arrays.toString(this.users));
    }

    public void removeUser(int index) {
        String[] tmp = new String[users.length - 1 - index];
        System.arraycopy(users, index + 1, tmp, 0, users.length - 1 - index);
        System.arraycopy(tmp, 0, users, index, users.length - 1 - index);
        users = Arrays.copyOf(users, users.length - 1);
        System.out.println(Arrays.toString(users));
        list.setListData(this.users);
    }
}

class SendData extends Thread {
    public MySocket s;
    public GUI gui;
    public int i;

    public SendData(MySocket s, GUI gui) {
        this.s = s;
        this.gui = gui;
        this.i = 0;
    }

    public void run() {
        String line = "";
        while (true) {
            try {
                gui.mon.lock.lock();
                while (!gui.mon.messageState) {
                    gui.mon.waitingToSend.await();
                }
                gui.mon.messageState = false;
                gui.mon.lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            line = gui.mon.getMessage();

            if (line != null) {
                this.s.println(line);
            }
        }
    }
}

class GetData extends Thread {
    public MySocket s;
    public GUI gui;
    public int i = 0;

    public GetData(MySocket s, GUI gui) {
        this.s = s;
        this.gui = gui;
    }

    public void run() {
        String line;
        // int i = 0;
        while ((line = s.readLine()) != null) {
            line = removeNonPrintable(line) + "\n";
            String nick = line.split(":")[0];
            gui.printMessage(line);
            gui.addUser(nick);
        }
    }

    public String removeNonPrintable(String text) {
        text = text.replaceAll("[^\\x00-\\x7F]", "");
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
        text = text.replaceAll("\\p{C}", "");
        return text.trim();
    }
}

class Timeout extends Thread {
    long[] times = { 0 };
    GUI gui;

    public Timeout(GUI gui) {
        this.gui = gui;
    }

    public void run() {
        while (true) {
            long mintime = 60000;
            long currentime = System.currentTimeMillis();
            for (int i = 0; i < this.times.length; i++) {
                long resta = currentime - this.times[i];
                System.out.println(resta);
                if (resta < mintime) {
                    System.out.println("Calculating");
                    mintime = resta;
                }
                if ((resta != currentime) && (resta >= 60000)) {
                    System.out.println("im in");
                    gui.removeUser(i);
                }
            }
            try {
                System.out.println(mintime);
                sleep(mintime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTime(int index) {
        int N = this.times.length;
        if (index < 0) {
            this.times = Arrays.copyOf(this.times, N + 1);
            this.times[N] = System.currentTimeMillis();
        } else if (index >= 0) {
            this.times[index] = System.currentTimeMillis();
        }
        System.out.println(Arrays.toString(this.times));
    }
}