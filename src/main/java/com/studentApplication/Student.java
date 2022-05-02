package com.studentApplication;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

public class Student {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student window = new Student();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Student() {
		initialize();
		Connect();
		table_load();
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String urls = "127.0.0.1";// you can even replace this with localhost
			String username = "root";
			String password = "";
			con = DriverManager.getConnection(
					"jdbc:mysql://" + urls + ":3306/student?useUnicode=yes&characterEncoding=UTF-8", username,
					password);

		} catch (ClassNotFoundException ex) {

		} catch (SQLException ex) {

		}
	}

	void table_load() {
		try {
			pst = con.prepareStatement("SELECT * FROM students");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 904, 495);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Dashboard gestion cours");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(263, 11, 384, 69);
		frame.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Enregistrement", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 79, 384, 278);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Nom");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 26, 47, 26);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Pr\u00E9nom");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 63, 65, 26);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Niveau");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(10, 100, 65, 26);
		panel.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_2 = new JLabel("Num\u00E9ro de t\u00E9l");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_2.setBounds(10, 137, 100, 26);
		panel.add(lblNewLabel_1_1_2);

		JLabel lblNewLabel_1_1_3 = new JLabel("Adresse");
		lblNewLabel_1_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_3.setBounds(10, 174, 65, 26);
		panel.add(lblNewLabel_1_1_3);

		final JTextPane textName = new JTextPane();
		textName.setBounds(141, 32, 199, 20);
		panel.add(textName);

		final JTextPane textLastName = new JTextPane();
		textLastName.setBounds(141, 69, 199, 20);
		panel.add(textLastName);

		final JTextPane textLevel = new JTextPane();
		textLevel.setBounds(141, 106, 199, 20);
		panel.add(textLevel);

		final JTextPane textTel = new JTextPane();
		textTel.setBounds(141, 143, 199, 20);
		panel.add(textTel);

		final JTextPane textAdresse = new JTextPane();
		textAdresse.setBounds(141, 180, 199, 20);
		panel.add(textAdresse);

		JButton btnSubscribe = new JButton("Enrigistrer");
		btnSubscribe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String lastName, firstName, level, phone, adress;
				lastName = textName.getText();
				firstName = textLastName.getText();
				level = textLevel.getText();
				phone = textTel.getText();
				adress = textAdresse.getText();

				try {
					pst = con.prepareStatement("INSERT INTO students(nom,prenom,niveau,tel,adresse)VALUES(?,?,?,?,?)");
					pst.setString(1, lastName);
					pst.setString(2, firstName);
					pst.setString(3, level);
					pst.setString(4, phone);
					pst.setString(5, adress);
					pst.executeUpdate();// requette préparée
					JOptionPane.showMessageDialog(null, "un nouveau élève est enregistré!");
					table_load();
					textName.setText("");
					textLastName.setText("");
					textLevel.setText("");
					textTel.setText("");
					textAdresse.setText("");
					textName.requestFocus();
				} catch (SQLException el) {
					el.printStackTrace();
				}

			}
		});
		btnSubscribe.setBounds(89, 230, 89, 23);
		panel.add(btnSubscribe);

		JButton btnClean = new JButton("Effacer");
		btnClean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textName.setText("");
				textLastName.setText("");
				textLevel.setText("");
				textTel.setText("");
				textAdresse.setText("");
				textName.requestFocus();

			}
		});
		btnClean.setBounds(213, 230, 89, 23);
		panel.add(btnClean);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Rechercher", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 367, 382, 78);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1_1_3_1 = new JLabel("Nom ");
		lblNewLabel_1_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1_3_1.setBounds(10, 35, 79, 26);
		panel_1.add(lblNewLabel_1_1_3_1);

		final JTextPane searchName = new JTextPane();
		searchName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				try {
					String name = searchName.getText();
					pst = con.prepareStatement("SELECT nom,prenom,niveau,tel,adresse FROM students WHERE nom = ?");
					pst.setString(1, name);
					ResultSet rs = pst.executeQuery();

					if (rs.next() == true) {
						String lastName = rs.getString(1);
						String firstName = rs.getString(2);
						String level = rs.getString(3);
						String tel = rs.getString(4);
						String adresse = rs.getString(5);

						textName.setText(lastName);
						textLastName.setText(firstName);
						textLevel.setText(level);
						textTel.setText(tel);
						textAdresse.setText(adresse);
					} else {
						textName.setText("");
						textLastName.setText("");
						textLevel.setText("");
						textTel.setText("");
						textAdresse.setText("");
					}

				} catch (SQLException ev) {

				}

			}
		});
		searchName.setBounds(127, 41, 185, 20);
		panel_1.add(searchName);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(404, 83, 474, 295);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Supprimer \u00E9l\u00E8ve");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String searchN;

				searchN = searchName.getText();

				try {
					pst = con.prepareStatement("DELETE FROM students WHERE nom=?");

					pst.setString(1, searchN);
					pst.executeUpdate();// requette préparée
					JOptionPane.showMessageDialog(null, "élève supprimé!");
					table_load();
					textName.setText("");
					textLastName.setText("");
					textLevel.setText("");
					textTel.setText("");
					textAdresse.setText("");
					textName.requestFocus();

				} catch (SQLException el) {
					el.printStackTrace();
				}

			}
		});
		btnNewButton.setBounds(477, 395, 136, 50);
		frame.getContentPane().add(btnNewButton);

		JButton btnMiseJour = new JButton("Mise \u00E0 jour");
		btnMiseJour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String lastName, firstName, level, phone, adress, searchN;
				lastName = textName.getText();
				firstName = textLastName.getText();
				level = textLevel.getText();
				phone = textTel.getText();
				adress = textAdresse.getText();
				searchN = searchName.getText();

				try {
					pst = con.prepareStatement(
							"UPDATE students SET nom=?,prenom=?,niveau=?,tel=?,adresse=? WHERE nom=?");
					pst.setString(1, lastName);
					pst.setString(2, firstName);
					pst.setString(3, level);
					pst.setString(4, phone);
					pst.setString(5, adress);
					pst.setString(6, searchN);
					pst.executeUpdate();// requette préparée
					JOptionPane.showMessageDialog(null, "La liste est mise à jour");
					table_load();
					textName.setText("");
					textLastName.setText("");
					textLevel.setText("");
					textTel.setText("");
					textAdresse.setText("");

				} catch (SQLException el) {
					el.printStackTrace();
				}

			}
		});
		btnMiseJour.setBounds(663, 395, 136, 50);
		frame.getContentPane().add(btnMiseJour);
	}
}
