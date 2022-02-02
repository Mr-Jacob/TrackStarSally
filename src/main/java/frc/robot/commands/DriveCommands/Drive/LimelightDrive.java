package frc.robot.commands.DriveCommands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class LimelightDrive extends CommandBase {
  private final Drivetrain m_driveSubsystem;
  
  public LimelightDrive(Drivetrain subsystem) {
    m_driveSubsystem = subsystem;
    addRequirements(m_driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.print("Limelight On...\n");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // THRUST, ROTATION, SQUARED INPUTS
    m_driveSubsystem.curvatureDrive(RobotContainer.controller_1.getLeftY(), m_driveSubsystem.limelight_tracking(), true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}