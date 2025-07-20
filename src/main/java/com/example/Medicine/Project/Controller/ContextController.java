package com.example.Medicine.Project.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContextController {

    @GetMapping({"/index","/"})
    public String IndexPage(){
        return "/index";
    }
    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/service")
    public String servicePage() {
        return "service";
    }

    @GetMapping("/patient")
    public String patientPage() {
        return "patient";
    }

    @GetMapping("/doctor")
    public String doctorPage() {
        return "doctor";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }
    @GetMapping("/capture")
    public String capturePage() {
        return "capture";
    }
    @GetMapping("/dotpat")
    public String DocPatPage() {
        return "dotpat";
    }

    @GetMapping("/appointment")
    public String AppointmentPage() {
        return "appointment";
    }
}
