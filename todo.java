import java.util.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;

class todo extends JFrame implements ActionListener{
  public static void main(String args[]){
    todopane();
  }

  public static void todopane(){
    todo frame = new todo("todo");
    frame.setVisible(true);
  }

  String[] data = new String[10000];
  String[] data_split = new String[10000];
  JButton[] button = new JButton[20];
  JLabel[] text = new JLabel[10000];
  int sc_where = 0;
  int data_line = 0;

  todo(String title){
    setTitle(title);
    setBounds(100,100,600,400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel p = new JPanel();

    Container contentPane = getContentPane();
    contentPane.add(p,BorderLayout.CENTER);

    try{
      File file = new File("input.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));
      //data read and set and output
      while((data[data_line] = br.readLine()) != null){
        System.out.println(data[data_line]);
        data_line++;
      }
      br.close();

      Scanner sc = new Scanner(System.in);
      System.out.println("which line do you choise? if you want to finish you must input 11111");
      sc_where = sc.nextInt() -1;
      if(sc_where == 11110){
        System.out.println("ok! bye...");
        System.exit(0);
      }

      if(data[sc_where] != null){
        System.out.println("OK!");
        String[] list = data[sc_where].split(",");
        int text_n = 0;
        for(String list_text:list){
          System.out.println(list_text);
          data_split[text_n] = list_text;
          text[text_n] = new JLabel(list_text);
          text[text_n].setFont(new Font( "‚l‚r ƒSƒVƒbƒN" , Font.BOLD, 15));
          button[text_n] = new JButton("Finished");
          button[text_n].addActionListener(this);//kore daiji wasuretete komatta
          p.add(text[text_n]);
          p.add(button[text_n]);
          text_n++;
        }
      }else{
        System.out.println("there is no content.");
        System.exit(0);
      }

      //data_line output
      System.out.println(data_line);

    }catch(IOException e){
      System.out.println("no file");
      System.exit(0);
    }
  }

  public void actionPerformed(ActionEvent e){
    for(int i = 0;i<20;i++){
      if(e.getSource() == button[i]){
        String delete_content = "";
        if(i != 0)delete_content +=",";
        delete_content += data_split[i];
        if(i == 0)delete_content += ",";
        System.out.println("I delete " + delete_content);
        data[sc_where] = data[sc_where].replace(delete_content,"");
        System.out.println(data[sc_where]);

        try{
          File file = new File("input.txt");
          FileWriter fw = new FileWriter(file);
          for(int j = 0;j<data_line;j++){
            fw.write(data[j]);
            fw.write("\r\n");
          }
          fw.close();
          this.setVisible(false);
          System.out.println("----------");
          todopane();
        }catch(IOException ee){
          System.out.println("no file");
          System.exit(0);
        }
        break;
      }
    }
  }
}
