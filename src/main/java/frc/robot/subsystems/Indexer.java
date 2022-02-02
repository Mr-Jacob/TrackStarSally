package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.*;

public class Indexer extends SubsystemBase {

  private final WPI_TalonFX falcon_indexer = new WPI_TalonFX(Constants.k_climb_cartridge_falcon);
  private static AnalogInput _sharpSensor;
  private Timer timer1 = new Timer();
  private boolean alliance = false;

  public void sys_init(){
    timer1.start();

    falcon_indexer.configFactoryDefault();
    falcon_indexer.setSelectedSensorPosition(0);

    falcon_indexer.set(ControlMode.PercentOutput, 0);

    System.out.print("zeroing indexer motor  \n");
    falcon_indexer.getSelectedSensorPosition(0);

    falcon_indexer.configNeutralDeadband(.04);

    falcon_indexer.configAllowableClosedloopError(0, Constants.k_index_band_PIDLoopIdx, Constants.k_index_band_TimeoutMs);

    falcon_indexer.setInverted(Constants.k_climb_cartridge_falcon_isInverted);

    falcon_indexer.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
                                              Constants.k_filter_wheel_PIDLoopIdx, 
                                              Constants.k_filter_wheel_TimeoutMs);

    falcon_indexer.setSensorPhase(Constants.k_index_band_SensorPhase);

    falcon_indexer.configNominalOutputForward(0, Constants.k_filter_wheel_TimeoutMs);
		falcon_indexer.configNominalOutputReverse(0, Constants.k_filter_wheel_TimeoutMs);
    falcon_indexer.configPeakOutputForward(.65, Constants.k_filter_wheel_TimeoutMs);
    falcon_indexer.configPeakOutputReverse(-.65, Constants.k_filter_wheel_TimeoutMs);

    falcon_indexer.config_kF(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_filter_wheel_Gains.kF, Constants.k_filter_wheel_TimeoutMs);
		falcon_indexer.config_kP(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_filter_wheel_Gains.kP, Constants.k_filter_wheel_TimeoutMs);
		falcon_indexer.config_kI(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_filter_wheel_Gains.kI, Constants.k_filter_wheel_TimeoutMs);
    falcon_indexer.config_kD(Constants.k_filter_wheel_PIDLoopIdx, Constants.k_filter_wheel_Gains.kD, Constants.k_filter_wheel_TimeoutMs);

    falcon_indexer.setNeutralMode(NeutralMode.Brake);

    _sharpSensor = new AnalogInput(0);

    reset_ballcount();

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kRedTarget);

    Field_Alliance();
  }

  public void set_indexer(double output) {
    falcon_indexer.set(ControlMode.PercentOutput, output);
  }

  
   //ballcount range is 1-3, 1 & 2 trigger movement, 3 does nothing, 0 is only on startup
   int ballcount;
  boolean ball_ready = false;

  //create climb enabled variable, set true to disable change_indexer_position

  
  @Override
  public void periodic(){
    get_sharp();
    ball_isReady();
    Colorperiodic();
    }


    //if timer is more than .5 seconds, then indexer is clear for next ball. Add color condition when ready
  public void get_sharp(){
        is_ballRed();
        SmartDashboard.putBoolean("Ball is Red: ", isBallRed);
        double sharp1 = _sharpSensor.getVoltage();

        if(sharp1 < 1){
         timer1.reset(); 
        }

        if(timer1.get() > 0.5 && ballcount < 2 &&  alliance == isBallRed){
          ball_ready = true;
        } else {
          ball_ready = false;
        }
      }
  //changes indexer position by set units, 2048 units per revolution

  public boolean ball_isReady(){
    return ball_ready;
  }

  public boolean is_ballRed(){
    return isBallRed;
  }

  public int ballcount(){
    return ballcount += 1;
  }

  public int reset_ballcount(){
    return ballcount = 0;
  }

  public boolean allianceColorBlue(){
    alliance = false;
    return alliance;
  }

  public boolean allianceColorRed(){
    alliance = true;
    return alliance;
  }

  //Color Sensor Stuff \\  //
  //                    \\//
  
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final Color kBlueTarget = new Color(0.143, 0.427, 0.429);
  private final Color kRedTarget = new Color(0.561, 0.232, 0.114);

  String colorString;

  boolean isBallRed;

  //eject feature? i.e. if red eject
  public boolean Colorperiodic(){

    Color detectedColor = m_colorSensor.getColor();
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) {
      isBallRed = false;
    } else if(match.color == kRedTarget) {
      isBallRed = true;
    }
    return isBallRed;
  } 

  final DriverStation.Alliance Blue = Alliance.Blue;
  final DriverStation.Alliance Red = Alliance.Red;

  public void Field_Alliance(){
    DriverStation.Alliance IsRedAlliance = DriverStation.getAlliance();
  
    if(IsRedAlliance == Blue){
      allianceColorBlue();
    } else if(IsRedAlliance == Red){
      allianceColorRed();
    }
  }
}
