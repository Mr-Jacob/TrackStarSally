
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Add your docs here. */
public class Intake extends SubsystemBase {
  
  private WPI_TalonFX _extIntakeFx = new WPI_TalonFX(Constants.k_exterior_intake_falcon);
  private DoubleSolenoid _extIntakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 5);

  public void sys_init() {
    _extIntakeFx.configFactoryDefault();
    _extIntakeFx.set(ControlMode.PercentOutput, 0);
    _extIntakeFx.setInverted(Constants.k_exterior_intake_falcon_isInverted);
    System.out.println("Intake Initiated...");
  }

  public void set_intake_speed(double input_speed) {
    _extIntakeFx.set(ControlMode.PercentOutput, input_speed);
  }

  public void set_intake_extended(boolean input_position) {
    if(input_position){
      _extIntakeSolenoid.set(Value.kForward);
    } else {
      _extIntakeSolenoid.set(Value.kReverse);
    }
  }
}