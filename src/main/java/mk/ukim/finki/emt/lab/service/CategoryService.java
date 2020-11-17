package mk.ukim.finki.emt.lab.service;

import mk.ukim.finki.emt.lab.model.Category;
import mk.ukim.finki.emt.lab.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(Long id){categoryRepository.deleteById(id);}

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
