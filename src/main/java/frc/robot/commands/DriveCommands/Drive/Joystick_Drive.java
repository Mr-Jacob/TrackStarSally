
package frc.robot.commands.DriveCommands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class Joystick_Drive extends CommandBase {
  private final Drivetrain m_driveSubsystem;
  
  public Joystick_Drive(Drivetrain subsystem) {
    m_driveSubsystem = subsystem;
    addRequirements(m_driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // THRUST, ROTATION, SQUARED INPUTS
    m_driveSubsystem.arcadeDrive(RobotContainer.controller_1.getLeftY(), RobotContainer.controller_1.getRightX(), true);
    System.out.print("Limelight On");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.print("Limelight Off");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}