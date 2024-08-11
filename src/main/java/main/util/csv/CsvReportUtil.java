/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.dto.OrderProductByCategoryDTO;
import main.dto.OrdersQtyDTO;
import main.dto.ProductDiscountDTO;
import main.dto.ProductReportResponseDTO;
import main.dto.ReviewsResponseDTO;
import main.dto.UserIdNameDTO;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class CsvReportUtil {
    public void writeReviewsToCsv(List<ReviewsResponseDTO> reviews, String filepath){
        try(FileWriter writer = new FileWriter(filepath)){
            writer.append("ReviewId,ReviewerName,ProductName,Rating,Title,Content\n");
            for(var review:reviews){
                writer.append(review.id().toString())
                        .append(',')
                        .append(review.username())
                        .append(',')
                        .append(review.productName())
                        .append(',')
                        .append(review.rating().toString())
                        .append(',')
                        .append(review.title())
                        .append(',')
                        .append(review.content())
                        .append('\n');                                             
            }
        }catch(IOException e){
        }
    }
    
    public void writeUserIdNameToCsv(List<UserIdNameDTO> users, String filepath){
        try(FileWriter writer = new FileWriter(filepath)){
            writer.append("Id,Username\n");
            for(var u:users){
                writer.append(u.userId().toString())
                .append(',')
                .append(u.userName())
                .append('\n');
            }
        } catch (IOException ex) {
            Logger.getLogger(CsvReportUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeOrderProductToCsv(List<OrderProductByCategoryDTO> list, String filepath){
        try(FileWriter writer = new FileWriter(filepath)){
            writer.append("orderId,productId,productName\n");
            for(var x:list){
                writer
                        .append(x.orderId().toString())
                        .append(',')
                        .append(x.productId().toString())
                        .append(',')
                        .append(x.productName())
                        .append('\n');

            }
        } catch (IOException ex) {
            Logger.getLogger(CsvReportUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeOrderQtyToCsv(List<OrdersQtyDTO> list, String filepath){
        try(FileWriter writer = new FileWriter(filepath)){
            writer.append("orderId,TotalQty\n");
            for(var x:list){
                writer
                        .append(x.orderId().toString())
                        .append(',')
                        .append(x.total().toString())
                        .append('\n');
            }
        } catch (IOException ex) {
            Logger.getLogger(CsvReportUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeProductToCsv(List<ProductReportResponseDTO> list,String filepath){
        try(FileWriter writer = new FileWriter(filepath)){
            writer.append("productId,productName\n");
            for(var x:list){
                writer
                        .append(x.productId().toString())
                        .append(',')
                        .append(x.productName())
                        .append('\n');
            }
        } catch (IOException ex) {
            Logger.getLogger(CsvReportUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeProductDiscountToCsv(List<ProductDiscountDTO> list,String filepath){
        try(FileWriter writer = new FileWriter(filepath)){
            writer.append("DiscountId,DiscountName,Percent,ProductName\n");
            for(var x:list){
                writer.append(x.discountId().toString())
                        .append(',')
                        .append(x.discountName())
                        .append(',')
                        .append(x.percent().toString())
                        .append(',')
                        .append(x.productName())
                        .append('\n');                                             
            }
        }catch(IOException e){
        }
    }
}
