package org.csc133.a3;

/**
 * DefenseStrategy class Implements IStrategy
 * and contains info for DefenseStrategy object
 */
public class DefenseStrategy implements IStrategy{
    private NonPlayerHelicopter nphHelicopter;
    private GameWorld gw;
    private final int SKYSCRAPER_1=3;
    private final int SKYSCRAPER_2=4;

    /**
     * Constructor for DefenseStrategy
     * @param nphHelicopter NonPlayerHelicopter object
     * @param gw GameWorld object
     */
    public DefenseStrategy(NonPlayerHelicopter nphHelicopter,
                        GameWorld gw){
        this.nphHelicopter=nphHelicopter;
        this.gw=gw;
    }


    /**
     * Applies the Defense Strategy
     */
    @Override
    public void apply() {
        int nextSkyScraper=nphHelicopter.getLastSkyScraperReached()+1;


        double dx=0;
        double dy=0;
        double skyScraperX=0;
        double skyScraperY=0;


        for(int i=0;i<gw.getObjectCollection().size();i++){
            if(gw.getObjectCollection().getElement(i) instanceof SkyScraper){
                if(((SkyScraper) gw.getObjectCollection().getElement(i))
                        .getSequenceNumber()==nextSkyScraper){
                    skyScraperX=
                            ((SkyScraper) gw.getObjectCollection()
                                    .getElement(i)).getLocation().getX();
                    skyScraperY=
                            ((SkyScraper) gw.getObjectCollection()
                                    .getElement(i)).getLocation().getY();

                            ((SkyScraper) gw.getObjectCollection()
                                    .getElement(i)).getSize();

                }
            }
        }

        dx = skyScraperX - nphHelicopter.getLocation().getX();
        dy = skyScraperY - nphHelicopter.getLocation().getY();

        defenseHeading(dx,dy);

    }
    /**
     * Sets the heading of  NonPLayerHelicopter to
     * target the next SkyScraper
     * @param dx Double value for change in x
     * @param dy Double value for change in y
     */
    private void defenseHeading(double dx,double dy){
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
     * @return String representation of Defense Strategy
     */
    @Override
    public String getStrategyType() {
        return "Defense Strategy";
    }

    /**
     * @return int value of SKYSCRAPER_1
     */
    public int getSkyScraper1(){
        return SKYSCRAPER_1;
    }

    /**
     * @return int value of SKYSCRAPER_2
     */
    public int getSkyScraper2(){
        return SKYSCRAPER_2;
    }




}
