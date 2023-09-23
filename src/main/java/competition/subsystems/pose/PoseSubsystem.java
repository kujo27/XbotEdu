package competition.subsystems.pose;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import xbot.common.controls.sensors.XGyro.XGyroFactory;
import xbot.common.properties.PropertyFactory;
import xbot.common.subsystems.pose.BasePoseSubsystem;

@Singleton
public class PoseSubsystem extends BasePoseSubsystem {

    private final DriveSubsystem drive;

    public double scalingFactorFromTicksToInches = 1.0 / 256.0;
    


    @Inject
    public PoseSubsystem(XGyroFactory gyroFactory, PropertyFactory propManager, DriveSubsystem drive) {
        super(gyroFactory, propManager);
        this.drive = drive;
    }

    public double getPosition() {
        return (getLeftDriveDistance() + getRightDriveDistance()) / 2.0; 
    }

    @Override
    protected double getLeftDriveDistance() {
        return drive.frontLeft.getSelectedSensorPosition(0) * scalingFactorFromTicksToInches;
    }

    @Override
    protected double getRightDriveDistance() {
        return drive.frontRight.getSelectedSensorPosition(0) * scalingFactorFromTicksToInches;
    }

}