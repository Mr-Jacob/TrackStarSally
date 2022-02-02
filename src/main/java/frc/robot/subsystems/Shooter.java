package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;


public final class Shooter extends CommandBase {
	/* Hardware */
  private final WPI_TalonFX _shooterLeftFx = new WPI_TalonFX(Constants.left_shooter);
  private final WPI_TalonFX _shooterRightFx = new WPI_TalonFX(Constants.right_shooter);
  DoubleSolenoid _hoodSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 4);

  private final Cartridge _cartridge = new Cartridge();
  private final Indexer2 _indexer = new Indexer2();
  
  public void sys_init() {
    _shooterLeftFx.configFactoryDefault();
    _shooterRightFx.configFactoryDefault();

    _shooterLeftFx.set(ControlMode.PercentOutput, 0);
    _shooterRightFx.set(ControlMode.PercentOutput, 0);

    _shooterLeftFx.setInverted(Constants.left_shooter_isInverted);
    _shooterRightFx.setInverted(Constants.right_shooter_isInverted);

    _shooterLeftFx.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                                 Constants.k_filter_wheel_PIDLoopIdx, Constants.k_filter_wheel_TimeoutMs);

                                  
    _shooterLeftFx.setSensorPhase(true);
    
    _shooterLeftFx.configNominalOutputForward(0, Constants.k_left_shoot_TimeoutMs);
		_shooterLeftFx.configNominalOutputReverse(0, Constants.k_left_shoot_TimeoutMs);
		_shooterLeftFx.configPeakOutputForward(1, Constants.k_left_shoot_TimeoutMs);
    _shooterLeftFx.configPeakOutputReverse(-1, Constants.k_left_shoot_TimeoutMs);
    
    _shooterLeftFx.configAllowableClosedloopError(0, Constants.k_filter_wheel_PIDLoopIdx, Constants.k_filter_wheel_TimeoutMs);

    _shooterLeftFx.config_kF(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_left_shoot_Gains.kF, Constants.k_filter_wheel_TimeoutMs);
		_shooterLeftFx.config_kP(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_left_shoot_Gains.kP, Constants.k_filter_wheel_TimeoutMs);
		_shooterLeftFx.config_kI(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_left_shoot_Gains.kI, Constants.k_filter_wheel_TimeoutMs);
    _shooterLeftFx.config_kD(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_left_shoot_Gains.kD, Constants.k_filter_wheel_TimeoutMs);

    _shooterRightFx.follow(_shooterLeftFx);

    System.out.println("Shooter Initiated...");
	}

  private double live_velocity;
  private boolean shooter_isReady = false;
  private boolean shooter_longshot = false;
  private boolean shooter_shortshot = false;
  private boolean hoodEnabled = false;
  private boolean hoodDisabled = true;
  private int Velocity;

  //revs up shooter, automatically triggers indexer band when ready
  //shooter_shortV & shooter_longV
  public void shooterActivate(){
    set_shooter_speed();
    _shooterLeftFx.set(ControlMode.Velocity, Velocity);
  }

  public void set_shooter_speed(){
    if(hoodEnabled){
      Velocity = Constants.shooter_longV;
    }else if(hoodDisabled){
      Velocity = Constants.shooter_shortV;
    }
  }

  public void auto_shoot(){

    //watch shooter velocity when indexer activates

    if(live_velocity>Constants.shooter_longV-2 && shooter_longshot == true){
      shooter_isReady = true;
    } else if(live_velocity>Constants.shooter_shortV-2 && shooter_shortshot == true){
      shooter_isReady = true;
    } else {
      shooter_isReady = false;
    }

    if(shooter_isReady = true){
      _cartridge.set_bands_speed(.65);
      _indexer.reset_ballcount();
    } else{
      _cartridge.set_bands_speed(0);
    }
  }

  //stops shooter from spinning
  public void stop_shooter() {
    _shooterLeftFx.set(ControlMode.PercentOutput, .15);
    shooter_isReady = false;
  }

  //periodically gets live motor velocity from shooter
  public void shooterPeriodic(){
    live_velocity = _shooterLeftFx.getActiveTrajectoryVelocity(0);
    }

  public void toggle_shooter_hood() {
    if(hoodEnabled){
      _hoodSolenoid.set(Value.kReverse);
      shooter_longshot = false;
      shooter_shortshot = true;
      hoodDisabled = false;
    } else {
      _hoodSolenoid.set(Value.kForward);
      shooter_longshot = true;
      shooter_shortshot = false;
      hoodDisabled = true;
    }
  }
}
