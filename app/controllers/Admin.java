package controllers;

import models.Assessment;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Admin extends Controller
{
    public static void index()
    {
        Logger.info("Rendering Admin");
        List<Assessment> assessmentList = Assessment.findAll();
        render("admin.html", assessmentList);
    }
}
