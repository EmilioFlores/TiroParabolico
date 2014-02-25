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
    
    private int posX;
    private int posY;
    private int velocidad;
    Animacion animacion;
    
    // Método constructor del objeto 
    public Base (int posX,int posY,Animacion animacion,int velocidad) {
        this.posX = posX;
        this.posY = posY;
        this.animacion = animacion;
        this.velocidad = velocidad;
    }
    // metodo que ajusta la posicion x
    public void setPosX (int posX) {
        this.posX = posX;
    }
    // método que regresa la posicion x
    public int getPosX () {
        return this.posX;
    }
    // metodo que ajusta la posicion y
    public void setPosY (int posY) {
        this.posY = posY;
    }
    // metodo que regresa la posicion y
    public int getPosY () {
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
		return new Rectangle(getPosX(),getPosY(),getAncho(),getAlto());
    }
    // metodo que me verifica si el objeto intersecta con el otro objeto. 
//    public boolean intersecta(Malo obj){
//		return getPerimetro().intersects(obj.getPerimetro());
//	}
     // metodo que me regresa un rectangulo con el perimetro de la imagen
   

    public  int getVelocidad() {
        return velocidad;
    }
    
    public  void setVelocidad(int velo) {
        velocidad = velo;
    }

     public boolean checaIntersecion (int posX, int posY) {
            return getPerimetro().contains(new Point(posX,posY));
        }
     
     public Rectangle cuadroAbajo () {
        return new Rectangle (getPosX()+getAncho()/4, getPosY()+3*getAlto()/4, getAncho()/2, getAlto()/4);
    }
    

                
}
