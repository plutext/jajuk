/*
 *  Jajuk
 *  Copyright (C) 2003 bflorat
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
 *  $Revision$
 */

package org.jajuk.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import org.jajuk.util.ConfigurationManager;
import org.jajuk.util.MD5Processor;
import org.jajuk.util.log.Log;

/**
 * Convenient class to manage files
 * 
 * @Author bflorat @created 17 oct. 2003
 */
public class FileManager implements ITechnicalStrings{
	/** Files collection* */
	private static ArrayList alFilesId = new ArrayList(1000);
	/** Files collection* */
	private static ArrayList alFiles = new ArrayList(1000);
	/** Sorted fiel collection ( for performances ) */
	private static TreeSet tsSortedFiles = new TreeSet();
	
	
	/**
	 * No constructor available, only static access
	 */
	private FileManager() {
		super();
	}

	/**
	 * Register an File
	 * 
	 * @param sName
	 */
	public static synchronized File registerFile(String sName, Directory directory, Track track, long lSize, String sQuality) {
		String sId = MD5Processor.hash(directory.getDevice().getName() + directory.getDevice().getUrl() + directory.getAbsolutePath() + sName);
		return registerFile(sId, sName, directory, track, lSize, sQuality);
	}

	/**
	 * Register an File with a known id
	 * 
	 * @param sName
	 */
	public static synchronized File registerFile(String sId, String sName, Directory directory, Track track, long lSize, String sQuality) {
		File file = new File(sId, sName, directory, track, lSize, sQuality);
		if ( !alFilesId.contains(sId)){
			alFilesId.add(sId);
			alFiles.add(file);
			tsSortedFiles.add(file);
			if ( directory.getDevice().isRefreshing()){
				Log.debug("registrated new file: "+ file);
			}
		}
		return file;
	}

	/**
	 * Clean all references for the given device
	 * @param sId : Device id
	 */
	public static synchronized void cleanDevice(String sId) {
		//we have to create a new list because we can't iterate on a moving size list
		Iterator it = alFiles.iterator();
		while (it.hasNext()) {
			File file = (File) it.next();
			if (file.getDirectory()==null || file.getDirectory().getDevice().getId().equals(sId)) {
				it.remove();
				alFilesId.remove(file.getId());
				tsSortedFiles.remove(file);
			}
		}
		System.gc(); //force garbage collection after cleanup
	}

	/** Return all registred files */
	public static synchronized ArrayList getFiles() {
		return new ArrayList(alFiles);
	} 
	
	/** Return sorted registred files*/
	public static synchronized ArrayList getSortedFiles() {
		return new ArrayList(tsSortedFiles);
	} 

	
	/**
	   * Return file by id
	   * 
	   * @param id
	   * @return
	   */
	public static synchronized File getFile(String sId) {
		int index = alFilesId.indexOf(sId);
		return (index==-1)?null:(File) alFiles.get(index);
	}
	
	/**
	 * Return a shuffle mounted file from the entire collection
	 * @return
	 */
	public static synchronized File getShuffleFile(){
		//create a tempory table to remove unmounted files
		ArrayList alEligibleFiles = new ArrayList(1000);
		Iterator it = alFiles.iterator();
		while ( it.hasNext()){
			File file = (File)it.next();
			if (file.isReady()){
				alEligibleFiles.add(file);
			}
		}
		if (alEligibleFiles.size() ==0 ){
			return null;
		}
		return (File)alEligibleFiles.get((int)(Math.random()*alEligibleFiles.size()));
	}
	
	/**
	 * Return a shuffle mounted file from the entire collection with rate weight ( best of mode )
	 * @return
	 */
	public static synchronized File getBestOfFile(){
		//create a tempory table to remove unmounted files
		TreeSet tsEligibleFiles = new TreeSet();
		Iterator it = alFiles.iterator();
		while ( it.hasNext()){
			File file = (File)it.next();
			if (file.isReady()){
				long lRate = file.getTrack().getRate();
				long lScore = (long)(Math.random()*(100/(file.getTrack().getSessionHits()+1))*Math.log(lRate));  //computes score for each file ( part of shuffleness, part of hits weight )
				tsEligibleFiles.add(new FileScore(file,lScore));
			}
		}
		FileScore fsBestOne = (FileScore)tsEligibleFiles.last(); 
		return fsBestOne.getFile(); //return highest score file
	}
	
	
	/** Return next mounted file ( used in continue mode )
	 * @param file : a file
	 * @return next file from entire collection
	 */
	public static synchronized File getNextFile(File file){
		File fileNext = null;
		ArrayList alSortedFiles = getSortedFiles();
		//look for a correct file from index to collection end
		boolean bOk = false;
		for (int index=alSortedFiles.indexOf(file)+1;index<alSortedFiles.size();index++){
			fileNext = (File)alSortedFiles.get(index);
			if (fileNext.isReady()){  //file must be on a mounted device not refreshing
				bOk = true;
				break;
			}
		}
		if ( bOk){
			return fileNext;
		}
		else if (!ConfigurationManager.getBoolean(CONF_OPTIONS_RESTART)){
			return null;
		}
		//ok, if we are in restart collection mode, restart from collection begin to file index
		for (int index=0;index<alSortedFiles.indexOf(file);index++){
			fileNext = (File)alSortedFiles.get(index);
			if (fileNext.isReady()){  //file must be on a mounted device not refreshing
				bOk = true;
				break;
			}
		}
		if ( bOk){
			return fileNext;
		}
		return null;
	}
	
	
	/** Return previous mounted file 
	 * @param file : a file
	 * @return previous file from entire collection
	 */
	public static synchronized File getPreviousFile(File file){
		File filePrevious = null;
		ArrayList alSortedFiles = getSortedFiles();
		//look for a correct file from index to collection begin
		boolean bOk = false;
		for (int index=alSortedFiles.indexOf(file)-1;index>=0;index--){
			filePrevious = (File)alSortedFiles.get(index);
			if (filePrevious.isReady()){  //file must be on a mounted device not refreshing
				bOk = true;
				break;
			}
		}
		if ( bOk){
			return filePrevious;
		}
		return null;
	}
	
	/**
	 * Perform a search in all files names with given criteria
	 * @param sCriteria
	 * @return
	 */
	public static synchronized TreeSet search(String sCriteria){
	 	TreeSet tsResu = new TreeSet(); 
		sCriteria = sCriteria.toLowerCase();
	 	Iterator it = alFiles.iterator();
	 	while ( it.hasNext()){
	 		File file = (File)it.next();
	 		if ( !file.getDirectory().getDevice().isMounted() || file.getDirectory().getDevice().isRefreshing()){
	 			continue;
	 		}
	 		String sResu = file.toStringSearch();
	 		if ( new StringBuffer(sResu.toLowerCase()).lastIndexOf(sCriteria) != -1 ){
	 			tsResu.add(new SearchResult(file,sResu));
	 		}
	 	}
	 	return tsResu;
	}

}

/**
 * 
 *  File score
 *
 * @author     bflorat
 * @created    22 janv. 2004
 */
class FileScore implements Comparable{
	/** The score */
	long lScore;
	/**The file*/
	File file;
	
	public FileScore(File file,long lScore){
		this.lScore = lScore;
		this.file = file;
	}
	
	/**
	 * @return Returns the file.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @return Returns the lScore.
	 */
	public long getLScore() {
		return lScore;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		FileScore fscore = (FileScore)o;
		if ( fscore.getLScore() < lScore){
			return 1;
		}
		else if ( fscore.getLScore() > lScore){
			return -1;
		}
		return 0;
	}

}
