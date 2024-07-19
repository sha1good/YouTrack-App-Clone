package com.luv2code.ProjectManagementSystem.controller;


import com.luv2code.ProjectManagementSystem.model.SubscriptionType;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.response.PaymentLinkResponse;
import com.luv2code.ProjectManagementSystem.service.UserService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${razorpay.api.key}")
    private String apiKey;
    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Autowired
    private UserService userService;


    @PostMapping("/{subType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable SubscriptionType subType,
            @RequestHeader("Authorization") String token
    ) throws Exception {
        User user = userService.findUserByToken(token);
        int amount = 799 * 100;
        if (subType.equals(SubscriptionType.ANNUALLY)) {
            amount = amount * 12;
            amount = (int) (amount * 0.7);
        }

        RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
        JSONObject paymentLinkReq = new JSONObject();
        paymentLinkReq.put("amount", amount);
        paymentLinkReq.put("currency", "cad");

        JSONObject customer = new JSONObject();
        customer.put("name", user.getFullName());
        customer.put("email", user.getEmail());
        paymentLinkReq.put("customer", customer);

        JSONObject notify = new JSONObject();
        notify.put("email", true);
        paymentLinkReq.put("notify", notify);

        paymentLinkReq.put("callback_url", "http://localhost:5173/upgrade_Plan/success?subType="+ subType);

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkReq);

        String paymentLinkId = payment.get("id");
        String paymentLinkUrl = payment.get("short_url");

        PaymentLinkResponse response = new PaymentLinkResponse();
        response.setPayment_link_id(paymentLinkId);
        response.setPayment_link_url(paymentLinkUrl);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
