package application;

public class Destroyer extends Ship{
    SpecialForce specialForces;

    public Destroyer(){
        super(1);
        specialForces = () -> GameManager.getInstance().randomCounterAttack();
    }

    @Override
    public void registerHit(Cell cell) {
        //only perform a counter-attack if this part of the ship was not already attacked
        if (!super.isHit(cell)) {
            super.registerHit(cell);
            GameManager.getInstance().enqueueSpecialCounterForce(specialForces);
        }
    }
}
