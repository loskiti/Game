package Paint;

public abstract class BaseActionUnitSprite extends UnitSprite {
	 /**
     * step
     */
    private void actionStep() {
        if(imageMaxX == 10 && imageMinX == 4) {
            return;
        }
        imageMaxX = 10;
        imageMinX = imageX = 4;
    }
    
    /**
     * stop
     */
    public void actionStand() {
        if(imageMaxX == 3 && imageMinX == 0) {
            return;
        }
        imageMinX = imageX = 0;
        imageMaxX = 3;        
    }
    
    /**
     * turn to the up
     */
    public void turnTop() {
    	imageMinY = imageMaxY = imageY = 3;
    }
    
    /**
     * turn to the left 
     */
    public void turnLeft() {
    	imageMinY = imageMaxY = imageY = 1; 
    }
    
    /**
     * turn to the right 
     */
    public void turnRight() {
    	imageMinY = imageMaxY = imageY = 5; 
    }
    
    /**
     * turn to the down
     */
    public void turnBoth() {
    	imageMinY = imageMaxY = imageY = 7;
    }
    
    @Override
    public void update() {
        super.update();
        //default
        if(directX == 0 && directY == 0) {
            actionStand();
        } else if(directX == 0 && directY == 1) {
            turnBoth();
            actionStep();
        } else if(directX == 0 && directY == -1) {
            turnTop();
            actionStep();
        } else if(directX == 1 && directY == 0) {
            turnRight();
            actionStep();
        } else if(directX == -1 && directY == 0) {
            turnLeft();
            actionStep();
        }        
    }
    
}
