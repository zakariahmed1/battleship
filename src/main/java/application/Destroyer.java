package application;

public class Destroyer extends Ship{
    SpecialForce specialForces;

    public Destroyer(){
        super(1);
        specialForces = () -> GameManager.getInstance().randomCounterAttack();
    }

    @Override
    public void registerHit(Cell cell) {
        //only perform a counter-attack if this ship is not hit
        if (!isSunk()) {
            super.registerHit(cell);
            GameManager.getInstance().enqueueSpecialCounterForce(specialForces);
        }
    }
}
