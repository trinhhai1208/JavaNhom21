package frontend.utils;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;


public class Animation {
	//di chuột vào panel
	public static void onHoover(JPanel p,Color o,Color n) {
		p.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				p.setBackground(n);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				p.setBackground(o);
			}
		});
	}	
}
