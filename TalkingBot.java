package main_package;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.BufferedReader;
import java.io.IOException;

class TalkingBot {
    static String str_in_JForm = "";
    static String log_file_name;
       
    public static void call_interface(){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }  
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFrame().setVisible(true);
            }
        });     
    }
    
    public static void write_in_log_file(String fileName, String text) {
        StringBuilder sb = new StringBuilder();
        try {
            try (BufferedReader in = new BufferedReader(new FileReader( new File(fileName).getAbsoluteFile()))) {
                String s;
                while (null != (s = in.readLine())) {
                    sb.append(s + "\r\n");
                }
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        sb.toString();
        try {
            try (PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile())) {
                
                out.write(sb + text);
            }
        } catch(IOException e) {
            System.out.println("Cannot open log-file");
        }
    }
    
    public static String string_read(String fileName, boolean flag_start, boolean flag_finish)
    {
        Random rand = new Random();
        String str_out = "problem in text file";
        
        try {
            List<String> mass_string = new ArrayList<>();
            try (BufferedReader in = new BufferedReader(new FileReader( new File(fileName).getAbsoluteFile()))) {
                String s;
                s = in.readLine();
                mass_string.add(s);
                
                while ((s = in.readLine()) != null)
                {
                    mass_string.add(s);
                }                              
                
                String[] last_print = mass_string.get(mass_string.size()-1).split(" ");
                
                if (flag_finish == true & flag_start == false)
                {   
                    str_out = "";
                    int n = 0;
                    while (n < last_print.length - 1)
                    {
                        if ("".equals(str_out))
                        {
                           str_out = last_print[n];
                        }
                        else {str_out = str_out + " " + last_print[n];}
                    n = n + 1;
                    }
                }

                if (flag_start == true & flag_finish == false)
                {
                    str_out = mass_string.get(0);
                }
            }
        } catch(IOException e) {
            str_out = "cannot open read_file";
        }
        return str_out;
    }
    
    public static String string_read_classif(String str_in, String fileName)
    {
        List<String> list_prepared_in = new ArrayList<>(); 
        String[] list_in_split = str_in.split(" "); 
        int n = 0;
        
        while (n < list_in_split.length)
        {
            list_prepared_in.add(list_in_split[n]);
            n = n + 1;
        }
        String out = "problem in text file";  
        
        try {
            List<String> mass_helper= new ArrayList<>();
            try (BufferedReader in = new BufferedReader(new FileReader( new File(fileName).getAbsoluteFile()))) {
                String s;
                while ((s = in.readLine()) != null)
                {
                    mass_helper.add(s);
                }              
                ArrayList<String>[] mass_prepared_out = (ArrayList<String>[])new ArrayList[mass_helper.size()];
                n = 0;  
            
                while (n < mass_prepared_out.length)
                {
                      mass_prepared_out[n] = new ArrayList<>();
                      String[] s1 = mass_helper.get(n).split(" ");
                      int m = 0;
                  
                      while (m < s1.length)
                      {
                      mass_prepared_out[n].add(s1[m]);
                      m = m + 1;
                      }
                  
                      n = n + 1;
                }
  
                n = 0;        
                String categories_found = "Random";   
                
                while (n < list_prepared_in.size())
                {       
                      if ((list_prepared_in.get(n).equals("Tomorrow")) | (list_prepared_in.get(n).equals("Today")) | (list_prepared_in.get(n).equals("Yesterday")))
                      {
                         categories_found = "Time";
                         break;
                      }
                      if (list_prepared_in.get(n).indexOf("!") == list_prepared_in.get(n).length()-1) 
                      {
                         categories_found = "Exclamation";
                         break;
                      }
                      if (list_prepared_in.get(n).indexOf("?") == list_prepared_in.get(n).length()-1) 
                      {
                         categories_found = "Answer";
                         break;
                      }
                      n = n + 1; 
                }     
                
                int m = 0;
                n = 0;
                out = "";
                
                ArrayList<Integer> num_found = new ArrayList<>();
                
                while (n < mass_prepared_out.length)
                { 
                      String s_try_find = mass_prepared_out[n].get(mass_prepared_out[n].size()-1);
                      if (s_try_find.equals(categories_found))
                      {
                         num_found.add(n); 
                      }
                      n = n + 1;
                }
                
                n = 0;
                
                int kol_word_prep = list_prepared_in.size();
                int kol_word_out = mass_prepared_out[num_found.get(0)].size()-1;
                int min_kol = (kol_word_prep - kol_word_out)^2;
                int found_num = num_found.get(0);
                
                while (n < num_found.size())
                { 
                      int kol_word = mass_prepared_out[num_found.get(n)].size();
                      if (((kol_word_prep - kol_word)^2)> min_kol)
                      {
                          min_kol = (kol_word_prep - kol_word)^2;
                          found_num = num_found.get(n);
                      }
                      n = n + 1;
                }   
                
                m = 0;
                
                while (m < mass_prepared_out[found_num].size()-1)
                {
                      out = out  + mass_prepared_out[found_num].get(m) + " ";
                      m = m + 1;
                }
                
            }
        } catch(IOException e) {
            System.out.println("cannot open read_file");
        }
        return out;
    }
    
    public static String create_log()
    {
      Date now = new Date();
      DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH-mm-ss");
      String log_create = formatter.format(now) + ".log";
      try {
          File file = new File(log_create);
          file.createNewFile();
      } 
      catch (IOException e) {
      }
      return log_create;
    }
    
    public static void init_text()
    {   //смотреть кавычки
        System.out.println("Choose something command from list:");
        System.out.println("\"Stop talking\"");
        System.out.println("\"Start talking\"");
        System.out.println("\"Goodbye\"");
        System.out.println("\"Use another file: way_to_file\"");  
    }
    
    @SuppressWarnings("empty-statement")
    
     public  static void main(String  args[])
    {
        String read_filename = "answers.txt";
        log_file_name = create_log();
        init_text();
        String s;
        s = string_read(read_filename, true, false);
        System.out.println(s);
        str_in_JForm = str_in_JForm + s;
        write_in_log_file(log_file_name, s);
        Scanner scan;
        scan = new Scanner(System.in);   
        boolean stop = false;
        boolean call_interface  = false;    
        
        if (args.length == 1)
        {
           if (args[0].equals("-g")) 
           { 
               System.out.println("Press enter if you want to work in interface");
               call_interface = true;
           }
        } 
        
        while(!"\"Goodbye\"".equals(s))
        { 
            s = scan.nextLine();

            if (call_interface == true)
            {
                s = "\"Use graphics\"";
            }
            
            write_in_log_file(log_file_name, s);
            str_in_JForm = str_in_JForm + "\n" + s + "\n";
            
            if (s.equals("\"Goodbye\""))
            {
               String str_last_write = string_read(read_filename, false, true);
               System.out.println(str_last_write);
               write_in_log_file(log_file_name, str_last_write);
               break;
            }     
            
            if (s.equals("\"Use graphics\""))
            {
               call_interface();
               call_interface = true;
               break;
            }
            
            if (s.equals("\"Stop talking\""))
            {
               stop = true;
               System.out.println("Stop talking activated");
            }
            
            Pattern p = Pattern.compile("\"Use another file: \\S+\"");
            Matcher m = p.matcher(s);
            boolean new_answers;
            new_answers = m.matches();
            
            if (true == new_answers)
            {
                String[] new_s = s.split(" ");
                read_filename = new_s[3].substring(0, new_s[3].length()-1);
                System.out.println("Start reading from " + read_filename);
                continue;
            }
            
            if (stop == false & !"\"Start talking\"".equals(s))
            {
                String str_wr;
                str_wr = string_read_classif(s, read_filename);
                System.out.println(str_wr);
                str_in_JForm = str_in_JForm + str_wr;
                write_in_log_file(log_file_name, str_wr);
            }
            
            if (s.equals("\"Start talking\""))
            {
                if (stop == true)
                {
                stop = false;
                System.out.println("Start talking activated");
                }
                else
                {
                    System.out.println("Stop is not activated");
                };
            }    
        }
        while (call_interface == true)
        {
           s = scan.nextLine();
           System.out.println("Use graphics activated!");
        }
    }
}
