package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveTrainType;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.5151363155)
                .setDimensions(14.375, 17)
                .setDriveTrainType(DriveTrainType.MECANUM)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(31.1875, -63.5, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(0, -32.5), Math.toRadians(180))
                .strafeToConstantHeading(new Vector2d(25, -36))
                .splineToConstantHeading(new Vector2d(37, -12), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(48, -12), Math.toRadians(270))
                //.splineToSplineHeading(new Pose2d(48, -5, Math.toRadians(270)), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(48, -54), Math.toRadians(270))
                //.splineToSplineHeading(new Pose2d(48, -54, Math.toRadians(270)), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(48, -12), Math.toRadians(90))
                //.splineToSplineHeading(new Pose2d(48, -12, Math.toRadians(270)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, -12), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(58, -54), Math.toRadians(270))
                //.splineToSplineHeading(new Pose2d(58, -54, Math.toRadians(270)), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(58, -12), Math.toRadians(90))
                //.splineToSplineHeading(new Pose2d(58, -12, Math.toRadians(270)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(61.5, -12), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(61.5, -54), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(61.5, -40), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(53.5, -63, Math.toRadians(0)), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(3, -32.5), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(53.5, -63), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(6, -32.5), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(53.5, -63), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(-2, -32.5), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(50, -60), Math.toRadians(180))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}