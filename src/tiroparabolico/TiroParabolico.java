/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiroparabolico;

import javax.swing.JFrame;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_P;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.ImageIcon;


/**
 *
 * @author Emilio
 */
public class TiroParabolico extends JFrame implements KeyListener, MouseListener, Runnable {

    // valores numericos
    private int velocidad; // velocidad constante 
    private double velocidadY;
    private double velocidadX; 
    private double angulo; // angulo variable
    private final int gravedad = 10; 
    private final int posInicialTiro = 400; // empieza en posicion 100 
    private int tamanoY;
    private int tamanoX;
    private int x; // posicion x de la canasta
    private double radianes; // radianes
    private double maxHeight; // altura maxima
    private double maxDista; // distancia maxima
    private final int panelScaleFactor = 300;
    private final int xPanelOrigin = 40, yPanelOrigin = 400;
    // boleanos
    private boolean pausa;      // bool que checa si se pauso
    private boolean clicked;    // checador que checa si se movio el objeto con tecla
    private boolean presionado; // checa si se presiono el objeto para lanzarse
    private boolean xClick; // booleano de que se movio la canasta
    
    //floating
    private long tiempoActual;  // tiempo actual
    private long tiempoInicial; // tiempo inicial
    
    
    // images
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private Image foto1;
    private Image foto2;
    private Image foto3;
    private Image foto4;
    private Image foto5;
    private Animacion anim;
    
    
    // sounds
    private SoundClip choqueConCanasta; // sonido cuando la pelota choca con canasta
    private SoundClip choqueConSuelo; // sonido si choca con el suelo
    private SoundClip sonidoAlLanzar; // sonido cuando se lanza la pelota 
  
    
    /**
     * @param args the command line arguments
     */

     public TiroParabolico () {
        init();
        start();       
    }
     /**
      * Se inicializan las variables en el metodo <I>Init</> @returns nada
      * 
      */
     
    public double AlturaMaxima (double angulo, int velocidad) {
        
        return ((velocidad*velocidad)*Math.sin(Math.toRadians(angulo))*Math.sin(Math.toRadians(angulo)))/(2*gravedad);
    
    }
    
    public double distMaxima (double angulo, int velocidad) {
        return ((velocidad*velocidad)*Math.sin(Math.toRadians(2*angulo)))/(gravedad);
    
    }
    void init () {
        
        addKeyListener(this);
        addMouseListener(this);
        setSize(1000, 500);
        setBackground(Color.PINK);
        
        tamanoY = posInicialTiro;
        tamanoX = getWidth();
       
        // loop que me calcula el angulo y la velocidad correcto para que la maxDista y max Height
        //  no se saldran del applet
        do { 
        velocidad = (5 + (int) (Math.random() * 8));
        angulo = 10 + (Math.random()*70);
        
        System.out.println(velocidad); 
        System.out.println(angulo);

        
        maxHeight = AlturaMaxima (angulo, velocidad)*100;
        maxDista = distMaxima (angulo, velocidad)*100;
        

        
        } while (maxHeight > tamanoY || (maxDista > tamanoX || maxDista < tamanoX/2) );
        
        velocidadX =  velocidad*Math.cos(Math.toRadians(angulo));
        velocidadY =  velocidad*Math.sin(Math.toRadians(angulo));
        
        System.out.println(velocidad); 
        System.out.println(velocidadX); 
        System.out.println(velocidadY);
        
        
        
//        choqueConCanasta = new SoundClip("Images/explosion.wav");
//        choqueConSuelo = new SoundClip("Images/explosion.wav");
//        sonidoAlLanzar = new SoundClip("Images/explosion.wav");
//        
//        foto1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/B1.png"));
//        foto2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/B1.png"));
//        foto3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/B1.png"));
//        foto4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/B1.png"));
//        foto5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/B1.png"));
//        
//        anim = new Animacion();
//        
//        anim.sumaCuadro(foto1, 200);
//        anim.sumaCuadro(foto2, 200);
//        anim.sumaCuadro(foto3, 200);
//        anim.sumaCuadro(foto4, 200);
//        anim.sumaCuadro(foto5, 200);
        
        
    }
    
     /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     */
    public void start() {
        //Crea el thread
        Thread hilo = new Thread(this);
        //Inicializa el thread
        hilo.start();
    }
    
  
    /**
     * Metodo stop sobrescrito de la clase Applet. En este metodo se pueden
     * tomar acciones para cuando se termina de usar el Applet. Usualmente
     * cuando el usuario sale de la pagina en donde esta este Applet.
     */
    public void stop() {

    }

    /**
     * Metodo destroy sobrescrito de la clase Applet. En este metodo se toman
     * las acciones necesarias para cuando el Applet ya no va a ser usado.
     * Usualmente cuando el usuario cierra el navegador.
     */
    public void destroy() {

    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, checa si pauso el juego, actualiza
     * llama al metodo checaColision, finalmente se repinta el
     * <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    
    public void run() {

        //Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();

        //Ciclo principal del Applet. Actualiza y despliega en pantalla la animación hasta que el Applet sea cerrado
        while (true) {
            if (!pausa) {
                actualiza();
                checaColision();
            }

            repaint();

            //Hace una pausa de 200 milisegundos
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                // no hace nada
            }
        }

    }

    /**
     * Metodo usado para checar la colision del objeto bueno con algún objeto
     * malo de la lista de malos, checa si algun malo choco con el <code>Applet
     * </code> por la parte inferior.
     */
    public void checaColision() {

        
        


        

        

    }

    /**
     * El método actualiza() del <code>Applet</code> que actualiza las
     * posiciones de el objeto bueno, los objetos malos y da los tiempos para
     * cada segmento de animacion.
     */
    public void actualiza() {

        if (clicked) {
            
                //si es x, se le agrega la velocidad acumulada en eje X a la posicion
                

                //se da el valor de 1 a la velocidad en Y para resetearlo
            } // si se mueve en Y, entonces es un movimiento vertical, y se resetea el valor de x
            else {
                
            }

        

        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        long tiempoTranscurrido
                = System.currentTimeMillis() - tiempoActual;

        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;

        //Actualiza la animación en base al tiempo transcurrido
       // bueno.getAnimacion().actualiza(tiempoTranscurrido);

        //Actualiza la animacion de los malos
    }
       
        

       


    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer

        dbImage = createImage(this.getSize().width, this.getSize().height);
        dbg = dbImage.getGraphics();

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);
            
        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {

        
       g.setColor(Color.WHITE);
       g.fillOval(300, 300, 3, 3);
       DrawParabola(g);

        

    }
    
    void DrawParabola (Graphics g) {
        
      int xScreen = 0, yScreen = tamanoY;
   
      int vel = (int) velocidadY;
    
       
      for ( int i = tamanoY; i >= 0; i-=gravedad ) {
         
     
         xScreen = xScreen + 6;  
         yScreen = yScreen + vel;
         vel += gravedad;
        
         
         
         g.fillOval(xScreen, yScreen, 3, 3);
     

      }
    
      
      
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == VK_P) {
            pausa = !pausa;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {

        clicked = true;
        if (e.getKeyCode() == KeyEvent.VK_A) {    //Presiono flecha izquierda
            x = -15;
            xClick = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {    //Presiono flecha derecha
            x = 15;
            xClick = true;
        }
    }

    public void mousePressed(MouseEvent e) {
        
    }

   

    
    @Override
    public void mouseClicked(MouseEvent e) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public void mouseReleased(MouseEvent e) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
