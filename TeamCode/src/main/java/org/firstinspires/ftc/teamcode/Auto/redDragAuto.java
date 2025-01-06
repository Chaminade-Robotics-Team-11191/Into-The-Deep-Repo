package org.firstinspires.ftc.teamcode.Auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "RED AUTONOMOUS")
public class redDragAuto extends LinearOpMode {
    public class linearSlide {
        private DcMotorEx linearSlide;
        public linearSlide(HardwareMap hardwareMap) {
            linearSlide = hardwareMap.get(DcMotorEx.class, "linearslide2");
            linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            linearSlide.setDirection(DcMotorSimple.Direction.REVERSE);
            linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        public Action up() {
            return new Action() {
                private boolean atPosition = false;
                private int currentPosition;
                private int position = 3500;
                @Override
                public boolean run(@NonNull TelemetryPacket packet) {
                    linearSlide.setTargetPosition(position);
                    linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    linearSlide.setVelocity(6000);
                    currentPosition = linearSlide.getCurrentPosition();
                    packet.put("currentPosition", currentPosition);
                    if (currentPosition == position) {
                        atPosition = true;
                    }
                    return atPosition;
                }
            };
        }
        public Action middle() {
            return new Action() {
                private boolean atPosition = false;
                private int currentPosition;
                private int position = 2700;
                @Override
                public boolean run(@NonNull TelemetryPacket packet) {
                    linearSlide.setTargetPosition(position);
                    linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    linearSlide.setVelocity(6000);
                    currentPosition = linearSlide.getCurrentPosition();
                    packet.put("currentPosition", currentPosition);
                    if (currentPosition == position) {
                        atPosition = true;
                    }
                    return atPosition;
                }
            };
        }
        public Action down() {
            return new Action() {
                private boolean atPosition = false;
                private int currentPosition;
                private int position = 0;
                @Override
                public boolean run(@NonNull TelemetryPacket packet) {
                    linearSlide.setTargetPosition(position);
                    linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    linearSlide.setVelocity(6000);
                    currentPosition = linearSlide.getCurrentPosition();
                    packet.put("currentPosition", currentPosition);
                    if (currentPosition == position) {
                        atPosition = true;
                    }
                    return atPosition;
                }
            };
        }
    }
    public class claw {
        private Servo claw;
        public claw(HardwareMap hardwareMap){
            claw = hardwareMap.get(Servo.class, "jackismadidk");
        }
        public Action setPosition(float pos) {
            return new Action() {
                private boolean atPosition = true;
                @Override
                public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                    claw.setPosition(pos);
                    return atPosition;
                }
            };
        }
        public Action open() {
            return new Action() {
                private boolean atPosition = true;
                @Override
                public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                    claw.setPosition(0.6);
                    return atPosition;
                }
            };
        }
        public Action close() {
            return new Action() {
                private boolean atPosition = true;
                @Override
                public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                    claw.setPosition(0.4);
                    return atPosition;
                }
            };
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPose = new Pose2d(31.1875, -63.5, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPose);
        claw claw = new claw(hardwareMap);
        linearSlide linearSlide = new linearSlide(hardwareMap);
        TrajectoryActionBuilder trajectory = drive.actionBuilder(startingPose)
                .afterDisp(0.001, linearSlide.up())
                .strafeToLinearHeading(new Vector2d(0, -32.5), Math.toRadians(180))
                .stopAndAdd(linearSlide.middle())
                .stopAndAdd(claw.open())
                .stopAndAdd(linearSlide.up())
                .strafeToConstantHeading(new Vector2d(25, -36))
                .afterDisp(3, linearSlide.down())
                .splineToConstantHeading(new Vector2d(37, -12), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(48, -12), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(48, -54), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(48, -12), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, -12), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(58, -54), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(58, -12), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(61.5, -12), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(61.5, -54), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(61.5, -40), Math.toRadians(90))
                .waitSeconds(1.5)
                .splineToSplineHeading(new Pose2d(53.5, -63, Math.toRadians(0)), Math.toRadians(270))
                .stopAndAdd(claw.close())
                .strafeToLinearHeading(new Vector2d(3, -32.5), Math.toRadians(180))
                .afterDisp(1, linearSlide.up())
                .stopAndAdd(linearSlide.middle())
                .stopAndAdd(claw.open())
                .strafeToLinearHeading(new Vector2d(53.5, -63), Math.toRadians(0))
                .afterDisp(3, linearSlide.down())
                .strafeToLinearHeading(new Vector2d(6, -32.5), Math.toRadians(180))
                .afterDisp(1, linearSlide.up())
                .stopAndAdd(linearSlide.middle())
                .stopAndAdd(claw.open())
                .strafeToLinearHeading(new Vector2d(53.5, -63), Math.toRadians(0))
                .afterDisp(3, linearSlide.down())
                .strafeToLinearHeading(new Vector2d(-2, -32.5), Math.toRadians(180))
                .afterDisp(1, linearSlide.up())
                .stopAndAdd(linearSlide.middle())
                .stopAndAdd(claw.open())
                .strafeToLinearHeading(new Vector2d(50, -60), Math.toRadians(180))
                .afterDisp(3, linearSlide.down());
        Action traj1 = trajectory.build();
        waitForStart();

        if (isStopRequested()) return;

        if (opModeIsActive()) {
            Actions.runBlocking(traj1);
        }
    }
}
