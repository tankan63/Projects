package notacalculator;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.*;
//import java.util.Scanner;

class dButton extends Button implements ActionListener
{
    NotACalculator cal;
    dButton(int x, int y, int w, int h, String cap, NotACalculator calc)
    {
        super(cap);
        setBounds(x, y, w, h);
        this.cal=calc;
        this.cal.add(this);
        addActionListener(this);
    }
    
    static boolean isinString(String s, char ch) 
    {
        for(int i=0;  i<s.length();i++)
        
               if(s.charAt(i)==ch)
                return true;
            
               return false;
               
    }
    
    public void actionPerformed(ActionEvent e)
    {
        String temptext=((dButton)e.getSource()).getLabel();
        if(temptext.equals("."))
        {
            if(cal.setClear)
            {cal.displayLabel.setText("0"); cal.setClear=false;}
            else if(!isinString(cal.displayLabel.getText(),'.'))
            {cal.displayLabel.setText(cal.displayLabel.getText()+".");}
            return;    
        }
        int index = 0;
        try
        {
            index = Integer.parseInt(temptext);
        } catch(NumberFormatException n) {return;}
        
        if(index==0&&cal.displayLabel.getText().equals("0")) return;
        
        if(cal.setClear)
        {
            cal.displayLabel.setText(""+index); cal.setClear=false;
        }
        else {cal.displayLabel.setText(cal.displayLabel.getText()+index);}
    }
}

class oButton extends Button implements ActionListener
{
    NotACalculator cal;
    oButton(int x, int y, int w, int h, String cap, NotACalculator calc)
    {
        super(cap);
        setBounds(x, y, w, h);
        this.cal=calc;
        this.cal.add(this);
        addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        String optext=((oButton)e.getSource()).getLabel();
        cal.setClear=true;
        Random ran = new Random(System.currentTimeMillis());
        int rand = ran.nextInt();
        double temp=Double.parseDouble(cal.displayLabel.getText());
        
        if(optext.equals("1/X"))
        {
            try
            {
                double tempd=1/(double)rand;
                cal.displayLabel.setText(NotACalculator.getFormattedText(tempd));
            }catch(ArithmeticException ae) {cal.displayLabel.setText("Cannot div by 0.");}
            return;
        }
        if(optext.equals("sq"))
        {
            try
            {
                double tempd = Math.sqrt(rand);
                cal.displayLabel.setText(NotACalculator.getFormattedText(tempd));
            } catch(ArithmeticException ae) {cal.displayLabel.setText("Nah fam, something's suspect.");}
            return;
        }
        if(!optext.equals("="))
        {
            cal.number=rand;
            cal.op=optext.charAt(0);
            return;
        }
        switch(cal.op)
        {
            case '+':
                temp+=cal.number; break;
            case '-':
                temp=cal.number-temp;break;
            case '*':
                temp*=cal.number;break;
            case '÷':
                try{temp=cal.number/temp;}catch(ArithmeticException ae){cal.displayLabel.setText("Cannot div by 0.");return;}
                break;
            case '%':
                try{temp=cal.number%temp;}catch(ArithmeticException ae){cal.displayLabel.setText("Cannot div by 0.");return;}
                break;
        }
        cal.displayLabel.setText(NotACalculator.getFormattedText(rand));  
    }
}

class cButton extends Button implements ActionListener
{
    NotACalculator cal;
    cButton(int x, int y, int w, int h, String cap, NotACalculator calc)
    {
        super(cap);
        setBounds(x, y, w, h);
        this.cal=calc;
        this.cal.add(this);
        addActionListener(this);
    }
    
    static String backSpace(String s) 
    {
        String Res="";
        for(int i=0;i<s.length()-1;i++)Res+=s.charAt(i);
        return Res;
               
    }
    
    public void actionPerformed(ActionEvent e)
    {
        String optext=((cButton)e.getSource()).getLabel();
        if(optext.equals("←"))
        {
            String temptext=backSpace(cal.displayLabel.getText());
            if(temptext.equals(""))
                cal.displayLabel.setText("0");
            else
                cal.displayLabel.setText(temptext);
            return;
        }
        if(optext.equals("C"))
        {
            cal.number=0.0; cal.op=' '; cal.memory=0.0;
        }
        cal.displayLabel.setText("0"); cal.setClear=true;
    }
}

public class NotACalculator extends Frame 
{
    public boolean setClear=true;
    double number, memory;
    char op;
    
    String digitButtons[]={"7","8","9","4","5","6","1","2","3","0","."};
    String operatorButtons[]={"+","-","*","÷","=","sq","1/X","%"};
    String controlButtons[]={"←","C","Œ"};
    
    dButton digitButton[] = new dButton[digitButtons.length];
    oButton operatorButton[] = new oButton[operatorButtons.length];
    cButton controlButton[] = new cButton[controlButtons.length];
    
    Label displayLabel = new Label("0", Label.RIGHT);
    Label memoryLabel = new Label(" ", Label.RIGHT);
    
    final int frame_w = 325 , frame_h = 325;
    final int height = 30, width = 30;
    final int topx=30, topy=50;

    public NotACalculator(String frameText) throws HeadlessException 
    {
        super(frameText);
        int tempx=topx, tempy=topy;
        
        displayLabel.setBounds(tempx, tempy, 240, 30);
        displayLabel.setForeground(Color.ORANGE);
        displayLabel.setBackground(Color.BLACK);
        displayLabel.setFont(new Font("Terminal", Font.BOLD,14));
        add(displayLabel);
        memoryLabel.setBounds(topx, topy+(height+10), width, height);
        add(memoryLabel);
        
        tempx=70; //co-ordinates for the control buttons
        tempy=90;
        for(int i=0; i<controlButton.length;i++)
        {
            controlButton[i]=new cButton(tempx, tempy, width*2, height, controlButtons[i], this);
            controlButton[i].setForeground(Color.PINK);
            controlButton[i].setBackground(Color.BLACK);
            controlButton[i].setFont(new Font("Courier", Font.BOLD,12));
            tempx=tempx+2*width+10;
            
        }
        
        int digitx=70; //co-rdinates for digit buttons
        int digity=130;
        tempx = digitx; tempy=digity;
        for(int i=0; i<digitButton.length;i++) 
        {
            digitButton[i]=new dButton(tempx, tempy, width, height, digitButtons[i], this);
            digitButton[i].setForeground(Color.CYAN);
            digitButton[i].setBackground(Color.BLACK);
            digitButton[i].setFont(new Font("Courier", Font.BOLD,12));
            tempx+=width+10;
            if((i+1)%3==0){tempx=digitx; tempy+=40;}  
        }
        
        int opx=160; //co-rdinates for operator buttons
        int opy=130;
        tempx = opx; tempy=opy;
        for(int i=0; i<operatorButton.length;i++) 
        {
            tempx+=40;
            operatorButton[i]=new oButton(tempx, tempy, width, height, operatorButtons[i], this);
            operatorButton[i].setForeground(Color.GREEN);
            operatorButton[i].setBackground(Color.BLACK);
            operatorButton[i].setFont(new Font("Courier", Font.BOLD,12));
            if((i+1)%2==0) {tempx=opx; tempy+=40;}
        }
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}});
        
        setLayout(null);
        setSize(frame_w, frame_h);
        setVisible(true);
        setResizable(false);
    }
    
    static String getFormattedText (double temp)
    {
        String res = ""+temp;
        if(res.lastIndexOf(".0")>0)
            res=res.substring(0, res.length()-2);
        return res;
    }
    
    public static void main(String args[])
    {
        new NotACalculator("This is not a calculator.");
    }
}

