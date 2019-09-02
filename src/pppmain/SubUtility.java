/*
 * Copyright (C) 2019 N216
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pppmain;

import java.awt.Container;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public final class SubUtility {
  
  public static final String
    C_M_INVALID = "";
  
  private static final JFileChooser
    O_FILE_CHOOSER = new JFileChooser();
  
  private SubUtility(){}//++!
  
  //===
  
  public static final ArrayList<File> ccGetFileList(
    String pxPath, String pxExtention
  ){
    
    ArrayList<File> lpRes = new ArrayList<>();
    
    //-- check in
    if(!ccIsValidString(pxPath)){
      ccMessageBox("invalid_path:"+pxPath);
      return lpRes;
    }//..?
    
    //-- check path
    File lpFolder = new File(pxPath);
    if(!lpFolder.isAbsolute()){
      ccMessageBox("is_not_path:"+pxPath);
      return lpRes;
    }if(!lpFolder.isDirectory()){
      ccMessageBox("is_not_folder:"+pxPath);
      return lpRes;
    }//..?
    
    //-- generate array
    File[] lpDesFile = lpFolder.listFiles();
    if(lpDesFile==null){
      ccMessageBox("cant_list_folder:"+pxPath);
      return lpRes;
    }if(lpDesFile.length==0){
      ccMessageBox("no_file_found:"+pxPath);
      return lpRes;
    }//..?
    
    //-- generate list
    for(File it : lpDesFile){
      if(it.isFile()){
        if(it.getName().endsWith(pxExtention)){
          lpRes.add(it);
        }
      }//..?
    }//..~
    return lpRes;
    
  }//+++
  
  public static final boolean ccRenameFile(
    String pxPath, String pxFind, String pxReplace
  ){
    
    //-- check in
    if(!ccIsValidString(pxPath)){
      ccMessageBox("invalid_path:"+pxPath);
      return false;
    }//..?
    
    //-- check path
    File lpFile = new File(pxPath);
    if(!lpFile.isFile()){
      ccMessageBox("is_not_file:"+pxPath);
      return false;
    }if(!lpFile.canWrite()){
      ccMessageBox("can_not_write_to_file:"+pxPath);
      return false;
    }//..?
    
    //-- check config
    if(
      !ccIsValidString(pxFind) ||
      !ccIsValidString(pxReplace)
    ){ccMessageBox("box_input_invalid");return false;}
    
    //-- generate new name
    String lpNewName = lpFile.getName().replaceFirst(pxFind, pxReplace);
    if(lpNewName==null){
      ccMessageBox("replace_process_failed:"+pxPath+" -> "+lpNewName);
      return false;
    }if(lpNewName.equals(lpFile.getName())){
      ccMessageBox("replace_result_invalid:"+pxPath+" -> "+lpNewName);
      return false;
    }//..?
    
    //-- generate dummy file
    File lpNewFile = new File(
      lpFile.getParent()+MainFrame.C_V_PATHSEP+lpNewName
    );
    
    //-- action
    boolean lpRes;
    try {
      lpRes=lpFile.renameTo(lpNewFile);
    } catch (Exception e) {
      System.err.println("pppmain.SubUtility.ccRenameFile()::"
        + e.getMessage());
      lpRes=false;
    }//..?
    return lpRes;
    
  }//+++
  
  static public final boolean ccIsValidString(String pxLine)
    {if(pxLine==null){return false;}else{return !pxLine.isEmpty();}}//+++
    
  public static final String ccGetPathByFileChooser
    (char pxMode)
  { if(ccIsEDT()){
      int lpMode=JFileChooser.FILES_AND_DIRECTORIES;
      switch(pxMode){
        case 'f':lpMode=JFileChooser.FILES_ONLY;break;
        case 'd':lpMode=JFileChooser.DIRECTORIES_ONLY;break;
        default:break;
      }//..?
      O_FILE_CHOOSER.updateUI();
      O_FILE_CHOOSER.setFileSelectionMode(lpMode);
      int lpFlag=O_FILE_CHOOSER
        .showDialog(MainFrame.ccGetFrame(), null);
      if(lpFlag==JFileChooser.APPROVE_OPTION){
        File lpFile=O_FILE_CHOOSER.getSelectedFile();
        if(lpFile!=null){
          return lpFile.getAbsolutePath();
        }else{return C_M_INVALID;}
      }else{return C_M_INVALID;}
    }return C_M_INVALID;
  }//+++
  
  public static final String ccGetPathByFileChooser(String pxDefaultFile){
    File lpFile=new File(pxDefaultFile);
    if(lpFile.isAbsolute()){
      O_FILE_CHOOSER.setSelectedFile(lpFile);
    }//+++
    return ccGetPathByFileChooser(lpFile.isDirectory()?'d':'f');
  }//+++
  
  public static final void ccMessageBox(String pxMessage){
    ccMessageBox(pxMessage,MainFrame.ccGetFrame());
  }//+++
  
  public static final void ccMessageBox
    (String pxMessage, Container pbOwner)
  { if( ccIsEDT() && (pbOwner!=null)){
      JOptionPane.showMessageDialog(pbOwner,pxMessage);
    }//+++
    else{System.err.println("..ccMessageBox()::"+pxMessage);}
  }//+++
    
  public static final boolean ccIsEDT(){
    if(SwingUtilities.isEventDispatchThread()){return true;}
    System.err.println("--err::out_from_edt");
    return false;
  }//+++
  
}//***eof
