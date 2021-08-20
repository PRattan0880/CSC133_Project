package org.csc133.a3;

/**
 * RaceStrategy class Implements IStrategy
 * and contains info for RaceStrategy object
 */
public class RaceStrategy implements IStrategy{

    private NonPlayerHelicopter nphHelicopter;
    private GameWorld gw;

    /**
     * Constructor for RaceStrategy
     * @param nphHelicopter NonPlayerHelicopter object
     * @param gw  GameWorld object
     */
    public RaceStrategy(NonPlayerHelicopter nphHelicopter,
                        GameWorld gw){
        this.nphHelicopter=nphHelicopter;
        this.gw=gw;
    }

    @Override
    /**
     * Applies the Race Strategy
     */
    public void apply() {
        int nextSkyScraper=nphHelicopter.getLastSkyScraperReached()+1;  //+1
        double skyScraperX=0;
        double skyScraperY=0;
        double dx=0,dy=0;

        for(int i=0;i<gw.getObjectCollection().size();i++){
            if(gw.getObjectCollection().getElement(i) instanceof SkyScraper){
                if(((SkyScraper) gw.getObjectCollection().getElement(i))
                        .getSequenceNumber()==nextSkyScraper){
                        skyScraperX=
                                ((SkyScraper) gw.getObjectCollection().
                                        getElement(i)).getLocation().getX();
                        skyScraperY=
                                ((SkyScraper) gw.getObjectCollection().
                                        getElement(i)).getLocation().getY();
                }
            }
        }

        dx = skyScraperX - nphHelicopter.getLocation().getX();
        dy = skyScraperY - nphHelicopter.getLocation().getY();
        raceHeading(dx,dy);
    }

    /**
     * Sets the heading of  NonPLayerHelicopter to
     * target the next SkyScraper
     * @param dx Double value for change in x
     * @param dy Double value for change in y
     */
    private void raceHeading(double dx,double dy){
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
     * @return String representation of RaceStrategy
     */
    @Override
    public String getStrategyType() {
        return "Race Strategy";
    }

}
