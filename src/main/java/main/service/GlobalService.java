/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.CategoryDTO;
import main.dto.OrderProductByCategoryDTO;
import main.dto.OrdersQtyDTO;
import main.dto.ProductDTO;
import main.dto.ReviewsResponseDTO;
import main.dto.UserIdNameDTO;
import main.dto.UserRegistrationResponseDTO;
import main.models.Order;
import main.models.Product;
import main.models.Reviews;
import main.models.User;
import main.models.UserPayment;
import main.repo.CategoryRepo;
import main.repo.OrderRepo;
import main.repo.ProductRepo;
import main.repo.ReviewsRepo;
import main.repo.UserPaymentRepo;
import main.repo.UserRepo;
import main.util.PaymentProvider;
import main.util.PaymentType;
import main.util.mapper.CategoryMapper;
import main.util.mapper.ProductMapper;
import main.util.mapper.ReviewsMapper;
import main.util.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
public class GlobalService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final UserPaymentRepo paymentRepo;
    private final ReviewsRepo rrepo;
    private final CategoryRepo crepo;
    private final UserRepo urepo;
    private final ProductMapper mapper;
    private final UserMapper umapper;
    private final ReviewsMapper rmapper;
    private final CategoryMapper cmapper;
    private final EntityManager em;
    
    //Retrieve all products in a specific category
    public List<ProductDTO> findProductsByCategoryName(String name){
        return productRepo.findByCategoryName(name).stream().map(mapper::toDTO).toList();
    }
    
    
    //List all active discounts and their associated products
    public List<ProductDTO> findProductsByActiveDiscount(){
        return productRepo.findByDiscountActive(Boolean.TRUE).stream().map(mapper::toDTO).toList();
    }
    
    //Calculate the total quantity of items in a user's cart
    public Integer findTotalQtyByUser(Integer userId){
        var query = "SELECT COALESCE(SUM(c.quantity), 0) "
                + "FROM cart_item c JOIN shopping_session s "
                + "ON c.session_id=s.id "
                + "WHERE s.user_id= :x "
                + "GROUP BY s.user_id";
        return (Integer) em.createNativeQuery(query).setParameter("x", userId).getSingleResult();
    }
    
    //Get all orders placed by a user with their total amounts
    public List<OrdersQtyDTO> findOrdersAmountsByUser(Integer id){
        var list = orderRepo.findByUserId(id);
        var map = list.stream().collect(Collectors.toMap(Order::getId, Order::getTotal));
        return map.keySet().stream().map(x -> new OrdersQtyDTO(x,map.get(x))).toList();
    }
    
    //Find all products with reviews above a certain rating
    public List<ProductDTO> findProductsByRatingAbove(Integer rating){
        var query = "SELECT DISTINCT p.* "
                + "FROM product p JOIN reviews r "
                + "ON p.id = r.product_id "
                + "WHERE r.rating>= :x ";
        var result =(List<Product>) em.createNativeQuery(query,Product.class).setParameter("x", rating).getResultList();
        return result.stream().map(mapper::toDTO).toList();
    }
    
    //Retrieve all users who have made at least one order
    public List<UserRegistrationResponseDTO> findUsersWhoMadeAtLeastOneOne(){
        var query = "SELECT DISTINCT u.* "
                + "FROM users u JOIN orders o "
                + "ON u.id = o.user_id";
        var result = (List<User>) em.createNativeQuery(query, User.class).getResultList();
        return result.stream().map(umapper::toDTO).toList();
    }
    
    //List all products that are out of stock
    public List<ProductDTO> findOutOfStockProducts(){
        return productRepo.findAll()
                .stream()
                .filter(x -> x.getInventory().getQuantity()==0)
                .map(mapper::toDTO)
                .toList();
    }
    
    //Find all orders that include products from a specific category
    public List<OrderProductByCategoryDTO> findOrdersWhoseProductsOfCategory(String categoryName){
        var query = "SELECT o.order_id, p.id, p.product_name "
                + "FROM order_item o JOIN product p "
                + "ON o.product_id = p.id "
                + "JOIN category c ON c.id = p.category_id "
                + "WHERE c.category_name= :x";
        List<Object[]> result = em.createNativeQuery(query).setParameter("x", categoryName).getResultList();
        return result.stream().map(x -> new OrderProductByCategoryDTO((Integer)x[0],(Integer) x[1],(String) x[2])).toList();
    }
    
    //List all users who have used a specific payment provider
    public List<UserIdNameDTO> findUsersWhoUsedProvider(PaymentProvider p){
        var result = paymentRepo.findAll().stream().filter(x-> x.getPaymentProvider()==p).map(d-> d.getUser()).toList();
        return result.stream().map(n-> new UserIdNameDTO(n.getId(),n.getUsername())).toList();
    }
    
    //Retrieve all reviews written by a user for products in a specific category
    public List<ReviewsResponseDTO> findReviews(String categoryName){
        var query = "SELECT r.* "
                + "FROM users u JOIN reviews r ON u.id = r.user_id "
                + "JOIN product p ON p.id = r.product-id "
                + "JOIN category c ON p.category_id = c.id "
                + "WHERE c.category_name= :x";
        var result =(List<Reviews>) em.createNativeQuery(query, Reviews.class).getResultList();
        return result.stream().map(rmapper::toDTO).toList();
    }
    
    //Find the average rating for a product
    public Double findAvgRatingForProduct(String name){
        return rrepo.findByProductName(name)
                .stream()
                .mapToInt(x-> x.getRating())
                .average()
                .getAsDouble();
    }
    
    //List all categories with more than a specified number of products
    public List<CategoryDTO> findCategoriesWithProductCountMore(Integer count){
        return crepo.findAll()
                .stream()
                .filter(c->c.getProducts().size()>=count)
                .map(cmapper::toDTO)
                .toList();
    }
    
    //Retrieve all expired payment methods for users
    public List<PaymentType> findExpiredPaymentType(){
        return paymentRepo.findAll()
                .stream()
                .filter(x->x.getExpiryDate().isAfter(LocalDate.now()))
                .map(UserPayment::getPaymentType).toList();
    }
    
    //Get all users who haven't placed any orders
    public List<UserRegistrationResponseDTO> findUsersWithNoOrders(){
        return urepo.findAll()
                .stream()
                .filter(x->x.getOrders().isEmpty())
                .map(umapper::toDTO)
                .toList();
    }
}
