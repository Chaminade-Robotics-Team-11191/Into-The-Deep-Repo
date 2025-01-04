package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "mecanumWheels")
public class mecanumWheels extends LinearOpMode {
    //Initializing hardware
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotorEx linearslide1;
    private CRServo lagrangepoints;
    private Servo jackismadidk;
    private DcMotorEx gilfoil;
    private DcMotorEx linearslide2;
    @Override
    public void runOpMode() throws InterruptedException {
        //Initializing variables
        double y;
        double x;
        double z;
        double theta;
        double sin;
        double cos;
        double max;
        double power;
        //Variable finds
        int post = 0;
        int post2 = 0;
        int post3 = 0;
        int tickRotation = 4000;
        final int tickRotation2 = 4000;
        final int tickRotation3 = 6000;
        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        linearslide1 = hardwareMap.get(DcMotorEx.class, "linearslide1");
        lagrangepoints = hardwareMap.get(CRServo.class, "lagrangepoints");
        gilfoil = hardwareMap.get(DcMotorEx.class, "gilfoil");
        linearslide2 = hardwareMap.get(DcMotorEx.class,"linearslide2");
        jackismadidk = hardwareMap.get(Servo.class, "jackismadidk");
        linearslide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearslide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearslide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearslide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        gilfoil.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gilfoil.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        if (isStopRequested()) return;
        if (opModeIsActive()) {
            leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            lagrangepoints.setDirection(DcMotorSimple.Direction.REVERSE);
            linearslide2.setDirection(DcMotorSimple.Direction.REVERSE);
            while(opModeIsActive()) {
                if (gamepad2.right_bumper) {
                    lagrangepoints.setPower(1);
                }
                else if (gamepad2.left_bumper) {
                    lagrangepoints.setPower(-1);
                }
                else {
                    lagrangepoints.setPower(0);
                }
                if (gamepad2.y) {
                    jackismadidk.setPosition(1);
                }
                else if (gamepad2.a) {
                    jackismadidk.setPosition(0);
                }
                //Linear Slide
                post = linearslide1.getCurrentPosition();
                telemetry.addData("post", post);
                if (post3 > 3500) {
                    tickRotation = 4000;
                }
                else {
                    tickRotation = 2400;
                }
                if (-gamepad2.left_stick_y != 0 && ((post < tickRotation && post > 10) || (post < 10 && -gamepad2.left_stick_y > 0) || (post > tickRotation && -gamepad2.left_stick_y < 0))) {
                    linearslide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    linearslide1.setPower(-gamepad2.left_stick_y);
                } else if (post > 10) {
                    linearslide1.setPower(0);
                    linearslide1.setTargetPosition(post);
                    linearslide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    linearslide1.setVelocity(200);
                }
                post2 = linearslide2.getCurrentPosition();
                telemetry.addData("post2", post2);
                if (-gamepad2.right_stick_y != 0 && ((post2 < tickRotation2 && post2 > 10) || (post2 < 10 && -gamepad2.right_stick_y > 0) || (post2 > tickRotation2 && -gamepad2.right_stick_y < 0))) {
                    linearslide2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    linearslide2.setPower(-gamepad2.right_stick_y);
                } else if (post2 > 10) {
                    linearslide2.setPower(0);
                    linearslide2.setTargetPosition(post2);
                    linearslide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    linearslide2.setVelocity(200);
                }
                float cool = 0;
                post3 = gilfoil.getCurrentPosition();
                if (gamepad2.left_trigger != 0) {
                    cool = -gamepad2.left_trigger;
                }
                else if (gamepad2.right_trigger != 0) {
                    cool = gamepad2.right_trigger;
                }
                else {
                    cool = 0;
                }
                telemetry.addData("post3", post3);
                if (Math.abs(cool) > 0.3 && ((post3 < tickRotation3 && post3 > -10000) || (post3 < 10 && cool > 0) || (post3 > tickRotation3 && cool < 0))) {
                    gilfoil.setPower(cool);
                }
                else {
                    gilfoil.setPower(0);
                }
                if (gamepad1.options) {
                    imu.resetYaw();
                }

                double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
                y = gamepad1.left_stick_y;
                x = gamepad1.left_stick_x;
                z = gamepad1.right_stick_x;
                theta = Math.atan2(y,x);
                power = Math.hypot(x,y);
                sin = Math.sin((theta - botHeading) - Math.PI/4); //
                cos = Math.cos((theta - botHeading) - Math.PI/4);
                max = Math.max(Math.abs(sin), Math.abs(cos));

                leftFront.setPower(power * cos/max + z);
                leftBack.setPower(power * sin/max + z);
                rightFront.setPower(power * sin/max - z);
                rightBack.setPower(power * cos/max - z);

                double[] motor = {leftFront.getPower(), leftBack.getPower(), rightBack.getPower(), rightFront.getPower()};

                if ((power + Math.abs(z)) > 1) {
                    leftFront.setPower(motor[0]/(power + Math.abs(z)));
                    rightFront.setPower(motor[3]/(power + Math.abs(z)));
                    leftBack.setPower(motor[1]/(power + Math.abs(z)));
                    rightBack.setPower(motor[2]/(power + Math.abs(z)));
                }
                telemetry.update();
            }
        }
    }
}