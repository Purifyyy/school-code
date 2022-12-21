package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.productincart.ProductInCart;
import sk.stuba.fei.uim.oop.assignment3.exceptions.WrongRequestException;
import sk.stuba.fei.uim.oop.assignment3.exceptions.ResourceNotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.IProductService;

@Service
public class CartService implements ICartService {

    private CartRepository repository;

    private IProductService productService;

    @Autowired
    public CartService(CartRepository repository, IProductService service) {
        this.repository = repository;
        this.productService = service;
    }

    @Override
    public Cart create() {
        return this.repository.save(new Cart());
    }

    @Override
    public Cart getById(Long id) {
        if (this.repository.findById(id).isPresent()) {
            return this.repository.findById(id).get();
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public void delete(Long id) {
        if (this.repository.findById(id).isPresent()) {
            this.repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public Cart addProduct(Long id, CartAddRequest request) {
        if (this.repository.findById(id).isPresent()) {
            Cart currCart = this.repository.findById(id).get();
            if(!currCart.isPayed() && this.productService.getById(request.getProductId()).getAmount() >= request.getAmount()) {
                for (ProductInCart p : currCart.getShoppingList()) {
                    if (p.getProductId().equals(request.getProductId())) {
                        p.setAmount(p.getAmount() + request.getAmount());
                        this.productService.getById(request.getProductId()).setAmount(this.productService.getById(request.getProductId()).getAmount() - request.getAmount());
                        return currCart;
                    }
                }
                currCart.getShoppingList().add(new ProductInCart(request));
                this.productService.getById(request.getProductId()).setAmount(this.productService.getById(request.getProductId()).getAmount() - request.getAmount());
                return currCart;
            }
            throw new WrongRequestException();
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public String pay(Long id) {
        if(this.repository.findById(id).isPresent()) {
            if(this.repository.findById(id).get().isPayed()) {
                throw new WrongRequestException();
            }
            double sum = 0;
            this.repository.findById(id).get().setPayed(true);
            for(ProductInCart p : this.repository.findById(id).get().getShoppingList()) {
                sum += this.productService.getById(p.getProductId()).getPrice()*p.getAmount();
            }
            return String.valueOf(sum);
        }
        throw new ResourceNotFoundException();
    }

}
