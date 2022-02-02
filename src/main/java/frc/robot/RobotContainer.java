package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.CustomTriggers.Indexer_trigger;
import frc.robot.CustomTriggers.xboxTrigger;
import frc.robot.CustomTriggers.xboxTrigger2;
import frc.robot.commands.DriveCommands.Cartridge.Auto_Shoot;
import frc.robot.commands.DriveCommands.Cartridge.IndexerAcceptBall;
import frc.robot.commands.DriveCommands.Cartridge.IndexerCount_Reset;
import frc.robot.commands.DriveCommands.Drive.Joystick_Drive;
import frc.robot.commands.DriveCommands.Drive.LimelightDrive;
import frc.robot.commands.DriveCommands.Intake.IntakeActuate;
import frc.robot.commands.DriveCommands.Intake.IntakeIn;
import frc.robot.commands.DriveCommands.Shooter.ShooterActive;
import frc.robot.commands.DriveCommands.Shooter.Toggle_Shooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;

public class RobotContainer {

  //define subsystems, then commands
  private final Drivetrain _drivetrain = new Drivetrain();
  private final Joystick_Drive _arcadeDrive = new Joystick_Drive(_drivetrain);

  private final static Shooter _shooter = new Shooter();

  private final Intake _intake = new Intake();
  private final IntakeIn _intakeIn = new IntakeIn(_intake);

  private final static Indexer _indexer = new Indexer();

  public static XboxController controller_1 = new XboxController(0);
  public static Joystick controller_1_joy = new Joystick(0);

  //controller 1 binds
  public static xboxTrigger left_trigger = new xboxTrigger(controller_1_joy, 2);
  public static xboxTrigger2 left_Trigger2 = new xboxTrigger2(controller_1_joy, 2, _shooter);

  public static Indexer_trigger indexer_trigger = new Indexer_trigger(_indexer);

  public static xboxTrigger right_trigger = new xboxTrigger(controller_1_joy, 3);
  
  public static JoystickButton xbox_1_a = new JoystickButton(controller_1, 1);
  public static JoystickButton xbox_1_rb = new JoystickButton(controller_1, 6);
  public static JoystickButton xbox_1_lb = new JoystickButton(controller_1, 5);
  public static JoystickButton xbox_1_x = new JoystickButton(controller_1, 3);
  public static JoystickButton xbox_1_b = new JoystickButton(controller_1, 2);
  public static JoystickButton xbox_1_select = new JoystickButton(controller_1, 7);
  //controller 2 binds
  
  public RobotContainer() {

    configureSubsystems();
    configureButtonBindings();
  }

  private void configureSubsystems(){
    _drivetrain.sys_init();
    _drivetrain.setDefaultCommand(_arcadeDrive);

    _shooter.sys_init();

    _intake.sys_init();
    _intake.setDefaultCommand(_intakeIn);

    _indexer.sys_init();
  }

  private void configureButtonBindings() {

    //brings intake down/up (pneumatic)
    xbox_1_a.whileActiveContinuous(new IntakeActuate(_intake));

    //revs up the shooter to the desired speed, set in constants
    left_trigger.whileActiveContinuous(new ShooterActive(_shooter));

    //tap to toggle shooter
    xbox_1_x.whenPressed(new Toggle_Shooter(_shooter));
    
    //if shooter reaches set velocity, sends balls into shooter continuously. Adds 60% deadzone to trigger
    left_Trigger2.whileActiveContinuous(new Auto_Shoot(_indexer));
    
  
    indexer_trigger.whenActive(new IndexerAcceptBall(_indexer));

    //hold to maintain limelight drive, cancels when released
    xbox_1_rb.whileActiveContinuous(new LimelightDrive(_drivetrain));

    xbox_1_lb.whenPressed(new IndexerAcceptBall(_indexer));

    xbox_1_select.whenPressed(new IndexerCount_Reset(_indexer));
  }

  //public Command getAutonomousCommand() {
  //} TBD
  }

