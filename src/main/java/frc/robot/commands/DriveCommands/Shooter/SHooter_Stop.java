package frc.robot.commands.DriveCommands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SHooter_Stop extends CommandBase {
  private final Shooter m_Shooter;

  public SHooter_Stop(Shooter subsystem) {
    m_Shooter = subsystem;
    //addRequirements(m_Shooter);
  }

// Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Shooter.stop_shooter();
    System.out.print("shooter stopping!...");
    //m_Shooter.limelight_on();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //m_Shooter.limelight_off();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}