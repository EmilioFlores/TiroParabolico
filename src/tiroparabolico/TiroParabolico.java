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
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_P;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    
    private String[] arr;  
    private final String nombreArchivo = "guardar.txt";
    
// boleanos
    private boolean pausa;      // bool que checa si se pauso
    private boolean clicked;    // checador que checa si se movio el objeto con tecla
    private boolean presionado; // checa si se presiono el objeto para lanzarse
    private boolean xClick; // booleano de que se movio la canasta
    private boolean chocado;
    private boolean instrucciones;
    private boolean sonido;
//floating
    private long tiempoActual;  // tiempo actual
    private long tiempoInicial; // tiempo inicial

    // images
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private Image foto1;
    private Image fotogranada;
    private Image fotogranada1;
    private Image fotogranada2;
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
    private Image fotoCanasta;
    private Image fotoCanasta1;
    private Image fotoCanasta2;
    private Image tableroInstrucciones;
    private Image pausaImagen; // imagen de causado
    private Image background; 

    private Animacion anim;
    private Animacion animCanasta;
    private Animacion animg;

    // sounds
    private SoundClip choqueConCanasta; // sonido cuando la pelota choca con canasta
    private SoundClip choqueConSuelo; // sonido si choca con el suelo

    private long tiempoAire;
    private long tMensaje;

    // objetos
    private Pelota granada;
    private Canasta canasta;

    /**
     * @param args the command line arguments
     */

    public TiroParabolico() {
        init();
        start();
    }

    /**
     * Se inicializan las variables en el metodo <I>Init</> @returns nada
     *
     */

    public double AlturaMaxima(double angulo, int velocidad) {

        return ((velocidad * velocidad) * Math.sin(Math.toRadians(angulo)) * Math.sin(Math.toRadians(angulo))) / (2 * gravedad);

    }

    public double distMaxima(double angulo, int velocidad) {
        return ((velocidad * velocidad) * Math.sin(Math.toRadians(2 * angulo))) / (gravedad);
    }

    void init() {
    //init     

        addKeyListener(this);
        addMouseListener(this);
        setSize(1000, 500);
      
        
        
        
        background = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/back.jpg"));
        
        Base.setW(getWidth());
        Base.setH(getHeight());

        tamanoY = posInicialTiro;
        tamanoX = getWidth();
        caidas = 0;
        vidas = 5;
        score = 0;
        // loop que me calcula el angulo y la velocidad correcto para que la maxDista y max Height
        //  no se saldran del applet

        chocado = false;
        instrucciones = false;
        sonido = true;
        tMensaje = 500;
        tiempo = System.currentTimeMillis() - tMensaje - 1;

        choqueConCanasta = new SoundClip("Mono/coin.wav");
        choqueConSuelo = new SoundClip("Mono/exp.wav");
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
        
       fotogranada=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/granada.png"));
       fotogranada1=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/granada1.png"));
       fotogranada2=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/granada2.png"));
     
       pausaImagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/pause.png"));
        
       fotoCanasta = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/Basket1.png"));
       fotoCanasta1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/Basket2.png"));
       
       //fotoCanasta2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/Basket2.png"));
       tableroInstrucciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/instruccionesTiroParabolico.jpg"));
       
        anim = new Animacion();
        animCanasta = new Animacion();
        animg= new Animacion();

        animCanasta.sumaCuadro(fotoCanasta, 700);
        animCanasta.sumaCuadro(fotoCanasta1, 700);
      //  animCanasta.sumaCuadro(fotoCanasta2, 200);
        

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
        
        animg.sumaCuadro(fotogranada, 200);
        animg.sumaCuadro(fotogranada1, 200);
        animg.sumaCuadro(fotogranada2, 200);

        canasta = new Canasta(0, 0, animCanasta);
        canasta.setPosX((int) (Math.random() * (getWidth() / 2 - canasta.getAncho())) + getWidth() / 2);
        //  canasta.setPosY(getHeight() - 3 * canasta.getAlto() / 2);
        canasta.setPosY(400);
        granada = new Pelota(30, yPanelOrigin - new ImageIcon(fotogranada).getIconHeight(), animg);
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
            if (!pausa && !instrucciones) {
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

        if (granada.getPosY() > getHeight() + 10 ) {
            if (sonido) {
                choqueConSuelo.play();
            }

            granada.volverInicio();
            caidas++;
            if (caidas % 3 == 0) {
                vidas--;
                Pelota.setAceleracion(Pelota.getAceleracion() + 50);

            }
        }
        if (granada.intersectaCentro(canasta) && !chocado) {
            score += 2;
           granada.volverInicio();
            if (sonido) {
                choqueConCanasta.play();
            }

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
        
        //granada.avanza();
//        setiempotDoublePosX(x + vx * time);
//
//        setDoublePosY(y - (vy*time - 0.5*aceleracion*time*time));
//        xScreen = xScreen + (int)velocidadX;  
//        yScreen = yScreen + vel;

        if (canasta.getMoveLeft()) {
            canasta.setPosX(canasta.getPosX() - 6);
        }
        if (canasta.getMoveRight()) {
            canasta.setPosX(canasta.getPosX() + 6);
        }

        
        if (granada.getMovimiento()) {
            granada.actualiza(tiempoTranscurrido);
            
        }
        
        canasta.animacion.actualiza(tiempoTranscurrido);
        
       
       

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
     *ph
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
g.drawImage(background, 0, 0, this);
        
        
        if (granada.getAnimacion() != null) {

            g.setColor(Color.BLACK);

            g.drawImage(granada.animacion.getImagen(), (int) granada.getPosX(), (int) granada.getPosY(), this);

            g.fillRect(0, getHeight() - 100, 70, 100);

        }
        
        g.setFont(new Font("TimesRoman", Font.PLAIN, 29)); 
        g.setColor(Color.ORANGE);
        g.drawString("Score: " + score, 20, 55);
        g.setColor(Color.cyan);
        g.drawString("Caídas: " + caidas, 20, 80);
        g.setColor(Color.red);
        g.drawString("Vidas: " + vidas, 20, 105);
        g.setColor(Color.black);
  

        
        if (canasta != null && canasta.animacion.getImagen() != null) {
            g.drawImage(canasta.animacion.getImagen(), (int) canasta.getPosX(), (int) canasta.getPosY(), this);
        }
        if (instrucciones) {

            g.drawImage(tableroInstrucciones, getWidth() / 2 - new ImageIcon(tableroInstrucciones).getIconWidth() / 2,
                    getHeight() / 2 - new ImageIcon(tableroInstrucciones).getIconHeight() / 2, this);    // Tablero de instrucciones
        }
        if (pausa) {
               // g.drawImage(pausaImagen.getImage(), getWidth()/2 - pausaImagen.getIconWidth()/2, getHeight()/2 - pausaImagen.getIconHeight()/2 , this);
                g.drawImage(pausaImagen,getWidth()/2 - new ImageIcon(pausaImagen).getIconWidth()/2, getHeight()/2 - new ImageIcon(pausaImagen).getIconHeight()/2, this);
            }
        
     
        
    }
    public void grabaArchivo() throws IOException {
                                                          
                PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
                fileOut.println(score + " " + caidas + " " + vidas + " " + granada.getDatos() +" " +  chocado);
                fileOut.close();
    }
    
    
public void leeArchivo() throws IOException {
                                                          
                BufferedReader fileIn;
                try {
                   fileIn = new BufferedReader(new FileReader(nombreArchivo));
                
                   
                   String dato=fileIn.readLine();
                  
                   arr = dato.split(" ");
                   
                   score = Integer.parseInt(arr[0]);
                   caidas = Integer.parseInt(arr[1]);
                   vidas = Integer.parseInt(arr[2]);   
                   granada.setPosX(Double.parseDouble(arr[5]));
                   granada.setPosY(Double.parseDouble(arr[6]));
                   granada.setDatos(arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9]);
                   chocado = Boolean.parseBoolean(arr[10]);
                  
                    fileIn.close();
                } catch (FileNotFoundException e){
        }

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
        } else if (e.getKeyCode() == KeyEvent.VK_I) {
           
            if (!instrucciones) {
                instrucciones = true;
                granada.pausa();
                
            } else {
                
                instrucciones = false;
                granada.despausa();
                
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            
            sonido = !sonido;

        }else if(e.getKeyCode() == KeyEvent.VK_G){
                
            try {
                   
                grabaArchivo();
                
                } catch (IOException ex) {
                  
                    Logger.getLogger(TiroParabolico.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        } else if (e.getKeyCode() == KeyEvent.VK_C ) {
            try {
                
                leeArchivo();
               
                
            } catch (IOException ex) {
                
                Logger.getLogger(TiroParabolico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!granada.getMovimiento() && granada.checaIntersecion(e.getX(), e.getY())) {
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
