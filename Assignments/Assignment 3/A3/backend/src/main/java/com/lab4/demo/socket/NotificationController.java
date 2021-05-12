package com.lab4.demo.socket;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    @MessageMapping("/checkIn")
    @SendTo("/topic/notification")
    public Notification checkIn(CheckInDTO receiveMessage) throws Exception {
        Thread.sleep(1000);
        return new Notification("Appointment created for patient:  "
                + HtmlUtils.htmlEscape(receiveMessage.getPatientId().toString())
                + " on: " + HtmlUtils.htmlEscape((receiveMessage.getDateOfConsult().toString())) +
                " with doctor: " + HtmlUtils.htmlEscape(receiveMessage.getDoctorId().toString()) );
    }
}

