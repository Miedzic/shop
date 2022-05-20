package com.miedzic.shop.service.impl;

import com.miedzic.shop.config.properties.FilePropertiesConfig;
import com.miedzic.shop.domain.dao.Product;
import com.miedzic.shop.helper.FileHelper;
import com.miedzic.shop.repository.ProductRepository;
import com.miedzic.shop.service.ProductService;
import com.miedzic.shop.validator.ExtensionValid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FilePropertiesConfig filePropertiesConfig;
    private final FileHelper fileHelper;

    @SneakyThrows
    @Override
    public Product save(final Product product, MultipartFile file) {
        //jeśli jest id to robi selecta sprawdzającego czy obiekt w bazie istnieje po tym id, update/insert
        productRepository.save(product);
        //  String[] split = file.getOriginalFilename().split("\\.");

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        //  Path path = Paths.get(filePropertiesConfig.getProduct(), product.getName() + "." + split[split.length - 1]);
        Path path = Paths.get(filePropertiesConfig.getProduct(), product.getName() + "." + extension);
        //Files.copy(file.getInputStream(), path);
        fileHelper.fileCopy(file.getInputStream(), path);
        product.setPath(path.toString());
        return productRepository.save(product);
    }

    @Override
    @Transactional
    // dirty update, jeśli obiekt z bazy danych zostanie zaaktualizowany w jakiś sposób to po wykonaniu funkcji zostanie zaaktualizowany w bazie danych
    public Product update(final Product product, final Long id, MultipartFile file) {
        var productDb = getById(id);
        productDb.setName(product.getName());
        productDb.setCategory(product.getCategory());
        productDb.setCost(product.getCost());
        String oldPath = productDb.getPath();
        // String[] split = file.getOriginalFilename().split("\\.");
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        Path path = Paths.get(filePropertiesConfig.getProduct(), productDb.getName() + "." + extension);
        try {
            //Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            fileHelper.fileCopy(file.getInputStream(),path);
            productDb.setPath(path.toString());
            if (!oldPath.equals(path.toString())) {
                fileHelper.fileDelete(Paths.get(oldPath));
            }
        } catch (IOException e) {
            log.error("file could not be updated", e);
        }
        return productDb;
    }

    @Override
    public Product getById(final Long id) {
        log.info("Product not in Cache {}", id);
        return productRepository.getById(id);
    }

    @Override
    public void deleteById(final Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getPage(final Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
