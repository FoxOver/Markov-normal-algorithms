package additions;
import javax.swing.*;
import java.io.*;

public class Window {

    private JTextField word;
    private JTextArea theRule;
    private JTextArea conclusion;


    public void initComponents(){
        JFrame form = new JFrame("Нормальный алгоритм Маркова");
        form.setBounds(100,100,500,400);
        form.setVisible(true);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setResizable(false);

        //Menu
        JMenuBar menuBar = new JMenuBar();
        form.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        JMenuItem newItem = new JMenuItem("Новый");
        JMenuItem openItem = new JMenuItem("Открыть");
        final JMenuItem saveItem = new JMenuItem("Сохранить как...");
        JMenuItem exitItem = new JMenuItem("Выход");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        JMenuItem helpMenu = new JMenuItem("Помощь");
        menuBar.add(helpMenu);

        newItem.addActionListener(e -> {
            word.setText("Слово");
            theRule.setText("Правила:");
            conclusion.setText("Вывод:");
        });

        openItem.addActionListener(arg0 -> {

            String[] rules = new String[100];
            String str;
            BufferedReader pr;
            JFileChooser fileOpen = new JFileChooser();
            int ret = fileOpen.showDialog(null, "Open");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();

                try {
                    pr = new BufferedReader(new FileReader(file));
                    int i = 0;
                    int numberOfRules = 0;
                    while ((str = pr.readLine()) != null) {
                        rules[i] = str;
                        numberOfRules++;
                        i++;
                    }
                    pr.close();
                    theRule.setText(null);
                    theRule.append(rules[0]);
                    for (int j = 1; j < numberOfRules; j++) {
                        theRule.append("\n" + rules[j]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        saveItem.addActionListener(e -> {
            try {
                saveToFile(theRule.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });


        exitItem.addActionListener(e -> System.exit(1));

        helpMenu.addActionListener(e -> {
            //JOptionPane.showMessageDialog(null, "Правила пользования");
            Help frame1 = new Help();
            frame1.init();
        });


        //TextField
        word = new JTextField("Слово");
        word.setToolTipText("Длиное поле");
        word.setVisible(true);
        word.setLocation(2,5);
        word.setSize(450,20);
        form.getContentPane().add(word);
        form.getContentPane().add(new JLabel());

        //Button
        JButton play = new JButton();
        play.setVisible(true);
        play.setLocation(460,0);
        play.setSize(30,30);
        play.setIcon(new ImageIcon("images/play2.png"));
        play.setBorderPainted(false);
        play.setFocusPainted(false);
        play.setContentAreaFilled(false);
        play.addActionListener(e -> {
            conclusion.setText(null);
            String pr = theRule.getText();
            String pr1 = pr.replace("->","\n");
            String world = word.getText();
            String deleter = "\n";

            String[][] rules1 = new String[100][2];
            String[] subStr;
            subStr = pr1.split(deleter);
            int numberOfRules = 0;
            for (int i = 0; i < subStr.length; i++)
            {
                if (i % 2 == 0) {
                    rules1[i / 2][0] = subStr[i];
                } else {
                    rules1[i / 2][1] = subStr[i];
                }
                numberOfRules++;
            }
            markAlg(world,rules1,numberOfRules);
        });
        form.getContentPane().add(play);
        form.getContentPane().add(new JLabel());


        //CheckBox
        JPanel edit = new JPanel();
        final JCheckBox editYes = new JCheckBox("редактировать",false);
        edit.add(editYes);
        edit.setSize(125,25);
        edit.setLocation(-5,20);
        edit.setVisible(true);
        form.getContentPane().add(edit);
        editYes.addActionListener(e -> {
            if (editYes.isSelected()) {
                theRule.setEditable(true);
            } else {
                theRule.setEditable(false);
            }
        });


        //Label пример
        JLabel example = new JLabel();
        example.setText("исходное слово=(правило)=результат");
        example.setVisible(true);
        example.setSize(300,25);
        example.setLocation(125, 25);
        form.getContentPane().add(example);
        form.getContentPane().add(new JLabel());


        //TextArea правила
        theRule = new JTextArea("Правила:");
        JScrollPane scroll2=new JScrollPane(theRule);
        theRule.setEditable(false);
        scroll2.setVisible(true);
        scroll2.setLocation(5,50);
        scroll2.setSize(110,300);
        form.getContentPane().add(scroll2);
        form.getContentPane().add(new JLabel());


        //TextArea вывод
        conclusion = new JTextArea("Вывод:");
        conclusion.setEditable(false);
        JScrollPane scroll1=new JScrollPane(conclusion);
        scroll1.setVisible(true);
        scroll1.setLocation(120,50);
        scroll1.setSize(375,300);
        form.getContentPane().add(scroll1);
        form.getContentPane().add(new JLabel());



    }

    private static String replace(String s, int p, String[] r){
        String str;
        str = s.substring(0,p);
        str += r[1];
        str += s.substring(p + r[0].length());
        return str;
    }

    private void saveToFile(String text) throws IOException {
        JFileChooser fileSave = new JFileChooser();
        int ret = fileSave.showDialog(null, "Save");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileSave.getSelectedFile();
            FileWriter inFile = new FileWriter(file);
            inFile.write(text);
            inFile.close();
        }
    }

    private void markAlg(String s, String[][] rules1, int numberOfRules) {
        String old = s;
        int n = 0;
        String rez = "";
        String str;
        for(int i = 0; i < numberOfRules/2; i++){
            int p1 = s.indexOf(rules1[i][0]);
            while (p1 > -1){
                n++;
                str = replace(s, p1, rules1[i]);
                rez += n + ") " + s + "=" +"("+rules1[i][0] + "->" + rules1[i][1] + ")" + "=" + str + "\n";
                conclusion.setText(rez);
                s = str;
                p1 = s.indexOf(rules1[i][0]);
            }
        }
        if(!old.equals(s)){
            markAlg(s, rules1, numberOfRules);
        }

    }
}
