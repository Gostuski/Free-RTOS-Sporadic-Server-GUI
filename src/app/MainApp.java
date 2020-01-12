package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

import arduino.Arduino;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class MainApp extends JFrame {

	private JLabel nameLabel;
	private JLabel arrivalLabel;
	private JLabel periodLabel;
	private JLabel durationLabel;
	private JLabel parameterLabel;
	private JLabel taskTypeLabel;
	private JLabel serverCapacityLabel;
	private JLabel serverPeriodLabel;
	private JLabel functionLabel;

	private JTextField nameTextField;
	private JTextField arrivalTextField;
	private JTextField periodTextField;
	private JTextField durationTextField;
	private JTextField parameterTextField;
	private JTextField serverCapacityTextField;
	private JTextField serverPeriodTextField;
	private JTextField deleteTextField;

	private JComboBox<String> taskTypeBox;
	private JComboBox<String> functionTypeBox;

	private JButton createButton;
	private JButton configureServerButton;
	private JButton addToBatchButton;
	private JButton sendBatchButton;
	private JButton getCapacityButton;
	private JButton restartBatchButton;
	private JButton deleteButton;
	private JButton serialMonitorButton;
	private JButton clearSerialButton;
	private JButton restartControllerButton;

	private JCheckBox autoScrollCheckBox;

	private String batch = "b ";

	private SerialPort serialPort;

	private JTextArea serialMonitor;

	private JFrame serialMonitorFrame;

	public MainApp() {

		nameLabel = new JLabel("Task name:");
		arrivalLabel = new JLabel("Task arrival:");
		periodLabel = new JLabel("Task period:");
		durationLabel = new JLabel("Task duration:");
		parameterLabel = new JLabel("Task parameter:");
		serverCapacityLabel = new JLabel("Server capacity:");
		serverPeriodLabel = new JLabel("Server period:");
		taskTypeLabel = new JLabel("Task type:");
		functionLabel = new JLabel("Task function:");

		nameTextField = new JTextField();
		arrivalTextField = new JTextField();
		periodTextField = new JTextField();
		durationTextField = new JTextField();
		parameterTextField = new JTextField();
		serverCapacityTextField = new JTextField();
		serverPeriodTextField = new JTextField();
		deleteTextField = new JTextField();

		taskTypeBox = new JComboBox<String>();

		taskTypeBox.addItem("Periodic");
		taskTypeBox.addItem("Aperiodic");

		taskTypeBox.setSelectedItem(null);

		functionTypeBox = new JComboBox<String>();

		functionTypeBox.addItem("Print words");
		functionTypeBox.addItem("Print numbers");

		createButton = new JButton("Create task");
		configureServerButton = new JButton("Configure server");
		addToBatchButton = new JButton("Add to batch");
		sendBatchButton = new JButton("Send batch");
		getCapacityButton = new JButton("Get server capacity");
		restartBatchButton = new JButton("Restart batch");
		deleteButton = new JButton("Delete task");
		serialMonitorButton = new JButton("Open serial monitor");
		clearSerialButton = new JButton("Clear output");
		restartControllerButton = new JButton("Restart controller");

		autoScrollCheckBox = new JCheckBox("Auto scroll");

		serialMonitor = new JTextArea(5, 20);

		serialMonitor.setEditable(false);

		DefaultCaret caret = (DefaultCaret) serialMonitor.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		setLayout(new BorderLayout());
		setSize(new Dimension(1000, 600));

		setTitle("Sporadic Server");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLocationRelativeTo(null);

		GridBagConstraints gbc = new GridBagConstraints();

		JPanel jPanel = new JPanel(new GridBagLayout());

		gbc.ipadx = 200;
		gbc.ipady = 10;

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		jPanel.add(serverCapacityLabel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		jPanel.add(serverCapacityTextField, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		jPanel.add(serverPeriodLabel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		jPanel.add(serverPeriodTextField, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 2;
		jPanel.add(configureServerButton, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		jPanel.add(functionLabel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 3;
		jPanel.add(functionTypeBox, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 4;
		jPanel.add(taskTypeLabel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 4;
		jPanel.add(taskTypeBox, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 5;
		jPanel.add(nameLabel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 5;
		jPanel.add(nameTextField, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 6;
		jPanel.add(arrivalLabel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 6;
		jPanel.add(arrivalTextField, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 7;
		jPanel.add(periodLabel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 7;
		jPanel.add(periodTextField, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 8;
		jPanel.add(durationLabel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 8;
		jPanel.add(durationTextField, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 9;
		jPanel.add(parameterLabel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 9;
		jPanel.add(parameterTextField, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 10;
		jPanel.add(addToBatchButton, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 10;
		jPanel.add(createButton, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 11;
		jPanel.add(sendBatchButton, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 11;
		jPanel.add(getCapacityButton, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 12;
		jPanel.add(serialMonitorButton, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 12;
		jPanel.add(restartBatchButton, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 13;
		jPanel.add(deleteTextField, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 13;
		jPanel.add(deleteButton, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 14;
		jPanel.add(restartControllerButton, gbc);

		this.add(jPanel, BorderLayout.CENTER);

		initialisePort();
		setHandlers();

	}

	private void setHandlers() {
		taskTypeBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (taskTypeBox.getSelectedItem() != null) {
					String selected = (String) taskTypeBox.getSelectedItem();
					if (selected.equals("Periodic")) {
						arrivalTextField.setText("0");
						arrivalTextField.setEditable(false);
						periodTextField.setEditable(true);
						periodTextField.setText("");
					} else {
						periodTextField.setText("0");
						periodTextField.setEditable(false);
						arrivalTextField.setEditable(true);
						arrivalTextField.setText("");
					}
				}
			}
		});

		configureServerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (serverCapacityTextField.getText().isBlank() || serverPeriodTextField.getText().isBlank()) {
					String text = "Fill in all fields";
					JOptionPane.showMessageDialog(null, text);
					return;
				}
				String message = "s " + serverCapacityTextField.getText() + " " + serverPeriodTextField.getText();

				try {
					serialPort.writeString(message);
				} catch (SerialPortException e1) {
					e1.printStackTrace();
				}

				serverCapacityTextField.setText("");
				serverPeriodTextField.setText("");
			}
		});

		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameTextField.getText().isBlank() || arrivalTextField.getText().isBlank()
						|| periodTextField.getText().isBlank() || periodTextField.getText().isBlank()
						|| durationTextField.getText().isBlank() || parameterTextField.getText().isBlank()
						|| taskTypeBox.getSelectedIndex() == -1 || functionTypeBox.getSelectedIndex() == -1) {
					String text = "Fill in all fields";
					JOptionPane.showMessageDialog(null, text);
					return;
				}
				
				if(functionTypeBox.getSelectedItem().equals("Print numbers")) {
					try {
						Integer.parseInt(parameterTextField.getText());						
					} catch (Exception e2) {
						String text = "Print number task must have a number parameter";
						JOptionPane.showMessageDialog(null, text);
						return;
					}
				}
				
				String message = "";
				if(((String) taskTypeBox.getSelectedItem()).equals("Periodic")){
					message += "p";
				}else {
					message += "a";
				}
				message += " ";
				message += nameTextField.getText();
				message += " ";
				
				if (functionTypeBox.getSelectedItem().equals("Print words")) {
					message += "w";
				} else {
					message += "n";
				}
				
				message += " ";
				message += parameterTextField.getText();
				message += " ";
				message += arrivalTextField.getText();
				message += " ";
				message += periodTextField.getText();
				message += " ";
				message += durationTextField.getText();
				
				try {
					serialPort.writeString(message);
				} catch (SerialPortException e1) {
					e1.printStackTrace();
				}

				nameTextField.setText("");
				periodTextField.setText("");
				arrivalTextField.setText("");
				durationTextField.setText("");
				parameterTextField.setText("");
				taskTypeBox.setSelectedIndex(-1);

			}
		});

		addToBatchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameTextField.getText().isBlank() || arrivalTextField.getText().isBlank()
						|| periodTextField.getText().isBlank() || periodTextField.getText().isBlank()
						|| durationTextField.getText().isBlank() || parameterLabel.getText().isBlank()
						|| taskTypeBox.getSelectedIndex() == -1 || functionTypeBox.getSelectedIndex() == -1) {
					String text = "Fill in all fields";
					JOptionPane.showMessageDialog(null, text);
					return;
				}
				
				if(((String) taskTypeBox.getSelectedItem()).equals("Aperiodic")){
					String text = "You can add only periodic tasks to batch";
					JOptionPane.showMessageDialog(null, text);
					return;
				}
				
				if (!batch.endsWith("b "))
					batch += "-";

				if(((String) taskTypeBox.getSelectedItem()).equals("Periodic")){
					batch += "p";
				}
				
				batch += "-";
				batch += nameTextField.getText();
				batch += "-";
				
				if (functionTypeBox.getSelectedItem().equals("Print words")) {
					batch += "w";
				} else {
					batch += "n";
				}
				
				batch += "-";
				batch += parameterTextField.getText();
				batch += "-";
				batch += arrivalTextField.getText();
				batch += "-";
				batch += periodTextField.getText();
				batch += "-";
				batch += durationTextField.getText();

				nameTextField.setText("");
				periodTextField.setText("");
				arrivalTextField.setText("");
				durationTextField.setText("");
				parameterTextField.setText("");
				taskTypeBox.setSelectedIndex(-1);

			}
		});

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (deleteTextField.getText().isBlank()) {
					String text = "Fill in all fields";
					JOptionPane.showMessageDialog(null, text);
					return;
				}
				String message = "d ";
				message += deleteTextField.getText();

				try {
					serialPort.writeString(message);
				} catch (SerialPortException e1) {
					e1.printStackTrace();
				}

				deleteTextField.setText("");
			}
		});
		sendBatchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (batch.endsWith("b ")) {
					String text = "Batch empty";
					JOptionPane.showMessageDialog(null, text);
					return;
				}
				
				System.out.println(batch);
				
				try {
					serialPort.writeString(batch);
				} catch (SerialPortException e1) {
					e1.printStackTrace();
				}

				batch = "b ";

			}
		});

		restartBatchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				batch = "batch ";
				String text = "Batch restarted";
				JOptionPane.showMessageDialog(null, text);
			}
		});

		clearSerialButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				serialMonitor.setText("");
			}
		});

		autoScrollCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (autoScrollCheckBox.isSelected()) {
					DefaultCaret caret = (DefaultCaret) serialMonitor.getCaret();
					caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				} else {
					DefaultCaret caret = (DefaultCaret) serialMonitor.getCaret();
					caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
				}
				serialMonitor.validate();
			}
		});

		serialMonitorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				serialMonitorFrame = new JFrame();

				serialMonitorFrame.setLayout(new BorderLayout());

				JScrollPane scrollPane = new JScrollPane(serialMonitor);

				serialMonitorFrame.add(scrollPane, BorderLayout.CENTER);

				JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

				jPanel.add(autoScrollCheckBox);
				jPanel.add(clearSerialButton);

				serialMonitorFrame.add(jPanel, BorderLayout.SOUTH);

				serialMonitorFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

				serialMonitorFrame.setLocationRelativeTo(null);
				
				serialMonitorFrame.setTitle("Serial monitor");
				
				serialMonitorFrame.setSize(300, 250);

				serialMonitorFrame.setVisible(true);

			}
		});

		restartControllerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					serialPort.closePort();
				} catch (SerialPortException e1) {
					e1.printStackTrace();
				}
				initialisePort();
			}
		});
		
		getCapacityButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(serverPeriodTextField.getText().isBlank()) {
					String text = "Fill server period field to get capacity";
					JOptionPane.showMessageDialog(null, text);
					return;
				}
				
				String period = "c " + serverPeriodTextField.getText();
				System.out.println(period);
				try {
					serialPort.writeString(period);
				} catch (SerialPortException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		
	}

	private void initialisePort() {
		serialPort = new SerialPort("/dev/tty.usbmodem14201");
		try {
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;// Prepare mask
			serialPort.setEventsMask(mask);

		} catch (Exception e) {
			System.err.println(e);
		}

		try {
			serialPort.addEventListener(new SerialPortEventListener() {

				@Override
				public void serialEvent(SerialPortEvent event) {
					if (event.isRXCHAR()) {
						try {
							String s = "";
							String a;
							while (true) {
								a = serialPort.readString(1);
								s += a;
								if (a.equals("\n"))
									break;
							}
							serialMonitor.append(s);
						} catch (SerialPortException ex) {
							System.out.println(ex);
						}
					}
				}
			});
		} catch (SerialPortException e1) {
			e1.printStackTrace();
		}
	}

}
