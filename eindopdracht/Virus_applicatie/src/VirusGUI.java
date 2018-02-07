import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Thijs Schoppema
 * @klas BIN-2a
 * @Studentennumeer 580144
 * @Datum 07-02-2018
 */
public class VirusGUI extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField fileName_textField, URL_textField;
	private JTextArea host2Text, host1Text, resultText;
	private JComboBox hostID1_comboBox, hostID2_comboBox, viralClass_comboBox;
	private JButton compare_btn, SearchDirectPath_btn, URL_btn, ReadFile_btn;
	private ButtonGroup group;
	private JFileChooser chooseFile;
	private JRadioButton iD, aantalHost, classificatie, soort;
	private Map<String, HashMap> virusHostMap;
	private Map<String, Set<Virus>> virusIdSet;


	/**
	 * Launch the application.
	 * Generieke Gui builde code
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VirusGUI frame = new VirusGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * Generieke Gui builde code
	 */
	public VirusGUI() {
		
		setTitle("Virus applicatie");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 925, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("File name:");
		lblNewLabel.setBounds(54, 86, 64, 29);
		contentPane.add(lblNewLabel);
		
		JLabel lblViralClassification = new JLabel("Viral classification:");
		lblViralClassification.setBounds(23, 175, 112, 29);
		contentPane.add(lblViralClassification);
		
		String[] classes = {"none", "dsDNA", "ssDNA", "ssRNA", "dsRNA",
							"Retro", "Satellites or virophages", "Viroid", "Others"};
		viralClass_comboBox = new JComboBox();
		viralClass_comboBox.setBounds(164, 177, 244, 25);
		viralClass_comboBox.setModel(new DefaultComboBoxModel(classes));
		contentPane.add(viralClass_comboBox);
		
		JLabel lblHostId = new JLabel("Host ID:");
		lblHostId.setBounds(36, 227, 99, 19);
		contentPane.add(lblHostId);
		
		hostID1_comboBox = new JComboBox();
		hostID1_comboBox.setBounds(164, 224, 244, 25);
		contentPane.add(hostID1_comboBox);
		
		hostID2_comboBox = new JComboBox();
		hostID2_comboBox.setBounds(488, 224, 244, 25);
		contentPane.add(hostID2_comboBox);
		
		JLabel lblSorteer = new JLabel("Sort options");
		lblSorteer.setBounds(54, 557, 119, 38);
		contentPane.add(lblSorteer);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 278, 400, 200);
		contentPane.add(scrollPane);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		scrollPane.setRowHeaderView(scrollBar_1);
		
		JLabel lblVirusesHost = new JLabel("Viruses host 1");
		scrollPane.setColumnHeaderView(lblVirusesHost);
		
		host1Text = new JTextArea();
		host1Text.setEditable(false);
		host1Text.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showMessageBox(arg0, 0);
			}
		});
		scrollPane.setViewportView(host1Text);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(488, 278, 400, 200);
		contentPane.add(scrollPane_1);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollPane_1.setRowHeaderView(scrollBar);
		
		JLabel lblVirusesHost_1 = new JLabel("Viruses host 2");
		scrollPane_1.setColumnHeaderView(lblVirusesHost_1);
		
		host2Text = new JTextArea();
		host2Text.setEditable(false);
		host2Text.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				showMessageBox(arg0, 1);
			}
		});
		scrollPane_1.setViewportView(host2Text);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(288, 521, 400, 200);
		contentPane.add(scrollPane_2);
		
		JLabel lblSimilarities = new JLabel("Similarities");
		scrollPane_2.setColumnHeaderView(lblSimilarities);
		
		JScrollBar scrollBar_2 = new JScrollBar();
		scrollPane_2.setRowHeaderView(scrollBar_2);
		
		resultText = new JTextArea();
		resultText.setEditable(false);
		resultText.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				showMessageBox(arg0, 2);
			}
		});
		scrollPane_2.setViewportView(resultText);
		
		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setBounds(54, 47, 64, 29);
		contentPane.add(lblUrl);
		
		URL_textField = new JTextField();
		URL_textField.setText("ftp://ftp.genome.jp/pub/db/virushostdb/virushostdb.tsv");
		URL_textField.setEditable(false);
		URL_textField.setColumns(10);
		URL_textField.setBounds(164, 52, 279, 24);
		contentPane.add(URL_textField);
		
		fileName_textField = new JTextField();
		fileName_textField.setBounds(164, 91, 279, 24);
		contentPane.add(fileName_textField);
		fileName_textField.setColumns(10);
		fileName_textField.setText("C:\\thijs\\blok2\\eindopdracht\\testdata.txt");
		
		compare_btn = new JButton("Compare");
		compare_btn.addActionListener(this);
		compare_btn.setBounds(485, 177, 247, 25);
		contentPane.add(compare_btn);
		
		SearchDirectPath_btn = new JButton("Search direct path");
		SearchDirectPath_btn.addActionListener(this);
		SearchDirectPath_btn.setBounds(510, 59, 178, 21);
		contentPane.add(SearchDirectPath_btn);
		
		URL_btn = new JButton("Use URL");
		URL_btn.addActionListener(this);
		URL_btn.setBounds(510, 28, 178, 21);
		contentPane.add(URL_btn);
		
		ReadFile_btn = new JButton("Read file");
		ReadFile_btn.addActionListener(this);
		ReadFile_btn.setBounds(510, 90, 178, 21);
		contentPane.add(ReadFile_btn);
		group = new ButtonGroup();
		
		iD = new JRadioButton("ID");
		iD.setBounds(54, 622, 119, 21);
		contentPane.add(iD);
		group.add(iD);
		iD.setSelected(true);
		
		aantalHost = new JRadioButton("Aantal hosts");
		aantalHost.setBounds(54, 599, 119, 21);
		contentPane.add(aantalHost);
		group.add(aantalHost);
		
		classificatie = new JRadioButton("Classificatie");
		classificatie.setBounds(54, 645, 119, 21);
		contentPane.add(classificatie);
		group.add(classificatie);
		
		soort = new JRadioButton("Soort");
		soort.setBounds(54, 668, 119, 21);
		group.add(soort);
		contentPane.add(soort);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * @param event
	 * @return null
	 * @functie neemt een button event op en roept de vereiste functies aan
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if (event.getSource() == compare_btn) {
			
			virusIdSet = VirusLogica.krijgVirusen((String) hostID1_comboBox.getSelectedItem(),(String) hostID2_comboBox.getSelectedItem(),(String) viralClass_comboBox.getSelectedItem());
			
	        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
		        AbstractButton button = buttons.nextElement();
		        if (button.isSelected()) {
					vulVirusBars(button.getText());
		        }
	        }
	
		} else if (event.getSource() == SearchDirectPath_btn) {
			searchDirectPath();
			
		} else if (event.getSource() == URL_btn) {
			try {
				
				virusHostMap = VirusLogica.readFile(0, URL_textField.getText());
				vulHostBars();
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "De webpagina " + URL_textField.getText() + " is niet gevonden", "Error",JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Er ontbreekt een virus.host ID in het bestand, dit moet altijd een getal zijn", "Error",JOptionPane.ERROR_MESSAGE);
			}
		} else if (event.getSource() == ReadFile_btn) {
			try {
				
				virusHostMap = VirusLogica.readFile(1, fileName_textField.getText());
				vulHostBars();
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Het bestand is niet gevonden", "Error",JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Er ontbreekt een virus/host ID in het bestand, dit moet altijd een getal zijn", "Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	/**
	 * @param sort, sort is een String afkomstig van de geselecteerde radio button
	 * @return null
	 * @functie Loopt over de HashSet en haalt de lijst van host1, host2 en de intersectie eruit. Deze sorteert
	 * hij en vervolgens haalt hij de ID van de virusen op en zet ze in de bijhorende textvelden
	 */
	private void vulVirusBars(String sort) {
		 
		 Set<Virus> virusSet;
		 String[] mapKey = {"ID1", "ID2", "Intersectoin"};
		 ArrayList<Virus> virusArray;
		 String x;
		 Virus b;
		 
		 for(String key : mapKey) {
			 
			 virusSet = virusIdSet.get(key);
			 virusArray = new ArrayList<Virus>();
			 virusArray.addAll(virusSet);
			 x = "";
			 
			 if (sort.equals("ID")) Collections.sort(virusArray, new IdComparator());
			 else if (sort.equals("Aantal hosts")) Collections.sort(virusArray, new NumHostsComparator());
			 else if (sort.equals("Classificatie")) Collections.sort(virusArray, new ClassificatieComparator());
			 else if (sort.equals("Soort")) Collections.sort(virusArray, new SoortComparator());
			 
			 for(int i = 0;i < virusArray.size(); i++) {
				 b = virusArray.get(i);
				 x += b.getId() + "\t"+ b.getSoort() + "\n";
			 }
			 
			 if(key == "ID1") host1Text.setText(x);
			 else if(key == "ID2") host2Text.setText(x);
			 else if(key == "Intersectoin") resultText.setText(x);
		 }
	}
	
	/**@return null
	 * @functie haalt de hosts op uit de Hashmap, zet ze in een ArrayList en sorteert ze vervolgens. Ten slotte worden de hosts aan de comboboxen toegevoegt.
	 */
	private void vulHostBars() {
		
		hostID1_comboBox.removeAllItems();
		hostID2_comboBox.removeAllItems();
		HashMap<String, Set<Integer>> host_virus = virusHostMap.get("host_virus");
		String[] buffer = host_virus.keySet().toString().replace("[", "").replace("]", "").split(", ");
		ArrayList<String> hostName = new ArrayList<String>(Arrays.asList(buffer));
		Collections.sort(hostName, new HostComparator());
		
		for(String naam: hostName) {
			hostID1_comboBox.addItem(naam);
			hostID2_comboBox.addItem(naam);
		}
	}

	/**
	 * @return null
	 * @functie opent een pop-up waar je een bestand in kan selecteren en voegt vervolgens het absolute pat in het textveld
	 */
	private void searchDirectPath() {
		
		File selectedFile;
	    int reply;
	    chooseFile = new JFileChooser();
	    reply = chooseFile.showOpenDialog(this);
	    
	    if (reply == JFileChooser.APPROVE_OPTION) {
	    	selectedFile = chooseFile.getSelectedFile();
	    	fileName_textField.setText(selectedFile.getAbsolutePath());        
	    }
	}
	
	/**@return null
	 * @param arg0
	 * @functie op basis van de locatie waar de muis clickt wordt er een message dialog gegenereed met daarin alle opgeslage informatie over het virus
	 */
	private void showMessageBox(MouseEvent arg0, int z) {
		
		try {
			JTextArea a = null;
			if (z == 0) a = host1Text;
			else if (z == 1) a = host2Text;
			else if (z == 2) a = resultText;
					
			int viewToModel = a.viewToModel(arg0.getPoint());
			int i = a.getLineOfOffset(viewToModel);
			String[] slist = a.getText().split("\n");
			HashMap<Integer, Virus> v = virusHostMap.get("id_virus");
			Virus virus = v.get(Integer.parseInt(slist[i].split("\t")[0]));
			slist = virus.getHostList().toString().replace("[", "").replace("]", "").split(", ");
			
			String text = "Virus ID = " + virus.getId() + "\n Hosts = " + slist[0].split("\\(")[1].replace(")", "");
			for (int y = 1; y < slist.length; y++) text += ", "+slist[y].split("\\(")[1].replace(")", "");
			text += "\n Soort = " + virus.getSoort();
			text += "\n Classificatie = " + virus.getClassificatie();
			if (virus.getPmid() != "" && virus.getPmid() != null) {
				text += "\n Pubmet artiel(en) = " + virus.getPmid();
			}
			
			JOptionPane.showMessageDialog(null, text, "Virus info", JOptionPane.PLAIN_MESSAGE);
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Het opgevraagde ID kan niet worden gevonden.", "Error",JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Het opgevraagde ID kan niet worden gevonden.", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}

/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */


