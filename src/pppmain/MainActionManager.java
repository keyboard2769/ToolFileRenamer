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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class MainActionManager implements ActionListener{
  
  private static MainActionManager self=null;
  public final static MainActionManager ccGetInstance(){
    if(self==null){
      self = new MainActionManager();
      return self;
    }else{
      return self;
    }
  }//++!
  private MainActionManager(){}//++!
  
  //===
  
  private ArrayList<File> cmFileListRef=null;
  
  //===
  
  @Override public void actionPerformed(ActionEvent ae) {
    String lpCommand = ae.getActionCommand();
    
    if(lpCommand.equals("--action-reset")){
      ssResetStatus();
      return;
    }//..?
    
    if(lpCommand.equals("--action-process")){
      ssRenameListedFiles();
      return;
    }//..?
    
    if(lpCommand.equals("--action-browse")){
      ssListFiles();
      return;
    }//..?
    
    if(lpCommand.equals("--action-quit")){
      System.out.println(
        "pppmain.MainActionManager.actionPerformed()::sys_exit <- 0"
      );System.exit(0);
      return;
    }//..?
    
    if(lpCommand.equals("--action-info")){
      SubUtility.ccMessageBox(
        "still_a_test_version",
        MainFrame.ccGetFrame()
      );
      return;
    }//..?
    
    System.err.println("pppmain.MainActionManager.actionPerformed()::"
      + "unhandled_action_command:"+lpCommand);
  }//+++
  
  //===
  
  private void ssListFiles(){
    String lpPath = SubUtility.ccGetPathByFileChooser('d');
    if(lpPath.equals(SubUtility.C_M_INVALID)){
      MainPanel.ccGetInstance().ccMessage("::user_canceled_selection");
      return;
    }//..?
    cmFileListRef = SubUtility.ccGetFileList(
      lpPath,
      MainPanel.ccGetInstance().cmExtentionTB.getText()
    );
    MainPanel.ccGetInstance().cmFileListModel.clear();
    for(File it : cmFileListRef){
      MainPanel.ccGetInstance().cmFileListModel.addElement(it.getName());
    }//..~
  }//+++
  
  private void ssRenameListedFiles(){
    
    //-- check
    if(cmFileListRef==null){return;}
    if(cmFileListRef.isEmpty()){return;}
    
    //-- process
    int lpFailCount=0;
    for(File it : cmFileListRef){
      boolean lpRes = SubUtility.ccRenameFile(
        it.getAbsolutePath(),
        MainPanel.ccGetInstance().cmFindTB.getText(),
        MainPanel.ccGetInstance().cmReplaceTB.getText()
      );
      System.out.println("pppmain.MainActionManager.ssRenameListedFiles()::"
        + (lpRes?"successed:":"failed:")
        + it.getAbsolutePath()
      );
      if(!lpRes){lpFailCount++;}
    }//..~
    
    //-- return
    ssResetStatus();
    MainPanel.ccGetInstance().ccMessage(
      "::ended_with_error_sum:"+Integer.toString(lpFailCount)
    );
  
  }//+++
  
  private void ssResetStatus(){
    MainPanel.ccGetInstance().cmFindTB.setText("SA0000_");
    MainPanel.ccGetInstance().cmReplaceTB.setText("SAxxxx_");
    MainPanel.ccGetInstance().cmStatusBar.setText("::Standby");
    MainPanel.ccGetInstance().cmFileListModel.clear();
    MainPanel.ccGetInstance().cmFileListModel.addElement("..");
    cmFileListRef = null;
  }//+++
  
}//***eof
