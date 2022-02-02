package frc.robot.CustomTriggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Shooter;

//this class takes shooter velocity state and combines with xbox trigger to automatically shoot when both shooter and driver are ready

public class xboxTrigger2 extends Trigger {
    Joystick m_Joystick;
    int m_axis;
    Shooter _Shooter;

    public xboxTrigger2(Joystick input_joystick, int raw_axis, Shooter Subsystem){
        m_Joystick = input_joystick;
        m_axis = raw_axis;
        _Shooter = Subsystem;
    }

    @Override
    public boolean get() {
      if(Math.abs(m_Joystick.getRawAxis(m_axis)) > 0.6 && _Shooter.get_readyState() == true){
        return true;
      } else {
        return false;
      }
    }
}