package org.oakbricks.launcher.gui;

import com.google.gson.*;
import org.oakbricks.launcher.Main;
import org.oakbricks.launcher.core.json.LauncherConfigJson;
import org.oakbricks.launcher.gui.tools.AccountManagementFrame;
import org.oakbricks.launcher.gui.tools.DebuggingFrame;
import org.oakbricks.launcher.gui.tools.InstanceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Objects;

import static org.oakbricks.launcher.Main.*;

public class GuiMain extends JFrame implements ActionListener, Runnable {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    JPanel instancesPanel;
    JToolBar toolBar;
    JButton accountsButton;
    JButton instancesButton;
    JButton settingsButton;
    JButton debugButton;
    JLabel lastUsedAccountLabel;
    Icon addInstancesIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/icons/01.png")));
    Icon accountManagementIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/icons/02.png")));
    Icon debuggingIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/icons/03.png")));
    Image frameIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/icons/00.png"))).getImage();

    @Override
    public void run() {
        instancesPanel = new JPanel();

        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        instancesButton = new JButton("Add instance", addInstancesIcon);
        instancesButton.addActionListener(this);

        accountsButton = new JButton("Manage Accounts", accountManagementIcon);
        accountsButton.addActionListener(this);

        debugButton = new JButton("Debugging", debuggingIcon);
        debugButton.addActionListener(this);
        new JOptionPane();

        lastUsedAccountLabel = new JLabel(config.getLastUsedAccount());

        toolBar.add(instancesButton);
        toolBar.add(accountsButton);
        if (Main.IS_DEBUGGING) {
            toolBar.add(debugButton);
        }
        toolBar.addSeparator(new Dimension());
        toolBar.add(lastUsedAccountLabel);
        if (Objects.equals(lastUsedAccountLabel.getText(), "Tm9uZQ\u003d\u003d")) {
            lastUsedAccountLabel.setText("No Account");
        }

        if (config.getWindowIconPath() == null) {
            setIconImage(frameIcon);
        } else {
            setIconImage(new ImageIcon(config.getWindowIconPath()).getImage());
        }

        add(toolBar, BorderLayout.PAGE_START);
        add(instancesPanel);

        setTitle("Launcher");
        setLocation(config.getWinX(), config.getWinY());
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == instancesButton) {
            new InstanceManager();
        } else if (e.getSource() == accountsButton) {
            LOGGER.info("Development placeholder");
            new Thread(new AccountManagementFrame()).start();
        } else if (e.getSource() == debugButton && Main.IS_DEBUGGING) {
            new DebuggingFrame();
        }
    }
}
