package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.dtos.ProductDto;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;
    public Page<ProductDto> findAll(int pageNo, int pageSize
//            , String sortBy, String sortDirection
    ){
//        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDto = new ArrayList<>();
        for(Product p: products){
            ProductDto dto = new ProductDto();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setManufacturer(p.getManufacturer());
            dto.setDescription(p.getDescription());
            dto.setUnit(p.getUnit());
            dto.setPrice(productPriceRepository.findByProductOrderByPrice_date_time(p.getId()).get(0).getPrice());
            productDto.add(dto);
        }
        int toIndex = pageSize*(pageNo+1);
        if(toIndex > productDto.size())
            toIndex = productDto.size();
//        return productRepository.findAll(pageable);
        return new PageImpl<>(productDto.subList(pageSize*pageNo, toIndex), pageable, productDto.size());
    }

    public Page<Product> findAllProduct(int pageNo, int pageSize, String sortBy, String sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return productRepository.findAll(pageable);
    }
}
