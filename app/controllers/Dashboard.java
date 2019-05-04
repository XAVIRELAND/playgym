package controllers;

import models.Analytics;
import models.Assessment;
import models.Member;
import play.Logger;
import play.mvc.Controller;
import java.util.Collections;
import java.util.List;

public class Dashboard extends Controller {

  public static void index() {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Assessment> assessmentList = member.assessmentList;
    Collections.reverse(assessmentList);
    Analytics analytics = new Analytics();
    double bmi;



    boolean isIdealBodyWeight;

    if (assessmentList.size() == 0) {
      bmi = analytics.calculateBmi(member, null);
      isIdealBodyWeight = analytics.isIdealBodyWeight(member, null);
    } else {
      bmi = analytics.calculateBmi(member, assessmentList.get(0));
      isIdealBodyWeight = analytics.isIdealBodyWeight(member, assessmentList.get(0));
    }

    String bmiCat = analytics.calculateBmiCat(bmi);

    String colour = "";
    if (!isIdealBodyWeight) {
      colour = "color:red";
    } else {
      colour = "color:green";
    }

    render("dashboard.html", member, assessmentList,bmi, bmiCat,colour);

  }

  public static void addAssessment(double weight, double chest, double thigh, double upperArm, double waist, double hips) {
    Member member = Accounts.getLoggedInMember();
    Assessment assessment = new Assessment(weight, chest, thigh, upperArm, waist, hips);
    member.assessmentList.add(assessment);
    member.save();
    Logger.info("Adding Assessment" + weight + chest + thigh + upperArm + waist + hips);
    redirect("/dashboard");
  }

  public static void deleteAssessment(Long id, Long assessmentid) {
    Member member = Member.findById(id);
    Assessment assessment = Assessment.findById(assessmentid);
    member.assessmentList.remove(assessment);
    member.save();
    assessment.delete();
    Logger.info("Deleting an assessment" + assessment.weight + assessment.chest + assessment.thigh + assessment.upperArm + assessment.waist + assessment.hips);
    redirect("/dashboard");
  }

}
