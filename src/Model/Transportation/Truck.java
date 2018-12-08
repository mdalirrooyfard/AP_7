package Model.Transportation;

import Model.Constants;

public class Truck extends Transportations{
    public Truck(){
        this.setWorkingTime(Constants.TRUCK_WORKING_TIME);
        this.setUpgradeCost(Constants.TRUCK_BASE_UPGRADE_COST);
    }
}
