
package frc.robot.commands.DriveCommands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeIn extends CommandBase {

  private Intake _intake;

  public IntakeIn(Intake Subsystem) {
    _intake = Subsystem;
    addRequirements(_intake);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    _intake.set_intake_speed(0);
    _intake.set_intake_extended(false);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
  }



  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    System.out.println("Intake Withdrawn...");
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }
}