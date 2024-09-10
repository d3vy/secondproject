package com.devy.manager.RestServer;

import com.devy.manager.controllers.payload.NewProductPayload;
import com.devy.manager.controllers.payload.UpdateProductPayload;
import com.devy.manager.models.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class ProductsRestServerImpl implements ProductsRestServer {

    private final RestClient restClient;
    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE
            = new ParameterizedTypeReference<>() {
    };

    @Override
    public List<Product> finaAllProducts(String filter) {
        return this.restClient
                .get()
                .uri("/shop-api/products?filter={filter}", filter)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Product createProduct(String title, String details) {
        try {
            return this.restClient
                    .post()
                    .uri("/shop-api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewProductPayload(title, details))
                    .retrieve()
                    .body(Product.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            log.error("Ошибка при создании продукта");
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            assert problemDetail != null;
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Product> findProduct(Integer id) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/shop-api/products/{id}", id)
                    .retrieve()
                    .body(Product.class));
        } catch (HttpClientErrorException.NotFound exception) {
            log.error("Продукт с id: {} не найден в методе find", id);
            return Optional.empty();
        }
    }

    @Override
    public void deleteProduct(Integer id) {
        try {
            this.restClient
                    .delete()
                    .uri("/shop-api/products/{productId}", id)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            log.error("Продукт с id: {} не найден в методе delete", id);
            throw new NoSuchElementException(exception);
        }
    }

    @Override
    public void updateProduct(Integer id, String title, String details) {
        try {
            this.restClient
                    .patch()
                    .uri("/shop-api/products/{ProductId}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateProductPayload(title, details))
                    .retrieve()
                    .toBodilessEntity();
        }catch (HttpClientErrorException.BadRequest exception){
            log.error("Ошибка при обновлении продукта");
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }
}
