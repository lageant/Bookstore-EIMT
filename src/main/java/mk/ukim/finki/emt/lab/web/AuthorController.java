package mk.ukim.finki.emt.lab.web;

import mk.ukim.finki.emt.lab.model.Author;
import mk.ukim.finki.emt.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors/add")
    public String addAuthor(Model model){
        if(!model.containsAttribute("author")) {
            model.addAttribute("author",new Author());
        }
        return "author.add";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@Valid @ModelAttribute("author") Author author,
                            BindingResult bindingResult,
                            Model model, RedirectAttributes attr){
        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("org.springframework.validation.BindingResult.author", bindingResult);
            attr.addFlashAttribute("author",author);
            return  "redirect:/authors/add";
        }
        authorService.save(author);
        return "redirect:/authors/";
    }

    @GetMapping("/authors") // prikaz na products
    public String AuthorList(Model model) {
        model.addAttribute("AuthorList",authorService.findAll());
        if(!model.containsAttribute("author_id")) {
            model.addAttribute("author_id", new OrderIdDTO());
        }
        return "Author.list";
    }

    @PostMapping(params = "delete", path = "/authors")
    public String deleteAuthor(Model model,@ModelAttribute OrderIdDTO author_id) {
        authorService.delete(author_id.getOrder_id());
        return "redirect:/authors";
    }
}
