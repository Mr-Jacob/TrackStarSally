package frc.robot.commands.DriveCommands.Cartridge;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class Auto_Shoot extends CommandBase {

  private final Indexer _indexer;

  public Auto_Shoot(Indexer m_Subsystem) {
    _indexer = m_Subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    _indexer.set_indexer(.65);
    System.out.print("Sending Balls into shooter...\n");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) { 
    _indexer.set_indexer(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
