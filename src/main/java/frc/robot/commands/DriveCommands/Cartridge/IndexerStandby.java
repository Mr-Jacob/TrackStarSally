package frc.robot.commands.DriveCommands.Cartridge;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cartridge;

public class IndexerStandby extends CommandBase {
  private final Cartridge m_IndexerSubsystem;
  
  public IndexerStandby(Cartridge subsystem) {
    m_IndexerSubsystem = subsystem;
    addRequirements(m_IndexerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Standby...");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_IndexerSubsystem.set_filter(0);
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