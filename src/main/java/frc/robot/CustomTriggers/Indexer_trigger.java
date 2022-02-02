package frc.robot.CustomTriggers;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Indexer;

public class Indexer_trigger extends Trigger {
    Indexer m_IndexerSubsytem;

    public Indexer_trigger(Indexer subsystem){
        m_IndexerSubsytem = subsystem;
    }

    @Override
    public boolean get() {
        return m_IndexerSubsytem.ball_isReady();
    }
}
