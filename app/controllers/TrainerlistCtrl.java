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
        double bmi = analytics.calculateBmi(member, assessmentList.get(0));
        String bmiCat = analytics.calculateBmiCat(bmi);
        render("trainerlist.html", member, assessmentList, bmi, bmiCat);
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
