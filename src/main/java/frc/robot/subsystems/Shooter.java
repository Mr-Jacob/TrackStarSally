package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;

//note: left shooter motor is primary motor. Right follows left. ID's: 10(left) and 11(right)


public class Shooter extends SubsystemBase {

	/* Hardware */
  private final WPI_TalonFX _shooterLeftFx = new WPI_TalonFX(Constants.left_shooter);
  private final WPI_TalonFX _shooterRightFx = new WPI_TalonFX(Constants.right_shooter);
  DoubleSolenoid _hoodSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 4);
  
  public void sys_init() {
    _hoodSolenoid.set(Value.kReverse);
    hoodEnabled = false;

    _shooterLeftFx.configFactoryDefault();
    _shooterRightFx.configFactoryDefault();

    _shooterLeftFx.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 0, 0));
    _shooterRightFx.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 0, 0));

    _shooterLeftFx.set(ControlMode.PercentOutput, 0);
    _shooterRightFx.set(ControlMode.PercentOutput, 0);

    _shooterLeftFx.setInverted(Constants.left_shooter_isInverted);
    _shooterRightFx.setInverted(Constants.right_shooter_isInverted);

    _shooterLeftFx.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                                 Constants.k_filter_wheel_PIDLoopIdx, Constants.k_filter_wheel_TimeoutMs);

                                  
    _shooterLeftFx.setSensorPhase(true);
    
    _shooterLeftFx.configNominalOutputForward(0, Constants.k_index_band_TimeoutMs);
		_shooterLeftFx.configNominalOutputReverse(0, Constants.k_index_band_TimeoutMs);
		_shooterLeftFx.configPeakOutputForward(1, Constants.k_index_band_TimeoutMs);
    _shooterLeftFx.configPeakOutputReverse(-1, Constants.k_index_band_TimeoutMs);
    
    _shooterLeftFx.configAllowableClosedloopError(0, Constants.k_filter_wheel_PIDLoopIdx, Constants.k_filter_wheel_TimeoutMs);

    _shooterLeftFx.config_kF(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_left_shoot_Gains.kF, Constants.k_filter_wheel_TimeoutMs);
		_shooterLeftFx.config_kP(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_left_shoot_Gains.kP, Constants.k_filter_wheel_TimeoutMs);
		_shooterLeftFx.config_kI(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_left_shoot_Gains.kI, Constants.k_filter_wheel_TimeoutMs);
    _shooterLeftFx.config_kD(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_left_shoot_Gains.kD, Constants.k_filter_wheel_TimeoutMs);

    _shooterRightFx.follow(_shooterLeftFx);
    _shooterLeftFx.configClosedloopRamp(1);
    _shooterLeftFx.setNeutralMode(NeutralMode.Coast);

	}

  @Override
  public void periodic(){
    get_shooterVelocity();
  }

  double activeVelocity;
  private boolean hoodEnabled;
  boolean shooter_ready = false;

  //revs up shooter, automatically triggers indexer band when ready
  //shooter_shortV & shooter_longV
  public void set_shooter_velocity(double velocity) {
    _shooterLeftFx.set(ControlMode.Velocity, velocity);
  }


  //gets and returns shooter velocity for autonomous ball sending based on motor velocity
  public void get_shooterVelocity(){

    if(_shooterLeftFx.getSelectedSensorPosition() >= Constants.motorVelocity){
      shooter_ready = true;
    } else {
      shooter_ready = false;
    }
  }

  public boolean get_readyState(){
    return shooter_ready;
  }

  //stops shooter from spinning
  public void stop_shooter() {
   _shooterLeftFx.set(ControlMode.PercentOutput, 0);
  }

  public void toggle_shooter_hood() {
    if(hoodEnabled){
      _hoodSolenoid.set(Value.kReverse);
      hoodEnabled = false;
    } else {
      _hoodSolenoid.set(Value.kForward);
      hoodEnabled = true;
    }
  }
}
