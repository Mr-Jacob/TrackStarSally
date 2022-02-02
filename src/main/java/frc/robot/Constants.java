
package frc.robot;

public final class Constants {

  public static boolean allianceColor;
  public boolean isBallRed;

   //Drive motor Hardware ID's, set when ready
    public static  int left_MasterDrive = 0;
    public static boolean left_MasterDrive_isInverted = true;

    public static  int left_SlaveDrive = 1;
    public static boolean left_SlaveDrive_isInverted = true;

    public static  int right_MasterDrive = 2;
    public static boolean right_MasterDrive_isInverted = false;

    public static  int right_SlaveDrive = 3;
    public static boolean right_SlaveDrive_isInverted = false;


   //shooter ID's
    public static int right_shooter = 10;
    public static boolean right_shooter_isInverted = false;

    public static int left_shooter = 11;
    public static boolean left_shooter_isInverted = true;

   //shooter gains & timeouts
    public static final int k_left_shoot_SlotIdx = 1;
    public static final int k_left_shoot_PIDLoopIdx = 1;
    public static final int k_left_shoot_TimeoutMs = 30 ;
    public static boolean k_left_shoot_SensorPhase = true;
    public static final Gains k_left_shoot_Gains = new Gains(0.25, 0, 0, 2048/200, 300, 1);

    public static final int shooter_shortV = 340;
    public static final int shooter_longV = 500;
    public static final int shooter_shortshot_goalVelocity = 500;

    //filter wheel constants
    public static final int k_filter_wheel_SlotIdx = 0;
    public static final int k_filter_wheel_PIDLoopIdx = 0;
    public static final int k_filter_wheel_TimeoutMs = 30;
    public static boolean k_filter_wheel_SensorPhase = false;
    public static final Gains k_filter_wheel_Gains = new Gains(0.7, 0, 0, 2048/250, 300, 1);

    //cartridge motor constants
    public static int k_green_intake_falcon = 32;
    public static boolean k_green_intake_falcon_isInverted = false;

    public static int k_climb_cartridge_falcon = 20;
    public static boolean k_climb_cartridge_falcon_isInverted = false;

    public static int k_intake_bands_viktor = 31;
    public static boolean k_intake_bands_viktor_isInverted = false;

      //Indexer Constants
        public static final int k_index_band_SlotIdx = 0;
        public static final int k_index_band_PIDLoopIdx = 0;
        public static final int k_index_band_TimeoutMs = 30;
        public static boolean k_index_band_SensorPhase = false;
      //public static final Gains k_index_band_Gains = new Gains(0.7, 0, 0, 2048/200, 300, 1);
        public static final Gains k_index_band_Gains = new Gains(1, 0, 0, 2048/100, 300, 1);
    
        public static final int k_indexer_position_tolerance = 20;
    
      //public static final int k_indexer_position_increment = 33000;
        public static final int k_indexer_position_increment = 35000;

        public static final int ballIncrement = 512; //change later, moves quarter revolution bc 2048 = 1 rev

      //intake constants
        public static int k_exterior_intake_falcon = 30;
        public static boolean k_exterior_intake_falcon_isInverted = false;

        public static double active_intake_speed = 0.6;

}
