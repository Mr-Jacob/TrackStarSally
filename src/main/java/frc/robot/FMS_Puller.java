
package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class FMS_Puller{

  final DriverStation.Alliance Blue = Alliance.Blue;
  final DriverStation.Alliance Red = Alliance.Red;
   //true = red, false = blue

  public void sys_init(){
    DriverStation.Alliance IsRedAlliance = DriverStation.getAlliance();

    //true = RedAlliance, false = BlueAlliance

  if(IsRedAlliance == Blue){
    Constants.allianceColor = false;
  } else if(IsRedAlliance == Red){
    Constants.allianceColor = true;
  }
}

}

