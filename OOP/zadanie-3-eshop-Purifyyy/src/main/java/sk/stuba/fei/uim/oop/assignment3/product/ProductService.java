package sk.stuba.fei.uim.oop.assignment3.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.IntResponse;
import sk.stuba.fei.uim.oop.assignment3.exceptions.ResourceNotFoundException;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Product create(ProductRequest request) {
        Product newProduct = new Product();
        newProduct.setName(request.getName());
        newProduct.setDescription(request.getDescription());
        newProduct.setAmount(request.getAmount());
        newProduct.setUnit(request.getUnit());
        newProduct.setPrice(request.getPrice());
        return this.repository.save(newProduct);
    }

    @Override
    public Product getById(Long id) {
        if(this.repository.findById(id).isPresent()){
            return this.repository.findById(id).get();
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public Product update(Long id, ProductRequest request) {
        Product existingProduct = getById(id);
        if(request.getName() != null) {
            existingProduct.setName(request.getName());
        }
        if(request.getDescription() != null) {
            existingProduct.setDescription(request.getDescription());
        }
        return this.repository.save(existingProduct);
    }

    @Override
    public void delete(Long id) {
        if(this.repository.findById(id).isPresent()){
            this.repository.deleteById(id);
        }
        else { throw new ResourceNotFoundException(); }
    }

    @Override
    public IntResponse getAmount(Long id) {
        if(this.repository.findById(id).isPresent()){
            return new IntResponse(this.repository.findById(id).get().getAmount());
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public IntResponse getIncreasedAmount(Long id, int amount) {
        if(this.repository.findById(id).isPresent()){
            Product existingProduct = getById(id);
            existingProduct.setAmount(existingProduct.getAmount()+amount);
            this.repository.save(existingProduct);
            return new IntResponse(this.repository.findById(id).get().getAmount());
        }
        throw new ResourceNotFoundException();
    }

}