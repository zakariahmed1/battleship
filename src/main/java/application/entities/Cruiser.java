package application.entities;

import application.Cell;
import application.GameManager;
import application.SpecialForce;

public class Cruiser extends Ship
{

    SpecialForce specialForces;

    public Cruiser() {
        super(3);
        specialForces = () -> GameManager.getInstance().skipRound();
    }

    @Override
    public void registerHit(Cell cell){
        super.registerHit(cell);
        if (getRemainigHealth() >= getSize()-1) //only skip round for the first hit
            GameManager.getInstance().enqueueSpecialCounterForce(specialForces);
    }
}
