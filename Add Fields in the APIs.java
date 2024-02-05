//Update User Model
   //Add the providerType field with an enum type in the User model.
   //Add the providerId field to store the generated Stripe customer ID.

public enum ProviderType {
    STRIPE
}

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    private String providerId;

    // Getters and setters
}


//Update Application Controller**:
   //Update the user signup endpoint in the application controller to handle the new fields.
   //Generate and store the Stripe customer ID during the user signup process.

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/signup")
    public ResponseEntity<User> signupUser(@RequestBody User user) {
        // Set provider type to Stripe
        user.setProviderType(ProviderType.STRIPE);
        
        // Call the method to create a customer in Stripe and get the customer ID
        String stripeCustomerId = createStripeCustomer(user.getName(), user.getEmail());
        
        // Set the generated Stripe customer ID in the user object
        user.setProviderId(stripeCustomerId);
        
        // Save the user in the database
        User savedUser = userService.saveUser(user);
        
        return ResponseEntity.ok(savedUser);
    }
    
    private String createStripeCustomer(String name, String email) {
        // Call your existing method or service to create a customer in Stripe and return the customer ID
        // This method should interact with Stripe API to create a customer and retrieve the customer ID
        return "sample-stripe-customer-id";
    }
}

