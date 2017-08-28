package com.horstmann.violet.framework.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.file.chooser.IFileChooserService;
import com.horstmann.violet.framework.file.export.FileExportService;
import com.horstmann.violet.framework.file.naming.ExtensionFilter;
import com.horstmann.violet.framework.file.naming.FileNamingService;
import com.horstmann.violet.framework.file.persistence.IFilePersistenceService;
import com.horstmann.violet.framework.file.persistence.IFileReader;
import com.horstmann.violet.framework.file.persistence.IFileWriter;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.framework.printer.PrintEngine;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.sun.javaws.jnl.XMLUtils;

public class GraphFile implements IGraphFile
{
    /**
     * Creates a new graph file with a new graph instance
     * 
     * @param graphClass
     */
    public GraphFile(Class<? extends IGraph> graphClass)//һ��Graph���͵�ͼ
    {
        ResourceBundleInjector.getInjector().inject(this);
        BeanInjector.getInjector().inject(this);
        try
        {
            this.graph = graphClass.newInstance();
        }
        catch (Exception e)
        {
            DialogFactory.getInstance().showErrorDialog(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructs a graph file from an existing file
     * 
     * @param file
     * 
     */
    public GraphFile(IFile file) throws IOException
    {
        ResourceBundleInjector.getInjector().inject(this);
        BeanInjector.getInjector().inject(this);
        IFileReader fileOpener = fileChooserService.getFileReader(file);
        if (fileOpener == null)
        {
            throw new IOException("Open file action cancelled by user");
        }
        InputStream in = fileOpener.getInputStream();
        if (in != null)
        {
            this.graph = this.filePersistenceService.read(in);
            this.currentFilename = fileOpener.getFileDefinition().getFilename();
            this.currentDirectory = fileOpener.getFileDefinition().getDirectory();
        }
        else
        {
            throw new IOException("Unable to read file " + fileOpener.getFileDefinition().getFilename() + " from location " + fileOpener.getFileDefinition().getDirectory());
        }
    }

    @Override
    public IGraph getGraph()
    {
        return this.graph;
    }

    @Override
    public String getFilename()
    {
        return this.currentFilename;
    }

    @Override
    public String getDirectory()
    {
        return this.currentDirectory;
    }

    @Override
    public void setSaveRequired()
    {
        this.isSaveRequired = true;
        fireGraphModified();
    }

    @Override
    public boolean isSaveRequired() 
    {
    	return this.isSaveRequired;
    }
    
    /**
     * Indicates if this file is new
     * @return b
     */
    private boolean isNewFile() {
    	if (this.currentFilename == null && this.currentDirectory == null) {
    		return true;
    	}
    	return false;
    }
    
    @Override
    public void save()
    {
//        if (this.isNewFile()) {
        	saveToNewLocation();
//        	return;
//        }
//    	try
//        {
//            IFileWriter fileSaver = getFileSaver(false);
//            OutputStream outputStream = fileSaver.getOutputStream();
//            this.filePersistenceService.write(this.graph, outputStream);
//            this.isSaveRequired = false;
//            fireGraphSaved();
//            this.currentFilename = fileSaver.getFileDefinition().getFilename();
//            this.currentDirectory = fileSaver.getFileDefinition().getDirectory();
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException(e);
//        }
    	   if (this.isNewFile()) {
           	//���½����ļ�
           	saveToNewLocation();
           	return;
           }
       	try
           {	//�����½����ļ���ֱ�ӱ�����ԭ�е�·��
               IFileWriter fileSaver = getFileSaver(false);
               OutputStream outputStream = fileSaver.getOutputStream();
               this.filePersistenceService.write(this.graph, outputStream);
               this.isSaveRequired = false;
               fireGraphSaved();
               this.currentFilename = fileSaver.getFileDefinition().getFilename();
               this.currentDirectory = fileSaver.getFileDefinition().getDirectory();
               
           }
           catch (Exception e)
           {
               throw new RuntimeException(e);
           }

    }
/*
 * �Ž��Ѹ�(non-Javadoc)
 * @see com.horstmann.violet.framework.file.IGraphFile#saveToNewLocation()
 */
    @Override
    public void saveToNewLocation()
    {
//        try
//        {
//            IFileWriter fileSaver = getFileSaver(true);
//            if (fileSaver == null) { 
//            	// This appends when the action is cancelled
//            	return;
//            }
//            OutputStream outputStream = fileSaver.getOutputStream();
//            this.filePersistenceService.write(this.graph, outputStream);
//            this.isSaveRequired = false;
//            this.currentFilename = fileSaver.getFileDefinition().getFilename();
//            this.currentDirectory = fileSaver.getFileDefinition().getDirectory();
//            fireGraphSaved();
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException(e);
//        }
    	  try
          {
              IFileWriter fileSaver = getFileSaver(true);
              if (fileSaver == null) { 
              	// This appends when the action is cancelled
              	return;
              }
              OutputStream outputStream = fileSaver.getOutputStream();
              this.filePersistenceService.write(this.graph, outputStream);
              this.isSaveRequired = false;
              this.currentFilename = fileSaver.getFileDefinition().getFilename();
              this.currentDirectory = fileSaver.getFileDefinition().getDirectory();
              
//              File newFile = new File(currentFilename+currentDirectory);
//              if(currentFilename.contains(".ucase.violet.xml")){
//            	  AutoSave(newFile, )
//              }
              
              
              fireGraphSaved();
              
              
          }
          catch (Exception e)
          {
              throw new RuntimeException(e);
          }

    }

    /**
     * Returns a IFileSaver instance. Then, this object allows to save graph content. If the graph has never been saved, the
     * FileChooserService<br/>
     * will open a dialog box to select a location. If not, the returned IFileSaver will automatically be bound to the last saving
     * location.<br/>
     * You can also force the FileChooserService to open the dialog box with the given argument.<br/>
     * 
     * @param isAskedForNewLocation if true, then the FileChooser will open a dialog box to allow to choice a new location
     * @return f
     */
    public IFileWriter getFileSaver(boolean isAskedForNewLocation)
    {
        try
        {
            if (isAskedForNewLocation)
            {
                ExtensionFilter extensionFilter = this.fileNamingService.getExtensionFilter(this.graph);
                ExtensionFilter[] array =
                {
                    extensionFilter
                };
                return this.fileChooserService.chooseAndGetFileWriter(array);
            }
            return this.fileChooserService.getFileWriter(this);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addListener(IGraphFileListener listener)
    {
        synchronized (listeners)
        {
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(IGraphFileListener listener)
    {
        synchronized (listeners)
        {
            listeners.remove(listener);
        }
    }

    /**
     * Sends an event to listeners each time the graph is modified
     */
    private void fireGraphModified()
    {
        synchronized (listeners)
        {
            for (IGraphFileListener listener : listeners) {
            	listener.onFileModified();
            }
        }
    }

    /**
     * Sends an event to listeners when the graph has been saved
     */
    private void fireGraphSaved()
    {
        synchronized (listeners)
        {
            for (IGraphFileListener listener : listeners) {
            	listener.onFileSaved();
            }
        }
    }

    @Override
    public void exportToClipboard()
    {
        FileExportService.exportToclipBoard(this.graph);
        JOptionPane optionPane = new JOptionPane();
        optionPane.setIcon(this.clipBoardDialogIcon);
        optionPane.setMessage(this.clipBoardDialogMessage);
        optionPane.setName(this.clipBoardDialogTitle);
        this.dialogFactory.showDialog(optionPane, this.clipBoardDialogTitle, true);
    }

    @Override
    public void exportImage(OutputStream out, String format)
    {
        if (!ImageIO.getImageWritersByFormatName(format).hasNext())
        {
            MessageFormat formatter = new MessageFormat(this.exportImageErrorMessage);
            String message = formatter.format(new Object[]
            {
                format
            });
            JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage(message);
            this.dialogFactory.showDialog(optionPane, this.exportImageDialogTitle, true);
            return;
        }
        try
        {

            try
            {
                ImageIO.write(FileExportService.getImage(this.graph), format, out);
            }
            finally
            {
                out.close();
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportToPrinter()
    {
        PrintEngine engine = new PrintEngine(this.graph);
        engine.start();
    }
    /*
     * �Ž�
     */
    public void saveToOneLocation(String soucePath) throws IOException
    {	
    	  try
          {
              IFileWriter fileSaver = getFileSaver2(true);
              if (fileSaver == null) { 
              	// This appends when the action is cancelled
              	return;
              }
              OutputStream outputStream = fileSaver.getOutputStream();
              this.filePersistenceService.write(this.graph, outputStream);
              this.isSaveRequired = false;
              this.currentFilename = fileSaver.getFileDefinition().getFilename();
              this.currentDirectory = fileSaver.getFileDefinition().getDirectory();
              
              File newFile = new File(currentFilename + currentDirectory);
              AutoSave(currentDirectory + "/"+currentFilename,soucePath,currentFilename);
              fireGraphSaved();
              
          }
          catch (Exception e)
          {    
        	  throw new RuntimeException(e);
          }
          

	}
   /*
    * �Ž�
    */
     private IFileWriter getFileSaver2(boolean isAskedForNewLocation)
        {
            try
            {
                if (isAskedForNewLocation)
                {
                    ExtensionFilter extensionFilter = this.fileNamingService.getExtensionFilter(this.graph);
                    ExtensionFilter[] array =
                    {
                        extensionFilter
                    };
                    return this.fileChooserService.DchooseAndGetFileWriter(array);//��������򿪱����ļ��ĶԻ���
                }
                //System.out.println("����");
                return this.fileChooserService.getFileWriter(this);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        } 
/*
 * �Ž� (non-Javadoc)
 * @see com.horstmann.violet.framework.file.IGraphFile#dsave()
 */
    @Override
	public void dsave(String sourePath) 
    {
		if(this.isNewFile()){
			try {
				saveToOneLocation(sourePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			  IFileWriter fileSaver = getFileSaver2(false);
	          OutputStream outputStream = fileSaver.getOutputStream();
	          this.filePersistenceService.write(this.graph, outputStream);
	          this.isSaveRequired = false;
	          fireGraphSaved();
	          this.currentFilename = fileSaver.getFileDefinition().getFilename();
	          this.currentDirectory = fileSaver.getFileDefinition().getDirectory();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

/*
 * �Ž�(non-Javadoc)
 * @see com.horstmann.violet.framework.file.IGraphFile#AutoSave(com.horstmann.violet.framework.file.IFile, java.lang.String)
 */
    //�ȶ���file�ļ���Ȼ�󱣴浽path·����
	public void AutoSave(IFile file,String path) {
		//�Զ����淽���������ļ���ʱ���Զ����棩
		 try {
			InputStream in =new FileInputStream(file.getDirectory()+"/"+file.getFilename());
//			//�ж����ͣ��������ʹ��ͼ�ε�λ��
//			String base="C:/Users/Administrator/Desktop/ModelDriveProjectFile/";
//			String type=(file.getFilename().split("\\."))[1];
//			 File f;
//			 if("class".equals(type)){
//				f =new File(base+"/ClassDiagram/ƽ̨/"); 
//			 }else{
//				f =new File("C:/Users/Administrator/Desktop/ModelDriveProjectFile/"); //��Ҫ�����λ��
//			 }
			 File f =new File( path);
			if (!f.exists()) {
				f.mkdirs();
			}
		
			FileOutputStream out =new FileOutputStream(f.getAbsolutePath()+"/"+file.getFilename());
			byte[] buffer = new byte[1024]; 
		    int b;
		    while ( ( b = in.read(buffer)) != -1) { 
              out.write(buffer, 0, b); 
            } 
            in.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void AutoSave(String sourcePath,String aimPath,String fileName){ //cai
		 try {
				InputStream in =new FileInputStream(sourcePath);
				 File f =new File(aimPath);
				if (!f.exists()) {
					f.mkdirs();
				}
				FileOutputStream out =new FileOutputStream(aimPath+"/"+fileName);
				byte[] buffer = new byte[1024]; 
			    int b;
			    while ( ( b = in.read(buffer)) != -1) { 
	              out.write(buffer, 0, b); 
	            } 
	            in.close(); 
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
    private IGraph graph;

    /**
     * Needed to identify the physical file used to save the graph
     */
    private String currentFilename;

    /**
     * Needed to identify the physical file used to save the graph
     */
    private String currentDirectory;

    private boolean isSaveRequired = false;

    @ResourceBundleBean(key = "dialog.export_to_clipboard.icon")
    private ImageIcon clipBoardDialogIcon;

    @ResourceBundleBean(key = "dialog.export_to_clipboard.title")
    private String clipBoardDialogTitle;

    @ResourceBundleBean(key = "dialog.export_to_clipboard.ok")
    private String clipBoardDialogMessage;

    @ResourceBundleBean(key = "dialog.error.unsupported_image")
    private String exportImageErrorMessage;

    @ResourceBundleBean(key = "dialog.error.title")
    private String exportImageDialogTitle;

    @InjectedBean
    private IFileChooserService fileChooserService;

    @InjectedBean
    private FileNamingService fileNamingService;

    @InjectedBean
    private IFilePersistenceService filePersistenceService;

    @InjectedBean
    private DialogFactory dialogFactory;

    private List<IGraphFileListener> listeners = new ArrayList<IGraphFileListener>();



}
