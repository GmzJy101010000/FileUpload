package ccom.ht.file.uploadfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ccom.ht.file.uploadfile.entity.Status;
import ccom.ht.file.uploadfile.listener.uploadFileProcessListner;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
   public static double roundData(int decimal,double data){
	   
	   BigDecimal bigDecimal=new BigDecimal(data);
	   return bigDecimal.setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
   }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("content-type","text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragrma", "no-cache");
		response.setDateHeader("Expries", 0);
		
		
		
		Status status= (Status) request.getSession(true).getAttribute("uploadStatus");
	    if(status ==null){
	    	
	    	response.getWriter().println("没有正在上传的文件：");
	    	return;
	    }
	    
	   long startTime = status.getStartTime();
	   long currentTime = System.currentTimeMillis();
	   
	   long usedTime =(currentTime-startTime)/1000 +1;
	   
	   double suduB =(double)status.getPass()/(double)usedTime;
	   double suduM = roundData(2,(double)status.getPass()/(double)usedTime/1024/1024);
	   double totalTime = status.getTotal()/suduB;
	   double leftTime =roundData(0, totalTime-usedTime);
	   
	   long percent = (long)(100*(double)status.getPass()/(double)status.getTotal());
	   
	   double lengthU = roundData(2,((double)status.getPass())/1024/1024);
	   double lengthM = roundData(2,((double)status.getTotal())/1024/1024);
	   System.out.println(status.getTotal());
	 
	   String value = percent +"||" + lengthU +"||" +lengthM+"||" + suduM+"||" +usedTime+"||" +totalTime+"||" +leftTime+"||" +status.getFileOrder();
	   
	   response.getWriter().println(value);
	   
	  
	}
@Override
protected void service(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	// TODO Auto-generated method stub
	super.service(req, resp);
}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		File file= null;
		request.setCharacterEncoding("UTF-8");
		//response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		
		Status status=new Status();
		uploadFileProcessListner  listener=  new uploadFileProcessListner(status);
		request.getSession().setAttribute("uploadStatus",status );
		
		ServletFileUpload upload=new ServletFileUpload(new DiskFileItemFactory());
		
		upload.setProgressListener(listener);
		
		try {
			
			List<FileItem>  fileItems=upload.parseRequest(request);
			
			for(FileItem item:fileItems){
				
				if(!item.isFormField()){
					File clientFile=new File(new String(item.getName().getBytes(),"UTF-8"));
					
					response.getWriter().println("正在上传文件："+clientFile.getAbsolutePath());
					
					file=new File("D:/Users/ZGM/Desktop/target",clientFile.getName());
				
					file.getParentFile().mkdir();
					file.createNewFile();
					
					InputStream ins=item.getInputStream();
					OutputStream ous=new FileOutputStream(file);
					
					try {
						
						byte[]  buffer =new byte[1024];
						int len=0;
						
						while((len =ins.read(buffer))>-1){
							ous.write(buffer,0,len);
						}
						response.getWriter().println("文件已上传至服务器");
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					finally{
						ins.close();
						ous.close();
					}
					
				}
				
			}
			
			
		} catch (FileUploadException e) {
			// TODO: handle exception
		}
	}

	public void sa(){
		
	}
    public void sa(int a){
		
	}
    
    public int  sa(String  a){
		return 0;
	}
}
