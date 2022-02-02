
package frc.robot.subsystems;

import static frc.robot.Constants.left_MasterDrive;
import static frc.robot.Constants.right_MasterDrive;
import static frc.robot.Constants.right_SlaveDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

//primary motor ID's: left = 0, right = 2
//follower motor ID's: left = 1, right = 3
//current drive type: Cheesy Drive-*see Joystick_Drive Command

/** Add your docs here. */
public class Drivetrain extends SubsystemBase {

  //plug in CAN ID's when available
  private WPI_TalonFX leftDriveMaster = new WPI_TalonFX(left_MasterDrive);
  private WPI_TalonFX leftDriveSlave = new WPI_TalonFX(right_MasterDrive);
  private WPI_TalonFX rightDriveMaster = new WPI_TalonFX(right_MasterDrive);
  private WPI_TalonFX rightDriveSlave = new WPI_TalonFX(right_SlaveDrive);

  //insert CAN motor ID's when possible
  private DifferentialDrive _drive = new DifferentialDrive(leftDriveMaster, rightDriveMaster);

  public void sys_init() {


    leftDriveMaster.configFactoryDefault();
    rightDriveMaster.configFactoryDefault();
    leftDriveSlave.configFactoryDefault();
    rightDriveSlave.configFactoryDefault();

    rightDriveSlave.configNeutralDeadband(.001);
    leftDriveSlave.configNeutralDeadband(.001);

    _drive.setDeadband(0.08);

    //configure power current limits
    leftDriveMaster.configSupplyCurrentLimit(new  SupplyCurrentLimitConfiguration(true, 40, 0, 0));
    rightDriveMaster.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 0, 0));
    leftDriveSlave.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 0, 0));
    rightDriveSlave.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 0, 0));

    leftDriveMaster.enableVoltageCompensation(true);
    rightDriveMaster.enableVoltageCompensation(true);
    leftDriveSlave.enableVoltageCompensation(true);
    rightDriveSlave.enableVoltageCompensation(true);

    leftDriveMaster.configVoltageCompSaturation(12);
    rightDriveMaster.configVoltageCompSaturation(12);
    leftDriveSlave.configVoltageCompSaturation(12);
    rightDriveSlave.configVoltageCompSaturation(12);


    //motor control method
    leftDriveMaster.set(ControlMode.PercentOutput, 0);
    rightDriveMaster.set(ControlMode.PercentOutput, 0);
    leftDriveSlave.set(ControlMode.PercentOutput, 0);
    rightDriveSlave.set(ControlMode.PercentOutput, 0);

    leftDriveSlave.follow(leftDriveMaster);
    rightDriveSlave.follow(rightDriveMaster);

    //Drivetrain Motor Inversion
    leftDriveMaster.setInverted(Constants.left_MasterDrive_isInverted);
    rightDriveMaster.setInverted(Constants.right_MasterDrive_isInverted);

    leftDriveSlave.setInverted(Constants.left_SlaveDrive_isInverted);
    rightDriveSlave.setInverted(Constants.right_SlaveDrive_isInverted);

    _drive.setMaxOutput(1);
  }

  public void curvatureDrive(double thrust_axis, double rotation_axis, boolean isSquared){
    double thrust, rotation = 0;
    thrust = thrust_axis * 1;
    rotation = -rotation_axis;
    _drive.curvatureDrive(thrust, rotation, isSquared);
  }

  public void setDrive(boolean is_squared) {
    _drive.arcadeDrive(thrust_axis_storage, rotation_axis_storage, is_squared);
  }

  private double thrust_axis_storage = 0;
  private double rotation_axis_storage = 0;

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");

  public void update_limelight_values(){
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    table.getEntry("ledMode").setNumber(3);
  }

  double kP = -0.08;
  double min_Command = 0.01;

  public double limelight_tracking(){
    double heading_error = -tx.getDouble(0.0);
    double steering_adjustment = 0.0;

    if(heading_error>1){
      steering_adjustment = kP * heading_error - min_Command;
    } else if (heading_error<1){
      steering_adjustment = kP * heading_error + min_Command;
    } 
    return steering_adjustment;
  }

  @Override
  public void periodic(){
    update_limelight_values();
  }
}