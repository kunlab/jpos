package com.kunlab.jpos.remote;

import com.kunlab.jpos.exception.RemoteException;
import com.kunlab.jpos.model.FtpObject;
import com.kunlab.jpos.util.IOUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * FTP读取文件
 * @author likun
 */
public class FtpGetRemoteImpl extends BaseRemoteImpl<FtpObject, Map<String, byte[]>> {
    private Logger logger = null;

    private static final String DEFAULT_CHARSET = "UTF-8";

    private String username;
    private String password;

    public FtpGetRemoteImpl() {
        logger = LogManager.getLogger(this.getClass());
    }

    @Override
    public Map<String, byte[]> execute(FtpObject req) throws RemoteException {
        Map<String, byte[]> result = new HashMap<String, byte[]>();
        FTPClient ftpClient = new FTPClient();

        long time = preReq(req);
        logger.info("{}:{} | {}ms[{}]", host, port, timeout, username);

        try {
            ftpClient.setControlEncoding(DEFAULT_CHARSET);
            ftpClient.setDefaultTimeout((int)timeout);
            ftpClient.setConnectTimeout((int)timeout);
            ftpClient.setDataTimeout((int)timeout);
            ftpClient.connect(host, port);
            ftpClient.setSoTimeout((int)timeout);
            logger.info("connect: {}", ftpClient.getReplyCode());

            boolean login = ftpClient.login(username, password);
            logger.info("login: {} | {}", ftpClient.getReplyCode(), login);

            String pwd = ftpClient.printWorkingDirectory();
            logger.info("pwd: {} | {}", ftpClient.getReplyCode(), pwd);
            logger.info("cwd: {} | {}", req.getPath(), ftpClient.changeWorkingDirectory(req.getPath()));
            pwd = ftpClient.printWorkingDirectory();
            logger.info("pwd: {} | {}", ftpClient.getReplyCode(), pwd);

            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClient.listFiles();
            logger.info("list : {} | {}", ftpClient.getReplyCode(), files.length);

            for(FTPFile file : files) {
                boolean flag = false;
                InputStream inputStream = null;
                try{
                    if(file.getName().contains(req.getName())) {
                        logger.info("file match\t: {}", file);

                        flag = true;
                        inputStream = ftpClient.retrieveFileStream(file.getName());
                        logger.info("file read\t: {}", file.getName());

                        byte[] bytes = IOUtil.read(inputStream, new byte[(int)file.getSize()]);
                        result.put(file.getName(), bytes);

                        logger.info("get : {} | {}", ftpClient.getReplyCode(), bytes.length);
                    } else {
                        logger.info("file pass\t: {}", file);
                    }
                } catch(Exception e){
                    logger.error(file, e);
                } finally{
                    if(flag) {
                        try{
                            inputStream.close();
                        } catch(Exception e){}
                        try{
                            boolean commit = ftpClient.completePendingCommand();
                            logger.info("commit : {} | {}", ftpClient.getReplyCode(), commit);
                        } catch(Exception e){}
                    }
                }
            }



        } catch (Exception e) {
            throw new RemoteException("ftp get failure", e);
        } finally {
            try{
                logger.info("quit : {}", ftpClient.quit());
            }catch(Exception e){}

            logger.info("ftp get finish : {}ms", preResp(result) - time);
        }

        return result;
    }




    public long preReq(FtpObject req) {
        logger.info("ftp get req obj : {}", req);
        return System.currentTimeMillis();
    }

    public long preResp(Map<String, byte[]> resp) {
        logger.info("ftp get resp length : {}", resp.size());
        return System.currentTimeMillis();
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
