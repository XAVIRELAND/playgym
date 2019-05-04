package models;

import play.db.jpa.Model;
import static java.lang.Math.round;


public class Analytics extends Model {


    public static double calculateBmi(Member member, Assessment assessment) {

        Double bmiVal;
        double weight;
        if (assessment == null) {
            weight = member.startingWeight;
        } else {
            weight = assessment.weight;
        }
        bmiVal = (100 * 100 * weight) / (member.height * member.height);
        bmiVal = round(bmiVal * 100) / 100.0;
        return bmiVal;

    }

    public static String calculateBmiCat(double bmiVal) {

        if (bmiVal < 16) {

            return "Severely underweight";
        }
        if ((bmiVal >= 16) && (bmiVal < 18.5)) {
            return "Underweight";
        }
        if ((bmiVal >= 18.5) && (bmiVal < 25)) {
            return "Normal";
        }
        if ((bmiVal >= 25) && (bmiVal < 30)) {
            return "Overweight";
        }
        if ((bmiVal >= 30) && (bmiVal < 35)) {
            return "Moderately obese";
        }
        else {
            return "Severely obese";
        }
    }
       /* Returns a boolean to indicate if the member has an ideal body weight based on the Devine formula:

        For males, an ideal body weight is: 50 kg + 2.3 kg for each inch over 5 feet.
                For females, an ideal body weight is: 45.5 kg + 2.3 kg for each inch over 5 feet.
                Note: if no gender is specified, return the result of the female calculation.
                Note: if the member is 5 feet or less, return 50kg for male and 45.5kg for female.

                men: Ideal Body Weight (in kg) = 50 + 2.3 kg x (height - 60 inches)
                women: Ideal Body Weight (in kg) = 45.5 + 2.3 kg x (height - 60 inches)
        */

    public static boolean isIdealBodyWeight(Member member, Assessment assessment) {

        boolean isIdealBodyweight = false;
        double standardHeight = 152.4;
        double excessHeight = 0, idealWeight;

        if (member.height > standardHeight) {
            excessHeight = (member.height - standardHeight) * 0.39; // gets the excess height in inches
        }

        if (member.gender.toLowerCase().equals("male")) {
            idealWeight = 50 + (2.3 * excessHeight);
        } else {
            idealWeight = 45.5 + (2.3 * excessHeight);
        }

        double weight;
        if (assessment == null) {
            weight = member.startingWeight;
        } else {
            weight = assessment.weight;
        }
        if (weight == round(idealWeight)) {
            isIdealBodyweight = true;
        }

        return isIdealBodyweight;
    }
}