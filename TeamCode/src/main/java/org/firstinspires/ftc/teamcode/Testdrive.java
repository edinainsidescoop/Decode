package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Teleop scoop 2025", group = "rev0/teleop")
//@Disabled
public class Testdrive extends OpMode {
    // THIS IS where we put our public variables that are stored in the oppmodes class
    public double LauncherCloseMax = 1175;
    public double LauncherCloseMin = 1150;
    public double LauncherFarMax = 1500;
    public double LauncherFarMin =1400;

    final double STOP_SPEED = 0.0; //We send this power to the servos when we want them to stop.
    final double FULL_SPEED = 1.0;
    final double FLIPPER_UP = -24;
    final double FLIPPER_DOWN =6;
    //THIS IS where we declare our OpMode Mappings
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotorEx launcher = null;
    private CRServo leftFeeder = null;
    private CRServo rightFeeder = null;
    private Servo scoop = null;

    double leftPower;
    double rightPower;

    @Override
    public void init() {
        // this code runs when the user preses start !ONCE!

        // Initialize Hardware Mappings
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        leftFeeder = hardwareMap.get(CRServo.class, "left_feeder");
        rightFeeder = hardwareMap.get(CRServo.class, "right_feeder");
        scoop = hardwareMap.get(Servo.class,"flipper");
        // then set directions and stuff
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        launcher.setDirection(DcMotor.Direction.FORWARD);
        leftFeeder.setDirection(CRServo.Direction.REVERSE);
        rightFeeder.setDirection(CRServo.Direction.FORWARD);
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive.setZeroPowerBehavior(BRAKE);
        rightDrive.setZeroPowerBehavior(BRAKE);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        //increse timer

        // this is where you putt all the code for the robots op mode when it is initialized
        arcadeDrive(-gamepad1.left_stick_y, -gamepad1.right_stick_x);


        if (gamepad1.a) {
            launcher.setVelocity(LauncherCloseMax);
            if ((Math.abs(launcher.getVelocity()) >= LauncherCloseMin)) {
                leftFeeder.setPower(FULL_SPEED);
                rightFeeder.setPower(FULL_SPEED);
            }
        } else {
            launcher.setVelocity(0);
            leftFeeder.setPower(0);
            rightFeeder.setPower(0);
        }

        telemetry.addData("motorSpeed", Math.abs(launcher.getVelocity()));

        if (gamepad1.b) {
            launcher.setVelocity(LauncherFarMax);
            if ((Math.abs(launcher.getVelocity()) >= LauncherFarMin)) {
                leftFeeder.setPower(FULL_SPEED);
                rightFeeder.setPower(FULL_SPEED);
            }
        } else {
            launcher.setVelocity(0);
            leftFeeder.setPower(0);
            rightFeeder.setPower(0);
        }

        telemetry.addData("motorSpeed", Math.abs(launcher.getVelocity()));

        if (gamepad1.left_bumper) {
         scoop.setPosition(FLIPPER_UP);
            telemetry.addLine("test 1");

        }
        if (gamepad1.right_bumper) {
            scoop .setPosition(FLIPPER_DOWN);
            telemetry.addLine("test 2");
        }
    }

    void arcadeDrive(double forward, double rotate) {
        leftPower = forward + rotate;
        rightPower = forward - rotate;

        /*
         * Send calculated power to wheels
         */
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }



    //CUSTOM METHODS
}