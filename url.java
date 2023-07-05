import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/*
URL ��ͳһ��Դ��λ��������ĸ��д���ǻ���������Դ�Ĳο�(��ַ)��
һ�� URL ��������Ҫ��������� URL http://example.com
        Э���ʶ����Э���ʶ���� http��
        ��Դ���ƣ���Դ������ example.com��
Java �ṩ�� java.net.URL ���ʾͳһ��Դ��λ����ͳһ��Դ��λ����Ŀ���ǻ�ȡ��Դ��
��� java.net.URL ͬ��Ҳ�ṩ�˻�ȡ��Դ�ķ�����
��ȡ��Դ�����ַ�ʽ��һ����ͨ�� openConnection �����Ȼ�ȡ URLConnection
Ȼ��ͨ�� URLConnection ��ȡ���������Դ��Ϣ��
 */
public class url extends JFrame implements ActionListener{

    private JPanel jpl=new JPanel();
    private JPanel jpl1=new JPanel();
    private JPanel jpl2=new JPanel();
    private JPanel jpl3=new JPanel();
    private JPanel jpl4=new JPanel();
    private JPanel jpl5=new JPanel();
    private JPanel jpl6=new JPanel();
    private JPanel jpl7=new JPanel();
    private JPanel jpl8=new JPanel();
    private JLabel jlbinput=new JLabel("��ȡ��ַ:");
    private JLabel jlbhtml=new JLabel("html����:");
    private JLabel jlbtxt=new JLabel("�ı�����:");
    private JTextField jtfsite=new JTextField(25);
    private JScrollPane jspsite=new JScrollPane(jtfsite);
    private JButton jbtstart=new JButton("��ʼ");
    private JTextArea jtahtml=new JTextArea(15, 25);
    private JScrollPane jsphtml=new JScrollPane(jtahtml);
    private JTextArea jtatext=new JTextArea(15,25);
    private JScrollPane jsptext=new JScrollPane(jtatext);
    private JTextArea jtaword=new JTextArea(8,25);
    private JScrollPane jspword=new JScrollPane(jtaword);
    private JButton jbtopen=new JButton(" ���дʿ�");
    private JButton jbtmatch=new JButton("��ʾ����");
    private JButton jbtsite=new JButton("��ַ��");
    private JComboBox<String> charitem=new JComboBox<String>();
    private String textType="UTF-8";

    private ArrayList<String> wordList=new ArrayList<String>();		//�������д�
    private ArrayList<Integer> wordNum=new ArrayList<Integer>();	//�����Ӧ���дʵĳ��ִ���
    //����������ʽ��ƥ���
    private String regExHtml="<[^>]+>";		//ƥ���ǩ
    private String regExScript = "<script[^>]*?>[\\s\\S]*?<\\/script>";		//ƥ��script��ǩ
    private String regExStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>";		//ƥ��style��ǩ
    private String regExSpace="[\\s]{2,}";	//ƥ�������ո��س���
    private String regExImg="&[\\S]*?;+";	//ƥ����ҳ��ͼ��������
    //����������ʽ
    private Pattern pattern3=Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
    private Pattern pattern1=Pattern.compile(regExScript,Pattern.CASE_INSENSITIVE);
    private Pattern pattern2=Pattern.compile(regExStyle,Pattern.CASE_INSENSITIVE);
    private Pattern pattern4=Pattern.compile(regExSpace, Pattern.CASE_INSENSITIVE);
    private Pattern pattern5=Pattern.compile(regExImg,Pattern.CASE_INSENSITIVE);

    public url(String str) throws IOException {
        super(str);
        //���ý�����
        this.setTitle("Spider");
        this.setLocation(200, 50);
        this.setSize(1000, 700);
//�˳���ť����
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    new start("Java����");
                    dispose();
                }catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });        jtfsite.setText(str);
        setFrame();
        if(!str.equals("ALL")) new Single(this,str).start();
    }
    //����
    public void setFrame(){

        jpl.setLayout(new BorderLayout());
        jpl.setBackground(new Color(234,207, 209));
        //������ַ,��ȡ��ť,�Լ����뷽ʽѡ��
        jpl1.setLayout(new BorderLayout());
        jlbinput.setPreferredSize(new Dimension(70,30));
        jspsite.setPreferredSize(new Dimension(300, 30));
        jbtstart.setPreferredSize(new Dimension(90, 30));
        jpl5.setLayout(new GridLayout(1, 2, 10,10));
        jpl5.add(jbtstart);
        jbtstart.setBackground(new Color(201,192,211));
        jpl5.add(jbtsite);
        jbtsite.setBackground(new Color(201,192,211));
        jpl1.add(jlbinput,BorderLayout.WEST);
        jpl1.add(jspsite,BorderLayout.CENTER);
        //jpl1.add(jpl5,BorderLayout.EAST);
        jlbinput.setFont(new Font("����",Font.BOLD,13));
        //Դ�����ı�,�Լ��������ı�������
        jtahtml.setEditable(false);
        jtahtml.setLineWrap(true);
        jtahtml.setFont(new Font("����", Font.PLAIN, 14));
        jpl2.setLayout(new BorderLayout());
        jpl2.add(jsphtml,BorderLayout.CENTER);
        jpl2.setSize(900,280);
        //���ò���
        jpl8.setLayout(new BorderLayout());
        jpl8.add(jbtopen,BorderLayout.CENTER);
        jbtopen.setBackground(new Color(201,192,211));
        jpl3.setLayout(new BorderLayout());
        jtaword.setLineWrap(true);
        jtaword.setEditable(true);
        jspword.setPreferredSize(new Dimension(6, 400));
        jpl3.add(jpl8,BorderLayout.NORTH);
        jpl3.add(jspword,BorderLayout.CENTER);
        jpl3.add(jbtmatch,BorderLayout.SOUTH);
        jbtmatch.setBackground(new Color(201,192,211));
        jtatext.setFont(new Font("����", Font.PLAIN, 14));
        jtatext.setEditable(false);
        jtatext.setLineWrap(true);
        jpl4.setLayout(new BorderLayout());
        jpl4.add(jsptext,BorderLayout.CENTER);
        jpl4.setSize(900,280);

        //չʾ��
        jpl7.setLayout(null);
        jpl7.add(jpl2);
        jpl2.setLocation(0,25);
        jpl7.add(jpl4);
        jpl4.setLocation(0,330);
        jpl7.add(jlbhtml);
        jpl7.add(jlbtxt);
        jlbhtml.setLocation(10,0);
        jlbhtml.setSize(100,25);
        jlbhtml.setFont(new Font("����", Font.BOLD, 14));
        jlbtxt.setLocation(10,310);
        jlbtxt.setSize(100,25);
        jlbtxt.setFont(new Font("����", Font.BOLD, 14));

        //������
        jpl6.setLayout(new BorderLayout());
        jpl6.add(jpl7,BorderLayout.CENTER);
        jpl6.add(jpl3,BorderLayout.WEST);

        jpl.add(jpl1,BorderLayout.NORTH);
        jpl.add(jpl6,BorderLayout.CENTER);

        //�¼�����
        jbtstart.addActionListener(this);
        jbtsite.addActionListener(this);
        jbtopen.addActionListener(this);
        jbtmatch.addActionListener(this);

        this.add(jpl);
        this.setVisible(true);
    }
    @Override
    //��ť�¼�
    public void actionPerformed(ActionEvent e) {
        JButton j=(JButton)e.getSource();	//�жϲ�����Դ
        if (j==jbtopen) {		//�����дʿ�
            getLib();
        }else if (j==jbtmatch){	//ƥ�䵥����ַ�����дʸ�����ʾ
            String[]s=jtaword.getText().split("\n");
            for(int i=0;i<s.length;i++){
                wordList.add(s[i]);
            }
            highlight();
        }
    }

    //���Ĳ��֣���ȡ��ҳ��html����
    public String getCode(String site) {

        String str=null;
        String code="";		//������ҳ������
        try {
            URL url=new URL(site);	//����URL����
            URLConnection urlConne=url.openConnection();//��������վ������
            urlConne.connect();//���ӵ�ָ����URL
            //��ȡ��ҳ��������
            BufferedReader br=new BufferedReader(new InputStreamReader(urlConne.getInputStream(),"UTF-8"));
            //System.out.println("��ʼ��ȡ");
            while((str = br.readLine()) != null) {	//���ж�ȡ��ҳ���ݵ���ȡ����
                code+=(str+"\n");
            }
            br.close();	//�ر�������
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, site+"��ȡԴ����ʧ��");
        }
        System.out.println("��ȡ����");
        return code;	//����html����
    }

    //����������ʽ��ģʽƥ��,��ȡ�ı���Ϣ
    public String getText(String str) {

        Matcher matcher=pattern1.matcher(str);
        str=matcher.replaceAll("");//ɾ����ͨ��ǩ
        matcher=pattern2.matcher(str);
        str=matcher.replaceAll("");//ɾ��script��ǩ
        matcher=pattern3.matcher(str);
        str=matcher.replaceAll("");//ɾ��style��ǩ
        matcher=pattern4.matcher(str);
        str=matcher.replaceAll("\n");//ɾ�������س���ո�
        matcher=pattern5.matcher(str);
        str=matcher.replaceAll("");//ɾ����ҳͼ������
        return str;		//�����ı�
    }

    //��ȡ���дʻ�
    public void getLib() {
        wordList.clear();//��ռ�¼	
        jtaword.setText("");
        File f=new File("word.txt");;	//��ȡtxt�ļ�
       
        try {	//��ȡѡ���ļ��еļ�¼
            BufferedReader br=new BufferedReader(new FileReader(f));
            String str;
            while((str = br.readLine()) != null) {
                wordList.add(str);	//��ӵ���¼��
                wordNum.add(0);		//���ö�Ӧ�ĳ�ʼֵ
                jtaword.append(str+"\n");	//��ӵ�������
            }
            br.close();	//�ر��ļ���
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "�ļ�������");
            e1.printStackTrace();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "�ļ���ȡʧ��");
            e1.printStackTrace();
        }
    }

    //������ʾ
    public void highlight() {
        Highlighter hg=jtatext.getHighlighter();//�����ı���ĸ�����ʾ
        hg.removeAllHighlights();//���֮ǰ�ĸ�����ʾ��¼
        String text=jtatext.getText();//�õ��ı�����ı�
        DefaultHighlighter.DefaultHighlightPainter painter=new DefaultHighlighter.DefaultHighlightPainter(new Color(201,192,211));//���ø�����ʾ��ɫ
        for(String str:wordList) {//ÿһ�����д�
            int index=0;//��¼ƥ�䵽�����д����ı��е�����λ��
            while((index=text.indexOf(str,index))>=0) {
                try {
                    hg.addHighlight(index, index+str.length(), painter);	//������ʾƥ�䵽�Ĵ���
                    index+=str.length();	//����Ϊ��ǰƥ�䵽�����дʵĽ���λ�õ���һ��λ��,����ƥ��
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //��ַ���е���ַ
    public void spiderAll() {

        if(wordNum.size()<=0) {		//�ж��Ƿ�ѡ�������дʿ�
            JOptionPane.showMessageDialog(null, "����ѡ�����дʿ�");
            return;
        }
        File file=new File("website.txt");
        new SpiderAll(this, file).start();	//���������ַ���߳���ȡ
    }

    /*public static void main(String[] args) throws IOException {
        new url();
    }
*/

    //��ȡһ����ַ
     class Single extends Thread{
        private String site=null;	//��ҳ����
        private MyProgressBar mpb=null;	//������
        //���캯����ʼ��
        public Single(JFrame fa,String s) {
            site=s;
            mpb=new MyProgressBar(fa, "��ȡ��");
        }

        public void run() {
            if(site.length()<=0) {	//�ж���ַ�Ƿ�����
                JOptionPane.showMessageDialog(null, "��ַ����Ϊ��");
                return;
            }
            jtahtml.setText("");
            jtatext.setText("");
            //���ý���������
            mpb.setText("��ȡ"+site+"��...");
            mpb.setVisible(true);

            String html=getCode(site);//��ʼ��ȡ

            mpb.dispose();	//�رս�����
            if(html.length()>0) {//����ȡ����
                //JOptionPane.showMessageDialog(null, "��ȡ���");
                jtahtml.append(html);	//��htmlԴ������ӵ�jta��
                String text=getText(html);	//����ҳ�ı�
                jtatext.append(text);	//��ҳ�ı���ӵ�jta��
            }
        }
    }

    //��ȡ�����ַ
    class SpiderAll extends Thread{
        private File file=null;		//��ַ���ı��ļ�
        private MyProgressBar mpb=null;		//������
        //���캯����ʼ��
        public SpiderAll(JFrame fa,File f) {
            file=f;
            mpb=new MyProgressBar(fa, "��ȡ��");
        }

        public void run() {
            try {
                //��ȡ��ַ���е���ַ
                BufferedReader brr=new BufferedReader(new FileReader(file));
                //��ƥ������д���ı���
                PrintStream ps=new PrintStream(new File("data.txt"));
                PrintStream ps2=new PrintStream(new File("getText.txt"));
                int size=wordList.size();
                mpb.setVisible(true);//��ʾ������
                String site=null;
                while((site=brr.readLine())!=null) {
                    mpb.setText("��ȡ"+site+"��...");//���ý������������
                    ps.println(site+"��������: ");
                    String html=getCode(site);//��ȡhtml����
                    jtahtml.append(html);	//��htmlԴ������ӵ�jta��
                    String text=getText(html);//ƥ����ҳ�ı�
                    jtatext.append(text);	//��ҳ�ı���ӵ�jta��
                    ps2.println(text);
                    for(int i=0;i<size;i++) {//����ҳ�ı��н���ƥ��
                        String word=wordList.get(i);
                        int index=0,account=0,len=word.length();
                        while((index=text.indexOf(word,index))>=0) {
                            account++;
                            int temp=wordNum.get(i);//��������
                            wordNum.set(i,++temp);
                            index+=len;	//����ƥ������
                        }
                        ps.println(word+"�� "+account+"��");//д�뵱ǰ����
                    }
                    ps2.println();
                }
                brr.close();//�ر��ļ���
                System.out.println("��ȡ���");
                ps.println("����ͳ��:");	//д��������
                for(int i=0;i<size;i++) {
                    ps.println(wordList.get(i)+"�� "+wordNum.get(i)+"��");
                }
                ps.close();		//�ر��ļ���
                //JOptionPane.showMessageDialog(null, "��ȡ��ϣ�����ļ��鿴!");
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, "��ȡʧ��");
            }finally {
                mpb.dispose();	//�رս�����
            }
        }
    }
}

//������
class MyProgressBar extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel jpl=new JPanel();
    private JProgressBar jpb=new JProgressBar();	//������
    private JLabel jlb=new JLabel();	//��ʾ��ǰ��ַ
    //���캯����ʼ��,���ø������Լ�����
    public MyProgressBar(JFrame f,String title) {
        super(f,title);
        this.setLocation(f.getWidth()/2+(int)f.getLocation().getX()/2-80, f.getHeight()/2+(int)f.getLocation().getY()/2-30);
        this.setSize(400,100);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jpb.setString("������...");
        jpb.setIndeterminate(true);		//���ý�����Ϊ��ȷ��ģʽ
        jpb.setStringPainted(true);
        jpb.setBorderPainted(false);
        jpb.setForeground(new Color(201, 192, 211));	//���ý�������ɫ
        jpb.setBackground(Color.WHITE);	//���ñ���
        jlb.setPreferredSize(new Dimension(400, 30));

        //���沼��
        jpl.setLayout(new BorderLayout());
        jpl.add(jlb,BorderLayout.NORTH);
        jpl.add(jpb,BorderLayout.CENTER);
        this.add(jpl);
    }

    //���õ�ǰ��ȡ��ַ�ӿ�
    public void setText(String text) {
        jlb.setText(text);
    }
}
