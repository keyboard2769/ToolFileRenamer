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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public final class MainPanel extends JPanel {

  private static MainPanel self = null;
  public final static MainPanel ccGetInstance() {
    if (self == null) {
      self = new MainPanel();
      return self;
    } else {
      return self;
    }
  }//++!
  
  //===

  public final JTextField
    cmExtentionTB,
    cmFindTB,
    cmReplaceTB,
    //--
    cmStatusBar
  ;//...
  
  public final DefaultListModel<String> cmFileListModel;
  
  private MainPanel() {

    super(new BorderLayout(2, 2));

    //-- components ** status bar
    cmStatusBar = new JTextField("::standby");
    cmStatusBar.setEditable(false);
    cmStatusBar.setEnabled(false);
    
    //-- components ** text box
    cmExtentionTB = new JTextField(".xls");
    cmExtentionTB.setPreferredSize(new Dimension(80, 32));
    
    cmFindTB = new JTextField("SA0000_");
    cmFindTB.setPreferredSize(new Dimension(80, 32));
    
    cmReplaceTB = new JTextField("SAxxxx_");
    cmReplaceTB.setPreferredSize(new Dimension(80, 32));
    
    //-- components ** button
    JButton lpResetSW = new JButton("reset");
    lpResetSW.setMnemonic(KeyEvent.VK_E);
    lpResetSW.setActionCommand("--action-reset");
    lpResetSW.addActionListener(MainActionManager.ccGetInstance());
    
    JButton lpProcessSW = new JButton("process");
    lpProcessSW.setMnemonic(KeyEvent.VK_R);
    lpProcessSW.setActionCommand("--action-process");
    lpProcessSW.addActionListener(MainActionManager.ccGetInstance());
    
    //-- components ** view
    cmFileListModel = new DefaultListModel<>();
    cmFileListModel.addElement("..");
    JList<String> lpFileViewList = new JList<>(cmFileListModel);
    lpFileViewList.setEnabled(false);
    JScrollPane lpCenterWingPane = new JScrollPane(lpFileViewList);
    lpCenterWingPane.setBorder(
      BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2)
    );
    
    //-- left-wing
    JPanel lpLeftWingPanel = new JPanel(new GridLayout(0,1,2,2));
    lpLeftWingPanel.add(new JLabel("Extention:"));
    lpLeftWingPanel.add(cmExtentionTB);
    lpLeftWingPanel.add(new JLabel("Find:"));
    lpLeftWingPanel.add(cmFindTB);
    lpLeftWingPanel.add(new JLabel("Replace:"));
    lpLeftWingPanel.add(cmReplaceTB);
    lpLeftWingPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
    lpLeftWingPanel.add(lpResetSW);
    lpLeftWingPanel.add(lpProcessSW);
    
    add(lpLeftWingPanel,BorderLayout.LINE_START);
    add(lpFileViewList,BorderLayout.CENTER);
    add(cmStatusBar,BorderLayout.PAGE_END);
    
    setBorder(BorderFactory.createEtchedBorder());
    
  }//++!
  
  public final void ccMessage(String pxLine){
    cmStatusBar.setText(pxLine);
  }//+++

}//***eof
