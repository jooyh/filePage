package com.siwan.studyApp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilters;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FtpUtil {

	private static final String BASE_DIR = "/www";
	private static final String HOST_URL = "183.111.174.35";
	private static final String HOST_ID = "zoz7184";
	private static final String HOST_PW = "qwe123@@";
	private static final String DOWNLOAD_DIR = "/Users/yeonghyeonju/Downloads/download_test/";
	
	protected static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);
	
	public FTPClient connection(String host, String user, String pwd) throws Exception{
		FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        return ftp;
    }
	
	public void disconnect(FTPClient ftp){
		if (ftp == null) return;
        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }
	
	public Map getFileInFtp(String path) {
		Map rtnMap = new HashMap();
		FTPClient ftp = null;
		List<Map> fileList = null;
		List<Map> dirList = null;
		if(path == null ) path = BASE_DIR;  
		try {
			ftp = connection(HOST_URL,HOST_ID,HOST_PW);
			fileList = new ArrayList();
			dirList = new ArrayList();
			FTPFile[] ftpfiles = ftp.listFiles(path, new FTPFileFilters().NON_NULL);  // public 폴더의 모든 파일을 list 합니다
			if (ftpfiles != null) {
				for (int i = 0; i < ftpfiles.length; i++) {
					FTPFile file = ftpfiles[i];
					if(".".equals(file.getName()) || "..".equals(file.getName())) continue;
					logger.debug(file.toString());
					Map map = new HashMap();
					map.put("name", file.getName());
					map.put("size", file.getSize());
					map.put("path", path+"/"+file.getName());
					if(file.getType() == 1) {
						dirList.add(map);
					}else { 
						fileList.add(map);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnect(ftp);
		}
		rtnMap.put("fileList", fileList);
		rtnMap.put("dirList", dirList);
		rtnMap.put("dir", path);
		return rtnMap;
	}
	
	public void uploadInFtp(List<MultipartFile> fileList , String hostDir) {
		FTPClient ftp = null;
		try {
			ftp = connection(HOST_URL,HOST_ID,HOST_PW);
			InputStream input;
			for(MultipartFile file : fileList) {
				input = file.getInputStream();
				logger.debug("TEST",ftp.list(hostDir + file.getOriginalFilename()));
				ftp.storeFile(hostDir + file.getOriginalFilename(), input);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		} finally {
			disconnect(ftp);
		}
	}
	
	public void downloadInFtp(String path) {
		FTPClient ftp = null;
		
		String [] splited = path.split("/");
		String fileName = splited[splited.length-1];
		String dri = path.replace(fileName, "");
		
		try {
			ftp = connection(HOST_URL,HOST_ID,HOST_PW);
			ftp.changeWorkingDirectory(dri);
			File f = new File(DOWNLOAD_DIR,fileName);
			FileOutputStream fos = null;
			fos = new FileOutputStream(f);
			boolean isSuccess = ftp.retrieveFile(fileName, fos);
			if (isSuccess) {
				// 다운로드 성공
			} else {
				// 다운로드 실패
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			disconnect(ftp); 
		}
	}
}
