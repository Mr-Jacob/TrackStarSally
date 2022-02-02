//MAJOR CHANGES NEEDED FOR THIS CLASS- Do Not Use Yet

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Cartridge extends SubsystemBase {
  //Green Flex Wheels - Filter wheels that separate the indexer and intake well.
  private final WPI_TalonFX _filterWheelsFx = new WPI_TalonFX(k_green_intake_falcon);

  //Blue Indexer Band - The indexer band has many interactions: Shoot, Index, Purge, and Climb 
  private final WPI_TalonFX _indexerBandFx = new WPI_TalonFX(k_climb_cartridge_falcon);
  
  //Orange Bands - Helps funnel balls from the intake to the green filter wheels.
  private WPI_VictorSPX _intBandVk = new WPI_VictorSPX(k_intake_bands_viktor);
  
  //The first sharp sensor is located within the intake well and is intended to read ball position if the filter wheels are stopped.
  //The second and third sharp sensors detect a ball that is about to approach the blue indexer band.
  private static AnalogInput _sharpSensor, _secondSharpSensor, _thirdSharpSensor;
  
  //The indexer ball count is a count that interfaces with several of the boolean returning functions to trigger ball count specific
  //events
  private int indexer_ball_count;

  private Timer ball_position_timer = new Timer();
  private Timer ball_position_timer_2 = new Timer();
  private Timer ball_position_timer_3 = new Timer();

  public Cartridge() {
    indexer_ball_count = 0;
    ball_position_timer.start();
    ball_position_timer_2.start();
    ball_position_timer_3.start();
  }

  public void sys_init() {
    _filterWheelsFx.configFactoryDefault();
    _indexerBandFx.configFactoryDefault();
    _intBandVk.configFactoryDefault();

    _filterWheelsFx.set(ControlMode.PercentOutput, 0);
    _indexerBandFx.set(ControlMode.PercentOutput, 0);
    _intBandVk.set(ControlMode.PercentOutput, 0);

    _filterWheelsFx.setInverted(Constants.k_green_intake_falcon_isInverted);
    _indexerBandFx.setInverted(k_climb_cartridge_falcon_isInverted);
    _intBandVk.setInverted(k_intake_bands_viktor_isInverted);

    

    _filterWheelsFx.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                                 k_filter_wheel_PIDLoopIdx, k_filter_wheel_TimeoutMs);
    _indexerBandFx.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
                                                 k_index_band_PIDLoopIdx, k_index_band_TimeoutMs);
                                  
    _filterWheelsFx.setSensorPhase(k_filter_wheel_SensorPhase);
    _indexerBandFx.setSensorPhase(k_index_band_SensorPhase);

    _filterWheelsFx.configNominalOutputForward(0, k_filter_wheel_TimeoutMs);
		_filterWheelsFx.configNominalOutputReverse(0, k_filter_wheel_TimeoutMs);
		_filterWheelsFx.configPeakOutputForward(1, k_filter_wheel_TimeoutMs);
    _filterWheelsFx.configPeakOutputReverse(-1, k_filter_wheel_TimeoutMs);
    
    _indexerBandFx.configNominalOutputForward(0, k_index_band_TimeoutMs);
		_indexerBandFx.configNominalOutputReverse(0, k_index_band_TimeoutMs);
		_indexerBandFx.configPeakOutputForward(1, k_index_band_TimeoutMs);
    _indexerBandFx.configPeakOutputReverse(-1, k_index_band_TimeoutMs);
    
    _filterWheelsFx.configAllowableClosedloopError(0, k_filter_wheel_PIDLoopIdx, k_filter_wheel_TimeoutMs);
    _indexerBandFx.configAllowableClosedloopError(0, k_index_band_PIDLoopIdx, k_index_band_TimeoutMs);

    _filterWheelsFx.config_kF(k_filter_wheel_PIDLoopIdx, k_filter_wheel_Gains.kF, k_filter_wheel_TimeoutMs);
		_filterWheelsFx.config_kP(k_filter_wheel_PIDLoopIdx, k_filter_wheel_Gains.kP, k_filter_wheel_TimeoutMs);
		_filterWheelsFx.config_kI(k_filter_wheel_PIDLoopIdx, k_filter_wheel_Gains.kI, k_filter_wheel_TimeoutMs);
    _filterWheelsFx.config_kD(k_filter_wheel_PIDLoopIdx, k_filter_wheel_Gains.kD, k_filter_wheel_TimeoutMs);
    
    _indexerBandFx.config_kF(k_index_band_PIDLoopIdx, k_index_band_Gains.kF, k_index_band_TimeoutMs);
		_indexerBandFx.config_kP(k_index_band_PIDLoopIdx, k_index_band_Gains.kP, k_index_band_TimeoutMs);
		_indexerBandFx.config_kI(k_index_band_PIDLoopIdx, k_index_band_Gains.kI, k_index_band_TimeoutMs);
    _indexerBandFx.config_kD(k_index_band_PIDLoopIdx, k_index_band_Gains.kD, k_index_band_TimeoutMs);

    _filterWheelsFx.setNeutralMode(NeutralMode.Brake);
    _indexerBandFx.setNeutralMode(NeutralMode.Brake);

    _sharpSensor = new AnalogInput(0);
    _secondSharpSensor = new AnalogInput(2);
    _thirdSharpSensor = new AnalogInput(3);
    
    System.out.println("Indexer Initiated...");
  }

  public void set_bands_speed(double input_speed) {
    _intBandVk.set(ControlMode.PercentOutput, input_speed);
  }

  public int change_indexer_position_by(int units) {
    int current_position = (int) _indexerBandFx.getSelectedSensorPosition();
    int new_position = current_position + units;

    _indexerBandFx.set(ControlMode.MotionMagic, ballIncrement);

    return new_position;
  }

  public int change_filter_position_by(int units) {
    int current_position = (int) _filterWheelsFx.getSelectedSensorPosition();
    int new_position = current_position + units;

    _filterWheelsFx.set(ControlMode.Position, new_position);

    return new_position;
  }

  public boolean is_indexer_within_tolerance(int goal_position) {
    if(_indexerBandFx.getSelectedSensorPosition() > goal_position) {
      return true;
    } else {
      return false;
    }
  }

  public boolean is_filter_within_tolerance(int goal_position) {
    if(_filterWheelsFx.getSelectedSensorPosition() > goal_position) {
      return true;
    } else {
      return false;
    }
  }

  public void set_indexer(double output) {
    _indexerBandFx.set(ControlMode.PercentOutput, output);
  }

  public void set_filter(double output) {
    _filterWheelsFx.set(ControlMode.PercentOutput, output);
  }

  private boolean ball_in_position = false;
  private boolean ball_in_position_2 = false;
  private boolean ball_in_position_3 = false;
  public boolean get_ball_ready() {
    return ball_in_position;
  }

  public boolean get_ball_ready_2() {
    return ball_in_position_2;
  }

  public boolean get_ball_ready_3() {
    return ball_in_position_3;
  }

  public void sharp_sensor_timer_reset() {
    if(_sharpSensor.getValue() < 900){
      ball_position_timer.reset();
    }
    if(_secondSharpSensor.getValue() < 450){
      ball_position_timer_2.reset();
    }
    //if(_thirdSharpSensor.getValue() < 350){  original jason code.  reducing value to current idle value
    if(_thirdSharpSensor.getValue() < 375){
      //if(_thirdSharpSensor.getValue() <200) -original value in Jason code for 2nd sharp sensor
      ball_position_timer_3.reset();
    }
  }

  public void is_ball_in_position() {
    if(ball_position_timer.get() > 0.5) {
      ball_in_position = true;
    } else {
      ball_in_position = false;
    }
    if(ball_position_timer_2.get() > 0.2) {
      ball_in_position_2 = true;
    } else {
      ball_in_position_2 = false;
    } 
  }

  public void increment_ball_count() {
    indexer_ball_count += 1;
  }

  public void reset_ball_count() {
    indexer_ball_count = 0;
  }

  public boolean has_less_than_three_balls(){
    return indexer_ball_count < 3;
  }

  public void reset_timer(){
    ball_position_timer.reset();
  }

  public boolean ready_to_index(){
    return _secondSharpSensor.getValue() > 800;
  }

  public boolean ready_to_accept_1st_sensor(){
    return _sharpSensor.getValue() > 400;
  }

  public int get_ball_count(){
    return indexer_ball_count;
  }

  public boolean ready_to_index_ball_1_to_3(){
    return get_ball_count() < 3 && get_twin_sharps();
  }

  public boolean get_twin_sharps() {
    return get_ball_ready_2() || get_ball_ready_3();
  }

  public boolean ready_to_index_ball_4(){
    return get_ball_count() == 3 && get_ball_ready();
  }

  public boolean ready_to_standby(){
    return !ready_to_index_ball_1_to_3() && get_ball_count() < 3 || !ready_to_index_ball_4() && get_ball_count() < 3;
  }

  public boolean has_4_balls(){
    return get_ball_count() == 3 || get_ball_count() == 4;
  }

  @Override
  public void periodic() {
    sharp_sensor_timer_reset();
    is_ball_in_position();

    SmartDashboard.putNumber("Sharp Sensor 1", _sharpSensor.getValue());
    SmartDashboard.putNumber("Sharp Sensor 2", _secondSharpSensor.getValue());
    SmartDashboard.putNumber("Sharp Sensor 3", _thirdSharpSensor.getValue());
    SmartDashboard.putNumber("Current Ball Count", get_ball_count());
    SmartDashboard.putBoolean("Ball is in Position", ball_in_position);
    SmartDashboard.putBoolean("Sharp Sensor 2 Position", ball_in_position_2);
    SmartDashboard.putBoolean("Sharp Sensor 3 Position", ball_in_position_3);
    SmartDashboard.putNumber("Timer 1", ball_position_timer.get());
    SmartDashboard.putNumber("Timer 2", ball_position_timer_2.get());

  }
}

