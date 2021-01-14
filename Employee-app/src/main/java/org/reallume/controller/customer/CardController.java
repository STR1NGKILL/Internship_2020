package org.reallume.controller.customer;

import org.reallume.controller.common.SecurityController;
import org.reallume.domain.main.Account;
import org.reallume.domain.main.Card;
import org.reallume.repository.employee.EmployeeRepository;
import org.reallume.repository.main.AccountRepository;
import org.reallume.repository.main.CardRepository;
import org.reallume.repository.main.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CardController {

    private static final Integer CARD_LIFETIME_YEAR = 4;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping(value = "/cards")
    public String cardsPage(Authentication authentication, Model model) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("cards", cardRepository.findAll());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        model.addAttribute("dateFormat", dateFormat);

        return "card/cards-page";
    }

    @GetMapping(value = "/cards/create")
    public String createCardPage(Authentication authentication, Model model) {

        //dates setting
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date currentDate = new Date();
        String openDateString = dateFormat.format(currentDate);

        Calendar instance = Calendar.getInstance();
        instance.setTime(currentDate);
        instance.add(Calendar.YEAR, CARD_LIFETIME_YEAR);
        Date closeDate = instance.getTime();
        String closeDateString = dateFormat.format(closeDate);

        Long selectedAccountId = 0L;
        Card newCard = new Card(); //new card creating

        //the card's number generating
        String generatedNumber = SecurityController.generateNumber(15);
        while(cardRepository.findByNumber(generatedNumber).isPresent())
            generatedNumber = SecurityController.generateNumber(15);
        newCard.setNumber(generatedNumber);

        //statuses setting
        newCard.setActiveStatus(true);
        newCard.setBlockStatus(false);

        //current user's authorities preparing
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());

        model.addAttribute("card", newCard);

        model.addAttribute("openDateString", openDateString);
        model.addAttribute("closeDateString", closeDateString);

        model.addAttribute("selectedAccount", selectedAccountId);

        List<Account> accounts = accountRepository.findAll();
        accounts.removeIf(account -> account.getCard() != null);
        model.addAttribute("accounts", accounts);

        return "card/create-page";
    }

    @PostMapping(value = "/cards/create")
    public String createCard(@ModelAttribute Card newCard,
                                @RequestParam String openDateString, @RequestParam String closeDateString, @RequestParam Long selectedAccount) throws ParseException {

        newCard.setOpenDate(converterStringToDate(openDateString));
        newCard.setCloseDate(converterStringToDate(closeDateString));
        newCard.setCustomer(accountRepository.findById(selectedAccount).get().getCustomer());
        newCard.setAccount(accountRepository.findById(selectedAccount).get());
        newCard.setActiveStatus(true);
        newCard.setBlockStatus(false);

        cardRepository.save(newCard);

        //linking the card to an account
        Account account = accountRepository.findById(selectedAccount).get();
        account.setCard(cardRepository.findByNumber(newCard.getNumber()).get());

        if(newCard.getAccount() == null)
            return "card/create-page";

        accountRepository.save(account);

        return "redirect:/cards";
    }

    @GetMapping(value = "/cards/{card_id}/edit")
    public String editCardPage(@PathVariable Long card_id, Authentication authentication, Model model) {

        Card card = cardRepository.findById(card_id).get();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String openDateString = dateFormat.format(card.getOpenDate());
        String closeDateString = dateFormat.format(card.getCloseDate());

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("card", card);
        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("openDateString", openDateString);
        model.addAttribute("closeDateString", closeDateString);
        model.addAttribute("selectedAccount", card.getAccount());
        model.addAttribute("accounts", accountRepository.findByCustomerAndStatus(card.getCustomer(), true));

        return "card/edit-page";
    }

    @PostMapping(value = "/cards/{card_id}/edit")
    public String editCard(@PathVariable Long card_id,
                              @RequestParam String openDateString, @RequestParam String closeDateString, @RequestParam Long selectedAccount) throws ParseException {

        Card originCard = cardRepository.findById(card_id).get();

        originCard.setOpenDate(converterStringToDate(openDateString));
        originCard.setCloseDate(converterStringToDate(closeDateString));
        originCard.setAccount(accountRepository.findById(selectedAccount).get());
        originCard.setId(card_id);

        cardRepository.save(originCard);

        if(originCard.getActiveStatus()) {
            Account account = accountRepository.findById(selectedAccount).get();
            account.setCard(cardRepository.findByNumber(originCard.getNumber()).get());
            accountRepository.save(account);
        }

        return "redirect:/cards/" + card_id.toString() + "/edit";
    }

    @GetMapping(value = "/cards/{card_id}/change_active_status")
    public String changeCardActiveStatus(@PathVariable Long card_id) {

        Card card = cardRepository.findById(card_id).get();

        card.setActiveStatus(!card.getActiveStatus());

        cardRepository.save(card);

        //the status checking is inside of method
        unlinkCardFromAccount(card_id);

        return "redirect:/cards/" + card_id.toString() + "/edit";
    }

    @GetMapping(value = "/cards/{card_id}/change_block_status")
    public String changeCardBlockStatus(@PathVariable Long card_id) {

        Card card = cardRepository.findById(card_id).get();

        card.setBlockStatus(!card.getBlockStatus());
        card.setActiveStatus(!card.getActiveStatus());

        cardRepository.save(card);

        //the status checking is inside of method
        unlinkCardFromAccount(card_id);

        return "redirect:/cards/" + card_id.toString() + "/edit";
    }


    @GetMapping(value = "/cards/{card_id}/delete")
    public String deleteCard(@PathVariable Long card_id) {

        Card card = cardRepository.findById(card_id).get();

        if(card.getAccount() == null || (card.getAccount() != null && !card.getActiveStatus()))
            cardRepository.deleteById(card_id);

        return "redirect:/cards";
    }

    @GetMapping(value = "/cards/{card_id}/unlink")
    public String unlinkCardFromAccount(@PathVariable Long card_id) {

        Card card = cardRepository.findById(card_id).get();
        Account account = accountRepository.findById(card.getAccount().getId()).get();

        if(!card.getActiveStatus() || card.getBlockStatus()) {
            account.setCard(null);
            accountRepository.save(account);
        }

        if((card.getActiveStatus() || !card.getBlockStatus()) && account.getCard() == null) {
            account.setCard(cardRepository.findById(card_id).get());
            accountRepository.save(account);
        }

        cardRepository.save(card);

        return "redirect:/cards/" + card_id.toString() + "/edit";
    }

    @GetMapping(value = "/cards/{card_id}/rerelease")
    public String rereleaseCard(@PathVariable Long card_id) {

        Date currentDate = new Date();

        Calendar instance = Calendar.getInstance();
        instance.setTime(currentDate);
        instance.add(Calendar.YEAR, CARD_LIFETIME_YEAR);
        Date closeDate = instance.getTime();

        Card originalCard = cardRepository.findById(card_id).get();

        Card newCard = new Card(originalCard.getNumber(), currentDate, closeDate, true, false);
        newCard.setAccount(originalCard.getAccount());
        newCard.setCustomer(originalCard.getCustomer());

        cardRepository.save(newCard);

        Account account = accountRepository.findById(originalCard.getAccount().getId()).get();

        if(account.getCard() != null) {

            //unlinking the card from an account to delete the unlinked card
            account.setCard(null);

            //an account linking
            account.setCard(newCard);
            accountRepository.save(account);
        }

        cardRepository.deleteById(originalCard.getId());

        return "redirect:/cards/" + newCard.getId().toString() + "/edit";
    }

    public Date converterStringToDate(String StringValue) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(StringValue);
    }

    public String converterDateToString(Date dateValue) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(dateValue);
    }

}
