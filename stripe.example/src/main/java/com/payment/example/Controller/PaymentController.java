package com.payment.example.Controller;
import com.payment.example.Entity.Product;
import com.payment.example.Repository.ProductRepository;
import com.payment.example.Service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${stripe.api.secretKey}")
    private String stripApiKey;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PaymentService paymentService;

    // http://localhost:9595/payment/initiate?productId=1
    @PostMapping("/initiate")
    public ResponseEntity<String> initiatePayment(@RequestParam Long productId)  {
       Product product = this.productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));
       String paymentIntentId =  this.paymentService.createPaymentIntent(product.getPrice());
       return ResponseEntity.ok(paymentIntentId);
    }

}
