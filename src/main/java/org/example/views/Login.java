package com.mycompany.prog;
import javax.swing.*;
import java.awt.*;

public class LogIn {


    public abstract class LogIn extends JFrame {
        private JLabel EmailL, PasswordL, ProblemL, ForgetL;
        private JButton LogInb1, LogInb2,SignUpb1;
        private JTextField EmailT;
        private JPasswordField pass1;

        public LogIn(){
            setTitle("LogIn");
            this.setLocation(250,250);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(400,400);




            EmailL = new JLabel("Email");
            PasswordL = new JLabel("Password");
            ProblemL = new JLabel("Haveing a problem? Contact Us");
            ForgetL = new JLabel("Forgit your password? click here");
            LogInb1 = new JButton("LogIn");
            LogInb2 = new JButton("LogIn");
            SignUpb1 = new JButton("SignUp");
            EmailT = new JTextField(30);
            pass1 = new JPasswordField(30);

            JPanel p = (JPanel)this.getContentPane();
            JPanel p1 = new JPanel();
            JPanel p2 = new JPanel();

            p1.add(LogInb1);
            p1.add(SignUpb1);
            p1.add(ProblemL);

            p2.add(EmailL);
            p2.add(EmailT);
            p2.add(PasswordL);
            p2.add(pass1);
            p2.add(LogInb2);
            p2.add(ForgetL);

            p.add(p1, BorderLayout.WEST);
            p.add(p2, BorderLayout.EAST);

            p.setVisible(true);
            p.pack();

        }


        public static void main(String[] args) {
            LogIn l= new LogIn();
        }

    }
}
