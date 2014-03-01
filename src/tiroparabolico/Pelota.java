/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiroparabolico;

/**
 * @author daniel rodriguez
 * @author Emilio Flores
 */
public class Pelota extends Base{
    
    
    // Se declaran todas las variables
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
     * Metodo que regresa a la granada al inicio y desactiva el movimiento
     */
    public void volverInicio() {
        
        setPosX(xInicial);
        setPosY(yInicial);
        movimiento = false;
    }
    /**
     * Calcula las velocidades minimas y maximas para ambos ejes en x o en y
     * Empieza a tomar el tiempo y activa el movimiento
     */
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
    /**
     * Método que hace que el objeto granada avanze utilizando las formulas de la fisica
     * de desplazamiento en cualquier momento de tiempo
     * Solo avanza si se lo permite
     */
    public void avanza () {
        if (movimiento) {
            double time = (double)(System.currentTimeMillis() - tiempoInicial)/1000;
           
            setPosX(xInicial + velX * time);
            setPosY(yInicial - (velY*time - 0.5*aceleracion*time*time));
           
            
        }
        
    }
    /**
     * Método de pausa para el juego
     */
     public void pausa() {
        tiempoPausa = System.currentTimeMillis();
    }
     
     /**
      * método que facilita el envio de datos para ser guardados en un string de una linea.
      * Regresa un string que se concatena al string de la clase main.
      * @return <code> String </code> 
      */
     public String getDatos() {
       
        String salida = String.valueOf(velX) + " " + String.valueOf (velY) + " " + String.valueOf (this.getPosX())+ " ";
        salida += String.valueOf (this.getPosY()) + " " + String.valueOf(movimiento) + " " +
       String.valueOf((System.currentTimeMillis() - tiempoInicial)) + " ";
        salida += String.valueOf(aceleracion);
        return salida;
        
    }
     /**
      * Método que carga los datos dependiendo el indice del arreglo que se le envia
      * despues de leer los datos para cargarlos en el JFrame
      * @param veloX es la velocidad que llevaba el objeto en X
      * @param veloY velocidad que el objeto llevaba en Y
      * @param xIni x donde se quedo el objeto
      * @param yIn y donde se quedo el objeto
      * @param mov si el objeto se estaba o no moviento
      * @param tiempoIni tiempo en el que el objeto estaba
      * @param acceleracion la gravedad que tenia
      */
    public void setDatos(String veloX, String veloY, String xIni, String yIn, String mov, String tiempoIni, String acceleracion) {
       
        long dif = (Long.parseLong(tiempoIni));
        tiempoInicial = System.currentTimeMillis() - dif;
        velX = Double.parseDouble(veloX);
        velY = Double.parseDouble(veloY);
        this.setPosX(Double.parseDouble(xIni));
        this.setPosY(Double.parseDouble(yIn));
        movimiento = Boolean.parseBoolean(mov);
        aceleracion = Double.parseDouble(acceleracion);
      
       
    }
    /**
     * método que quita la <code>pausa</code> del juego
     */
    public void despausa() {
        tiempoInicial += System.currentTimeMillis() - tiempoPausa;
    }
    /** 
     * Método que calcula la máxima velocidad en Y que puede tener dependiendo de la gravedad
     * @return double que representa la máxima velocidad
     */
    private double getMaxVelY() {
        return Math.sqrt(2*yInicial*aceleracion);
    }
    
    /**
     * Método que utiliza las formlas de <I>fisica</I> para calcular la velocidad en x
     * @param posX 
     * @param posY
     * @return double que es la velocidad en x
     */
    private double getVx(double posX, double posY) {
        double t = (velY + Math.sqrt(velY*velY - 2*aceleracion*(yInicial - posY)))/aceleracion;
        return (posX-xInicial)/t;
    }
    /**
     * Metodo para verificar movimiento
     * @return booleano de movimiento o no
     */
    public boolean getMovimiento() {
        return movimiento;
    }
    
 
    /**
     * Método para asignar posicion en X
     * @param X 
     */
    public void setPosX(int X) {
        xInicial = X;
        setPosX(X);
    }
    
    /**
     * Método para asignar posicion en y
     * @param Y 
     */
    public void setPosY(int Y) {
        yInicial = Y;
        setPosY(Y);
    }
    
    /**
     * Método para asignar velocidad en X
     * @param v 
     */
    public void setVelX(double v) {
        velX = v;
    }
    
    /**
     * Método para asignar velocidad en Y
     * @param v 
     */
    public void setVelY(double v) {
        velY = v;
    }
    
    /**
     * Método que regresa velocidad en eje X
     * @return double que es velocidad X
     */
     public double getVelX() {
        return velX;
    }
     
     /**
      * Método que regresa la velocidad del eje Y
      * @return double = vlocidad Y
      */
    public double getVelY() {
        return velY;
    }
    
    /**
     * Método que asigna la aceleracion del aplet de manera estatica
     * @param a que representa la aceleracion
     */
     public static void setAceleracion(double a) {
        aceleracion = a;
    }
    
   /**
    * Método que acessa a la variable estatica de la <code>aceleracion</code>
    * @return aceleracion
    */
    public static double getAceleracion() {
        return aceleracion;
    }
}