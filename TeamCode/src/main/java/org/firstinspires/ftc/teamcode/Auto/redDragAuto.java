package org.firstinspires.ftc.teamcode.Auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilderParams;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "redDragAuto")
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
                private boolean atPosition = false;
                @Override
                public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                    claw.setPosition(pos);
                    if (claw.getPosition() == pos) {
                        atPosition = true;
                    }
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
        TrajectoryActionBuilder traj1 = drive.actionBuilder(startingPose)
                .splineTo(new Vector2d(0, -32.5), Math.toRadians(180))
                .afterDisp(0.1, claw.setPosition(1))
                .afterDisp(0.1, linearSlide.up())
                .stopAndAdd(linearSlide.middle())
                .stopAndAdd(claw.setPosition(0))
                .stopAndAdd(linearSlide.up())
                .strafeToConstantHeading(new Vector2d(25, -36))
                .afterDisp(1, linearSlide.down())
                .splineToConstantHeading(new Vector2d(35, -5), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(48, -5, Math.toRadians(270)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(48, -54, Math.toRadians(270)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(48, -12, Math.toRadians(270)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, -12), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(58, -54, Math.toRadians(270)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(58, -12, Math.toRadians(270)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(62, -12), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(62, -54, Math.toRadians(270)), Math.toRadians(270));
                //.splineToConstantHeading(new Vector2d(63, -40), Math.toRadians(90))
    }
}
