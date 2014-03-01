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
 * @author daniel rodriguez
 * @author Emilio Flores
 */
public class TiroParabolico extends JFrame implements KeyListener, MouseListener, Runnable {

 // valores numericos
    private final int posInicialTiro = 400; // empieza en posicion 100 
    private final int xPanelOrigin = 40;
    private final int yPanelOrigin = 400;
    private int caidas;
    private int vidas;
    private int score;

// strings
    private String[] arr;  
    private final String nombreArchivo = "guardar.txt";
    
// boleanos
    private boolean pausa;      // bool que checa si se pauso
    private boolean clicked;    // checador que checa si se movio el objeto con tecl
    private boolean chocado;
    private boolean instrucciones;
    private boolean sonido;

//floating
    private long tiempoActual;  // tiempo actual
    private long tiempoAire;
    private long tMensaje;

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
    private Image tableroInstrucciones;
    private Image pausaImagen; 
    private Image background; 

// animaciones
    private Animacion anim;
    private Animacion animCanasta;
    private Animacion animg;

// sounds
    private SoundClip choqueConCanasta; // sonido cuando la pelota choca con canasta
    private SoundClip choqueConSuelo; // sonido si choca con el suelo

   

    // objetos
    private Pelota granada;
    private Canasta canasta;

   
/**
 * Se crea un objeto de la misma clase
 */
    public TiroParabolico() {
        init();
        start();
    }

    /**
     * Se inicializan las variables en el metodo <I>Init</> 
     * Se inicializa el tamaño del applet en 1000x500
     *
     */
    void init() {
 

        addKeyListener(this);
        addMouseListener(this);
        setSize(1000, 500);
  
        
       
        Base.setW(getWidth());
        Base.setH(getHeight());
    
        caidas = 0;
        vidas = 5;
        score = 0;
        
        
        chocado = false;
        instrucciones = false;
        sonido = true;
        
       

        choqueConCanasta = new SoundClip("Mono/coin.wav");
        choqueConSuelo = new SoundClip("Mono/exp.wav");
    
      
        
       fotogranada=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/granada.png"));
       fotogranada1=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/granada1.png"));
       fotogranada2=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/granada2.png"));
     
        
       fotoCanasta = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/Basket1.png"));
       fotoCanasta1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/Basket2.png"));
       
       background = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/back.jpg"));
       tableroInstrucciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Mono/instruccionesTiroParabolico.jpg"));
       
        
        
        animCanasta = new Animacion();
        animg= new Animacion();

        animCanasta.sumaCuadro(fotoCanasta, 700);
        animCanasta.sumaCuadro(fotoCanasta1, 700);        
        animg.sumaCuadro(fotogranada, 200);
        animg.sumaCuadro(fotogranada1, 200);
        animg.sumaCuadro(fotogranada2, 200);

        canasta = new Canasta(0, 0, animCanasta);
        canasta.setPosX((int) (Math.random() * (getWidth() / 2 - canasta.getAncho())) + getWidth() / 2);
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

        // no se mueve menos de la mitdad la canasta
        if (canasta.getPosX() < getWidth() / 2) {
            canasta.setPosX(getWidth() / 2);
        }
        
        // No se sale del applet la canasta
        if (canasta.getPosX() + canasta.getAncho() > getWidth()) {
            canasta.setPosX(getWidth() - canasta.getAncho());
        }

        // si la granada choco con el suelo
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
        
        // si la granada choco con la canasta
        if (granada.intersectaCentro(canasta) && !chocado) {
            score += 2;
           granada.volverInicio();
           chocado = true;
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
       
        
        granada.avanza();


        if (canasta.getMoveLeft()) {
            canasta.setPosX(canasta.getPosX() - 6);
        }
        if (canasta.getMoveRight()) {
            canasta.setPosX(canasta.getPosX() + 6);
        }

        
        if (granada.getMovimiento()) {
            granada.actualiza(tiempoTranscurrido);
            
        }
        canasta.actualiza(tiempoTranscurrido);
       
        
       
       

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
                g.drawImage(pausaImagen,getWidth()/2 - new ImageIcon(pausaImagen).getIconWidth()/2, getHeight()/2 - new ImageIcon(pausaImagen).getIconHeight()/2, this);
            }
        
     
        
    }
    
    /**
     * Método para grabar archivo que envia todas las variables del juego dentro de
     * un string, el cual es guardado con el nombre <code> NombreArchivo </code>
     * @throws IOException 
     */
    public void grabaArchivo() throws IOException {
                                                          
                PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
                fileOut.println(score + " " + caidas + " " + vidas + " " + granada.getDatos() 
                        +" " +  clicked + " " + chocado + " " + pausa + " " + instrucciones + " " + sonido + " " + 
                        canasta.getPosX());
                fileOut.close();
    }
    
    
    /**
     * Método que lee el <code> nombreArchivo</code> que contiene una linea con todos los 
     * valores utilizados en el juego para volverse a cargar
     * @throws IOException 
     */
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
                   clicked = Boolean.parseBoolean(arr[10]);
                   chocado = Boolean.parseBoolean(arr[11]);
                   pausa = Boolean.parseBoolean(arr[12]);
                   instrucciones = Boolean.parseBoolean(arr[13]);
                   sonido = Boolean.parseBoolean(arr[14]);
                   canasta.setPosX(Double.parseDouble(arr[15]));
                  
                    fileIn.close();
                } catch (FileNotFoundException e){
        }

}

    /**
     * Método que identifica si se movio hacia algun lado la canasta
     * @param e 
     */
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

    /**
     * Si se preciono el mouse sobre el objeto granada
     * @param e 
     */
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
