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
 *  $$Revision: 2403 $$
 */
package org.jajuk.ui.action;

import org.jajuk.base.FIFO;
import org.jajuk.base.File;
import org.jajuk.base.Track;
import org.jajuk.i18n.Messages;
import org.jajuk.util.IconLoader;
import org.jajuk.util.error.JajukException;

import java.awt.event.ActionEvent;


/**
 * 
 *  Manual increase of rate
 */
public class IncRateAction extends ActionBase {

	private static final long serialVersionUID = 1L;
	
	private static final int RATING_INC = 10;

	IncRateAction() {
		super(
				Messages.getString("IncRateAction.0"), IconLoader.ICON_INC_RATING, true); 
		setShortDescription(Messages.getString("IncRateAction.0")); 
	}

	public void perform(ActionEvent evt) throws JajukException {
		File file = FIFO.getInstance().getCurrentFile();
		if (file != null){
			Track track = file.getTrack();
			track.setRate(track.getRate() + RATING_INC);
		}
	}
}
