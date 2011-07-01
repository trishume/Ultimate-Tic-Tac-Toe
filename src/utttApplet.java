import javax.swing.JApplet;

public class utttApplet extends JApplet {
     public void init(){
    	 tttcolor5 t = new tttcolor5(false);
    	 this.add(t.theWindow.getContentPane());
    	 (t.theWindow.getContentPane()).repaint();
     }
     public static void main(String args){
    	 utttApplet a = new utttApplet();
    	 a.init();
     }
}
