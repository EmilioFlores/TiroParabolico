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
    private int velocidadY;
    private int velocidadX; 
    private double angulo; // angulo variable
    private final double gravedad = 1;
    private int aceleracion;
    private final int posInicialTiro = 400; // empieza en posicion 100 
    private int tamanoY;
    private int tamanoX;
    private int x; // posicion x de la canasta
    private double radianes; // radianes
    private double maxHeight; // altura maxima
    private double maxDista; // distancia maxima
    private final int panelScaleFactor = 300;
    private final int xPanelOrigin = 40, yPanelOrigin = 400;
    private int xScreen = 0, yScreen = posInicialTiro;
    private double tiempo;
    private double velMinX;
    private double velMinY;
    private double velMaxX;
    private double velMaxY;
    private int caidas;
    private int vidas;
    private int score;
    
// boleanos
    private boolean pausa;      // bool que checa si se pauso
    private boolean clicked;    // checador que checa si se movio el objeto con tecla
    private boolean presionado; // checa si se presiono el objeto para lanzarse
    private boolean xClick; // booleano de que se movio la canasta
    private boolean chocado;
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
    private Image foto6;
    private Image foto7;
    private Image foto8;
    private Image foto9;
    private Image foto10;
    private Image foto11;
    private Image foto12;
    private Image foto13;
    private Image foto14;
    private Image foto15;
    private Image foto16;
    
    private Animacion anim;
    private Animacion animCanasta;
    
    
    // sounds
    private SoundClip choqueConCanasta; // sonido cuando la pelota choca con canasta
    private SoundClip choqueConSuelo; // sonido si choca con el suelo
    private SoundClip sonidoAlLanzar; // sonido cuando se lanza la pelota 
  
    private long tiempoAire;
    private long tMensaje;
   
    // objetos
    private Pelota granada;
    private Canasta canasta;
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
    //init     
        
        
        addKeyListener(this);
        addMouseListener(this);
        setSize(1000, 500);
        setBackground(Color.PINK);
        Base.setW(getWidth());
        Base.setH(getHeight());
        
        tamanoY = posInicialTiro;
        tamanoX = getWidth();
        caidas = 0;
        vidas = 4;
        score = 0;
        // loop que me calcula el angulo y la velocidad correcto para que la maxDista y max Height
        //  no se saldran del applet
       
        
        chocado = false;
        tMensaje = 500;
        tiempo = System.currentTimeMillis() - tMensaje - 1;    
        
        
//        choqueConCanasta = new SoundClip("Images/explosion.wav");
//        choqueConSuelo = new SoundClip("Images/explosion.wav");
//        sonidoAlLanzar = new SoundClip("Images/explosion.wav");
//        
        foto1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g1.png"));
        foto2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g2.png"));
        foto3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g3.png"));
        foto4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g4.png"));
        foto5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g5.png"));
        foto6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g6.png"));
        foto7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g7.png"));
        foto8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g8.png"));
        foto9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g9.png"));
        foto10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g10.png"));
        foto11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g11.png"));
        foto12 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g12.png"));
        foto13 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g13.png"));
        foto14 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g14.png"));
        foto15 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g15.png"));
        foto16 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g16.png"));
        
        anim = new Animacion();
        animCanasta = new Animacion(); 
        Image fotoCanasta = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/g6.png"));
        
        animCanasta.sumaCuadro(fotoCanasta, 200);
        
        anim.sumaCuadro(foto1, 200);
        anim.sumaCuadro(foto2, 200);
        anim.sumaCuadro(foto3, 200);
        anim.sumaCuadro(foto4, 200);
        anim.sumaCuadro(foto5, 200);
        anim.sumaCuadro(foto6, 200);
        anim.sumaCuadro(foto7, 200);
        anim.sumaCuadro(foto8, 200);
        anim.sumaCuadro(foto9, 200);
        anim.sumaCuadro(foto10, 200);
        anim.sumaCuadro(foto11, 200);
        anim.sumaCuadro(foto12, 200);
        anim.sumaCuadro(foto13, 200);
        anim.sumaCuadro(foto14, 200);
        anim.sumaCuadro(foto15, 200);
        anim.sumaCuadro(foto16, 200);
        
        
         canasta = new Canasta(0, 0, animCanasta);
         canasta.setPosX((int) (Math.random() * (getWidth() / 2 - canasta.getAncho())) + getWidth() / 2);
       //  canasta.setPosY(getHeight() - 3 * canasta.getAlto() / 2);
         canasta.setPosY(300);
        granada = new Pelota(-10, yPanelOrigin  - new ImageIcon(foto11).getIconHeight(), anim);
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
                Thread.sleep(60);
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

         if (canasta.getPosX() < getWidth() / 2) {
            canasta.setPosX(getWidth() / 2);
        }
        if (canasta.getPosX() + canasta.getAncho() > getWidth()) {
            canasta.setPosX(getWidth() - canasta.getAncho());
        }

        if (granada.getPosY() > getHeight() + 10) {
            granada.volverInicio();
            caidas++;
            if (caidas % 3 == 0) {
                vidas--;
                Pelota.setAceleracion(Pelota.getAceleracion() + 50);
             
            }
        }
        if (granada.checaIntersecion((int) canasta.getPosX(), (int) canasta.getPosY()) && !chocado) {
            score += 2;
            chocado = true;
            System.out.println (" choco ");
        }
        


        

        

    }

    /**
     * El método actualiza() del <code>Applet</code> que actualiza las
     * posiciones de el objeto bueno, los objetos malos y da los tiempos para
     * cada segmento de animacion.
     */
    public void actualiza() {
        
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        tiempo += tiempoTranscurrido;
        
        
        granada.avanza();
//        setDoublePosX(x + vx * time);
//
//        setDoublePosY(y - (vy*time - 0.5*aceleracion*time*time));
//        xScreen = xScreen + (int)velocidadX;  
//        yScreen = yScreen + vel;
       
       
        
           if (canasta.getMoveLeft()) {
            canasta.setPosX(canasta.getPosX() - 3);
            }
            if (canasta.getMoveRight()) {
                canasta.setPosX(canasta.getPosX() + 3);
            }

        if ( !pausa ) {
            if ( clicked ) {
                 granada.getAnimacion().actualiza(tiempoTranscurrido);
            }
           
        }
        
        if (chocado) {
          
                chocado = false;
                granada.volverInicio();
            }
        if (granada.getMovimiento()) {
            granada.actualiza(tiempoTranscurrido);
        }
        
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

       
     if (granada.getAnimacion() != null) {

       g.setColor(Color.BLACK);
       
       g.drawImage(granada.animacion.getImagen(), (int) granada.getPosX(), (int) granada.getPosY(), this);
       
       
       g.fillRect(0,getHeight()-100,70,100);
       DrawParabola(g);
     
     }
        g.setColor(Color.green);
        g.drawString("Score: " + score, 20, 55);
        g.setColor(Color.blue);
        g.drawString("Caídas: " + caidas, 20, 80);
        g.setColor(Color.red);
        g.drawString("Vidas: " + vidas, 20, 105);
     
       if (canasta != null && canasta.animacion.getImagen() != null) {
            g.drawImage(canasta.animacion.getImagen(), (int) canasta.getPosX(), (int) canasta.getPosY(), this);
        }
   }
    
    void DrawParabola (Graphics g) {
        
      
   
//      System.out.println(vel);
//      System.out.println(xScreen);
//      System.out.println(yScreen);
//      System.out.println(" ");
      
         g.setColor(Color.WHITE);
         g.fillOval(xScreen, yScreen, 10, 10);
     

      
      
      
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            canasta.setMoveLeft(false);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            canasta.setMoveRight(false);
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

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            canasta.setMoveLeft(true);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            canasta.setMoveRight(true);
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            if (!pausa) {
                pausa = true;
                granada.pausa();
            } else {
                pausa = false;
                granada.despausa();
            }
            
    }
    }
    public void mousePressed(MouseEvent e) {
        
    }

   

    
    @Override
    public void mouseClicked(MouseEvent e) {
        if ( !granada.getMovimiento() && granada.checaIntersecion(e.getX(), e.getY()))
        {
            clicked = true;
            granada.arroja();
        }
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
