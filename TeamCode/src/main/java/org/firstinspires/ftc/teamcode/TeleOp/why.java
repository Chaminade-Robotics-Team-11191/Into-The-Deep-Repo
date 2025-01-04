package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "Wunderlie Charlich 2")
public class why extends LinearOpMode {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    /*private DcMotor rightEncoder;
    private DcMotor leftEncoder;
    private DcMotor backEncoder;*/
    @Override
    public void runOpMode() throws InterruptedException {
        int post;
        final int tickRotation = 3800;
        //odometry odometry = new odometry(166,312,0.05026548245,216, -2520 , Math.PI/2);
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        /*leftEncoder = hardwareMap.get(DcMotor.class, "leftFront");
        rightEncoder = hardwareMap.get(DcMotor.class, "rightFront");
        backEncoder = hardwareMap.get(DcMotor.class, "leftBack");*/
        Deadline gamepadRateLimit = new Deadline(500, TimeUnit.MILLISECONDS);

        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        imu.initialize(parameters);
        /*linearSlide1 = hardwareMap.get(DcMotorEx.class, "linearSlide1");
        linearSlide2 = hardwareMap.get(DcMotorEx.class, "linearSlide2");
        linearSlide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);*/
        /*leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //linearSlide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //linearSlide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        if (opModeIsActive()) {
            //linearSlide1.setDirection(DcMotorSimple.Direction.REVERSE);
            while(opModeIsActive()) {
                //linearSlide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //linearSlide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


                //Mecanum Wheels Code
                double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
                double x = gamepad1.left_stick_x;
                double rx = gamepad1.right_stick_x;

                // This button choice was made so that it is hard to hit on accident,
                // it can be freely changed based on preference.
                // The equivalent button is start on Xbox-style controllers.
                if (gamepad1.options) {
                    imu.resetYaw();
                }

                double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

                // Rotate the movement direction counter to the bot's rotation
                double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
                double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

                rotX = rotX * 1.1;  // Counteract imperfect strafing

                // Denominator is the largest motor power (absolute value) or 1
                // This ensures all the powers maintain the same ratio,
                // but only if at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
                double frontLeftPower = (rotY + rotX + rx) / denominator;
                double backLeftPower = (rotY - rotX + rx) / denominator;
                double frontRightPower = (rotY - rotX - rx) / denominator;
                double backRightPower = (rotY + rotX - rx) / denominator;

                leftFront.setPower(frontLeftPower);
                leftBack.setPower(backLeftPower);
                rightFront.setPower(frontRightPower);
                rightBack.setPower(backRightPower);
                /*//Linear Slide
                post = linearSlide2.getCurrentPosition();
                if (-gamepad2.left_stick_y != 0 && ((post < tickRotation && post > 10) || (post < 10 && -gamepad2.left_stick_y > 0) || (post > tickRotation && -gamepad2.left_stick_y < 0))) {
                    linearSlide2.setPower(-gamepad2.left_stick_y);
                    linearSlide1.setPower(-gamepad2.left_stick_y);
                } else if (post > 10) {
                    linearSlide2.setPower(0);
                    linearSlide1.setPower(0);
                    linearSlide1.setTargetPosition(post);
                    linearSlide2.setTargetPosition(post);
                    linearSlide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    linearSlide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    linearSlide1.setVelocity(700);
                    linearSlide2.setVelocity(700);
                }*/
                telemetry.update();
            }
        }
    }
}