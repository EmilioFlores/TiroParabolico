package tiroparabolico;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emilio
 */
public class Canasta extends Base {
    
    private boolean moveRight;
    private boolean moveLeft;
    
    
    public Canasta(int posX, int posY, Animacion animacion) {
        super(posX, posY, animacion); 
        moveRight = false;
        moveLeft = false;
    }
    
    
     public void setMoveRight(boolean b) {
        moveRight = b;
    }
    
    public boolean getMoveRight() {
        return moveRight;
    }
    
    public void setMoveLeft(boolean b) {
        moveLeft = b;
    }
    
    public boolean getMoveLeft() {
        return moveLeft;
    }
}
