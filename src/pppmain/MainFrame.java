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

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainFrame {
  
  public static final String C_V_PATHSEP
   = System.getProperty("file.separator");
  
  public static final String   C_V_NEWLINE
   = System.getProperty("line.separator");
  
  public static final String   C_V_OS
   = System.getProperty("os.name");
  
  public static final String C_V_PWD
   = System.getProperty("user.dir");

  private static JFrame pbFrame;
  
  //===
  
  public static final JFrame ccGetFrame(){return pbFrame;}//+++
  
  private static void ssSetupFrame() {

    //-- menu bar
    JMenuItem lpBrowseItem = new JMenuItem("browse");
    lpBrowseItem.setActionCommand("--action-browse");
    lpBrowseItem.setMnemonic(KeyEvent.VK_O);
    lpBrowseItem.addActionListener(MainActionManager.ccGetInstance());
    
    JMenuItem lpInfoItem = new JMenuItem("info");
    lpInfoItem.setActionCommand("--action-info");
    lpInfoItem.setMnemonic(KeyEvent.VK_H);
    lpInfoItem.addActionListener(MainActionManager.ccGetInstance());
    
    JMenuItem lpQuitItem = new JMenuItem("quit");
    lpQuitItem.setActionCommand("--action-quit");
    lpQuitItem.setMnemonic(KeyEvent.VK_Q);
    lpQuitItem.addActionListener(MainActionManager.ccGetInstance());
    
    JMenu lpMenu=new JMenu("Operate");
    lpMenu.setMnemonic(KeyEvent.VK_F);
    lpMenu.add(lpBrowseItem);
    lpMenu.add(lpInfoItem);
    lpMenu.add(lpQuitItem);
    JMenuBar lpMenuBar = new JMenuBar();
    lpMenuBar.add(lpMenu);
    
    //-- frame
    pbFrame = new JFrame("File Renamer v0.011");
    pbFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pbFrame.setJMenuBar(lpMenuBar);
    pbFrame.getContentPane().add(MainPanel.ccGetInstance());
    
    //-- frame ** packup
    Point lpOrigin=ccGetScreenInitPoint();
    Dimension lpScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension lpWindowSize = new Dimension(640, 480);
    pbFrame.setLocation(
      lpOrigin.x+lpScreenSize.width/2-lpWindowSize.width/2,
      lpOrigin.y+lpScreenSize.height/2-lpWindowSize.height/2
    );
    pbFrame.setPreferredSize(lpWindowSize);
    pbFrame.setResizable(false);
    pbFrame.pack();
    pbFrame.setVisible(true);
    
  }//+++

  private static void ssApplyLookAndFeel(int pxIndex, boolean pxRead) {

    String lpTarget = UIManager.getCrossPlatformLookAndFeelClassName();

    //-- getting
    if (pxIndex >= 0) {
      UIManager.LookAndFeelInfo[] lpInfos = UIManager.getInstalledLookAndFeels();
      if (pxRead) {
        System.out.println("--installed lookNfeel: 0->");
        int cnt=0;
        for (UIManager.LookAndFeelInfo it : lpInfos) {
          System.out.print("["+Integer.toString(cnt)+"] ");
          System.out.println(it.getClassName());
          cnt++;
        }//..~
      }//..?
      int lpIndex=pxIndex>(lpInfos.length-1)?lpInfos.length-1:pxIndex;
      lpTarget = lpInfos[lpIndex].getClassName();
    }//..?

    //-- applying
    try {
      UIManager.setLookAndFeel(lpTarget);
    } catch (ClassNotFoundException e) {
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    } catch (InstantiationException e) {
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    } catch (IllegalAccessException e) {
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    } catch (UnsupportedLookAndFeelException e) {
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    }//..%

  }//+++
  
  public static final Point ccGetScreenInitPoint(){
    Point lpDummyPoint = null;
    Point lpInitPoint = null;
    for (
      GraphicsDevice lpDevice:
      GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()
    ){
      if (lpDummyPoint == null) {
        lpDummyPoint = 
          lpDevice.getDefaultConfiguration().getBounds().getLocation();
      } else if (lpInitPoint == null) {
        lpInitPoint = 
          lpDevice.getDefaultConfiguration().getBounds().getLocation();
      }//..?
    }//..~
    if (lpInitPoint == null) {lpInitPoint = lpDummyPoint;}
    if (lpInitPoint == null) {lpInitPoint = new Point(0,0);}
    return lpInitPoint;
  }//+++
  
  //=== entry

  public static void main(String[] args) {
    System.out.println("pppmain.MainFrame.main()::activate");
    ssApplyLookAndFeel(4, false);
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ssSetupFrame();
      }//+++
    });
    System.out.println("pppmain.MainFrame.main()::exit");
  }//++!

}//***eof
