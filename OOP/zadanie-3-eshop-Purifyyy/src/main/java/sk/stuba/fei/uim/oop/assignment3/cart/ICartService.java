package sk.stuba.fei.uim.oop.assignment3.cart;

public interface ICartService {
    Cart create();
    Cart getById(Long id);
    void delete(Long id);
    Cart addProduct(Long id, CartAddRequest request);
    String pay(Long id);
}
