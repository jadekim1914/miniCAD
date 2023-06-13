package figuras;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public class Linea extends ModeloFigura{
        
        public Linea(){
            super();
            nombre = "Linea";
            puntos = new PuntosFigu(2);
        }
        
        @Override
        public void dibujar(Graphics2D g){
            if(!canDraw()){
                puntos.dibujar(g);
                return;
            }
            
            if(isSelected()){
                g.setStroke(new BasicStroke(10));
                g.setColor(Color.RED);
            }else{
                g.setColor(background);
            }
            
            g.drawLine(puntos.getXPointsInt()[0], puntos.getYPointsInt()[0], puntos.getXPointsInt()[1], puntos.getYPointsInt()[1]);
            
            //Reset color
            g.setColor(Color.BLACK);
        }
}
