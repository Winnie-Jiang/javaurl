
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class start extends JFrame  implements ActionListener {
    private JPanel jpl = new JPanel();//����һ��JPanel����//���
    private JTextField jtfz = new JTextField();//�˺�

    private JLabel jlb = new JLabel("JAVA�������");
    private JLabel jlbz = new JLabel("������ַ��");
    private JButton jbty = new JButton("ȷ��");
    private JButton jbtn = new JButton("ȡ��");
    private JButton jbtsite = new JButton("��ַ��");

    //���캯�������ڵ����
    public start(String str) throws Exception {
        super(str);
        //�������
        this.setLocation(400, 100);
        this.setSize(500, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(jpl);//�����ӵ�������
        jpl.setLayout(null);//���ַ�ʽ������
        this.setVisible(true);
        //��ӭ����
        jpl.add(jlb);
        jlb.setSize(500, 100);
        jlb.setLocation(140, 22);
        jlb.setFont(new Font("����", Font.BOLD, 35));
        //�ǳ�
        jpl.add(jlbz);
        jlbz.setSize(150, 30);
        jlbz.setLocation(40, 122);
        jlbz.setFont(new Font("����", Font.BOLD, 25));
        jpl.add(jtfz);
        jtfz.setSize(250, 30);
        jtfz.setLocation(160, 122);
        jtfz.setFont(new Font("����", Font.BOLD, 25));
        //��ť
        jpl.add(jbtsite);
        jbtsite.setSize(280,30);
        jbtsite.addActionListener(this);
        jbtsite.setLocation(100, 182);
        jbtsite.setBackground(new Color(201, 192, 211));
        jbtsite.setFont(new Font("����", Font.BOLD, 20));
        jpl.add(jbty);
        jbty.setSize(80, 30);
        jbty.setLocation(100, 252);
        jbty.setBackground(new Color(201, 192, 211));
        jbty.setFont(new Font("����", Font.BOLD, 20));
        jbty.addActionListener(this);//������
        jpl.add(jbtn);
        jbtn.setSize(80, 30);
        jbtn.setLocation(300, 252);
        jbtn.setBackground(new Color(201, 192, 211));
        jbtn.setFont(new Font("����", Font.BOLD, 20));
        jbtn.addActionListener(this);//������
    }

    @Override
    //��ť�¼�����
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==jbtn){
            System.exit(0);
        }
        if(e.getSource()==jbty){
            try {
                if(jtfz.getText().length()<=0) {	//�ж���ַ�Ƿ�����
                    JOptionPane.showMessageDialog(null, "��ַ����Ϊ��");
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
        new start("Java����");
    }
}
