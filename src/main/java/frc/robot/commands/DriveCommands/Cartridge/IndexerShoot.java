
package frc.robot.commands.DriveCommands.Cartridge;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cartridge;

public class IndexerShoot extends CommandBase {
  private final Cartridge m_IndexerSubsystem;
  public IndexerShoot(Cartridge m_Subsystem) {
    m_IndexerSubsystem = m_Subsystem;
    addRequirements(m_IndexerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      m_IndexerSubsystem.set_indexer(0.65);
      m_IndexerSubsystem.set_filter(0.5);
      m_IndexerSubsystem.set_bands_speed(0.2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_IndexerSubsystem.set_indexer(0);
    m_IndexerSubsystem.set_filter(0);
    m_IndexerSubsystem.set_bands_speed(0);
    m_IndexerSubsystem.reset_ball_count();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}