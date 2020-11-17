package mk.ukim.finki.emt.lab.service;

import mk.ukim.finki.emt.lab.model.Product;
import mk.ukim.finki.emt.lab.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {productRepository.deleteById(id);}

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategory_id(categoryId);
    }

    public List<Product> findByCategoryAndAuthor(Long categoryId, Long authorId) {
        return productRepository.findByCategory_idAndAuthor_Id(categoryId, authorId);
    }

    public Page<Product> findPaginated(Pageable pageable){
        int pageSize = 4;
        int currentPage = pageable.getPageNumber();
        int startItemIndex = currentPage * pageSize;
        List<Product> productList = this.findAll();
        List<Product> pageProductList;

        if(productList.size() < startItemIndex){
            pageProductList = Collections.emptyList();
        }
        else{
            int toIndex = Math.min(startItemIndex + pageSize, productList.size());
            pageProductList = productList.subList(startItemIndex,toIndex);
        }
        Page<Product> productPage = new PageImpl<Product>(pageProductList,
                PageRequest.of(currentPage,pageSize),productList.size());

        return productPage;
    }
}
