
package frc.robot.commands.DriveCommands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class IntakeActuate extends CommandBase {

  private Intake _intake;

  public IntakeActuate(Intake Subsystem) {
    _intake = Subsystem;
    addRequirements(_intake);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    _intake.set_intake_speed(Constants.active_intake_speed);
    _intake.set_intake_extended(true);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    System.out.println("Intake Initiated...");
  }
  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {}

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }
}
