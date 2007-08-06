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

package org.jajuk.ui.views;

import org.jajuk.i18n.Messages;
import org.jajuk.util.ConfigurationManager;
import org.jajuk.util.log.Log;

import java.net.URL;
import java.util.Locale;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.BoxLayout;

/**
 * Help view using java help api
 * <p>
 * Help perspective *
 */
public class HelpView extends ViewAdapter {

	private static final long serialVersionUID = 1L;

	/** hepl set */
	HelpSet hs;

	/** Help broker */
	HelpBroker hb;

	/** Help component */
	JHelp jhelp;

	public HelpView() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jajuk.ui.IView#display()
	 */
	public void initUI() {
		try {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			ClassLoader cl = HelpView.class.getClassLoader();
			URL url = HelpSet
					.findHelpSet(
							cl,
							"jajuk.hs", new Locale(ConfigurationManager.getProperty(CONF_OPTIONS_LANGUAGE))); 
			hs = new HelpSet(null, url);
			hb = hs.createHelpBroker();
			jhelp = new JHelp(hs);
			add(jhelp);
		} catch (Exception e) {
			Log.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jajuk.ui.IView#getDesc()
	 */
	public String getDesc() {
		return Messages.getString("HelpView.2"); 
	}

}
