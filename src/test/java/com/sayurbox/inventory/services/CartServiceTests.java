package com.sayurbox.inventory.services;

import com.sayurbox.dtos.ProductQuantity;
import com.sayurbox.entities.Product;
import com.sayurbox.entities.Unit;
import com.sayurbox.exceptions.NotEnoughQuantityException;
import com.sayurbox.repositories.CartProductRepository;
import com.sayurbox.services.CartService;
import com.sayurbox.services.InventoryService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class CartServiceTests {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartProductRepository cartProductRepository;

    @Mock
    private InventoryService inventoryService;

    private List<Product> products;
    private List<Integer> productIds;
    private Unit unit;


    //Run before with each test
    @Before
    public void init() {

        products = new ArrayList<>();
        products.add(new Product(1, unit, "Mango", 50, 100, 1 ));
        products.add(new Product(2, unit, "Papaya", 100, 150, 1 ));

        productIds = Arrays.asList(1,2);
        unit = new Unit(1,"kilogram","kg");
    }

    @Test
    public void testAddToCartSuccess() {
        List<ProductQuantity> requiredQuantities = new ArrayList<>();
        requiredQuantities.add(new ProductQuantity(1,5));
        requiredQuantities.add(new ProductQuantity(2,10));

        Mockito.when(inventoryService.getProductsByIds(productIds)).thenReturn(products);
        //Mockito.when(cartProductRepository.findByUserId(123)).thenReturn(Collections.emptyList());

        cartService.addToCart(123, requiredQuantities);

        Mockito.verify(cartProductRepository).saveAll(Mockito.anyList());
    }

    @Test(expected = NotEnoughQuantityException.class)
    public void testAddToCartWithNotEnoughQuantity() {
        List<ProductQuantity> requiredQuantities = new ArrayList<>();
        requiredQuantities.add(new ProductQuantity(1,5));
        requiredQuantities.add(new ProductQuantity(2,101));

        Mockito.when(inventoryService.getProductsByIds(productIds)).thenReturn(products);

        cartService.addToCart(123, requiredQuantities);
    }

}
