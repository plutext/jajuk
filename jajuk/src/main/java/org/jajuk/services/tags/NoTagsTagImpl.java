/*
 *  Jajuk
 *  Copyright (C) 2003 The Jajuk Team
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
 *  $Revision: 3216 $
 */

package org.jajuk.services.tags;

import java.io.File;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import org.jajuk.util.UtilFeatures;

/**
 * Tagger implementation for formats without tags and read by BasicPlayer API
 */
public class NoTagsTagImpl implements ITagImpl {
  /** Analyzed file */
  File fio;

  /** Current file data */
  Map<String, Object> mapInfo;

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#getTrackName()
   */
  public String getTrackName() throws Exception {
    return ""; // doing that, the item wil be the default jajuk unknown
    // string
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#getAlbumName()
   */
  public String getAlbumName() throws Exception {
    return ""; // doing that, the item will be the default jajuk unknown
    // string
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#getAuthorName()
   */
  public String getAuthorName() throws Exception {
    return ""; // doing that, the item will be the default jajuk unknown
    // string
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#getStyleName()
   */
  public String getStyleName() throws Exception {
    return ""; // doing that, the item will be the default jajuk unknown
    // string
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#getLength()
   */
  public long getLength() throws Exception {
    // we have to open the file to get length
    BasicPlayer player = new BasicPlayer();
    player.addBasicPlayerListener(new BasicPlayerListener() {
      @SuppressWarnings("unchecked")
      public void opened(Object arg0, Map mProperties) {
        NoTagsTagImpl.this.mapInfo = mProperties;
      }

      @SuppressWarnings("unchecked")
      public void progress(int iBytesread, long lMicroseconds, byte[] bPcmdata, Map mProperties) {
      }

      public void stateUpdated(BasicPlayerEvent bpe) {
      }

      public void setController(BasicController arg0) {
      }
    });
    if (fio != null) {
      player.open(fio);
      return UtilFeatures.getTimeLengthEstimation(mapInfo) / 1000;
    }
    return 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#getComment()
   */
  public String getComment() throws Exception {
    return ""; // by doing that, the item will be the default jajuk
    // unknown string
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#setTrackName(java.lang.String)
   */
  public void setTrackName(String sTrackName) throws Exception {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#setAlbumName(java.lang.String)
   */
  public void setAlbumName(String sAlbumName) throws Exception {
  }

  public void setComment(String sComment) throws Exception {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#setAuthorName(java.lang.String)
   */
  public void setAuthorName(String sAuthorName) throws Exception {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#setStyleName(java.lang.String)
   */
  public void setStyleName(String style) throws Exception {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#setLength(long)
   */
  public void setLength(@SuppressWarnings("unused")
  long length) throws Exception {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#setQuality(java.lang.String)
   */
  public void setQuality(@SuppressWarnings("unused")
  String sQuality) throws Exception {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.base.ITagImpl#setFile(java.io.File)
   */
  public void setFile(File fio) throws Exception {
    this.fio = fio;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.tag.ITagImpl#commit()
   */
  public void commit() throws Exception {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.tag.ITagImpl#getOrder()
   */
  public long getOrder() throws Exception {
    return 0l;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.tag.ITagImpl#setOrder(java.lang.String)
   */
  public void setOrder(long lOrder) throws Exception {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.tag.ITagImpl#setYear(int)
   */
  public void setYear(String year) throws Exception {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.tag.ITagImpl#getYear()
   */
  public String getYear() throws Exception {
    return "0";
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.tag.ITagImpl#getQuality()
   */
  public long getQuality() throws Exception {
    return 0l;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.services.tags.ITagImpl#getAlbumArtist()
   */
  public String getAlbumArtist() throws Exception {
    return "";
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.services.tags.ITagImpl#getDiscNumber()
   */
  public int getDiscNumber() throws Exception {
    return 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.services.tags.ITagImpl#setAlbumArtist(java.lang.String)
   */
  public void setAlbumArtist(String albumArtist) throws Exception {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.jajuk.services.tags.ITagImpl#setDiscNumber(int)
   */
  public void setDiscNumber(int discnumber) throws Exception {
    // TODO Auto-generated method stub

  }

}
