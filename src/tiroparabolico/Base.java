package tiroparabolico;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emilio
 */
public class Base {
    
    private double posX;
    private double posY;
    private int velocidad;
    Animacion animacion;
    private static int W;
    private static int H;
    
    
    // Método constructor del objeto 
    public Base (double posX,double posY,Animacion animacion) {
        this.posX = posX;
        this.posY = posY;
        this.animacion = animacion;
   
    }
    // metodo que ajusta la posicion x
    public void setPosX (double posX) {
        this.posX = posX;
    }
    // método que regresa la posicion x
    public double getPosX () {
        return this.posX;
    }
    // metodo que ajusta la posicion y
    public void setPosY (double posY) {
        this.posY = posY;
    }
    // metodo que regresa la posicion y
    public double getPosY () {
        return this.posY;
    }
  
  
    // metodo que ajusta la imagen del objeto
    public void setAnimacion (Animacion anim) {
        this.animacion = anim;
    }
    // metodo que regresa la imagen
    public Animacion getAnimacion () {
        return this.animacion;
    }
    // metodo que calcula lo ancho de la imagen
    public int getAncho () {
        return new ImageIcon(this.animacion.getImagen()).getIconWidth();
    }
    // metodo que calcula lo alto de la imagen
    public int getAlto() {
        return new ImageIcon (this.animacion.getImagen()).getIconHeight();
    }
    // metodo que me regresa un rectangulo con el perimetro de la imagen
    public Rectangle getPerimetro(){
		return new Rectangle((int) getPosX(),(int) getPosY(),getAncho(),getAlto());
    }
    // metodo que me verifica si el objeto intersecta con el otro objeto. 
//    public boolean intersecta(Malo obj){
//		return getPerimetro().intersects(obj.getPerimetro());
//	}
     // metodo que me regresa un rectangulo con el perimetro de la imagen
   


    

    public static void setW(int w) {
        W = w;
    }
    
  
    public static int getW() {
        return W;
    }
    
  
    public static void setH(int h) {
        H = h;
    }
    
  
    public static int getH() {
        return H;
    }
    
    public void actualiza(long tiempo) {
        animacion.actualiza(tiempo);
    }

     public boolean checaIntersecion (int posX, int posY) {
            return getPerimetro().contains(new Point(posX,posY));
        }
     
     public Rectangle cuadroAbajo () {
        return new Rectangle ((int) getPosX()+getAncho()/4, (int)getPosY()+3*getAlto()/4, getAncho()/2, getAlto()/4);
    }
    

                
}
