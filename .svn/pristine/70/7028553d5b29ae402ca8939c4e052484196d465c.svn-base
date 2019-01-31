package org.parafia.util;

import java.io.File;

import org.apache.log4j.Logger;
import org.parafia.Constants;

public class FileUtil {
	private static final Logger log = Logger.getLogger(FileUtil.class);
	private static final DirectoryFilter df = new DirectoryFilter();
	
	public static File[] getDirectoryList(String root, boolean showFiles) {
		log.debug("szukamy w katalogu " + root);
		File f = new File(root);
		File[] filesList;
		if (f.exists() && f.isDirectory())
			filesList = f.listFiles(df);
		else
			filesList = new File[0];
		
		return filesList;
	}
	
	public static boolean removeFile(File path) {
		if (path.exists() && path.isDirectory()) {
			File[] files = path.listFiles();
			for(int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()) {
					removeFile(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
	
	public static boolean removeFile(String path) {
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			for(int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()) {
					removeFile(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (file.delete());
	}
	
	public static boolean addSubDir(String dirName) {
		java.io.File f = new java.io.File(dirName);
		return f.mkdir();
	}
	
	public static boolean addSubDir(String parentDir, String subDir) {
		java.io.File f = new java.io.File(parentDir + Constants.FILE_SEP + subDir);
		return f.mkdir();
	}
	
	public static boolean addDir(String dir, String name) {
		java.io.File f = new java.io.File(dir);
		java.io.File parent = f.getParentFile();
		log.debug(parent.getAbsolutePath() + Constants.FILE_SEP + name);
		java.io.File newDir = new java.io.File(parent.getAbsolutePath() + Constants.FILE_SEP + name);
		return newDir.mkdir();
	}
	
	public static boolean directoryExists(String path) {
		if (path == null) {
			log.warn("Path is null");
			return false;
		}
		File f = new File(path);
		return (f.exists() && f.isDirectory());
	}
	
	public static boolean moveDir(String oldDir, String newPath) {
		if (oldDir == null || newPath == null) {
			log.warn("oldDir or newName is null");
			return false;
		}
		File f = new File(oldDir);
		if (!f.exists() || !f.isDirectory()) {
			log.warn(oldDir + " is not a valid directory");
			return false;
		} else {
			if (log.isDebugEnabled())
				log.debug("Changing filename from " + oldDir + " to " + newPath);
			if (f.getParent() != null) {
				return f.renameTo(new File(newPath));
			} else
				return false;
			
		}
	}
}
