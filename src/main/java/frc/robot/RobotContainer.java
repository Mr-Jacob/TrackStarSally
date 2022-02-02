package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.CustomTriggers.xboxTrigger;
import frc.robot.CustomTriggers.xboxTrigger2;
import frc.robot.commands.DriveCommands.Cartridge.Auto_Shoot;
import frc.robot.commands.DriveCommands.Cartridge.IndexerShoot;
import frc.robot.commands.DriveCommands.Cartridge.IndexerStandby;
import frc.robot.commands.DriveCommands.Drive.Joystick_Drive;
import frc.robot.commands.DriveCommands.Drive.LimelightDrive;
import frc.robot.commands.DriveCommands.Intake.IntakeActuate;
import frc.robot.commands.DriveCommands.Intake.IntakeIn;
import frc.robot.commands.DriveCommands.Shooter.ShooterActive;
import frc.robot.subsystems.Cartridge;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer2;

public class RobotContainer {

  //define subsystems, then commands
  private final Drivetrain _drivetrain = new Drivetrain();
  private final Joystick_Drive _arcadeDrive = new Joystick_Drive(_drivetrain);

  private final Shooter _shooter = new Shooter();

  private final Intake _intake = new Intake();
  private final IntakeIn _intakeIn = new IntakeIn(_intake);

  private final Cartridge _Cartridge = new Cartridge();
  private final IndexerStandby _indexStandby = new IndexerStandby(_Cartridge);

  private final Indexer2 _indexer = new Indexer2();

  private final FMS_Puller _allianceColor = new FMS_Puller();

  public static XboxController controller_1 = new XboxController(0);
  public static Joystick controller_1_joy = new Joystick(0);

  //controller 1 binds
  public static xboxTrigger left_trigger = new xboxTrigger(controller_1_joy, 2);
  public static xboxTrigger2 left_Trigger2 = new xboxTrigger2(controller_1_joy, 2);

  public static xboxTrigger right_trigger = new xboxTrigger(controller_1_joy, 3);
  
  public static JoystickButton xbox_1_a = new JoystickButton(controller_1, 1);
  public static JoystickButton xbox_1_rb = new JoystickButton(controller_1, 6);
  public static JoystickButton xbox_1_lb = new JoystickButton(controller_1, 5);

  //controller 2 binds
  
  public RobotContainer() {

    configureSubsystems();
    configureButtonBindings();
  }

  private void configureSubsystems(){
    _drivetrain.sys_init();
    _drivetrain.setDefaultCommand(_arcadeDrive);

    _shooter.sys_init();
    _shooter.stop_shooter();

    _intake.setDefaultCommand(_intakeIn);

    _Cartridge.sys_init();
    _Cartridge.setDefaultCommand(_indexStandby);

    _indexer.sys_init();
    _indexer.ColorSensor();

    _allianceColor.sys_init();
  }

  private void configureButtonBindings() {

    //brings intake down (pneumatic)
    xbox_1_a.whileActiveContinuous(new IntakeActuate(_intake));

    //revs up the shooter to the desired speed based on shooter toggle long || short
    left_trigger.whileActiveContinuous(new ShooterActive(_shooter));

    //left_trigger2 adds a 50% deadzone for the indexer auto_shoot command
    left_Trigger2.whileActiveContinuous(new Auto_Shoot(_shooter))
                  .whenInactive(new IndexerStandby(_Cartridge));

    //hold to maintain limelight drive, cancels when released
    xbox_1_rb.whileActiveContinuous(new LimelightDrive(_drivetrain));

    //manually spins cartridge at set 65%
    xbox_1_lb.whileActiveContinuous(new IndexerShoot(_Cartridge));
  }

  /**public Command getAutonomousCommand() {

    return name of sendable chooser var
    return PLACEHOLDER;
  } */
}
