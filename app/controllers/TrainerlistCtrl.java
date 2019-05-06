package controllers;


import models.Analytics;
import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;
import java.util.Collections;
import java.util.List;

public class TrainerlistCtrl extends Controller {
    public static void index(Long id) {
        Logger.info("Rendering trainerlist");
        Member member = Member.findById(id);
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

        render("trainerlist.html", member, assessmentList,bmi, bmiCat,colour);

    }

    public static void addComment(Long id, Long assessmentid, String comment){
        Trainer trainer= Accounts.getLoggedInTrainer();
        Assessment assessment = Assessment.findById(assessmentid);
        assessment.comment = comment;
        assessment.save();
        Member member = Member.findById(id);
        List<Assessment> assessmentList = member.assessmentList;
        Logger.info("Adding a comment"+ comment);
        redirect("/trainerlist/"+id);
    }


}
