package Download;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import javax.swing.JOptionPane;
import javax.swing.JComboBox;

// 测试下载地址    https://d1.music.126.net/dmusic/cloudmusicsetup_2.5.2.196939.exe
public class ThreadDownLoadGUI extends JFrame implements ActionListener{
	private int num=1;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private String name;
	private static int index;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThreadDownLoadGUI frame = new ThreadDownLoadGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ThreadDownLoadGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 703, 238);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblPath = new JLabel("Path:");
		lblPath.setFont(new Font("Arial Black", Font.ITALIC, 24));
		lblPath.setBounds(14, 13, 72, 26);
		
		contentPane.add(lblPath);
		
		textField = new JTextField();
		textField.setFont(new Font("宋体", Font.PLAIN, 26));
		textField.setBounds(102, 13, 467, 30);
		contentPane.add(textField);
		textField.setText("https://d1.music.126.net/dmusic/cloudmusicsetup_2.5.2.196939.exe");
		textField.setColumns(10);
		
		JLabel lblSavepath = new JLabel("SavePath:");
		lblSavepath.setFont(new Font("Arial Black", Font.ITALIC, 24));
		lblSavepath.setBounds(14, 88, 140, 30);
		contentPane.add(lblSavepath);
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setFont(new Font("仿宋", Font.ITALIC, 24));
		textField_1.setBounds(153, 88, 436, 40);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnFind = new JButton("Find");
		btnFind.setBounds(603, 88, 68, 40);
		btnFind.setActionCommand("open");
		btnFind.addActionListener(this);
		contentPane.add(btnFind);
		String ThreadNum[]= {"1","2","3","4","5"};		
		JComboBox comboBox = new JComboBox(ThreadNum);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				num=Integer.parseInt((String) comboBox.getSelectedItem());
			}
			
		});
		comboBox.setBounds(581, 16, 37, 30);
		
	
		contentPane.add(comboBox);
		
		JLabel label = new JLabel("\u4E2A\u7EBF\u7A0B");
		label.setBounds(617, 18, 68, 27);
		contentPane.add(label);
		
		JButton button = new JButton("\u5F00\u59CB\u4E0B\u8F7D");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url=textField.getText();
				name=textField_1.getText();
				if(name==null||name.equals("")) {
					JOptionPane.showMessageDialog(null, "保存地址无效");
					return;
				}else {
					downloadTest(url);
				}
				
				
			}
		});
		button.setBounds(286, 151, 113, 27);
		contentPane.add(button);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 if(e.getActionCommand().equals("open")) {
			 JFileChooser jf = new JFileChooser();
			 jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			 int flag=-1;
			 String savePath=null;
			 try {
				 flag=jf.showOpenDialog(this);
				
			 }catch(Exception e1) {
				 e1.printStackTrace();
			 }finally {
				 if(flag==JFileChooser.APPROVE_OPTION) {   //如果是确定按钮
					
					 File f=jf.getSelectedFile();
				     savePath=f.getAbsolutePath();
					
				 }
			 }
			 if(savePath!=null) {
				 textField_1.setText(savePath); 
			 }
			 
			
		 }
	}
	
	public void downloadTest(String url) {
		InputStream io=null;
		BufferedInputStream bio=null;
		BufferedOutputStream bpo=null;
		HttpURLConnection con=null;
		 try {
			 URL file=new URL(url);
			String path=file.getPath();
			name=path.substring(path.lastIndexOf("/")+1);
			con=(HttpURLConnection)file.openConnection();
			con.connect();
			if(!(con.getResponseCode()==200)) {
				JOptionPane.showMessageDialog(null, "地址无效");
				return;
			}else {
				
				 io=con.getInputStream();
				 bio=new BufferedInputStream(io);
				 long fileLength=con.getContentLengthLong();
				 
				String savePath=textField_1.getText();
				new ThreadsDownLoad(url, savePath, num, fileLength, name).start();
				
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
				try {
					if(bio!=null) {
					bio.close();
					}
					if(bpo!=null) {
						bpo.close();
					}
					if(con!=null) {
						con.disconnect();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	
	
	
}
//测试下载地址    https://d1.music.126.net/dmusic/cloudmusicsetup_2.5.2.196939.exe