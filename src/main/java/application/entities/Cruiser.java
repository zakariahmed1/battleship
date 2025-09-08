package application;

public class Cruiser extends Ship {

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
