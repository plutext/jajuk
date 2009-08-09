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
 *  $$Revision$$
 */
package org.jajuk.ui.actions;

import java.awt.event.ActionEvent;

import org.jajuk.services.players.QueueModel;
import org.jajuk.util.IconLoader;
import org.jajuk.util.JajukIcons;
import org.jajuk.util.Messages;
import org.jajuk.util.log.Log;

public class StopTrackAction extends JajukAction {
  private static final long serialVersionUID = 1L;

  StopTrackAction() {
    super(Messages.getString("JajukWindow.27"), IconLoader.getIcon(JajukIcons.STOP_16X16),
        "ctrl S", false, false);
    setShortDescription(Messages.getString("JajukWindow.27"));
  }

  @Override
  public void perform(ActionEvent evt) {
    new Thread("StopTrackAction") {
      @Override
      public void run() {
        try {
          QueueModel.stopRequest();
        } catch (Exception e) {
          Log.error(e);
        }
      }
    }.start();
  }
}
