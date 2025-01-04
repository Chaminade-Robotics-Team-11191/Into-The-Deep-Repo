package org.firstinspires.ftc.teamcode.Auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

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
        //Go Hang the first preloaded block
        TrajectoryActionBuilder traj1 = drive.actionBuilder(startingPose)
                .setTangent(Math.toRadians(90))
                .splineTo(new Vector2d(0, -32.5), Math.toRadians(180));
    }
}
