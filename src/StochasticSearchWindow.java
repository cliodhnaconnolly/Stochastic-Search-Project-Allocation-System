/* Encoka St. - Stochastic Search Project Allocation System */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class StochasticSearchWindow {

	private final int DEFAULT_GA = 1;
	private final int DEFAULT_SA = 10;
	
	private JFrame frmStochasticSearch;
	private JTextField textField;
	
	private String filename;
	private CandidateSolution bestSol;
	private JButton btnDownload;
	private JButton btnDownload2;
	private JButton btnDownload3;
	private JButton btnRunAgain;
	private JButton BrowseBtn;
	private JButton SubmitBtn;
	private JButton btnChangeParamters;	
	
	private ProjectSolver worker;
	private JProgressBar progressBar;
	
	private JLabel emoji;
	private JLabel lblOverallDisappointment;
	private JLabel lblNumberOfPreassigned;
	private JLabel lblNumberOfFirst;
	private JLabel lblValidSolutionObtained;
	private JLabel lblActualProjectName;
	
	private PreferenceTable prefs;
	private int timesToRunSA = DEFAULT_SA;
	private int timesToRunGA = DEFAULT_GA;
	private boolean runSlowSA = false;
	private ChangeParameters chngPar = null;
	
		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StochasticSearchWindow window = new StochasticSearchWindow();
					window.frmStochasticSearch.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public StochasticSearchWindow() {
		initialize();
		setUpListeners();
	}

	
	private void initialize() {
		// So that it looks the same on different platforms
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		frmStochasticSearch = new JFrame();
		frmStochasticSearch.setIconImage(Toolkit.getDefaultToolkit().getImage(StochasticSearchWindow.class.getResource("/resources/EncokaStLogo.png")));
		frmStochasticSearch.setTitle("Stochastic Search Project Allocation System \u00A9 2016 Encoka St.");
		frmStochasticSearch.setBounds(100, 100, 617, 401);
		frmStochasticSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStochasticSearch.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(24, 31, 322, 20);
		frmStochasticSearch.getContentPane().add(textField);
		textField.setColumns(10);
		
		BrowseBtn = new JButton("Browse...");
		BrowseBtn.setBounds(356, 30, 95, 23);
		frmStochasticSearch.getContentPane().add(BrowseBtn);
		
		JLabel lblEnterStudentPreferences = new JLabel("Enter Student Preferences (.tsv)");
		lblEnterStudentPreferences.setBounds(24, 11, 199, 14);
		frmStochasticSearch.getContentPane().add(lblEnterStudentPreferences);
		
		SubmitBtn = new JButton("Submit");
		SubmitBtn.setBounds(461, 30, 91, 23);
		frmStochasticSearch.getContentPane().add(SubmitBtn);
		
		progressBar = new JProgressBar(0,100);
		progressBar.setBounds(161, 77, 401, 14);
		progressBar.setStringPainted(true);
		frmStochasticSearch.getContentPane().add(progressBar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 62, 584, 4);
		frmStochasticSearch.getContentPane().add(separator);
		
		JLabel lblGeneratingSolutions = new JLabel("Generating Solutions");
		lblGeneratingSolutions.setBounds(20, 77, 131, 14);
		frmStochasticSearch.getContentPane().add(lblGeneratingSolutions);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 102, 584, 4);
		frmStochasticSearch.getContentPane().add(separator_1);
		
		JLabel lblResults = new JLabel("Results");
		lblResults.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
		lblResults.setBounds(10, 114, 78, 23);
		frmStochasticSearch.getContentPane().add(lblResults);
		
		JLabel lblResultstsv = new JLabel("results.tsv");
		lblResultstsv.setBounds(24, 148, 64, 14);
		frmStochasticSearch.getContentPane().add(lblResultstsv);
		
		JLabel lblStudentswithoutpreftsv = new JLabel("studentsWithoutPref.tsv");
		lblStudentswithoutpreftsv.setBounds(24, 173, 153, 14);
		frmStochasticSearch.getContentPane().add(lblStudentswithoutpreftsv);
		
		JLabel lblOverallstatstxt = new JLabel("overallStats.txt");
		lblOverallstatstxt.setBounds(24, 198, 91, 14);
		frmStochasticSearch.getContentPane().add(lblOverallstatstxt);
		
		JLabel lblGeneralStatistics = new JLabel("General Statistics");
		lblGeneralStatistics.setFont(new Font("Segoe UI Symbol", Font.BOLD, 17));
		lblGeneralStatistics.setBounds(10, 219, 186, 23);
		frmStochasticSearch.getContentPane().add(lblGeneralStatistics);
		
		lblOverallDisappointment = new JLabel("Overall Disappointment...");
		lblOverallDisappointment.setBounds(24, 249, 172, 14);
		frmStochasticSearch.getContentPane().add(lblOverallDisappointment);
		
		lblNumberOfFirst = new JLabel("Number of first preferences achieved...");
		lblNumberOfFirst.setBounds(24, 274, 275, 14);
		frmStochasticSearch.getContentPane().add(lblNumberOfFirst);
		
		lblNumberOfPreassigned = new JLabel("Number of pre-assigned projects...");
		lblNumberOfPreassigned.setBounds(24, 299, 275, 14);
		frmStochasticSearch.getContentPane().add(lblNumberOfPreassigned);
		
		JLabel lblMostPopularProject = new JLabel("Most popular project...");
		lblMostPopularProject.setBounds(24, 324, 275, 14);
		frmStochasticSearch.getContentPane().add(lblMostPopularProject);
		
		JLabel lblGraphicalRepresentationOf = new JLabel(" Graphical Representation of Disappointment");
		lblGraphicalRepresentationOf.setBounds(311, 136, 253, 14);
		frmStochasticSearch.getContentPane().add(lblGraphicalRepresentationOf);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(311, 161, 251, 1);
		frmStochasticSearch.getContentPane().add(separator_2);
		
		emoji = new JLabel("");
		emoji.setBounds(399, 173, 64, 73);
		frmStochasticSearch.getContentPane().add(emoji);
		
		lblValidSolutionObtained = new JLabel("Valid Solution Obtained?");
		lblValidSolutionObtained.setBounds(356, 253, 172, 14);
		frmStochasticSearch.getContentPane().add(lblValidSolutionObtained);
		
		btnChangeParamters = new JButton("Change Parameters");
		btnChangeParamters.setBounds(356, 288, 151, 29);
		frmStochasticSearch.getContentPane().add(btnChangeParamters);
		
		btnRunAgain = new JButton("Run Again");
		btnRunAgain.setBounds(356, 321, 151, 29);
		frmStochasticSearch.getContentPane().add(btnRunAgain);
		
		btnDownload = new JButton("Download");
		btnDownload.setBounds(187, 139, 95, 23);
		btnDownload.setToolTipText("Not available yet");
		frmStochasticSearch.getContentPane().add(btnDownload);
		
		btnDownload2 = new JButton("Download");
		btnDownload2.setBounds(187, 164, 95, 23);
		btnDownload2.setToolTipText("Not available yet");
		frmStochasticSearch.getContentPane().add(btnDownload2);
		
		btnDownload3 = new JButton("Download");
		btnDownload3.setBounds(187, 189, 95, 23);
		btnDownload3.setToolTipText("Not available yet");
		frmStochasticSearch.getContentPane().add(btnDownload3);
		
		lblActualProjectName = new JLabel("");
		lblActualProjectName.setBounds(24, 338, 322, 14);
		frmStochasticSearch.getContentPane().add(lblActualProjectName);
	}

	private void setUpListeners(){
		
		BrowseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter tsvFilter = new FileNameExtensionFilter("tsv files only", "tsv");
				fc.setFileFilter(tsvFilter);
				fc.showOpenDialog(fc);
				try{
					filename = fc.getSelectedFile().getAbsolutePath();
					String extension = filename.substring(filename.lastIndexOf("."), filename.length());
					if(!extension.equals(".tsv")){
						filename = null;
						textField.setText("FILENAME NOT VALID, TSV FILES ONLY");
					} else { textField.setText(filename); }
				} catch ( NullPointerException e) {}
				
				
			}
		});
		
		SubmitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(filename != null &&(worker == null || worker.isDone()))
				runningProgram();
			}
		});
		
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filename = textField.getText();
			}
		});
		
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(worker!= null && worker.isDone()){
					JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = fc.showSaveDialog(fc);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						File file = new File(fc.getSelectedFile(), "results.tsv");
						try {
							FileWriter fw = new FileWriter(file);
							fw.write("Results - Stochastic Search Project Allocation System © 2016 Encoka St." +  bestSol.results());
							fw.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
					}
				}    
			}
		});
		
		btnRunAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(filename != null && (worker == null || worker.isDone()))
				runningProgram();
			}
		});
	
		btnDownload2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(worker != null && worker.isDone()){
					JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = fc.showSaveDialog(fc);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						File file = new File(fc.getSelectedFile(), "studentsWithoutPref.tsv");
						try {
							FileWriter fw = new FileWriter(file);
							fw.write(bestSol.getStudentsWithOutPref(prefs));
							fw.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
					}
				} 
			}
		});
		
		btnDownload3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(worker != null && worker.isDone()){
					JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = fc.showSaveDialog(fc);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						File file = new File(fc.getSelectedFile(), "overallStats.txt");
						try {
							FileWriter fw = new FileWriter(file);
							String overallStats = "OverallStats of Stochastic Search Project Allocation System";
							overallStats += "\r\n---------------------------------------------------------------";
							overallStats += "\r\n\nOverall Disappointment = " + bestSol.getEnergy();
							overallStats += "\r\nNumber of students = " + prefs.getNumberOfStudents();
							overallStats +="\r\nNumber of projects = " + prefs.getNumberOfProjects();
							overallStats += bestSol.getNumberOfPreferences();
							overallStats += "\r\nNumber of pre-assigned projects = " + prefs.getNumberPreAssignedProjects();
							overallStats += "\r\nMost popular project = " + prefs.getMostPopularProject();
							fw.write(overallStats);
							fw.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
					}
				} 
			}
		});
	
		btnChangeParamters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							if(chngPar == null){
								chngPar = new ChangeParameters();
							} 							
							chngPar.setVisible(true);
							chngPar.addWindowListener(new WindowListener() {
								private boolean hasClosing;
								
								public void windowOpened(WindowEvent e) {
									hasClosing = false;
								}
								
								public void windowIconified(WindowEvent e) {
								}
								
								public void windowDeiconified(WindowEvent e) {					
								}
								
								public void windowDeactivated(WindowEvent e) {
									if(!hasClosing){
										timesToRunGA = chngPar.getTimesToRunGA();
										timesToRunSA = chngPar.getTimesToRunSA();
										runSlowSA = chngPar.getSlowSAValue();
									}
								}
								
								public void windowClosing(WindowEvent e) {
									hasClosing = true;
								}
								
								public void windowClosed(WindowEvent e) {
									timesToRunGA = DEFAULT_GA;
									timesToRunSA = DEFAULT_SA;
									runSlowSA = false;
								}

								public void windowActivated(WindowEvent arg0) {
								}
								
								
							});
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
				
			}
			
		});
	}

	private void runningProgram(){
		progressBar.setIndeterminate(true);
		bestSol = null;
		try{
			// Create new worker each time
			worker = new ProjectSolver(timesToRunSA, timesToRunGA, filename, runSlowSA);
			
			worker.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if(!worker.isDone()){
						int progress = worker.getProgress();
						if(progress == 0){
							progressBar.setIndeterminate(true);
							progressBar.setString("Running simulated annealing...");
						} else {
							if(progress >= 50){
								progressBar.setString("Running genetic algorithm...");
							}
							progressBar.setIndeterminate(false);
							progressBar.setValue(progress);
						}
					} else { 
						progressBar.setValue(100); 
						progressBar.setString("Finished!");
						try {
							bestSol = worker.get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
						//bestSol = worker.getBestSol(); 
						if(bestSol != null){
							int energy = bestSol.getEnergy();
							if(energy <= 300){
								emoji.setIcon(new ImageIcon(StochasticSearchWindow.class.getResource("/resources/superHappy.png")));
							} else if(energy <= 500){
								emoji.setIcon(new ImageIcon(StochasticSearchWindow.class.getResource("/resources/moreHappy.png")));
							} else if(energy <= 700){
								emoji.setIcon(new ImageIcon(StochasticSearchWindow.class.getResource("/resources/fairlyHappy.png")));
							} else if(energy <= 900){
								emoji.setIcon(new ImageIcon(StochasticSearchWindow.class.getResource("/resources/meh.png")));
							} else if(energy <= 1200){
								emoji.setIcon(new ImageIcon(StochasticSearchWindow.class.getResource("/resources/fairlySad.png")));
							} else if(energy <= 1400){
								emoji.setIcon(new ImageIcon(StochasticSearchWindow.class.getResource("/resources/moreSad.png")));
							} else {
								emoji.setIcon(new ImageIcon(StochasticSearchWindow.class.getResource("/resources/superSad.png")));
							}
							
							lblOverallDisappointment.setText("Overall Disappointment..." + energy);
							
							prefs = worker.getPrefs();
							
							lblNumberOfPreassigned.setText("Number of pre-assigned projects..." + prefs.getNumberPreAssignedProjects());
							lblNumberOfFirst.setText("Number of first preferences achieved..." + bestSol.getNumberOfFirstPrefs());
							lblActualProjectName.setText(prefs.getMostPopularProject());
							boolean noPenalties = bestSol.noPenalties();
							if(noPenalties){
								lblValidSolutionObtained.setText("Valid Solution Obtained? Yes" );
							} else {
								lblValidSolutionObtained.setText("Valid Solution Obtained? No" );
							}
							btnDownload.setToolTipText("");
							btnDownload2.setToolTipText("");
							btnDownload3.setToolTipText("");
						}
					}
					
				}
			});
			worker.execute();
			
		} catch( NullPointerException e) {} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
