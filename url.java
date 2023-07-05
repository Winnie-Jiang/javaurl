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
URL 是统一资源定位符的首字母缩写，是互联网上资源的参考(地址)。
一个 URL 有两个主要组件，比如 URL http://example.com
        协议标识符：协议标识符是 http。
        资源名称：资源名称是 example.com。
Java 提供了 java.net.URL 类表示统一资源定位符，统一资源定位符的目的是获取资源，
因此 java.net.URL 同样也提供了获取资源的方法。
获取资源有两种方式，一种是通过 openConnection 方法先获取 URLConnection
然后通过 URLConnection 获取到更多的资源信息，
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
    private JLabel jlbinput=new JLabel("爬取网址:");
    private JLabel jlbhtml=new JLabel("html代码:");
    private JLabel jlbtxt=new JLabel("文本内容:");
    private JTextField jtfsite=new JTextField(25);
    private JScrollPane jspsite=new JScrollPane(jtfsite);
    private JButton jbtstart=new JButton("开始");
    private JTextArea jtahtml=new JTextArea(15, 25);
    private JScrollPane jsphtml=new JScrollPane(jtahtml);
    private JTextArea jtatext=new JTextArea(15,25);
    private JScrollPane jsptext=new JScrollPane(jtatext);
    private JTextArea jtaword=new JTextArea(8,25);
    private JScrollPane jspword=new JScrollPane(jtaword);
    private JButton jbtopen=new JButton(" 敏感词库");
    private JButton jbtmatch=new JButton("显示高亮");
    private JButton jbtsite=new JButton("网址库");
    private JComboBox<String> charitem=new JComboBox<String>();
    private String textType="UTF-8";

    private ArrayList<String> wordList=new ArrayList<String>();		//保存敏感词
    private ArrayList<Integer> wordNum=new ArrayList<Integer>();	//保存对应敏感词的出现次数
    //设置正则表达式的匹配符
    private String regExHtml="<[^>]+>";		//匹配标签
    private String regExScript = "<script[^>]*?>[\\s\\S]*?<\\/script>";		//匹配script标签
    private String regExStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>";		//匹配style标签
    private String regExSpace="[\\s]{2,}";	//匹配连续空格或回车等
    private String regExImg="&[\\S]*?;+";	//匹配网页上图案的乱码
    //定义正则表达式
    private Pattern pattern3=Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
    private Pattern pattern1=Pattern.compile(regExScript,Pattern.CASE_INSENSITIVE);
    private Pattern pattern2=Pattern.compile(regExStyle,Pattern.CASE_INSENSITIVE);
    private Pattern pattern4=Pattern.compile(regExSpace, Pattern.CASE_INSENSITIVE);
    private Pattern pattern5=Pattern.compile(regExImg,Pattern.CASE_INSENSITIVE);

    public url(String str) throws IOException {
        super(str);
        //设置界面风格
        this.setTitle("Spider");
        this.setLocation(200, 50);
        this.setSize(1000, 700);
//退出按钮操作
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    new start("Java爬虫");
                    dispose();
                }catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });        jtfsite.setText(str);
        setFrame();
        if(!str.equals("ALL")) new Single(this,str).start();
    }
    //界面
    public void setFrame(){

        jpl.setLayout(new BorderLayout());
        jpl.setBackground(new Color(234,207, 209));
        //输入网址,爬取按钮,以及编码方式选择
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
        jlbinput.setFont(new Font("宋体",Font.BOLD,13));
        //源代码文本,以及处理后的文本框设置
        jtahtml.setEditable(false);
        jtahtml.setLineWrap(true);
        jtahtml.setFont(new Font("宋体", Font.PLAIN, 14));
        jpl2.setLayout(new BorderLayout());
        jpl2.add(jsphtml,BorderLayout.CENTER);
        jpl2.setSize(900,280);
        //设置布局
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
        jtatext.setFont(new Font("宋体", Font.PLAIN, 14));
        jtatext.setEditable(false);
        jtatext.setLineWrap(true);
        jpl4.setLayout(new BorderLayout());
        jpl4.add(jsptext,BorderLayout.CENTER);
        jpl4.setSize(900,280);

        //展示区
        jpl7.setLayout(null);
        jpl7.add(jpl2);
        jpl2.setLocation(0,25);
        jpl7.add(jpl4);
        jpl4.setLocation(0,330);
        jpl7.add(jlbhtml);
        jpl7.add(jlbtxt);
        jlbhtml.setLocation(10,0);
        jlbhtml.setSize(100,25);
        jlbhtml.setFont(new Font("宋体", Font.BOLD, 14));
        jlbtxt.setLocation(10,310);
        jlbtxt.setSize(100,25);
        jlbtxt.setFont(new Font("宋体", Font.BOLD, 14));

        //导入区
        jpl6.setLayout(new BorderLayout());
        jpl6.add(jpl7,BorderLayout.CENTER);
        jpl6.add(jpl3,BorderLayout.WEST);

        jpl.add(jpl1,BorderLayout.NORTH);
        jpl.add(jpl6,BorderLayout.CENTER);

        //事件处理
        jbtstart.addActionListener(this);
        jbtsite.addActionListener(this);
        jbtopen.addActionListener(this);
        jbtmatch.addActionListener(this);

        this.add(jpl);
        this.setVisible(true);
    }
    @Override
    //按钮事件
    public void actionPerformed(ActionEvent e) {
        JButton j=(JButton)e.getSource();	//判断操作来源
        if (j==jbtopen) {		//打开敏感词库
            getLib();
        }else if (j==jbtmatch){	//匹配单个网址的敏感词高亮显示
            String[]s=jtaword.getText().split("\n");
            for(int i=0;i<s.length;i++){
                wordList.add(s[i]);
            }
            highlight();
        }
    }

    //核心部分：爬取网页的html代码
    public String getCode(String site) {

        String str=null;
        String code="";		//保存网页的内容
        try {
            URL url=new URL(site);	//建立URL对象
            URLConnection urlConne=url.openConnection();//建立与网站的连接
            urlConne.connect();//连接到指定的URL
            //获取网页的输入流
            BufferedReader br=new BufferedReader(new InputStreamReader(urlConne.getInputStream(),"UTF-8"));
            //System.out.println("开始爬取");
            while((str = br.readLine()) != null) {	//逐行读取网页内容到爬取结束
                code+=(str+"\n");
            }
            br.close();	//关闭输入流
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, site+"爬取源代码失败");
        }
        System.out.println("爬取结束");
        return code;	//返回html代码
    }

    //进行正则表达式的模式匹配,提取文本信息
    public String getText(String str) {

        Matcher matcher=pattern1.matcher(str);
        str=matcher.replaceAll("");//删除普通标签
        matcher=pattern2.matcher(str);
        str=matcher.replaceAll("");//删除script标签
        matcher=pattern3.matcher(str);
        str=matcher.replaceAll("");//删除style标签
        matcher=pattern4.matcher(str);
        str=matcher.replaceAll("\n");//删除连续回车或空格
        matcher=pattern5.matcher(str);
        str=matcher.replaceAll("");//删除网页图案乱码
        return str;		//返回文本
    }

    //读取敏感词汇
    public void getLib() {
        wordList.clear();//清空记录	
        jtaword.setText("");
        File f=new File("word.txt");;	//获取txt文件
       
        try {	//读取选中文件中的记录
            BufferedReader br=new BufferedReader(new FileReader(f));
            String str;
            while((str = br.readLine()) != null) {
                wordList.add(str);	//添加到记录中
                wordNum.add(0);		//设置对应的初始值
                jtaword.append(str+"\n");	//添加到界面中
            }
            br.close();	//关闭文件流
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "文件不存在");
            e1.printStackTrace();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "文件读取失败");
            e1.printStackTrace();
        }
    }

    //高亮显示
    public void highlight() {
        Highlighter hg=jtatext.getHighlighter();//设置文本框的高亮显示
        hg.removeAllHighlights();//清除之前的高亮显示记录
        String text=jtatext.getText();//得到文本框的文本
        DefaultHighlighter.DefaultHighlightPainter painter=new DefaultHighlighter.DefaultHighlightPainter(new Color(201,192,211));//设置高亮显示颜色
        for(String str:wordList) {//每一个敏感词
            int index=0;//记录匹配到的敏感词在文本中的索引位置
            while((index=text.indexOf(str,index))>=0) {
                try {
                    hg.addHighlight(index, index+str.length(), painter);	//高亮显示匹配到的词语
                    index+=str.length();	//更新为当前匹配到的敏感词的结束位置的下一个位置,继续匹配
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //网址库中的网址
    public void spiderAll() {

        if(wordNum.size()<=0) {		//判断是否选择了敏感词库
            JOptionPane.showMessageDialog(null, "请先选择敏感词库");
            return;
        }
        File file=new File("website.txt");
        new SpiderAll(this, file).start();	//开启多个网址的线程爬取
    }

    /*public static void main(String[] args) throws IOException {
        new url();
    }
*/

    //爬取一个网址
     class Single extends Thread{
        private String site=null;	//网页链接
        private MyProgressBar mpb=null;	//进度条
        //构造函数初始化
        public Single(JFrame fa,String s) {
            site=s;
            mpb=new MyProgressBar(fa, "爬取中");
        }

        public void run() {
            if(site.length()<=0) {	//判断网址是否正常
                JOptionPane.showMessageDialog(null, "网址不能为空");
                return;
            }
            jtahtml.setText("");
            jtatext.setText("");
            //设置进度条界面
            mpb.setText("爬取"+site+"中...");
            mpb.setVisible(true);

            String html=getCode(site);//开始爬取

            mpb.dispose();	//关闭进度条
            if(html.length()>0) {//若爬取正常
                //JOptionPane.showMessageDialog(null, "爬取完毕");
                jtahtml.append(html);	//将html源代码添加到jta中
                String text=getText(html);	//找网页文本
                jtatext.append(text);	//网页文本添加到jta中
            }
        }
    }

    //爬取多个网址
    class SpiderAll extends Thread{
        private File file=null;		//网址库文本文件
        private MyProgressBar mpb=null;		//进度条
        //构造函数初始化
        public SpiderAll(JFrame fa,File f) {
            file=f;
            mpb=new MyProgressBar(fa, "爬取中");
        }

        public void run() {
            try {
                //读取网址库中的网址
                BufferedReader brr=new BufferedReader(new FileReader(file));
                //将匹配数据写入文本中
                PrintStream ps=new PrintStream(new File("data.txt"));
                PrintStream ps2=new PrintStream(new File("getText.txt"));
                int size=wordList.size();
                mpb.setVisible(true);//显示进度条
                String site=null;
                while((site=brr.readLine())!=null) {
                    mpb.setText("爬取"+site+"中...");//设置进度条界面标题
                    ps.println(site+"数据如下: ");
                    String html=getCode(site);//获取html代码
                    jtahtml.append(html);	//将html源代码添加到jta中
                    String text=getText(html);//匹配网页文本
                    jtatext.append(text);	//网页文本添加到jta中
                    ps2.println(text);
                    for(int i=0;i<size;i++) {//在网页文本中进行匹配
                        String word=wordList.get(i);
                        int index=0,account=0,len=word.length();
                        while((index=text.indexOf(word,index))>=0) {
                            account++;
                            int temp=wordNum.get(i);//更新数据
                            wordNum.set(i,++temp);
                            index+=len;	//更新匹配条件
                        }
                        ps.println(word+"： "+account+"次");//写入当前数据
                    }
                    ps2.println();
                }
                brr.close();//关闭文件流
                System.out.println("爬取完毕");
                ps.println("数据统计:");	//写入总数据
                for(int i=0;i<size;i++) {
                    ps.println(wordList.get(i)+"： "+wordNum.get(i)+"次");
                }
                ps.close();		//关闭文件流
                //JOptionPane.showMessageDialog(null, "爬取完毕！请打开文件查看!");
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, "爬取失败");
            }finally {
                mpb.dispose();	//关闭进度条
            }
        }
    }
}

//进度条
class MyProgressBar extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel jpl=new JPanel();
    private JProgressBar jpb=new JProgressBar();	//进度条
    private JLabel jlb=new JLabel();	//显示当前网址
    //构造函数初始化,设置父窗口以及标题
    public MyProgressBar(JFrame f,String title) {
        super(f,title);
        this.setLocation(f.getWidth()/2+(int)f.getLocation().getX()/2-80, f.getHeight()/2+(int)f.getLocation().getY()/2-30);
        this.setSize(400,100);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jpb.setString("加载中...");
        jpb.setIndeterminate(true);		//设置进度条为不确定模式
        jpb.setStringPainted(true);
        jpb.setBorderPainted(false);
        jpb.setForeground(new Color(201, 192, 211));	//设置进度条颜色
        jpb.setBackground(Color.WHITE);	//设置背景
        jlb.setPreferredSize(new Dimension(400, 30));

        //界面布局
        jpl.setLayout(new BorderLayout());
        jpl.add(jlb,BorderLayout.NORTH);
        jpl.add(jpb,BorderLayout.CENTER);
        this.add(jpl);
    }

    //设置当前爬取网址接口
    public void setText(String text) {
        jlb.setText(text);
    }
}
