package com.springboot.ECommerce;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


import com.springboot.ECommerce.domain.CouponType;
import com.springboot.ECommerce.domain.OrderStatus;
import com.springboot.ECommerce.dto.CustomerOrderResponse;
import com.springboot.ECommerce.dto.SellerOrderResponse;
import com.springboot.ECommerce.dto.WeeklyTopProductResponse;
import com.springboot.ECommerce.model.Address;
import com.springboot.ECommerce.model.Cart;
import com.springboot.ECommerce.model.CartItem;
import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.model.OrderItem;
import com.springboot.ECommerce.model.Orders;
import com.springboot.ECommerce.model.Product;
import com.springboot.ECommerce.repository.AddressRepository;
import com.springboot.ECommerce.repository.CartItemRepository;
import com.springboot.ECommerce.repository.CartRepository;
import com.springboot.ECommerce.repository.CustomerRepository;
import com.springboot.ECommerce.repository.OrderItemRepository;
import com.springboot.ECommerce.repository.OrderRepository;
import com.springboot.ECommerce.repository.ProductRepository;
import com.springboot.ECommerce.service.OrderService;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlaceOrder_Success() {
        Customer customer = new Customer();
        customer.setId(1);
        Cart cart = new Cart();
        cart.setId(1);
        Product product = new Product();
        product.setId(1);
        product.setSellingPrice(100);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        List<CartItem> cartItems = List.of(cartItem);

        Address address = new Address();

        when(customerRepository.getCustomerByUsername("user")).thenReturn(customer);
        when(cartRepository.findByCustomerId(1)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findAllByCartId(1)).thenReturn(cartItems);
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        when(orderRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Orders order = orderService.placeOrder("user", 1, CouponType.FESTIVAL_DISCOUNT10);

        assertNotNull(order);
        assertEquals(OrderStatus.PLACED, order.getStatus());
        verify(cartItemRepository).deleteByCartId(1);
    }

    @Test
    public void testGetOrdersByCustomer_ThrowsIfCustomerNotFound() {
        when(customerRepository.getCustomerByUsername("unknown")).thenReturn(null);
        assertThrows(RuntimeException.class, () -> orderService.getOrdersByCustomer("unknown", 0, 10));
    }

   

    @Test
    public void testGetTopSellingProductsThisWeek() {
        Product product = new Product();
        product.setTitle("Product1");
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(5);

        when(orderItemRepository.findOrderItemsBySellerUsername("seller")).thenReturn(List.of(item));

        List<WeeklyTopProductResponse> result = orderService.getTopSellingProductsThisWeek("seller");
        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getProductTitle());
        assertEquals(5, result.get(0).getTotalQuantitySold());
    }

    @Test
    public void testUpdateOrderItemStatus_AllMatchUpdatesParent() {
        Orders order = new Orders();
        order.setId(1);
        OrderItem item = new OrderItem();
        item.setId(1);
        item.setOrder(order);

        when(orderItemRepository.findById(1)).thenReturn(Optional.of(item));
        when(orderItemRepository.findByOrderId(1)).thenReturn(List.of(item));

        orderService.updateOrderItemStatus(1, OrderStatus.SHIPPED);
        verify(orderRepository).save(any());
    }

    @Test
    public void testDeleteOrder() {
        Orders order = new Orders();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        orderService.deleteOrder(1);

        verify(orderItemRepository).deleteByOrderId(1);
        verify(orderRepository).delete(order);
    }
}
