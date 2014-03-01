/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiroparabolico;

/**
 *
 * @author Emilio
 */
public class Pelota extends Base{
    
    
 
    private double velX;
    private double velY;
    private final double CONST = .97;
    private double xInicial;
    private double yInicial;
    private boolean movimiento;
    private long tiempoInicial;
    private long tiempoPausa;
    private static double aceleracion = 300;
    
    /**
     *  Metdo constructor 
     * @param posX Pos inicial x
     * @param posY Pos inicial y
     * @param animacion Animacion asignada
     */
    public Pelota(double posX, double posY, Animacion animacion) {
        super(posX, posY, animacion); 
        xInicial = posX;
        yInicial = posY;
        volverInicio();
    }
    /**
     * Reaparece <code> Pelota </code> en el inicio
     */
    public void volverInicio() {
        
        setPosX(xInicial);
        setPosY(yInicial);
        movimiento = false;
    }
    
    public void arroja () {
        
        movimiento = true;
        tiempoInicial = System.currentTimeMillis();
        double maxVelY = CONST*getMaxVelY();
        double minVelY = .3*maxVelY;
        velY = Math.random()*(maxVelY - minVelY) + minVelY;

        double maxVelX = CONST*getVx(getW() - getAncho() + 10, getH() - getAlto());
        double minVx = getVx(getW()/2 + 20, getH() - 2*getAlto());
        velX = Math.random()*(maxVelX - minVx) + minVx;
        
        
    }
    public void avanza () {
        if (movimiento) {
            double time = (double)(System.currentTimeMillis() - tiempoInicial)/1000;
            setPosX(xInicial + velX * time);
            setPosY(yInicial - (velY*time - 0.5*aceleracion*time*time));
      
        }
    }
    
     public void pausa() {
        tiempoPausa = System.currentTimeMillis();
    }
     
     public String getDatos() {
        
        String salida = String.valueOf(velX) + " " + String.valueOf (velY) + " " + String.valueOf (this.getPosX())+ " ";
        salida += String.valueOf (this.getPosY()) + " " + String.valueOf(movimiento) + " " + String.valueOf(tiempoInicial) + " ";
        salida += String.valueOf(aceleracion);
        return salida;
        
    }
     
    public void setDatos(String veloX, String veloY, String xIni, String yIn, String mov, String tiempoIni, String acceleracion) {
        velX = Double.parseDouble(veloX);
        velY = Double.parseDouble(veloY);
        setPosX(Double.parseDouble(xIni));
        setPosY(Double.parseDouble(yIn));
        movimiento = Boolean.parseBoolean(mov);
      
        tiempoInicial= Long.parseLong(tiempoIni);
        aceleracion = Double.parseDouble(acceleracion);
        
       
    }
    
    public void despausa() {
        tiempoInicial += System.currentTimeMillis() - tiempoPausa;
    }
    
    private double getMaxVelY() {
        return Math.sqrt(2*yInicial*aceleracion);
    }
    
    
    private double getVx(double posX, double posY) {
        double t = (velY + Math.sqrt(velY*velY - 2*aceleracion*(yInicial - posY)))/aceleracion;
        return (posX-xInicial)/t;
    }
    
    public boolean getMovimiento() {
        return movimiento;
    }
    
 
    
    public void setPosX(int X) {
        xInicial = X;
        setPosX(X);
    }
    
 
    public void setPosY(int Y) {
        yInicial = Y;
        setPosY(Y);
    }
    
    public void setVelX(double v) {
        velX = v;
    }
    public void setVelY(double v) {
        velY = v;
    }
    
     public double getVelX() {
        return velX;
    }
    public double getVelY() {
        return velY;
    }
    
     public static void setAceleracion(double a) {
        aceleracion = a;
    }
    
   
    public static double getAceleracion() {
        return aceleracion;
    }
}