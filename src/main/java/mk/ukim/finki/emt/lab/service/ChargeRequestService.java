package mk.ukim.finki.emt.lab.service;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import mk.ukim.finki.emt.lab.model.ChargeRequest;
import mk.ukim.finki.emt.lab.repository.ChargeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChargeRequestService {
    @Value("sk_test_h85PnzvZS5U8HrjRnn6e7ygG00iaPhOy3d")
    private String secretKey;

    @PostConstruct
    public void init() {
    Stripe.apiKey = secretKey;
    }
    public Charge charge(ChargeRequest chargeRequest) throws AuthenticationException,InvalidRequestException,APIConnectionException, CardException, APIException {
    Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("amount", chargeRequest.getAmount());
    chargeParams.put("currency", chargeRequest.getCurrency());
    chargeParams.put("description", chargeRequest.getDescription());
    chargeParams.put("source", chargeRequest.getStripeToken());
    return Charge.create(chargeParams);
    }


    @Autowired
    private ChargeRequestRepository chargeRequestRepository;

    public ChargeRequest save(ChargeRequest chargeRequest) {
        return chargeRequestRepository.save(chargeRequest);
    }

    public List<ChargeRequest> findAll() {
        return chargeRequestRepository.findAll();
    }
}