package Model.Transportation;

import Model.Constants;

public class Helicopter extends Transportations{
    public Helicopter(){
        this.setWorkingTime(Constants.HELICOPTER_WORKING_TIME);
        this.setUpgradeCost(Constants.HELICOPTER_BASE_UPGRADE_COST);
    }
}
