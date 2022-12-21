package sk.stuba.fei.uim.oop.assignment3.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.IntResponse;
import sk.stuba.fei.uim.oop.assignment3.exceptions.ResourceNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService service;

    @GetMapping()
    public List<ProductResponse> getAllProducts() {
        return this.service.getAll().stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @PostMapping()
    public ProductResponse createProduct(@RequestBody ProductRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        return new ProductResponse(this.service.create(request));
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return new ProductResponse(this.service.getById(id));
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest request) throws ResourceNotFoundException {
        return new ProductResponse(this.service.update(id,request));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) throws ResourceNotFoundException {
        this.service.delete(id);
    }

        @RequestMapping(value = "/{id}/amount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public IntResponse productAmount(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return this.service.getAmount(id);
    }

    @RequestMapping(value = "/{id}/amount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IntResponse incrementAmount(@PathVariable("id") Long id, @RequestBody ProductRequest request) throws ResourceNotFoundException {
        return this.service.getIncreasedAmount(id,request.getAmount());
    }

}
