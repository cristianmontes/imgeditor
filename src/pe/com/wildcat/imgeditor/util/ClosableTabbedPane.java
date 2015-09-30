/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.com.wildcat.imgeditor.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JTabbedPane;

/**
 *
 * @author cmontes
 */
public class ClosableTabbedPane extends JTabbedPane{
	private TabCloseUI closeUI = new TabCloseUI(this);
        //private Map<Integer, UUID> mapComponentes = null;
        private List<UUID> listComponentes = null;
	
        @Override
	public void paint(Graphics g){
		super.paint(g);
		closeUI.paint(g);
	}
	
        
	public void addTab(UUID identity, String title, Component component) {
            if(listComponentes == null){
                listComponentes = new ArrayList<UUID>();
            }
            if(!listComponentes.contains(identity)){
                listComponentes.add(identity);
                super.addTab(title+"    ", component);
            }
	}
	
	
	public String getTabTitleAt(int index) {
		return super.getTitleAt(index).trim();
	}
        
        @Override
        public void removeTabAt(int index){
            listComponentes.remove(index);
            super.removeTabAt(index);
        }
        /*
        @Override
        public void removeAll(){
            listComponentes = new ArrayList<UUID>(); 
            super.removeAll();
        }*/

        public List<UUID> getListComponentes() {
            return listComponentes;
        }

        public void setListComponentes(List<UUID> listComponentes) {
            this.listComponentes = listComponentes;
        }
        
        
	
	//private class TabCloseUI implements MouseListener, MouseMotionListener {
        private class TabCloseUI extends MouseAdapter {
		private ClosableTabbedPane  tabbedPane;
		private int closeX = 0 ,closeY = 0, meX = 0, meY = 0;
		private int selectedTab;
		private final int  width = 8, height = 8;
		private Rectangle rectangle = new Rectangle(0,0,width, height);
		private TabCloseUI(){}
		public TabCloseUI(ClosableTabbedPane pane) {
			
			tabbedPane = pane;
			tabbedPane.addMouseMotionListener(this);
			tabbedPane.addMouseListener(this);
		}
		
		public void mouseReleased(MouseEvent me) {
			if(closeUnderMouse(me.getX(), me.getY())){
				boolean isToCloseTab = tabAboutToClose(selectedTab);
				if (isToCloseTab && selectedTab > -1){			
					tabbedPane.removeTabAt(selectedTab);
				}
				selectedTab = tabbedPane.getSelectedIndex();
			}
		}

		public void mouseMoved(MouseEvent me) {	
			meX = me.getX();
			meY = me.getY();			
			if(mouseOverTab(meX, meY)){
				controlCursor();
				tabbedPane.repaint();
			}
		}

		private void controlCursor() {
			if(tabbedPane.getTabCount()>0)
				if(closeUnderMouse(meX, meY)){
					tabbedPane.setCursor(new Cursor(Cursor.HAND_CURSOR));	
					if(selectedTab > -1)
						tabbedPane.setToolTipTextAt(selectedTab, "Close " +tabbedPane.getTitleAt(selectedTab));
				}
				else{
					tabbedPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if(selectedTab > -1)
						tabbedPane.setToolTipTextAt(selectedTab,"");
				}	
		}

		private boolean closeUnderMouse(int x, int y) {		
			rectangle.x = closeX;
			rectangle.y = closeY;
			return rectangle.contains(x,y);
		}

		public void paint(Graphics g) {
			
			int tabCount = tabbedPane.getTabCount();
			for(int j = 0; j < tabCount; j++)
				if(tabbedPane.getComponent(j).isShowing()){			
					int x = tabbedPane.getBoundsAt(j).x + tabbedPane.getBoundsAt(j).width -width-5;
					int y = tabbedPane.getBoundsAt(j).y +5;	
					drawClose(g,x,y);
					break;
				}
			if(mouseOverTab(meX, meY)){
				drawClose(g,closeX,closeY);
			}
		}

		private void drawClose(Graphics g, int x, int y) {
			if(tabbedPane != null && tabbedPane.getTabCount() > 0){
				Graphics2D g2 = (Graphics2D)g;
                                if(isUnderMouse(x,y)){
                                    drawColored(g2, Color.BLACK, x, y, new BasicStroke(3,BasicStroke.JOIN_ROUND,BasicStroke.CAP_ROUND));
                                }else{
                                    drawColored(g2, Color.BLACK, x, y, new BasicStroke(1,BasicStroke.JOIN_ROUND,BasicStroke.CAP_ROUND));
                                }
				
			}
		}

		private void drawColored(Graphics2D g2, Color color, int x, int y, Stroke stroke) {
			g2.setStroke(stroke);
			g2.setColor(Color.BLACK);
			g2.drawLine(x, y, x + width, y + height);
			g2.drawLine(x + width, y, x, y + height);
			g2.setColor(color);
			g2.setStroke(stroke);
			g2.drawLine(x, y, x + width, y + height);
			g2.drawLine(x + width, y, x, y + height);
		}

		private boolean isUnderMouse(int x, int y) {
			if(Math.abs(x-meX)<width && Math.abs(y-meY)<height )
				return  true;		
			return  false;
		}

		private boolean mouseOverTab(int x, int y) {
			int tabCount = tabbedPane.getTabCount();
			for(int j = 0; j < tabCount; j++)
				if(tabbedPane.getBoundsAt(j).contains(meX, meY)){
					selectedTab = j;
					closeX = tabbedPane.getBoundsAt(j).x + tabbedPane.getBoundsAt(j).width -width-5;
					closeY = tabbedPane.getBoundsAt(j).y +5;					
					return true;
				}
			return false;
		}
	}
        
	public boolean tabAboutToClose(int tabIndex) {
		return true;
	}	
}
