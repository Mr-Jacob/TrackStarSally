
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Indexer2 extends SubsystemBase {

  private final WPI_TalonFX falcon_indexer = new WPI_TalonFX(k_climb_cartridge_falcon);
  private static AnalogInput _sharpSensor, _secondSharpSensor, _thirdSharpSensor;

  public void sys_init(){

    falcon_indexer.configFactoryDefault();

    falcon_indexer.set(ControlMode.PercentOutput, 0);

    falcon_indexer.setInverted(k_climb_cartridge_falcon_isInverted);

    falcon_indexer.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
                                               k_filter_wheel_PIDLoopIdx, 
                                              k_filter_wheel_TimeoutMs);

    falcon_indexer.setSensorPhase(true);

    falcon_indexer.configNominalOutputForward(0, k_filter_wheel_TimeoutMs);
		falcon_indexer.configNominalOutputReverse(0, k_filter_wheel_TimeoutMs);
    falcon_indexer.configPeakOutputForward(1, k_filter_wheel_TimeoutMs);
    falcon_indexer.configPeakOutputReverse(-1, k_filter_wheel_TimeoutMs);

    falcon_indexer.configAllowableClosedloopError(0, k_filter_wheel_PIDLoopIdx, k_filter_wheel_TimeoutMs);

    falcon_indexer.config_kF(k_filter_wheel_PIDLoopIdx, k_filter_wheel_Gains.kF, k_filter_wheel_TimeoutMs);
		falcon_indexer.config_kP(k_filter_wheel_PIDLoopIdx, k_filter_wheel_Gains.kP, k_filter_wheel_TimeoutMs);
		falcon_indexer.config_kI(k_filter_wheel_PIDLoopIdx, k_filter_wheel_Gains.kI, k_filter_wheel_TimeoutMs);
    falcon_indexer.config_kD(k_filter_wheel_PIDLoopIdx, k_filter_wheel_Gains.kD, k_filter_wheel_TimeoutMs);

    falcon_indexer.setNeutralMode(NeutralMode.Brake);

    _sharpSensor = new AnalogInput(0);
    _secondSharpSensor = new AnalogInput(2);
    _thirdSharpSensor = new AnalogInput(3);

    falcon_indexer.getSelectedSensorPosition(0);
  }

  
  //ballcount range is 1-3, 1 & 2 trigger movement, 3 does nothing, 0 is only on startup
  private int ballcount = 0;
  private int indexer_pos = 0;
  private int newindexer_pos;
  //create climb enabled variable, set true to disable change_indexer_position

  public void sharp_sensorValues(){
    //calibrate sharp sensor values!
    if(_sharpSensor.getValue()>900 && allianceColor == true|| _secondSharpSensor.getValue()>450 && allianceColor == true|| _thirdSharpSensor.getValue()>350 && allianceColor == true){
      change_indexer_position_red();
    } else if(_sharpSensor.getValue()>900 && allianceColor == false|| _secondSharpSensor.getValue()>450 && allianceColor == false|| _thirdSharpSensor.getValue()>350 && allianceColor == false){
      change_indexer_position_blue();
    }
  }

  //changes indexer position by set units, 2048 units per revolution
  public void change_indexer_position_red() {

    ballcount = ballcount + 1;
  
    if(ballcount == 1 && isBallRed == true){
      falcon_indexer.set(ControlMode.MotionMagic, indexer_pos);
      newindexer_pos = indexer_pos + 1024;
    } else if(ballcount == 2 && isBallRed == true){
      falcon_indexer.set(ControlMode.MotionMagic, newindexer_pos);
    } else if(ballcount>2 && isBallRed == true){
      falcon_indexer.set(ControlMode.PercentOutput, 0);
    } else {
      falcon_indexer.set(ControlMode.PercentOutput, 0);
    }
  }

  public void change_indexer_position_blue() {

    ballcount = ballcount + 1;
  
    if(ballcount == 1 && isBallRed == false){
      falcon_indexer.set(ControlMode.MotionMagic, indexer_pos);
      newindexer_pos = indexer_pos + 1024;
    } else if(ballcount == 2 && isBallRed == false){
      falcon_indexer.set(ControlMode.MotionMagic, newindexer_pos);
    } else if(ballcount>2 && isBallRed == false){
      falcon_indexer.set(ControlMode.PercentOutput, 0);
    } else {
      falcon_indexer.set(ControlMode.PercentOutput, 0);
    }
  }

  public void reset_ballcount(){
    ballcount = 0;
  }

  //Color Sensor Stuff \\  //
  //                    \\//
  
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final Color kBlueTarget = new Color(0.143, 0.427, 0.429);
  private final Color kRedTarget = new Color(0.561, 0.232, 0.114);

  boolean isBallRed;

  public void ColorSensor() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kRedTarget);    
  }

  public void Colorperiodic(){

    Color detectedColor = m_colorSensor.getColor();

    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) {
      System.out.print("Blue Detected");
      isBallRed = false;
    } else if (match.color == kRedTarget) {
      System.out.print("Red Detected");
      isBallRed = true;
    }
  }

  @Override
  public void periodic(){
    sharp_sensorValues();
  }
}
