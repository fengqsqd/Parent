package cn.edu.hdu.lab.service.parser;
/*
 * �쳣����
 * ��Բ��Ϸ����
 */
public class InvalidTagException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String tagName;
	
	public InvalidTagException(String message,String tagName)
	{
		super(message);
		this.tagName=tagName;
		
	}
	
	public void setTagName(String tagName) 
	{
		this.tagName = tagName;
	}
	public String getMissingTagName()
	{
		return this.tagName;
	}

}
