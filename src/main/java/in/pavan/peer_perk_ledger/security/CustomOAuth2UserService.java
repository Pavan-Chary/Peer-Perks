package in.pavan.peer_perk_ledger.security;

import in.pavan.peer_perk_ledger.constants.TransactionConstants;
import in.pavan.peer_perk_ledger.enums.AccountStatus;
import in.pavan.peer_perk_ledger.enums.UserRole;
import in.pavan.peer_perk_ledger.exception.InvalidOperationException;
import in.pavan.peer_perk_ledger.model.User;
import in.pavan.peer_perk_ledger.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepo userRepo;

    @Value("${app.admin.emails}")
    private List<String> emails;

    //This Object will intercept when the Google will return the SpringSecurity will load the user data from the google.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
        //This is to call the default loadUser so that it will communicate with the google and bring the user.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //We want to add the user if the user doesn't exist in or database right
        // so we are writing this custom logic
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        Optional<User> user = userRepo.findByEmail(email);

        User finalUser;

        if(user.isEmpty()){
            User newUser = new User();
            newUser.setName(name);
            newUser.setStatus(AccountStatus.ACTIVE);
            newUser.setAllowanceBalance(TransactionConstants.ADD_POINTS);
            newUser.setRedeemableBalance(0);
            newUser.setEmail(email);
            if(emails==null || emails.size()==0){
                throw new InvalidOperationException("Emails not loaded");
            }
            if(emails!=null && emails.contains(email)){
                newUser.setRole(UserRole.ROLE_ADMIN);
            }else{
                newUser.setRole(UserRole.ROLE_USER);
            }
            finalUser = userRepo.save(newUser);
            System.out.println("New user created successfully");
        }else{
            finalUser = user.get();
            System.out.println("Existign user Logged in");
        }
        //We are specifically Adding Our Enum value as the authority of the user so that it will be used to find in the ROle of the User
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(finalUser.getRole().name());

        // We changed the authority field of the User so we need to eleminate the Old User and
        // Create new OAuth2user and send
        return new DefaultOAuth2User(
                Collections.singletonList(authority),
                oAuth2User.getAttributes(),
                "email" //This is the Name tag to uniquly identify the User Session
        );
    }

}
