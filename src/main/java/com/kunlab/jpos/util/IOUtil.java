package com.kunlab.jpos.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zeroturnaround.zip.ZipEntryCallback;
import org.zeroturnaround.zip.ZipUtil;
import org.zeroturnaround.zip.commons.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author likun
 */
public class IOUtil {
    private static Logger logger = LogManager.getLogger(IOUtil.class);

    public static byte[] read(InputStream inputStream, byte[] bytes) throws Exception {
        byte[] inBytes = new byte[1];
        long time = System.currentTimeMillis();

        logger.info("read total length : {} - {}", bytes.toString(), bytes.length);
        if(bytes.length > 0)
        {
            inBytes = new byte[bytes.length / 10];
            logger.info("read block length : {} - {}", bytes.toString(), inBytes.length);

            int read = 0;
            int size = 0;
            while((read = inputStream.read(inBytes)) > 0)
            {
                logger.info("read process info : {} - [{}+{}={}]", bytes.toString(), read, size, read + size);
                System.arraycopy(inBytes, 0, bytes, size, read);
                size += read;
            }
        }
        else
        {
            while(inputStream.read(inBytes) > 0)
            {
                byte[] temp = new byte[inBytes.length + bytes.length];
                System.arraycopy(bytes, 0, temp, 0, bytes.length);
                System.arraycopy(inBytes, 0, temp, bytes.length, inBytes.length);
                bytes = new byte[temp.length];
                System.arraycopy(temp, 0, bytes, 0, temp.length);
            }
        }
        logger.info("read finish : {} - {}ms", bytes.toString(), System.currentTimeMillis() - time);

        return bytes;
    }

    public static void write(byte[] bytes,  String file) throws Exception {
        OutputStream outputStream = null;

        try{
            outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
        }
        finally{
            try{
                outputStream.close();
            }
            catch(Exception e){}
        }
    }

    public static String unzip(byte[] bytes, String path) throws Exception {
        String result = null;

        InputStream inputStream = null;
        ZipInputStream zipInputStream = null;
        OutputStream outputStream = null;

        try{
            inputStream = new ByteArrayInputStream(bytes);
            zipInputStream = new ZipInputStream(inputStream);

            ZipEntry zipEntry = zipInputStream.getNextEntry();
            if(zipEntry != null)
            {
                byte[] _bytes = read(zipInputStream, new byte[0]);

                outputStream = new FileOutputStream(path + zipEntry.getName());
                outputStream.write(_bytes);

                result = zipEntry.getName();
            }
        }
        finally{
            try{
                outputStream.close();
            }
            catch(Exception e){}
            try{
                zipInputStream.close();
            }
            catch(Exception e){}
            try{
                inputStream.close();
            }
            catch(Exception e){}
        }

        return result;
    }

    private static byte[] zipDir(File dir){
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        ZipUtil.pack(dir, result, -1);
        return result.toByteArray();
    }

    /**
     * 压缩文件、文件目录
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static byte[] zip(File file) throws FileNotFoundException{
        if(file==null){
            return null;
        }
        if(!file.exists()){
            throw new FileNotFoundException();
        }
        if(file.isDirectory()){
            return zipDir(file);
        }else{
            return ZipUtil.packEntry(file);
        }
    }

    /**
     * 将字节数组压缩为ZIP格式
     * @param fileName
     * @param fileBytes
     * @return
     * @throws IOException
     */
    public static byte[] zip(String fileName, byte[] fileBytes) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(result);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipEntry.setSize(fileBytes.length);
        zipEntry.setTime(System.currentTimeMillis());
        InputStream in = new BufferedInputStream(new ByteArrayInputStream(fileBytes));
        try {
            out.putNextEntry(zipEntry);
            if (in != null) {
                IOUtils.copy(in, out);
            }
            out.closeEntry();
        } finally {
            IOUtils.closeQuietly(in);
        }
        out.close();
        return result.toByteArray();
    }

    /**
     * 将多个字节数组压缩为ZIP格式
     * @param files
     * @return
     * @throws IOException
     */
    public static byte[] zip(Map<String,byte[]> files) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(result);
        for(Map.Entry<String, byte[]> file : files.entrySet()){
            ZipEntry zipEntry = new ZipEntry(file.getKey());
            zipEntry.setSize(file.getValue().length);
            zipEntry.setTime(System.currentTimeMillis());
            InputStream in = new BufferedInputStream(new ByteArrayInputStream(file.getValue()));
            try {
                out.putNextEntry(zipEntry);
                if (in != null) {
                    IOUtils.copy(in, out);
                }
                out.closeEntry();
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
        out.close();
        return result.toByteArray();
    }

    /**
     * 从zip中解压出文件fileName
     * @param zip
     * @param fileName
     * @return
     */
    public static byte[] unzip(InputStream zip,String fileName){
        return ZipUtil.unpackEntry(zip, fileName);
    }

    /**
     * 从zip中解压出所有文件到目录
     * @param zip
     * @param outputDir
     */
    public static void unzip(InputStream zip,File outputDir){
        ZipUtil.unpack(zip, outputDir);
    }

    /**
     * 从zip中解压出所有文件
     * @param zip
     * @return
     */
    public static Map<String,ByteArrayOutputStream> unzip(InputStream zip){
        final Map<String,ByteArrayOutputStream> result = new HashMap<String,ByteArrayOutputStream>();
        ZipUtil.iterate(zip,new ZipEntryCallback(){
            @Override
            public void process(InputStream paramInputStream, ZipEntry paramZipEntry) throws IOException {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                IOUtils.copy(paramInputStream, bos);
                result.put(paramZipEntry.getName(), bos);
            }
        });
        return result;
    }
}
