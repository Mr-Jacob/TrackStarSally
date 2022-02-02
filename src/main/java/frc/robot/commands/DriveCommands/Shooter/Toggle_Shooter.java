package frc.robot.commands.DriveCommands.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class Toggle_Shooter extends InstantCommand {
  private final Shooter m_ShooterSubsystem;
  public Toggle_Shooter(Shooter subsystem) {
    m_ShooterSubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ShooterSubsystem.toggle_shooter_hood();
  }
}