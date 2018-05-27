package ccom.ht.file.uploadfile.listener;

import org.apache.commons.fileupload.ProgressListener;

import ccom.ht.file.uploadfile.entity.Status;


public class uploadFileProcessListner implements ProgressListener{

	private Status status=null;
	
	public uploadFileProcessListner(Status status){
		
		this.status=status;
	}
	
	
	public void update(long arg0, long arg1, int arg2) {
		
		status.setPass(arg0);
		status.setTotal(arg1);
		status.setFileOrder(arg2);
		
		
	}



}

