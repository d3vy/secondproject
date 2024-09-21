package com.devy.customer.controller;

import com.devy.customer.client.FavoriteProductsClient;
import com.devy.customer.client.ProductReviewsClient;
import com.devy.customer.client.ProductsClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    //Создание Mock объектов для того, чтобы не делать этого вручную.
    @Mock
    ProductsClient productsClient;

    @Mock
    FavoriteProductsClient favoriteProductsClient;

    @Mock
    ProductReviewsClient productReviewsClient;

    //Создает экземпляр класса, передавая в него Mock объекты.
    @InjectMocks
    ProductController productController;

    //Создание тестового метода.
    //Название теста: НазваниеТестируемогоМетода_УсловияТестирования_ОжидаемыйРезультат
    @Test
    @DisplayName("Исключение NoSuchElementException должно быть транслировано  в страницу errors/404")
    void handleNoSuchElementException_ReturnsErrors404() {
        // given
        var exception = new NoSuchElementException("Product not found");
        var model = new ConcurrentModel();

        //  when
        var result = this.productController.handleNoSuchElementException(exception, model);

        //  then
        assertEquals("errors/404", result);
        assertEquals("Product not found", model.getAttribute("error"));
    }
}