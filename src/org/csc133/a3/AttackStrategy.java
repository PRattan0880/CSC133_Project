package org.csc133.a3;

/**
 * AttackStrategy class Implements IStrategy
 * and contains info for AttackStrategy object
 */
public class AttackStrategy implements IStrategy {
    private NonPlayerHelicopter nphHelicopter;
    private Helicopter targetHelicopter;

    /**
     * Constructor for AttackStrategy
     * @param nphHelicopter NonPlayerHelicopter
     * @param targetHelicopter  Helicopter
     */
    public AttackStrategy(NonPlayerHelicopter nphHelicopter,Helicopter
            targetHelicopter){
        this.nphHelicopter=nphHelicopter;
        this.targetHelicopter=targetHelicopter;
    }

    /**
     * Applies the Attack Strategy
     */
    @Override
    public void apply() {
        double dx,dy;
        dx=
                targetHelicopter.getLocation().getX()-
                        nphHelicopter.getLocation().getX();
        dy=
                targetHelicopter.getLocation().getY()-
                        nphHelicopter.getLocation().getY();
        attackHeading(dx,dy);
    }

    /**
     * Sets the heading of  NonPLayerHelicopter to
     * target the Helicopter
     * @param dx Double value for change in x
     * @param dy Double value for change in y
     */
    public void attackHeading(double dx,double dy){
        int angle=(int)Math.toDegrees(Math.atan(dy/dx));
        if(dy==0){
            if(dx<0){
                nphHelicopter.setHeading(270);
            }else if(dx>0){
                nphHelicopter.setHeading(90);
            }
        }

        if(dx==0){
            if(dy>0){
                nphHelicopter.setHeading(0);
            }else if(dy<0){
                nphHelicopter.setHeading(180);
            }
        }
        int idealHeading=90-angle;
        if (dx > 0) {
            if(dy>0){
                nphHelicopter.setHeading(Math.abs(idealHeading));
            }else if(dy<0){
                nphHelicopter.setHeading(idealHeading);
            }

        }else if(dx<0){
            if(dy>0){
                nphHelicopter.setHeading(180+idealHeading);
            }else if(dy<0){
                nphHelicopter.setHeading(180+Math.abs(idealHeading));
            }
        }
    }

    /**
     * @return String representation of current Strategy
     */
    public String getStrategyType(){
        return "ATTACK NPH";
    }
}
