
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class start extends JFrame  implements ActionListener {
    private JPanel jpl = new JPanel();//创建一个JPanel对象//面板
    private JTextField jtfz = new JTextField();//账号

    private JLabel jlb = new JLabel("JAVA爬虫软件");
    private JLabel jlbz = new JLabel("输入网址：");
    private JButton jbty = new JButton("确认");
    private JButton jbtn = new JButton("取消");
    private JButton jbtsite = new JButton("网址库");

    //构造函数：窗口的设计
    public start(String str) throws Exception {
        super(str);
        //窗口设计
        this.setLocation(400, 100);
        this.setSize(500, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(jpl);//面板添加到容器中
        jpl.setLayout(null);//布局方式，任意
        this.setVisible(true);
        //欢迎字体
        jpl.add(jlb);
        jlb.setSize(500, 100);
        jlb.setLocation(140, 22);
        jlb.setFont(new Font("楷体", Font.BOLD, 35));
        //昵称
        jpl.add(jlbz);
        jlbz.setSize(150, 30);
        jlbz.setLocation(40, 122);
        jlbz.setFont(new Font("楷体", Font.BOLD, 25));
        jpl.add(jtfz);
        jtfz.setSize(250, 30);
        jtfz.setLocation(160, 122);
        jtfz.setFont(new Font("楷体", Font.BOLD, 25));
        //按钮
        jpl.add(jbtsite);
        jbtsite.setSize(280,30);
        jbtsite.addActionListener(this);
        jbtsite.setLocation(100, 182);
        jbtsite.setBackground(new Color(201, 192, 211));
        jbtsite.setFont(new Font("宋体", Font.BOLD, 20));
        jpl.add(jbty);
        jbty.setSize(80, 30);
        jbty.setLocation(100, 252);
        jbty.setBackground(new Color(201, 192, 211));
        jbty.setFont(new Font("宋体", Font.BOLD, 20));
        jbty.addActionListener(this);//监听绑定
        jpl.add(jbtn);
        jbtn.setSize(80, 30);
        jbtn.setLocation(300, 252);
        jbtn.setBackground(new Color(201, 192, 211));
        jbtn.setFont(new Font("宋体", Font.BOLD, 20));
        jbtn.addActionListener(this);//监听绑定
    }

    @Override
    //按钮事件监听
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==jbtn){
            System.exit(0);
        }
        if(e.getSource()==jbty){
            try {
                if(jtfz.getText().length()<=0) {	//判断网址是否正常
                    JOptionPane.showMessageDialog(null, "网址不能为空");
                    return;
                }
                url u=new url(jtfz.getText());
                dispose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==jbtsite){
            try {
                url u=new url("ALL");
                u.getLib();
                u.spiderAll();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public static void main(String[] args) throws Exception   {
        new start("Java爬虫");
    }
}
