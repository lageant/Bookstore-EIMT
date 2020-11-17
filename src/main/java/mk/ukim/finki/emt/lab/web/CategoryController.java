package mk.ukim.finki.emt.lab.web;

import mk.ukim.finki.emt.lab.model.Category;
import mk.ukim.finki.emt.lab.service.CategoryService;
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
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories/add")
    public String addCategory(Model model){
        if(!model.containsAttribute("category")) {
            model.addAttribute("category",new Category());
        }
        return "Category.add";
    }

    @PostMapping("/categories/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category,
                              BindingResult bindingResult,
                              Model model, RedirectAttributes attr){
        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("org.springframework.validation.BindingResult.category", bindingResult);
            attr.addFlashAttribute("category",category);
            return  "redirect:/categories/add";
        }
        categoryService.save(category);
        return "redirect:/categories/";
    }

    @GetMapping("/categories") // prikaz na products
    public String CategoryList(Model model) {
        model.addAttribute("CategoryList",categoryService.findAll());
        //Reusing the Order Id DTO in order not to make redundant classes
        model.addAttribute("category_id", new OrderIdDTO());
        return "Category.list";
    }

    @PostMapping(params = "delete", path = "/categories")
    public String deleteCategory(Model model,@ModelAttribute OrderIdDTO category_id) {
        categoryService.delete(category_id.getOrder_id());
        return "redirect:/categories";
    }
}
