package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;
import java.util.List;

public class TrainerDashCtrl extends Controller {
    public static void index() {
        Logger.info("Rendering TrainerDashCtrl");
        Trainer trainer = Accounts.getLoggedInTrainer();
        List<Member> memberList = trainer.memberList;
        render("trainerdashboard.html",trainer,memberList);

    }
    public static void deleteMember(Long id){

        Trainer trainer = Accounts.getLoggedInTrainer();
        Member member = Member.findById(id);
        trainer.memberList.remove(member);
        trainer.save();
        member.delete();
        Logger.info("Deleting a member" +member.name, member.email, member.password, member.address, member.gender, member.height, member.startingWeight);
        redirect("/trainerdashboard");
    }
}
