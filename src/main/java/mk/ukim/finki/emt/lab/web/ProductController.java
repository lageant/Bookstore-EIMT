package mk.ukim.finki.emt.lab.web;

import mk.ukim.finki.emt.lab.model.Author;
import mk.ukim.finki.emt.lab.model.Category;
import mk.ukim.finki.emt.lab.model.Product;
import mk.ukim.finki.emt.lab.service.AuthorService;
import mk.ukim.finki.emt.lab.service.CategoryService;
import mk.ukim.finki.emt.lab.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    @PostConstruct
    public void loadData()
    {
        Author a = new Author();
        a.setName("George R.R. Martin");
        authorService.save(a);

        Author a1 = new Author();
        a1.setName("J.R.R. Tolkien");
        authorService.save(a1);

        Category c = new Category();
        c.setName("Medieval fantasy");
        categoryService.save(c);

        Category c2 = new Category();
        c2.setName("High fantasy");
        categoryService.save(c2);

        Product p = new Product();
        p.setPicture_link("https://upload.wikimedia.org/wikipedia/en/thumb/9/93/AGameOfThrones.jpg/220px-AGameOfThrones.jpg");
        p.setDescription("First book");
        p.setName("A Game of Thrones");
        p.setPrice(50);
        p.setCategory(c);
        p.setAuthor(a);
        productService.save(p);

        p = new Product();
        p.setPicture_link("https://upload.wikimedia.org/wikipedia/en/3/39/AClashOfKings.jpg");
        p.setDescription("Second book");
        p.setName("A Clash of Kings");
        p.setPrice(60);
        p.setCategory(c);
        p.setAuthor(a);
        productService.save(p);

        p = new Product();
        p.setPicture_link("https://upload.wikimedia.org/wikipedia/en/thumb/2/24/AStormOfSwords.jpg/220px-AStormOfSwords.jpg");
        p.setDescription("Third book");
        p.setName("A Storm of Swords");
        p.setPrice(70);
        p.setCategory(c);
        p.setAuthor(a);
        productService.save(p);

        p = new Product();
        p.setPicture_link("https://upload.wikimedia.org/wikipedia/en/thumb/a/a3/AFeastForCrows.jpg/220px-AFeastForCrows.jpg");
        p.setDescription("Fourth book");
        p.setName("A Feast for Crows");
        p.setPrice(70);
        p.setCategory(c);
        p.setAuthor(a);
        productService.save(p);

        p = new Product();
        p.setPicture_link("https://upload.wikimedia.org/wikipedia/en/5/5d/A_Dance_With_Dragons_US.jpg");
        p.setDescription("Fifth book");
        p.setName("A Dance with Dragons");
        p.setPrice(70);
        p.setCategory(c);
        p.setAuthor(a);
        productService.save(p);

        p = new Product();
        p.setPicture_link("https://upload.wikimedia.org/wikipedia/en/thumb/8/8e/The_Fellowship_of_the_Ring_cover.gif/220px-The_Fellowship_of_the_Ring_cover.gif");
        p.setDescription("First book");
        p.setName("The Fellowship of the Ring");
        p.setPrice(55);
        p.setCategory(c2);
        p.setAuthor(a1);
        productService.save(p);

        p = new Product();
        p.setPicture_link("https://upload.wikimedia.org/wikipedia/en/thumb/8/8e/The_Fellowship_of_the_Ring_cover.gif/220px-The_Fellowship_of_the_Ring_cover.gif");
        p.setDescription("First book");
        p.setName("The Fellowship of the Ring");
        p.setPrice(55);
        p.setCategory(c2);
        p.setAuthor(a1);
        productService.save(p);

        p = new Product();
        p.setPicture_link("https://upload.wikimedia.org/wikipedia/en/thumb/a/a1/The_Two_Towers_cover.gif/220px-The_Two_Towers_cover.gif");
        p.setDescription("Second book");
        p.setName("The Two Towers");
        p.setPrice(65);
        p.setCategory(c2);
        p.setAuthor(a1);
        productService.save(p);

        p = new Product();
        p.setPicture_link("https://upload.wikimedia.org/wikipedia/en/thumb/1/11/The_Return_of_the_King_cover.gif/220px-The_Return_of_the_King_cover.gif");
        p.setDescription("Third book");
        p.setName("The Return of the King");
        p.setPrice(65);
        p.setCategory(c2);
        p.setAuthor(a1);
        productService.save(p);

        p = new Product();
        p.setPicture_link("https://upload.wikimedia.org/wikipedia/en/thumb/4/4a/TheHobbit_FirstEdition.jpg/220px-TheHobbit_FirstEdition.jpg");
        p.setDescription("The prequel");
        p.setName("The Hobbit");
        p.setPrice(40);
        p.setCategory(c2);
        p.setAuthor(a1);
        productService.save(p);
    }
    @GetMapping("/products")
    public String Products(Model model,@RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);

        Page<Product> productPage = productService.findPaginated(PageRequest.of(currentPage - 1, 4));

        model.addAttribute("productPage",productPage);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        if(!model.containsAttribute("orderForm")) {
            model.addAttribute("orderForm", new OrderDTO());
        }
        model.addAttribute("product_id", new OrderIdDTO());
        return "Products.form";
    }

    @PostMapping(params = "delete", path = "/products")
    public String deleteCategory(Model model,@ModelAttribute OrderIdDTO product_id) {
        productService.delete(product_id.getOrder_id());
        return "redirect:/products";
    }

    @GetMapping("/product/{id}")
    public String ProductId (Model model, @PathVariable("id") Long id){
        //Get index from product list with this id
        Product product = null;
        for (Product p: this.productService.findAll()
        ) {
            if(p.getId().equals(id)){
                product = p;
                break;
            }
        }
        model.addAttribute("product",product);
        if(!model.containsAttribute("orderForm")) {
            model.addAttribute("orderForm", new OrderDTO());
        }
        return "Product.idform";
    }


    @GetMapping("/products/add")
    public String addProduct(Model model){
        model.addAttribute(authorService.findAll());
        model.addAttribute(categoryService.findAll());

        if(!model.containsAttribute("product")) {
            model.addAttribute("product",new Product());
        }
        return "Products.add";
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product,
                             BindingResult bindingResult,
                             Model model, RedirectAttributes attr){
        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            attr.addFlashAttribute("product",product);
            return  "redirect:/products/add";
        }
        for (Author m: this.authorService.findAll()
        ) {
            if(m.getId().equals(product.getAuthor().getId())){
                product.setAuthor(m);
                break;
            }
        }
        for (Category c: this.categoryService.findAll()
        ) {
            if(c.getId().equals(product.getCategory().getId())){
                product.setCategory(c);
                break;
            }
        }
        productService.save(product);
        return "redirect:/products/";
    }


}
