package competition.subsystems.drive;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import javax.inject.Inject;
import javax.inject.Singleton;

import competition.electrical_contract.ElectricalContract;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.controls.actuators.XCANTalon.XCANTalonFactory;
import xbot.common.injection.electrical_contract.CANTalonInfo;
import xbot.common.math.PIDManager;
import xbot.common.math.XYPair;
import xbot.common.subsystems.drive.BaseDriveSubsystem;

@Singleton
public class DriveSubsystem extends BaseDriveSubsystem {

    public final XCANTalon frontLeft;
    public final XCANTalon frontRight;

    public boolean precisionMode;

    private final double simulatedEncoderFactor = 256.0 * 39.3701; //256 "ticks" per meter, and ~39 inches in a meter

    @Inject
    public DriveSubsystem(XCANTalonFactory talonFactory, ElectricalContract electricalContract) {
        log.info("Creating DriveSubsystem");
        // instantiate speed controllers and sensors here, save them as class members

        this.frontLeft = talonFactory
                .create(new CANTalonInfo(1, true, FeedbackDevice.CTRE_MagEncoder_Absolute, false, simulatedEncoderFactor));
        this.frontRight = talonFactory
                .create(new CANTalonInfo(2, true, FeedbackDevice.CTRE_MagEncoder_Absolute, false, simulatedEncoderFactor));

        frontLeft.createTelemetryProperties(this.getPrefix(), "frontLeft");
        frontRight.createTelemetryProperties(this.getPrefix(), "frontRight");

        this.register();
    }

    public void tankDrive(double leftPower, double rightPower) {
        // You'll need to take these power values and assign them to all of the motors.
        // As
        // an example, here is some code that has the frontLeft motor to spin according
        // to
        // the value of leftPower:
        frontLeft.simpleSet(leftPower);
        frontRight.simpleSet(rightPower);
    }

    public void togglePrecisionMode() {
        if (precisionMode) {
            precisionMode = false;
            tankDrive(1.0, 1.0);
        }
        else {
            precisionMode = true;
            tankDrive(0.5, 0.5);
        }
    }
    
    @Override
    public void periodic() {
        super.periodic();
        frontLeft.updateTelemetryProperties();
        frontRight.updateTelemetryProperties();
    }

    @Override
    public PIDManager getPositionalPid() {
        // TODO: Auto-generated method stub
        return null;
    }

    @Override
    public PIDManager getRotateToHeadingPid() {
        // TODO: Auto-generated method stub
        return null;
    }

    @Override
    public PIDManager getRotateDecayPid() {
        // TODO: Auto-generated method stub
        return null;
    }

    @Override
    public void move(XYPair translate, double rotate) {
       throw new RuntimeException("Not yet implemented");
    }

    @Override
    public double getLeftTotalDistance() {
        // TODO: Auto-generated method stub
        return 0;
    }

    @Override
    public double getRightTotalDistance() {
        // TODO: Auto-generated method stub
        return 0;
    }

    @Override
    public double getTransverseDistance() {
        // TODO: Auto-generated method stub
        return 0;
    }
}
