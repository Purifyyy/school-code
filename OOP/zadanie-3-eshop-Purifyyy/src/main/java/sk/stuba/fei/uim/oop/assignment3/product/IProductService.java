package sk.stuba.fei.uim.oop.assignment3.product;

import sk.stuba.fei.uim.oop.assignment3.IntResponse;
import java.util.List;

public interface IProductService {
    List<Product> getAll();
    Product create(ProductRequest request);
    Product getById(Long id);
    Product update(Long id, ProductRequest response);
    void delete(Long id);
    IntResponse getAmount(Long id);
    IntResponse getIncreasedAmount(Long id, int amount);
}
