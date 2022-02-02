package frc.robot.CustomTriggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class xboxTrigger2 extends Trigger {
    Joystick m_Joystick;
    int m_axis;

    public xboxTrigger2(Joystick input_joystick, int raw_axis){
        m_Joystick = input_joystick;
        m_axis = raw_axis;
    }

    @Override
    public boolean get() {
      if(Math.abs(m_Joystick.getRawAxis(m_axis)) > 0.5){
        return true;
      } else {
          return false;
      }
    }
}