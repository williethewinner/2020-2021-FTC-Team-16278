package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

//@Disabled
public abstract class MecanumDrive extends OpMode
{
    protected DcMotor leftFrontMotor;
    protected DcMotor rightFrontMotor;
    protected DcMotor leftRearMotor;
    protected DcMotor rightRearMotor;
    protected DcMotor launcherMotor;
    protected DcMotor feederMotor;
    protected boolean isLauncherOn;
    protected boolean isFeederOn;
    protected Servo launcherServo;
    protected boolean isLauncherServoActive;

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFrontMotor = hardwareMap.get(DcMotor.class, "Left Front");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "Right Front");
        leftRearMotor = hardwareMap.get(DcMotor.class, "Left Rear");
        rightRearMotor = hardwareMap.get(DcMotor.class, "Right Rear");
        launcherMotor = hardwareMap.get(DcMotor.class, "Shooter");
        feederMotor = hardwareMap.get(DcMotor.class, "Feeder");
        launcherServo = hardwareMap.get(Servo.class, "launcher servo");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.REVERSE);
        launcherMotor.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }


}
