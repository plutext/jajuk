/*
 *  Jajuk
 *  Copyright (C) 2004 The Jajuk Team
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *  $Revision: 3156 $
 */

package org.jajuk.ui.wizard;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

import net.miginfocom.swing.MigLayout;

import org.jajuk.base.FileManager;
import org.jajuk.base.SearchResult;
import org.jajuk.events.JajukEvent;
import org.jajuk.events.JajukEvents;
import org.jajuk.events.ObservationManager;
import org.jajuk.services.alarm.AlarmManager;
import org.jajuk.ui.widgets.JajukJDialog;
import org.jajuk.ui.widgets.SearchBox;
import org.jajuk.ui.windows.JajukMainWindow;
import org.jajuk.util.Conf;
import org.jajuk.util.Const;
import org.jajuk.util.Messages;
import org.jajuk.util.log.Log;

/**
 * Alarm Clock Dialog window
 */
public class AlarmClockDialog extends JajukJDialog implements ActionListener, ItemListener, Const {
  private static final long serialVersionUID = 1L;

  private final JPanel jpOKCancel;

  private final JLabel jlChoice;

  private final ButtonGroup bgChoices;

  private final JButton jbOK;

  private final JButton jbCancel;

  private final JCheckBox jcbTime;

  private final JLabel jlAlarmAction;

  private final JRadioButton jrbShuffle;

  private final JRadioButton jrbBestof;

  private final JRadioButton jrbNovelties;

  private final JRadioButton jrbFile;

  private final JTextField jtfHour;

  private final JTextField jtfMinutes;

  private final JTextField jtfSeconds;

  private final JComboBox jcbAlarmAction;

  private final SearchBox sbSearch;

  transient private SearchResult sr;

  public AlarmClockDialog() {
    super();

    jcbTime = new JCheckBox(Messages.getString("AlarmDialog.0"));
    jcbTime.addActionListener(this);

    jtfHour = new JTextField(2);
    jtfHour.setToolTipText(Messages.getString("AlarmDialog.1"));
    jtfMinutes = new JTextField(2);
    jtfMinutes.setToolTipText(Messages.getString("AlarmDialog.2"));
    jtfSeconds = new JTextField(2);
    jtfSeconds.setToolTipText(Messages.getString("AlarmDialog.3"));

    jlAlarmAction = new JLabel(Messages.getString("AlarmDialog.4"));
    jcbAlarmAction = new JComboBox();
    jcbAlarmAction.addItem(Const.ALARM_START_ACTION);
    jcbAlarmAction.addItem(Const.ALARM_STOP_ACTION);
    jcbAlarmAction.setToolTipText(Messages.getString("AlarmDialog.5"));
    jcbAlarmAction.addActionListener(this);

    jlChoice = new JLabel(Messages.getString("ParameterView.9"));
    jrbShuffle = new JRadioButton(Messages.getString("ParameterView.14"));
    jrbShuffle.setToolTipText(Messages.getString("ParameterView.15"));
    jrbShuffle.addItemListener(this);
    jrbBestof = new JRadioButton(Messages.getString("ParameterView.131"));
    jrbBestof.setToolTipText(Messages.getString("ParameterView.132"));
    jrbBestof.addItemListener(this);
    jrbNovelties = new JRadioButton(Messages.getString("ParameterView.133"));
    jrbNovelties.setToolTipText(Messages.getString("ParameterView.134"));
    jrbNovelties.addItemListener(this);
    jrbFile = new JRadioButton(Messages.getString("ParameterView.16"));
    jrbFile.setToolTipText(Messages.getString("ParameterView.17"));
    jrbFile.addItemListener(this);
    sbSearch = new SearchBox() {
      private static final long serialVersionUID = 1L;

      @Override
      public void valueChanged(final ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          sr = sbSearch.getResult(sbSearch.getSelectedIndex());
          sbSearch.setText(sr.getFile().getTrack().getName());
          sbSearch.hidePopup();
        }
      }
    };
    // disabled by default, is enabled only if jrbFile is enabled
    sbSearch.setEnabled(false);
    sbSearch.setToolTipText(Messages.getString("ParameterView.18"));

    bgChoices = new ButtonGroup();
    bgChoices.add(jrbShuffle);
    bgChoices.add(jrbBestof);
    bgChoices.add(jrbNovelties);
    bgChoices.add(jrbFile);

    jrbShuffle.setSelected(true);

    jpOKCancel = new JPanel();
    jpOKCancel.setLayout(new FlowLayout());
    jbOK = new JButton(Messages.getString("Ok"));
    jbOK.addActionListener(this);
    jpOKCancel.add(jbOK);
    jbCancel = new JButton(Messages.getString("Cancel"));
    jbCancel.addActionListener(this);
    jpOKCancel.add(jbCancel);
    jpOKCancel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    setLayout(new MigLayout("insets 10,gapy 15", "[grow][grow]"));
    add(jcbTime, "right");
    add(jtfHour, "left,split 5,width 30!");
    add(new JLabel(":"));
    add(jtfMinutes, "width 30!");
    add(new JLabel(":"));
    add(jtfSeconds, "width 30!,wrap");
    add(jlAlarmAction, "center");
    add(jcbAlarmAction, "left,wrap");
    add(jlChoice, "left,wrap");
    add(jrbShuffle, "left,wrap");
    add(jrbBestof, "left,wrap");
    add(jrbNovelties, "left,wrap");
    add(jrbFile, "left");
    add(sbSearch, "left,wrap,grow");
    add(jpOKCancel, "center,grow,span");

    // Reload on GUI saved values
    loadValues();

    setTitle(Messages.getString("AlarmClock.0"));
    setMinimumSize(new Dimension(250, 100));
    setModal(true);
    pack();
    setLocationRelativeTo(JajukMainWindow.getInstance());
    setVisible(true);
  }

  public final void actionPerformed(final ActionEvent e) {
    boolean playAction = (jcbAlarmAction.getSelectedIndex() == 0);
    if (e.getSource() == jcbAlarmAction) {
      handleAction(playAction);
    } else if (e.getSource() == jbOK) {
      saveValues();
    } else if (e.getSource() == jbCancel) {
      dispose();
    } else if (e.getSource() == jcbTime) {
      handleTimeCheckbox(playAction);
    }
  }

  /**
   * @param playAction
   */
  private void handleAction(boolean playAction) {
    jlChoice.setEnabled(playAction);
    jrbShuffle.setEnabled(playAction);
    jrbBestof.setEnabled(playAction);
    jrbNovelties.setEnabled(playAction);
    jrbFile.setEnabled(playAction);
    sbSearch.setEnabled(playAction);
  }

  /**
   * @param playAction
   */
  private void handleTimeCheckbox(boolean playAction) {
    // Enable/ disable all widgets if user enables or disables the entire
    // alarm
    boolean enabled = jcbTime.isSelected();
    jtfHour.setEnabled(enabled);
    jtfMinutes.setEnabled(enabled);
    jtfSeconds.setEnabled(enabled);
    jcbAlarmAction.setEnabled(enabled);
    jlChoice.setEnabled(enabled && playAction);
    jrbShuffle.setEnabled(enabled && playAction);
    jrbBestof.setEnabled(enabled && playAction);
    jrbNovelties.setEnabled(enabled && playAction);
    jrbFile.setEnabled(enabled && playAction);
    sbSearch.setEnabled(enabled && playAction);
  }

  public void itemStateChanged(final ItemEvent e) {
    if (e.getSource() == jrbFile) {
      sbSearch.setEnabled(jrbFile.isSelected());
    }
  }

  /**
   * Store GUI values to persisted values
   */
  public void saveValues() {
    // Parse the final alarm value
    Calendar cal = Calendar.getInstance();
    try {
      cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(jtfHour.getText()));
      cal.set(Calendar.MINUTE, Integer.parseInt(jtfMinutes.getText()));
      cal.set(Calendar.SECOND, Integer.parseInt(jtfSeconds.getText()));
    } catch (Exception e) {
      Log.error(e);
      Messages.showErrorMessage(177);
      return;
    }
    // Store values
    Conf.setProperty(Const.CONF_ALARM_ENABLED, ((Boolean) jcbTime.isSelected()).toString());
    Conf.setProperty(Const.CONF_ALARM_ACTION, jcbAlarmAction.getSelectedItem().toString());
    Conf.setProperty(CONF_ALARM_TIME_HOUR, jtfHour.getText());
    Conf.setProperty(CONF_ALARM_TIME_MINUTES, jtfMinutes.getText());
    Conf.setProperty(CONF_ALARM_TIME_SECONDS, jtfSeconds.getText());
    if (jrbShuffle.isSelected()) {
      Conf.setProperty(Const.CONF_ALARM_MODE, Const.STARTUP_MODE_SHUFFLE);
    } else if (jrbFile.isSelected()) {
      Conf.setProperty(Const.CONF_ALARM_MODE, Const.STARTUP_MODE_FILE);
      // sr = null means none search occurred in this session
      if (sr != null) {
        Conf.setProperty(Const.CONF_ALARM_FILE, sr.getFile().getID());
      }
    } else if (jrbBestof.isSelected()) {
      Conf.setProperty(Const.CONF_ALARM_MODE, Const.STARTUP_MODE_BESTOF);
    } else if (jrbNovelties.isSelected()) {
      Conf.setProperty(Const.CONF_ALARM_MODE, Const.STARTUP_MODE_NOVELTIES);
    }
    // Store properties in case of
    try {
      Conf.commit();
    } catch (Exception e) {
      Log.error(e);
    }
    // Close the window
    dispose();
    // Notify the Alarm manager
    ObservationManager.notify(new JajukEvent(JajukEvents.ALARMS_CHANGE));
    // Display a message
    Messages.showInfoMessage(Messages.getString("Success"));
    // Start manager up
    AlarmManager.getInstance();
  }

  /**
   * Load persisted values to GUI
   */
  private final void loadValues() {
    jcbTime.setSelected(Conf.getBoolean(CONF_ALARM_ENABLED));
    jtfHour.setText(Conf.getString(CONF_ALARM_TIME_HOUR));
    jtfMinutes.setText(Conf.getString(CONF_ALARM_TIME_MINUTES));
    jtfSeconds.setText(Conf.getString(CONF_ALARM_TIME_SECONDS));
    // Alarm mode (play/stop)
    if (ALARM_START_ACTION.equals(Conf.getString(CONF_ALARM_ACTION))) {
      jcbAlarmAction.setSelectedIndex(0);
    } else if (ALARM_STOP_ACTION.equals(Conf.getString(CONF_ALARM_ACTION))) {
      jcbAlarmAction.setSelectedIndex(1);
    }
    // Alarm action
    if (Const.STARTUP_MODE_BESTOF.equals(Conf.getString(CONF_ALARM_MODE))) {
      jrbBestof.setSelected(true);
    } else if (Const.STARTUP_MODE_NOVELTIES.equals(Conf.getString(CONF_ALARM_MODE))) {
      jrbNovelties.setSelected(true);
    } else if (Const.STARTUP_MODE_FILE.equals(Conf.getString(CONF_ALARM_MODE))) {
      jrbFile.setSelected(true);
      String fileName = FileManager.getInstance()
          .getFileByID(Conf.getString(Const.CONF_ALARM_FILE)).getName();
      sbSearch.setText(fileName);
    } else if (Const.STARTUP_MODE_SHUFFLE.equals(Conf.getString(CONF_ALARM_MODE))) {
      jrbShuffle.setSelected(true);
    }
    // Force an an action event to update enable state of widgets
    actionPerformed(new ActionEvent(jcbTime, 0, null));
  }

}
