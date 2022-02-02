package frc.robot.commands.DriveCommands.Cartridge;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class IndexerAcceptBall extends CommandBase {
  private Indexer m_IndexerSubsystem;
  private Timer m_Timer;

  public IndexerAcceptBall(Indexer subsystem) {
    m_IndexerSubsystem = subsystem;
    addRequirements(m_IndexerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_IndexerSubsystem.set_indexer(0.3);
    m_IndexerSubsystem.ballcount();
    System.out.println("Accepting Ball...");
    m_Timer = new Timer();
    m_Timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_IndexerSubsystem.set_indexer(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_Timer.get() > 0.35) {
      return true;
    } else {
      return false;
    }
  }
}                         
