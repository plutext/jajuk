/*
 *  Jajuk
 *  Copyright (C) 2005 The Jajuk Team
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
 *  $$Revision: 3156 $$
 */
package org.jajuk.services.alarm;

import java.util.Date;
import java.util.List;

import org.jajuk.base.File;
import org.jajuk.services.players.FIFO;
import org.jajuk.util.Conf;
import org.jajuk.util.Const;
import org.jajuk.util.UtilFeatures;

/**
 * An Alarm
 */
public class Alarm {
  private List<File> alToPlay;
  private String alarmAction;
  private Date aTime;

  public Alarm(java.util.Date aTime, List<File> alFiles, String mode) {
    this.aTime = aTime;
    this.alToPlay = alFiles;
    this.alarmAction = mode;
  }

  /**
   * Effective action to perform by the alarm
   */
  public void wakeUpSleeper() {
    if (alarmAction.equals(Const.ALARM_START_MODE)) {
      FIFO.push(UtilFeatures.createStackItems(alToPlay, Conf.getBoolean(Const.CONF_STATE_REPEAT),
          false), false);
    } else {
      FIFO.stopRequest();
    }
  }

  public Date getAlarmTime() {
    return this.aTime;
  }
  
  /**
   * Add 24 hours to current alarm
   */
  public void nextDay(){
    aTime = new Date(aTime.getTime() + Const.DAY_MS);
  }

}
