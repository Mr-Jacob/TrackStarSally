package frc.robot.commands.DriveCommands.Cartridge;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class Auto_Shoot extends CommandBase {

  private final Shooter _shooter;

  public Auto_Shoot(Shooter m_Subsystem) {
    _shooter = m_Subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  _shooter.auto_shoot();
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
