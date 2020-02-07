package com.siwan.studyApp;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

public class FtpTest {

	FTPClient ftp = null;

    //param( host server ip, username, password )
    public FTPClient FTPUploader(String host, String user, String pwd) throws Exception{
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host);//호스트 연결
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);//로그인
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        return ftp;
    }
    //param( 보낼파일경로+파일명, 호스트에서 받을 파일 이름, 호스트 디렉토리 )
//    public void uploadFile(String localFileFullName, String fileName, String hostDir)
//            throws Exception {
//        try(InputStream input = new FileInputStream(new File(localFileFullName))){
//        this.ftp.storeFile(hostDir + fileName, input);
//        //storeFile() 메소드가 전송하는 메소드
//        }
//    }

    public void disconnect(){
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }

    @Test
    public void connectionTest() throws Exception {
    	System.out.println("Start");
    	FTPClient ftp = FTPUploader("183.111.174.35", "zoz7184", "qwe123@@");
    	System.out.println("STATUS ::>"+ftp.getStatus());
    	disconnect();
        System.out.println("Done");
    }

}
